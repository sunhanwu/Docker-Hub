package top.sunhanwu.cvehub.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import top.sunhanwu.cvehub.bean.utils.MailBody;

import java.util.Arrays;

@Component
public class SendMail {

    @Autowired(required = false)
    private JavaMailSender javaMailSender;

    public boolean sendMail(MailBody mailBody){

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(mailBody.getFromAddr());
        simpleMailMessage.setBcc();
        simpleMailMessage.setTo(mailBody.getToAddr());
        simpleMailMessage.setSubject(mailBody.getSubject());
        simpleMailMessage.setText(mailBody.getMsg());

        try {
            javaMailSender.send(simpleMailMessage);
            return true;
        }
        catch (MailException e)
        {
            System.out.println(Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

}
