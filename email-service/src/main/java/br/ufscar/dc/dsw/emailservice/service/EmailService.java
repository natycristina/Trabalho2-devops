// src/main/java/br/ufscar/dc/dsw/emailservice/service/EmailService.java
package br.ufscar.dc.dsw.emailservice.service;

import java.io.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import br.ufscar.dc.dsw.emailservice.dto.EmailRequest;
import br.ufscar.dc.dsw.emailservice.dto.EmailResponse;

@Service
public class EmailService {

	@Autowired
	JavaMailSender emailSender;

	// New method that accepts EmailRequest (called by controller)
	public EmailResponse sendEmail(EmailRequest emailRequest) {
    try {
        InternetAddress from = new InternetAddress(emailRequest.getFromEmail(), emailRequest.getFromName());
        InternetAddress to = new InternetAddress(emailRequest.getToEmail(), emailRequest.getToName());
        
        send(from, to, emailRequest.getSubject(), emailRequest.getBody());
        
        // Retorna sucesso
        return new EmailResponse(true, "Email enviado com sucesso!");
        
    } catch (Exception e) {
        System.out.println("Erro ao processar EmailRequest: " + e.getMessage());
        e.printStackTrace();
        
        // Retorna erro
        return new EmailResponse(false, "Falha ao enviar email: " + e.getMessage());
    }
}

	public void send(InternetAddress from, InternetAddress to, String subject, String body, File file) {
		try {
			MimeMessage message = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);

			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(body);

			if (file != null) {
				helper.addAttachment(file.getName(), file);
			}

			emailSender.send(message);
			System.out.println("Mensagem enviada com sucesso!");
		} catch (MessagingException e) {
			System.out.println("Mensagem não enviada!");
			e.printStackTrace();
		}
	}

	public void send(InternetAddress from, InternetAddress to, String subject, String body) {
		send(from, to, subject, body, null);
	}
}