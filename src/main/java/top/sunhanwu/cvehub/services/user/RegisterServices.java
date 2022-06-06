package top.sunhanwu.cvehub.services.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.sunhanwu.cvehub.dao.AccountInfoMapper;
import top.sunhanwu.cvehub.model.AccountInfo;

@Component
public class RegisterServices {

    @Autowired
    private AccountInfoMapper accountInfoMapper;

    public boolean createAccount(AccountInfo accountInfo){
        try{
            accountInfoMapper.insert(accountInfo);
        }
        catch (Exception e){
            return false;
        }
        return true;
    }
}
