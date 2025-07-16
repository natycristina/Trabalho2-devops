// email-service/src/main/java/br/ufscar/dc/dsw/emailservice/controller/EmailController.java
package br.ufscar.dc.dsw.emailservice.controller;

import br.ufscar.dc.dsw.emailservice.dto.EmailRequest;
import br.ufscar.dc.dsw.emailservice.dto.EmailResponse;
import br.ufscar.dc.dsw.emailservice.service.EmailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<EmailResponse> sendEmail(@Valid @RequestBody EmailRequest emailRequest,
                                                   BindingResult bindingResult) {
        
        System.out.println("=== EMAIL SERVICE - Recebendo requisição ===");
        System.out.println("From: " + emailRequest.getFromEmail());
        System.out.println("To: " + emailRequest.getToEmail());
        System.out.println("Subject: " + emailRequest.getSubject());
        
        if (bindingResult.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            bindingResult.getFieldErrors().forEach(error ->
                errors.append(error.getDefaultMessage()).append("; ")
            );
            
            EmailResponse response = new EmailResponse(false, "Dados inválidos: " + errors.toString());
            return ResponseEntity.badRequest().body(response);
        }

        EmailResponse response = emailService.sendEmail(emailRequest);

        if (response.isSuccess()) {
            System.out.println("✅ Email enviado com sucesso!");
            return ResponseEntity.ok(response);
        } else {
            System.err.println("❌ Erro ao enviar email: " + response.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("🟢 Email Service is running!");
    }
    
    // Endpoint adicional para teste
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("📧 Email Service está funcionando! Timestamp: " + 
                                java.time.LocalDateTime.now());
    }
}