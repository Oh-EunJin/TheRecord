package com.record.the_record.email;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;

    @Override
    public void sendEmail(String userEmail, String msg) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = emailSender.createMimeMessage();

        message.addRecipients(Message.RecipientType.TO, userEmail);
        message.setSubject("The Record 인증 메일입니다.");

        StringBuilder formattedMsg = new StringBuilder("");
        formattedMsg.append(msg);

        message.setText(formattedMsg.toString(), "utf-8", "html");
        message.setFrom(new InternetAddress("ssafy0601@gmail.com", "TheRecord"));

        emailSender.send(message);

    }
}
