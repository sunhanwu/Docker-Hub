package top.sunhanwu.cvehub.bean.utils;

import org.springframework.beans.factory.annotation.Value;

public class MailBody {

    private String fromAddr;

    private String subject;

    private String toAddr;

    private String msg;

    public String getFromAddr() {
        return fromAddr;
    }

    @Value("spring.mail.username")
    public void setFromAddr(String fromAddr) {
        this.fromAddr = fromAddr;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getToAddr() {
        return toAddr;
    }

    public void setToAddr(String toAddr) {
        this.toAddr = toAddr;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
