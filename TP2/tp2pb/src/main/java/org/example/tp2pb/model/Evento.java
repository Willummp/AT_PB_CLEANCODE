package org.example.tp2pb.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.Data; // Do Lombok, para gerar Getters, Setters, etc.
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * Entidade que representa um Evento no sistema.
 * Contém validações para garantir integridade dos dados.
 */
@Entity
@Data
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome do evento é obrigatório")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @NotNull(message = "Data do evento é obrigatória")
    @FutureOrPresent(message = "Data do evento deve ser presente ou futura")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate data;
    @NotBlank(message = "Local do evento é obrigatório")
    @Size(min = 3, max = 150, message = "Local deve ter entre 3 e 150 caracteres")
    private String local;
}