package top.sunhanwu.cvehub.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import top.sunhanwu.cvehub.dao.AccountInfoMapper;
import top.sunhanwu.cvehub.model.AccountInfo;
import top.sunhanwu.cvehub.services.AuthServices;
import top.sunhanwu.cvehub.utils.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private AuthServices authServices;

    @Autowired
    private AccountInfoMapper accountInfoMapper;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        String token = request.getHeader("token");
        if (token != null) {
            String username = JwtUtil.getUserNameByToken(request);
            AccountInfo accountInfo = accountInfoMapper.selectByPrimaryKey(username);
            String password = accountInfo.getPassword();
            boolean verifyResult = JwtUtil.verify(token, username, password);
            if(verifyResult){
                return true;
            }
        }

        return false;
    }
}
