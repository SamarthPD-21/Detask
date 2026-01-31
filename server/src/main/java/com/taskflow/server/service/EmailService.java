package com.taskflow.server.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async("emailExecutor")
    public void sendWelcomeEmail(String to, String name) {
        String subject = "Welcome to TaskFlow!";
        String content = buildWelcomeEmail(name);
        sendEmail(to, subject, content);
    }

    @Async("emailExecutor")
    public void sendBoardInvitation(String to, String inviterName, String boardName, String boardId) {
        String subject = inviterName + " invited you to join a board on TaskFlow";
        String content = buildBoardInvitationEmail(inviterName, boardName, boardId);
        sendEmail(to, subject, content);
    }

    @Async("emailExecutor")
    public void sendTaskAssignment(String to, String assignerName, String taskTitle, String boardName) {
        String subject = "You've been assigned to a task on TaskFlow";
        String content = buildTaskAssignmentEmail(assignerName, taskTitle, boardName);
        sendEmail(to, subject, content);
    }

    @Async("emailExecutor")
    public void sendDueDateReminder(String to, String taskTitle, String dueDate) {
        String subject = "Task Due Soon: " + taskTitle;
        String content = buildDueDateReminderEmail(taskTitle, dueDate);
        sendEmail(to, subject, content);
    }

    @Async("emailExecutor")
    public void sendPasswordReset(String to, String resetLink) {
        String subject = "Reset your TaskFlow password";
        String content = buildPasswordResetEmail(resetLink);
        sendEmail(to, subject, content);
    }

    private void sendEmail(String to, String subject, String content) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(message);
            log.info("Email sent successfully to: {}", to);
        } catch (MessagingException e) {
            log.error("Failed to send email to: {}", to, e);
        }
    }

    private String buildWelcomeEmail(String name) {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <style>
                    body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; margin: 0; padding: 0; background-color: #f4f5f7; }
                    .container { max-width: 600px; margin: 0 auto; background-color: #ffffff; }
                    .header { background: linear-gradient(135deg, #0079bf 0%, #5067c5 100%); padding: 40px 20px; text-align: center; }
                    .header h1 { color: #ffffff; margin: 0; font-size: 28px; }
                    .content { padding: 40px 30px; }
                    .content h2 { color: #172b4d; margin-top: 0; }
                    .content p { color: #5e6c84; line-height: 1.6; }
                    .button { display: inline-block; background-color: #0079bf; color: #ffffff; padding: 12px 30px; text-decoration: none; border-radius: 4px; margin-top: 20px; }
                    .footer { background-color: #f4f5f7; padding: 20px; text-align: center; color: #5e6c84; font-size: 12px; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>✨ TaskFlow</h1>
                    </div>
                    <div class="content">
                        <h2>Welcome, %s!</h2>
                        <p>We're thrilled to have you on board! TaskFlow is designed to help you organize your work and collaborate seamlessly with your team.</p>
                        <p>Here's what you can do with TaskFlow:</p>
                        <ul style="color: #5e6c84; line-height: 1.8;">
                            <li>Create boards to organize your projects</li>
                            <li>Add lists and cards to track tasks</li>
                            <li>Collaborate with team members</li>
                            <li>Set due dates and priorities</li>
                            <li>Track progress with analytics</li>
                        </ul>
                        <a href="http://localhost:3000" class="button">Get Started</a>
                    </div>
                    <div class="footer">
                        <p>© 2024 TaskFlow. All rights reserved.</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(name);
    }

    private String buildBoardInvitationEmail(String inviterName, String boardName, String boardId) {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <style>
                    body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; margin: 0; padding: 0; background-color: #f4f5f7; }
                    .container { max-width: 600px; margin: 0 auto; background-color: #ffffff; }
                    .header { background: linear-gradient(135deg, #0079bf 0%, #5067c5 100%); padding: 40px 20px; text-align: center; }
                    .header h1 { color: #ffffff; margin: 0; font-size: 28px; }
                    .content { padding: 40px 30px; }
                    .content h2 { color: #172b4d; margin-top: 0; }
                    .content p { color: #5e6c84; line-height: 1.6; }
                    .button { display: inline-block; background-color: #0079bf; color: #ffffff; padding: 12px 30px; text-decoration: none; border-radius: 4px; margin-top: 20px; }
                    .footer { background-color: #f4f5f7; padding: 20px; text-align: center; color: #5e6c84; font-size: 12px; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>📋 TaskFlow</h1>
                    </div>
                    <div class="content">
                        <h2>You've been invited!</h2>
                        <p><strong>%s</strong> has invited you to collaborate on the board <strong>"%s"</strong>.</p>
                        <p>Click the button below to view the board and start collaborating.</p>
                        <a href="http://localhost:3000/boards/%s" class="button">View Board</a>
                    </div>
                    <div class="footer">
                        <p>© 2024 TaskFlow. All rights reserved.</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(inviterName, boardName, boardId);
    }

    private String buildTaskAssignmentEmail(String assignerName, String taskTitle, String boardName) {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <style>
                    body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; margin: 0; padding: 0; background-color: #f4f5f7; }
                    .container { max-width: 600px; margin: 0 auto; background-color: #ffffff; }
                    .header { background: linear-gradient(135deg, #0079bf 0%, #5067c5 100%); padding: 40px 20px; text-align: center; }
                    .header h1 { color: #ffffff; margin: 0; font-size: 28px; }
                    .content { padding: 40px 30px; }
                    .content h2 { color: #172b4d; margin-top: 0; }
                    .content p { color: #5e6c84; line-height: 1.6; }
                    .task-card { background-color: #f4f5f7; padding: 20px; border-radius: 8px; margin: 20px 0; }
                    .footer { background-color: #f4f5f7; padding: 20px; text-align: center; color: #5e6c84; font-size: 12px; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>✅ TaskFlow</h1>
                    </div>
                    <div class="content">
                        <h2>New Task Assignment</h2>
                        <p><strong>%s</strong> assigned you to a task.</p>
                        <div class="task-card">
                            <p><strong>Task:</strong> %s</p>
                            <p><strong>Board:</strong> %s</p>
                        </div>
                    </div>
                    <div class="footer">
                        <p>© 2024 TaskFlow. All rights reserved.</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(assignerName, taskTitle, boardName);
    }

    private String buildDueDateReminderEmail(String taskTitle, String dueDate) {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <style>
                    body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; margin: 0; padding: 0; background-color: #f4f5f7; }
                    .container { max-width: 600px; margin: 0 auto; background-color: #ffffff; }
                    .header { background: linear-gradient(135deg, #eb5a46 0%, #ff8c42 100%); padding: 40px 20px; text-align: center; }
                    .header h1 { color: #ffffff; margin: 0; font-size: 28px; }
                    .content { padding: 40px 30px; }
                    .content h2 { color: #172b4d; margin-top: 0; }
                    .content p { color: #5e6c84; line-height: 1.6; }
                    .footer { background-color: #f4f5f7; padding: 20px; text-align: center; color: #5e6c84; font-size: 12px; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>⏰ TaskFlow</h1>
                    </div>
                    <div class="content">
                        <h2>Task Due Soon!</h2>
                        <p>Your task <strong>"%s"</strong> is due on <strong>%s</strong>.</p>
                        <p>Don't forget to complete it on time!</p>
                    </div>
                    <div class="footer">
                        <p>© 2024 TaskFlow. All rights reserved.</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(taskTitle, dueDate);
    }

    private String buildPasswordResetEmail(String resetLink) {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <style>
                    body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; margin: 0; padding: 0; background-color: #f4f5f7; }
                    .container { max-width: 600px; margin: 0 auto; background-color: #ffffff; }
                    .header { background: linear-gradient(135deg, #0079bf 0%, #5067c5 100%); padding: 40px 20px; text-align: center; }
                    .header h1 { color: #ffffff; margin: 0; font-size: 28px; }
                    .content { padding: 40px 30px; }
                    .content h2 { color: #172b4d; margin-top: 0; }
                    .content p { color: #5e6c84; line-height: 1.6; }
                    .button { display: inline-block; background-color: #0079bf; color: #ffffff; padding: 12px 30px; text-decoration: none; border-radius: 4px; margin-top: 20px; }
                    .footer { background-color: #f4f5f7; padding: 20px; text-align: center; color: #5e6c84; font-size: 12px; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>🔐 TaskFlow</h1>
                    </div>
                    <div class="content">
                        <h2>Reset Your Password</h2>
                        <p>We received a request to reset your password. Click the button below to create a new password.</p>
                        <a href="%s" class="button">Reset Password</a>
                        <p style="margin-top: 30px; font-size: 14px;">If you didn't request this, you can safely ignore this email.</p>
                    </div>
                    <div class="footer">
                        <p>© 2024 TaskFlow. All rights reserved.</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(resetLink);
    }
}
