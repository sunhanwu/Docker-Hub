package top.sunhanwu.cvehub.bean.requests;

public class RegisterRequests {

    private String username;

    private String passwordMd5;

    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordMd5() {
        return passwordMd5;
    }

    public void setPasswordMd5(String passwordMd5) {
        this.passwordMd5 = passwordMd5;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "RegisterRequests{" +
                "username='" + username + '\'' +
                ", passwordMd5='" + passwordMd5 + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
