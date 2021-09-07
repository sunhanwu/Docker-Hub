package top.sunhanwu.cvehub.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.sunhanwu.cvehub.bean.requests.AuthRequests;
import top.sunhanwu.cvehub.bean.response.AuthResponse;
import top.sunhanwu.cvehub.services.AuthServices;

@RestController
public class UserController {

    @Autowired
    private AuthServices authServices;

    /**
     *  auth by username and password
     * @param authRequests request body
     * @return token
     */
    @GetMapping("/auth")
    public AuthResponse auth(@RequestBody AuthRequests authRequests) {
        AuthResponse authResponse = new AuthResponse();
        String token = authServices.authByUsernameAndPasswd(authRequests);
        if (token == null)
        {
            authResponse.setToken("");
            authResponse.setMsg("auth failed");
            return authResponse;
        }
        else
        {
            authResponse.setToken(token);
            authResponse.setMsg("success");
            return authResponse;
        }
    }

    @GetMapping("/test")
    public String test()
    {
        return "test scuess";
    }
}
