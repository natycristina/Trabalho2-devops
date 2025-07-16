/*package br.ufscar.dc.dsw.service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Value("${email.service.url:http://email-service:3000}")
    private String emailServiceUrl;

    private final RestTemplate restTemplate;

    public EmailService() {
        this.restTemplate = new RestTemplate();
    }

    /**
     * Método original com anexo - mantém a mesma assinatura
     
    public void send(InternetAddress from, InternetAddress to, String subject, String body, File file) {
        try {
            Map<String, String> emailData = new HashMap<>();
            emailData.put("to", to.getAddress());
            emailData.put("subject", subject);
            emailData.put("html", body); // Assumindo que o body pode ter HTML
            
            // Nota: Anexos requerem implementação adicional no microserviço
            if (file != null) {
                System.out.println("Aviso: Anexo '" + file.getName() + "' será ignorado na versão atual do microserviço");
                // TODO: Implementar upload de anexos se necessário
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, String>> request = new HttpEntity<>(emailData, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(
                emailServiceUrl + "/send-email", 
                request, 
                String.class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("Mensagem enviada com sucesso!");
            } else {
                System.out.println("Mensagem não enviada! Status: " + response.getStatusCode());
            }

        } catch (Exception e) {
            System.out.println("Mensagem não enviada!");
            e.printStackTrace();
        }
    }

    /**
     * Método original sem anexo - mantém a mesma assinatura
     
    public void send(InternetAddress from, InternetAddress to, String subject, String body) {
        send(from, to, subject, body, null);
    }

    /**
     * Método auxiliar para envio simples com strings
     
    public void sendSimple(String fromEmail, String toEmail, String subject, String body) {
        try {
            InternetAddress from = new InternetAddress(fromEmail);
            InternetAddress to = new InternetAddress(toEmail);
            send(from, to, subject, body);
        } catch (Exception e) {
            System.out.println("Erro ao criar endereços de email: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Método para verificar se o serviço de email está funcionando
     
    public boolean isEmailServiceHealthy() {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(
                emailServiceUrl + "/health", 
                String.class
            );
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            System.out.println("Serviço de email não está respondendo: " + e.getMessage());
            return false;
        }
    }

    /**
     * Método adicional: enviar email de boas-vindas usando template
     
    public void sendWelcomeEmail(String toEmail, String nome) {
        try {
            Map<String, Object> emailData = new HashMap<>();
            emailData.put("to", toEmail);
            emailData.put("templateType", "welcome");
            
            Map<String, String> data = new HashMap<>();
            data.put("nome", nome);
            emailData.put("data", data);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(emailData, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(
                emailServiceUrl + "/send-template-email", 
                request, 
                String.class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("Email de boas-vindas enviado com sucesso para: " + toEmail);
            } else {
                System.out.println("Erro ao enviar email de boas-vindas");
            }

        } catch (Exception e) {
            System.out.println("Erro ao enviar email de boas-vindas: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Método adicional: enviar notificação de candidatura
     
    public void sendJobApplicationNotification(String toEmail, String candidato, String vaga) {
        try {
            Map<String, Object> emailData = new HashMap<>();
            emailData.put("to", toEmail);
            emailData.put("templateType", "job_application");
            
            Map<String, String> data = new HashMap<>();
            data.put("candidato", candidato);
            data.put("vaga", vaga);
            data.put("data", java.time.LocalDateTime.now().toString());
            emailData.put("data", data);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(emailData, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(
                emailServiceUrl + "/send-template-email", 
                request, 
                String.class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("Notificação de candidatura enviada com sucesso para: " + toEmail);
            } else {
                System.out.println("Erro ao enviar notificação de candidatura");
            }

        } catch (Exception e) {
            System.out.println("Erro ao enviar notificação de candidatura: " + e.getMessage());
            e.printStackTrace();
        }
    }
}*/