package com.codinginterview.interview_platform.auth.service;

import org.springframework.stereotype.Service;

@Service
public class EmailService {

    /*
    private final JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
     */
    public void sendVerificationEmail(String toEmail, String verificationToken) {
        // MimeMessage message = mailSender.createMimeMessage();
        // try {
        //     MimeMessageHelper helper = new MimeMessageHelper(message, true);
        //     helper.setTo(toEmail);
        //     helper.setSubject("Verify your email address");
        //     helper.setText("Please click the following link to verify your email address: " +
        //             "http://localhost:8080/verification/verify?token=" + verificationToken);

        //     mailSender.send(message);
        // } catch (MessagingException e) {
        //     throw new RuntimeException("Failed to send email", e);
        // }
    }

    public void sendMail(String to, String subject, String text) {
        // SimpleMailMessage message = new SimpleMailMessage();
        // message.setTo(to);
        // message.setSubject(subject);
        // message.setText(text);
        // mailSender.send(message);
    }
}
