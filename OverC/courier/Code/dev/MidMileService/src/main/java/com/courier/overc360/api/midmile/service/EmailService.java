package com.courier.overc360.api.midmile.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String fromEmail, String toEmail, String subject, String message) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom(fromEmail);
        email.setTo(toEmail);
        email.setSubject(subject);
        email.setText(message);

        mailSender.send(email);
    }

    // Send Email Pdf
    public void sendEmailPdf(File pdfFile) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom("no-reply@overc360.com");
            helper.setTo("yogesh.m@tekclover.com");

            String subject = "Monthly shipment consolidated invoice - reg";
            String body = "Dear " +
                    "Please find the attached monthly invoice - June. For your reference";
            helper.setSubject(subject);
            helper.setText(body);

            // Attach PDF file
            FileSystemResource file = new FileSystemResource(pdfFile);
            helper.addAttachment(pdfFile.getName(), file);

            // Send the email
            mailSender.send(mimeMessage);

            System.out.println("Email sent successfully with attachment.");
        } catch (MessagingException e) {
            System.err.println("Error while sending email: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
