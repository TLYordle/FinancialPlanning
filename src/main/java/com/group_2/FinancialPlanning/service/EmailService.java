package com.group_2.FinancialPlanning.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendAccount(String to, String username, String password) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("your email");
        message.setTo(to);
        message.setSubject(subjectEmail(username));
        message.setText(contentEmail(to, password));
        mailSender.send(message);
    }

    private String subjectEmail(String usename) {
        StringBuilder builder = new StringBuilder();
        builder.append("Financial Planning-system email");
        builder.append(usename);
        return builder.toString();
    }

    private String contentEmail(String email, String password) {
        StringBuilder sb = new StringBuilder();

        sb.append("This email is from Financial Planning system,\n\n")
                .append("Your account has been created. Please use the following credential to \n")
                .append("login:  \n")
                .append(" User name: ")
                .append(email)
                .append("\n\nPassword: ")
                .append(password)
                .append("\n \nIf anything wrong, please reach-out recruiter <offer recruiter owner account>. We are so sorry for this inconvenience. \n\n")
                .append("Thanks & Regards! \n")
                .append("Financial Planning Team.");

        return sb.toString();
    }

}
