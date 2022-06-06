package top.sunhanwu.cvehub.interceptor;

import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import top.sunhanwu.cvehub.bean.response.AuthInterceptorResponse;
import top.sunhanwu.cvehub.dao.AccountInfoMapper;
import top.sunhanwu.cvehub.model.AccountInfo;
import top.sunhanwu.cvehub.utils.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private AccountInfoMapper accountInfoMapper;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        String token = request.getHeader("token");
        AuthInterceptorResponse authInterceptorResponse = new AuthInterceptorResponse(); authInterceptorResponse.setCode(501);
        PrintWriter out = null;
        if (token != null) {
            String username = "";
            try{
                username = JwtUtil.getUserNameByToken(request);
            }
            catch (Exception e)
            {
                authInterceptorResponse.setMsg("token格式错误");
                JSONObject jsonObject = JSONObject.fromObject(authInterceptorResponse);
                String jsonstr = jsonObject.toString();
                out = response.getWriter();
                out.append(jsonstr);
                return false;
            }
            AccountInfo accountInfo = accountInfoMapper.selectByPrimaryKey(username);
            String password = accountInfo.getPassword();
            boolean verifyResult = JwtUtil.verify(token, username, password);
            if(verifyResult){
                return true;
            }
        }

        authInterceptorResponse.setCode(502);
        authInterceptorResponse.setMsg("no token");
        JSONObject jsonObject = JSONObject.fromObject(authInterceptorResponse);
        String jsonstr = jsonObject.toString();
        out = response.getWriter();
        out.append(jsonstr);
        return false;
    }
}
