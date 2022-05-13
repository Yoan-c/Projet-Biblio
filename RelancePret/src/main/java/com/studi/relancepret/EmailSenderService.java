package com.studi.relancepret;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.mail.SendFailedException;

@Component
@Slf4j
public class EmailSenderService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String to , String subject, String text) throws SendFailedException {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("studibiblio@gmail.com");
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(text);
        if(to != null) {
            javaMailSender.send(mailMessage);
            log.info("sendEmail : "+to);
        }
    }
}
