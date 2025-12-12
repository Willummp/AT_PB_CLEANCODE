package org.example.tp2pb.repository;

import org.example.tp2pb.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {
    // Spring Data JPA: todos os métodos CRUD básicos
    // (findAll, findById, save, deleteById) já estão implementados

    // EventoRepository.save(evento) -> Insere ou atualiza um evento
    // EventoRepository.findAll() -> Retorna uma lista com todos os eventos
    // EventoRepository.findById(id) -> Retorna um evento pelo ID
    // EventoRepository.deleteById(id) -> Exclui um evento pelo ID
}