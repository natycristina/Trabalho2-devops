// email-service/src/main/java/br/ufscar/dc/dsw/emailservice/dto/EmailResponse.java
package br.ufscar.dc.dsw.emailservice.dto;

public class EmailResponse {
    private boolean success;
    private String message;

    // Construtores
    public EmailResponse() {}

    public EmailResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    // Getters e Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "EmailResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                '}';
    }
}