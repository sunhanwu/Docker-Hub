package top.sunhanwu.cvehub.services.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.sunhanwu.cvehub.bean.requests.ResetPasswordRequests;
import top.sunhanwu.cvehub.bean.response.FoundPasswordResponse;
import top.sunhanwu.cvehub.bean.response.RetrieveResponse;
import top.sunhanwu.cvehub.dao.AccountInfoMapper;
import top.sunhanwu.cvehub.dao.RetrieveValidMapper;
import top.sunhanwu.cvehub.model.AccountInfo;
import top.sunhanwu.cvehub.model.RetrieveValid;
import top.sunhanwu.cvehub.utils.JwtUtil;

@Component
public class ResetPasswordService {

    @Autowired
    private AccountInfoMapper accountInfoMapper;

    @Autowired
    private RetrieveValidMapper retrieveValidMapper;

    @Autowired
    private RetrieveServices retrieveServices;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public boolean resetPassword(String token, ResetPasswordRequests resetPasswordRequests){
        RetrieveValid retrieveValid = null;
        AccountInfo accountInfo = null;
        /**
         * check retrieveValid record
         */
        try {
            retrieveValid =  retrieveValidMapper.selectByPrimaryKey(resetPasswordRequests.getUsername());
            if(retrieveValid == null){
                logger.error("ResetPassword Service: retrieveValidMapper Error, No Record in database");
                return false;
            }
        }
        catch (Exception e){
            logger.error("ResetPassword Service Error, Error Massage: " + e.getMessage());
        }

        /**
         * check accountInfo record
         */
        try {
            accountInfo = accountInfoMapper.selectByPrimaryKey(retrieveValid.getUsername());
            if(accountInfo == null){
                logger.error("ResetPassword Service: accountInfoMapper Error, No Record in database");
                return false;
            }
        }
        catch (Exception e){
            logger.error("ResetPassword Service Error, Error Massage: " + e.getMessage());
        }

        /**
         * check token
         */
        if(!JwtUtil.verify(token, accountInfo.getUsername(), accountInfo.getPassword())){
            logger.error("ResetPassword Service Error, Token Invalid");
            return false;
        }

        /**
         * reset password
         */
        try {
            AccountInfo newAccountInfo = new AccountInfo();
            newAccountInfo.setEmail(accountInfo.getEmail());
            newAccountInfo.setPassword(resetPasswordRequests.getPassword());
            newAccountInfo.setUsername(accountInfo.getUsername());
            accountInfoMapper.updateByPrimaryKey(newAccountInfo);
            logger.warn("ResetPassword Service Warn: " + resetPasswordRequests.getUsername() + " reset password successfully");
        }
        catch (Exception e){
            logger.error("ResetPassword Service Error: reset password error, error massage: " + e.getMessage());
            return false;
        }
        return true;
    }

    public FoundPasswordResponse foundPassword(String username, String email){
        FoundPasswordResponse foundPasswordResponse = new FoundPasswordResponse();
        AccountInfo accountInfo = accountInfoMapper.selectByPrimaryKey(username);
        if(accountInfo == null){
            foundPasswordResponse.setCode(500);
            foundPasswordResponse.setMsg("用户不存在");
            return foundPasswordResponse;
        }
        if(!accountInfo.getEmail().equals(email)){
            foundPasswordResponse.setMsg("邮箱不匹配");
            foundPasswordResponse.setCode(500);
            return foundPasswordResponse;
        }
        else{
            RetrieveResponse retrieveResponse = null;
            try {
                retrieveResponse = retrieveServices.findPasswdByUsername(username);
            }
            catch (Exception e){
                foundPasswordResponse.setMsg("内部错误");
                foundPasswordResponse.setCode(500);
                return foundPasswordResponse;
            }
            if (retrieveResponse.getMsg().equals("success")){
                foundPasswordResponse.setCode(200);
                foundPasswordResponse.setMsg("success");
                return foundPasswordResponse;
            }
            else{
                foundPasswordResponse.setCode(500);
                foundPasswordResponse.setMsg(retrieveResponse.getMsg());
                return foundPasswordResponse;
            }
        }
    }
}
