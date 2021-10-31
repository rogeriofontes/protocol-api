package br.com.unipac.protocoloapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Erro ao processar o Recurso")
public class BadResourceException extends Exception {

    private List<String> errorMessages = new ArrayList<>();

    public BadResourceException() {
    }

    public BadResourceException(String msg) {
        super(msg);
    }

    /**
     * @return the errorMessages
     */
    public List<String> getErrorMessages() {
        return errorMessages;
    }

    /**
     * @param errorMessages the errorMessages to set
     */
    public void setErrorMessages(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public void addErrorMessage(String message) {
        this.errorMessages.add(message);
    }
}
