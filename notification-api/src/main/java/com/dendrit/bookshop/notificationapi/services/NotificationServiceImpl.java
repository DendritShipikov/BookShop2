package com.dendrit.bookshop.notificationapi.services;

import com.dendrit.bookshop.notificationapi.data.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class NotificationServiceImpl implements NotificationService {

    private JavaMailSender javaMailSender;

    @Autowired
    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendMessage(Message message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(message.getUserMail());
        mailMessage.setText(message.getText());
        mailMessage.setSubject("BookShop notification");
        javaMailSender.send(mailMessage);
    }

}
