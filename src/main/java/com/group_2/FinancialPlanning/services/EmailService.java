package com.group_2.FinancialPlanning.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

        private String contentEmail(String email,String fullname, String password) {
        StringBuilder sb = new StringBuilder();

            sb.append("Dear ")
                    .append(fullname)
                    .append(",\n\n")
                    .append("Welcome to the Financial Planning system!\n\n")
                    .append("Your account has been successfully created. Below are your login credentials:\n\n")
                    .append("📧 Email: ")
                    .append(email)
                    .append("\n🔑 Password: ")
                    .append(password)
                    .append("\n\n")
                    .append("If you encounter any issues, please contact our support team. ")
                    .append("We apologize for any inconvenience.\n\n")
                    .append("Best regards,\n")
                    .append("The Financial Planning Team.");

            return sb.toString();
    }

        private String subjectEmail(String usename) {
        StringBuilder builder = new StringBuilder();
        builder.append("Financial Planning-system email");
        builder.append(usename);
        return builder.toString();
    }

    public  void sendAccount(String email, String fullname, String password){
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);

            helper.setFrom("FinancialPlanning");
            helper.setTo(email);
            helper.setSubject(subjectEmail(fullname));
            helper.setText(contentEmail(email,fullname,password),true);

            mailSender.send(mimeMessage);

        }catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }

    public void sendEmailPassword(String email, String newPassword) {
        String subject = "Reset Password - Financial Planning";
        String message = "Your new password is: " + newPassword + "\nPlease change it after login";

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setFrom("FinancialPlanning");
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(message, false);

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
