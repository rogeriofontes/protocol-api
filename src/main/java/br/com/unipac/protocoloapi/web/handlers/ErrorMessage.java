package br.com.unipac.protocoloapi.web.handlers;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString(callSuper = true, of = { "name" })
@Builder
@Data
public class ErrorMessage {
    private int statusCode;
    private Date timestamp;
    private String message;
}
