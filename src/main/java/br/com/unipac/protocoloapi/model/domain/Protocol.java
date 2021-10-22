package br.com.unipac.protocoloapi.model.domain;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "tb_protocol")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class Protocol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    @Email
    private String email;
    @Column(name = "document_id")
  //  @NotBlank
    private Integer documentId;

    public void update(Long id, Protocol protocol) {
        this.id = id;
        this.name = protocol.getName();
        this.email = protocol.getEmail();
        this.documentId = protocol.getDocumentId();
    }
}


