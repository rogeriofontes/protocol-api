package br.com.unipac.protocoloapi.model.domain;


import lombok.*;

import javax.persistence.*;

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
    private String name;
    private String email;
    @Column(name = "document_id")
    private Integer documentId;
}


