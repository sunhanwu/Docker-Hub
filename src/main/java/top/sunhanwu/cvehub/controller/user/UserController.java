package top.sunhanwu.cvehub.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.sunhanwu.cvehub.bean.requests.AuthRequests;
import top.sunhanwu.cvehub.bean.requests.RegisterRequests;
import top.sunhanwu.cvehub.bean.response.AuthResponse;
import top.sunhanwu.cvehub.bean.response.RegisterResponse;
import top.sunhanwu.cvehub.model.AccountInfo;
import top.sunhanwu.cvehub.services.AuthServices;
import top.sunhanwu.cvehub.services.RegisterServices;

@RestController
public class UserController {

    @Autowired
    private AuthServices authServices;

    @Autowired
    private RegisterServices registerServices;
    /**
     *  auth by username and password
     * @param authRequests request body
     * @return AuthResponse response body
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

    @PostMapping("/register")
    public RegisterResponse register(@RequestBody RegisterRequests registerRequests) {
        RegisterResponse registerResponse = new RegisterResponse();
        try {
            if(registerRequests.getUsername().isEmpty() || registerRequests.getPasswordMd5().isEmpty()){
                registerResponse.setCode(400);
                registerResponse.setMsg("Bad Request, username or password can not be empty!");
                return registerResponse;
            }

            if(registerRequests.getPasswordMd5().length()!=32){
                registerResponse.setCode(400);
                registerResponse.setMsg("Bad Request, password must be md5 format!");
                return registerResponse;
            }

            AccountInfo accountInfo = new AccountInfo();
            accountInfo.setUsername(registerRequests.getUsername());
            accountInfo.setPassword(registerRequests.getPasswordMd5());
            accountInfo.setEmail(registerRequests.getEmail());
            if(registerServices.createAccount(accountInfo)){
                registerResponse.setCode(200);
                registerResponse.setMsg("success!");
                return registerResponse;
            }
            else {
                registerResponse.setCode(500);
                registerResponse.setMsg("Internal Server Error, create account failed!");
                return registerResponse;
            }
        }
        catch (Exception e){
            registerResponse.setCode(400);
            registerResponse.setMsg("Bad request! parameter error");
            return registerResponse;
        }
    }

    @GetMapping("/test")
    public String test()
    {
        return "test scuess";
    }
}
