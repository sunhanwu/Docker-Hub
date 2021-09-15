package top.sunhanwu.cvehub.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.sunhanwu.cvehub.bean.response.RetrieveResponse;
import top.sunhanwu.cvehub.bean.utils.MailBody;
import top.sunhanwu.cvehub.common.StaticValue;
import top.sunhanwu.cvehub.dao.AccountInfoMapper;
import top.sunhanwu.cvehub.dao.RetrieveValidMapper;
import top.sunhanwu.cvehub.model.AccountInfo;
import top.sunhanwu.cvehub.model.RetrieveValid;
import top.sunhanwu.cvehub.utils.JwtUtil;
import top.sunhanwu.cvehub.utils.SendMail;

import java.util.*;

@Component
public class RetrieveServices {

    @Autowired
    private AccountInfoMapper accountInfoMapper;

    @Autowired
    private RetrieveValidMapper retrieveValidMapper;

    @Autowired
    private SendMail sendMail;

    // Token过期时间5分钟
    public static final long EXPIRE_TIME = 5 * 60 * 1000;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public RetrieveResponse findPasswdByUsername(String username){

        /**
         * 通过email查询用户账户信息
         */
        RetrieveResponse retrieveResponse = new RetrieveResponse();
        AccountInfo userInfo = null;
        try {
            userInfo = accountInfoMapper.selectByPrimaryKey(username);
            if(userInfo == null){
                retrieveResponse.setCode(400);
                retrieveResponse.setMsg(username + " not found");
                logger.error(username + "not found");
                return retrieveResponse;
            }
        }
        catch (Exception e){
            retrieveResponse.setCode(500);
            retrieveResponse.setMsg("internal error");
            logger.error("accountInfoMapper selectByEmail error, e:"+e.getMessage());
            return retrieveResponse;
        }
        /**
         * 通过账户信息生成用于修改密码的token，并存储于RetrieveValid表中
         */
        String token = insertRetrieveValid(userInfo);
        if(token.equals("")){
            retrieveResponse.setCode(500);
            retrieveResponse.setMsg("Internal Error");
            logger.error(userInfo.getUsername() + "retrieve error");
            return retrieveResponse;
        }

        /**
         * 拼接邮件内容
         */
        MailBody mailBody = buildMailBody(userInfo.getEmail(), token);
        try {
            sendMail.sendMail(mailBody);
            retrieveResponse.setMsg("success");
            return retrieveResponse;
        }
        catch (Exception e){
            retrieveResponse.setMsg("failed");
            return retrieveResponse;
        }

    }

    private String insertRetrieveValid(AccountInfo accountInfo){
        RetrieveValid retrieveValid = null;
        /**
         * check 数据库中是否已经存在记录，如果存在检查是否达到当日请求次数上限
         */
        retrieveValid = retrieveValidMapper.selectByPrimaryKey(accountInfo.getUsername());
        String token = JwtUtil.sign(accountInfo.getUsername(), accountInfo.getPassword(), EXPIRE_TIME);
        if(retrieveValid!=null){
            /**
             * 如果超过5词
             */
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(retrieveValid.getCreateTime());
            calendar.add(calendar.DATE, 1);
            Date expireTIme = calendar.getTime();
            if(retrieveValid.getTimes() >= 5 && new Date().before(expireTIme)){
                logger.warn(accountInfo.getUsername()+" The number of requests has reached the limit");
                return "";
            }
            RetrieveValid retrieveValidNew = new RetrieveValid();
            retrieveValidNew.setUsername(accountInfo.getUsername());
            retrieveValidNew.setToken(token);
            if(new Date().after(expireTIme)){
                retrieveValidNew.setTimes(0);
                retrieveValidNew.setCreateTime(new Date());
            }
            else{
                retrieveValidNew.setCreateTime(retrieveValid.getCreateTime());
                retrieveValidNew.setTimes(retrieveValid.getTimes() + 1);
            }
            try {
                retrieveValidMapper.updateByPrimaryKey(retrieveValidNew);
            }
            catch (Exception e)
            {
                logger.error(accountInfo.getUsername()+" update retrieveValid error");
                return "";
            }
            return token;
        }
        else{
            retrieveValid = new RetrieveValid();
            retrieveValid.setUsername(accountInfo.getUsername());
            retrieveValid.setToken(token);
            retrieveValid.setCreateTime(new Date());
            retrieveValid.setTimes(1);
            try {
                retrieveValidMapper.insert(retrieveValid);
            }
            catch (Exception e){
                logger.error(accountInfo.getUsername()+" insert retrieveValid error");
                return "";
            }
            return token;
        }
    }

    private MailBody buildMailBody(String toEmail, String token){
        MailBody mailBody = new MailBody();
        mailBody.setFromAddr("1965190613@qq.com");
        mailBody.setToAddr(toEmail);
        mailBody.setSubject("cvehub密码修改申请");
        StringBuilder content = new StringBuilder();
        String url = StaticValue.PROTOCOL + StaticValue.HOST + "/resetPassword?token="+token;
        content.append("点击下面的链接重置密码:\n" + url);
        mailBody.setMsg(content.toString());
        return mailBody;
    }
}
