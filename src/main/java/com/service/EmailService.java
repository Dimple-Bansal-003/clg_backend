package com.service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;



@Service
public class EmailService {
    @Value("${sendgrid.api.key}")
    private String apiKey;
    
    public void sendCredentials(String toEmail, String username, String password, String rollNo, String name) {
        Email from = new Email("dimple03bansal@gmail.com");  // Your verified sender
        String subject = "Your College Credentials";
        Email to = new Email(toEmail);
        
        /*String body = String.format("""
            RollNo: %s
            Username: %s
            Password: %s
            Welcome, %s!
            """, rollNo, username, password, name);*/
        
        Content content = new Content(
        	    "text/plain",
        	    "Hello,\n\nYour login credentials:\n\nUsername: " + username +
        	    "\nPassword: " + password +
        	    "\n\nRegards,\nCollege Admin"
        	);

        Mail mail = new Mail(from, subject, to, content);
        
        SendGrid sg = new SendGrid(apiKey);
        try {
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println("✅ Email sent! Status: " + response.getStatusCode());
        } catch (Exception e) {
            System.err.println("❌ Email failed: " + e.getMessage());
        }
    }
}
