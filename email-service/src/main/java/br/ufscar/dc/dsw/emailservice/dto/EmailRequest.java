// email-service/src/main/java/br/ufscar/dc/dsw/emailservice/dto/EmailRequest.java
package br.ufscar.dc.dsw.emailservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class EmailRequest {
    
    @NotBlank(message = "Email do remetente é obrigatório")
    @Email(message = "Email do remetente deve ser válido")
    private String fromEmail;
    
    @NotBlank(message = "Nome do remetente é obrigatório")
    private String fromName;
    
    @NotBlank(message = "Email do destinatário é obrigatório")
    @Email(message = "Email do destinatário deve ser válido")
    private String toEmail;
    
    @NotBlank(message = "Nome do destinatário é obrigatório")
    private String toName;
    
    @NotBlank(message = "Assunto é obrigatório")
    private String subject;
    
    @NotBlank(message = "Corpo do email é obrigatório")
    private String body;

    // Construtores
    public EmailRequest() {}

    public EmailRequest(String fromEmail, String fromName, String toEmail, String toName, String subject, String body) {
        this.fromEmail = fromEmail;
        this.fromName = fromName;
        this.toEmail = toEmail;
        this.toName = toName;
        this.subject = subject;
        this.body = body;
    }

    // Getters e Setters
    public String getFromEmail() {
        return fromEmail;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "EmailRequest{" +
                "fromEmail='" + fromEmail + '\'' +
                ", fromName='" + fromName + '\'' +
                ", toEmail='" + toEmail + '\'' +
                ", toName='" + toName + '\'' +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}