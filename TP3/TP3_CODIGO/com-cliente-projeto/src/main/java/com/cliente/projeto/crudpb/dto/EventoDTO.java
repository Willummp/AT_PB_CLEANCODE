package com.cliente.projeto.crudpb.dto;

import com.cliente.projeto.crudpb.model.Evento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// Estas são as validações que o "GlobalExceptionHandler" vai capturar
public record EventoDTO(
        @NotBlank(message = "O nome é obrigatório.")
        @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres.")
        String nome,

        @Size(max = 255, message = "A descrição não pode exceder 255 caracteres.")
        String descricao
) {
    // Método utilitário para converter o DTO em Entidade
    public Evento toEntity() {
        return new Evento(this.nome, this.descricao);
    }
}