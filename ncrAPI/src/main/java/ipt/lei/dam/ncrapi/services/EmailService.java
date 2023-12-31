package ipt.lei.dam.ncrapi.services;

import ipt.lei.dam.ncrapi.utils.ConstantsUtils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendSimpleEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    private void sendHtmlMessage(String to, String subject, String htmlBody) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);

        mailSender.send(message);
    }

    public void sendRecoverPasswordEmail(String toEmail, String OTP) throws MessagingException {
        String subject = "Recuperação de palavra-passe";
        String htmlBody = ConstantsUtils.recoverPasswordHTMLEmail.replace("[OTP_CODE]", OTP);

        sendHtmlMessage(toEmail, subject, htmlBody);
    }

    public void sendConfirmAccountEmail(String toEmail, String OTP) throws MessagingException {
        String subject = "Confirmação de conta";
        String htmlBody = ConstantsUtils.confirmAccountHTMLEmail.replace("[OTP_CODE]", OTP);

        sendHtmlMessage(toEmail, subject, htmlBody);
    }

}
