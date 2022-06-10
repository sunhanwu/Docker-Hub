package top.sunhanwu.cvehub.controller.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import top.sunhanwu.cvehub.bean.requests.*;
import top.sunhanwu.cvehub.bean.response.*;
import top.sunhanwu.cvehub.model.AccountInfo;
import top.sunhanwu.cvehub.services.user.AuthServices;
import top.sunhanwu.cvehub.services.user.RegisterServices;
import top.sunhanwu.cvehub.services.user.ResetPasswordService;
import top.sunhanwu.cvehub.services.user.RetrieveServices;

@RestController
public class UserController {

    @Autowired
    private AuthServices authServices;

    @Autowired
    private RegisterServices registerServices;

    @Autowired
    private RetrieveServices retrieveServices;

    @Autowired
    private ResetPasswordService resetPasswordService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     *  auth by username and password
     * @param authRequests request body
     * @return AuthResponse response body
     */
    @PostMapping("/auth")
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
    @GetMapping("/isIn")
    public String isIn(@RequestParam("username") String username) {
        boolean isIn = authServices.isIn(username);
        if(isIn){
            return "true";
        }
        else{
            return "false";
        }
    }

    /**
     * 注册账号
     * @param registerRequests
     * @return
     */
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
                registerResponse.setMsg("success");
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

    @GetMapping("/retrieve")
    public RetrieveResponse retrieve(@RequestBody RetrieveRequests retrieveRequests){
        RetrieveResponse retrieveResponse;
        retrieveResponse = retrieveServices.findPasswdByUsername(retrieveRequests.getUsername());
        return retrieveResponse;
    }

    /**
     * 用于忘记密码修改密码
     * @param token
     * @param resetPasswordRequests
     * @return
     */
    @PostMapping("/resetPassword")
    public ResetPasswordResponse resetPassword(@RequestParam String token, @RequestBody ResetPasswordRequests resetPasswordRequests){
        ResetPasswordResponse resetPasswordResponse = new ResetPasswordResponse();
        if(token.equals("")){
            logger.error("Controller Get ResetPassword Error, No Token");
            resetPasswordResponse.setCode(400);
            resetPasswordResponse.setMsg("no token");
            return resetPasswordResponse;
        }
        boolean status = resetPasswordService.resetPassword(token, resetPasswordRequests);
        if(!status){
            logger.warn("Controller Warn: ResetPassword Service failed");
            resetPasswordResponse.setCode(500);
            resetPasswordResponse.setMsg("Internal Error");
            return resetPasswordResponse;
        }
        else{
            resetPasswordResponse.setCode(200);
            resetPasswordResponse.setMsg("success");
            return resetPasswordResponse;
        }
    }

    @PostMapping("/foundPassword")
    public FoundPasswordResponse foundPassword(@RequestBody FoundPasswordRequests foundPasswordRequests){
        FoundPasswordResponse foundPasswordResponse;
        foundPasswordResponse = resetPasswordService.foundPassword(foundPasswordRequests.getUsername(), foundPasswordRequests.getEmail());
        return foundPasswordResponse;
    }

//    @PostMapping("/resetPassword/{token}")
//    public String resetPassword(@PathVariable(name = "token") String token){
//        return "success";
//    }


    @GetMapping("/test")
    public String test()
    {
        return "test scuess";
    }
}
