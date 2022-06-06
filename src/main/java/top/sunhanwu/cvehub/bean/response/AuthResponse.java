package top.sunhanwu.cvehub.bean.response;

public class AuthResponse {
    private String token;

    private String msg;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "AuthResponse{" +
                "token='" + token + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
