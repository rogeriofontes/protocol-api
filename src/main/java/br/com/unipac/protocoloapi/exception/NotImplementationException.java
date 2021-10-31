package br.com.unipac.protocoloapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type Not implementation exception.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Recurso não implementado")
public class NotImplementationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new Not implementation exception.
     */
    public NotImplementationException() {
        super();
    }

    /**
     * Instantiates a new Not implementation exception.
     *
     * @param message the message
     */
    public NotImplementationException(String message) {
        super(message);
    }

}
