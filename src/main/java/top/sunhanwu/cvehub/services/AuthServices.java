package top.sunhanwu.cvehub.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.sunhanwu.cvehub.bean.requests.AuthRequests;
import top.sunhanwu.cvehub.dao.AccountInfoMapper;
import top.sunhanwu.cvehub.model.AccountInfo;
import top.sunhanwu.cvehub.utils.JwtUtil;

@Component
public class AuthServices {

    @Autowired
    private AccountInfoMapper accountInfoMapper;

    public String authByUsernameAndPasswd(AuthRequests authRequests)
    {
        if (checkUsernameAndPasswd(authRequests.getUsername(), authRequests.getPassword()))
        {
            String token = JwtUtil.sign(authRequests.getUsername(), authRequests.getPassword());
            return token;
        }
        else
            return null;
    }

    private boolean checkUsernameAndPasswd(String username, String password)
    {
        AccountInfo accountInfo = accountInfoMapper.selectByPrimaryKey(username);
        if (accountInfo == null)
            return false;
        if (accountInfo.getPassword().equals(password))
            return true;
        else
            return false;
    }
}