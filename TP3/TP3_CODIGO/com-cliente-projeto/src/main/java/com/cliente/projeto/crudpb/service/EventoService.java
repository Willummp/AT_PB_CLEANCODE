package com.cliente.projeto.crudpb.service;

import com.cliente.projeto.crudpb.exception.RecursoNaoEncontradoException;
import com.cliente.projeto.crudpb.exception.ValidacaoException;
import com.cliente.projeto.crudpb.model.Evento;
import com.cliente.projeto.crudpb.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventoService {

    private final EventoRepository eventoRepository;

    public EventoService(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    public List<Evento> listarTodos() {
        return eventoRepository.findAll();
    }

    public Evento buscarPorId(Long id) {
        return eventoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Evento não encontrado com ID: " + id));
    }

    public Evento criarEvento(Evento evento) {
        // Regra de negócio (não pode ter nome duplicado)
        Optional<Evento> eventoExistente = eventoRepository.findByNome(evento.getNome());
        if (eventoExistente.isPresent()) {
            throw new ValidacaoException("Já existe um evento com o nome: " + evento.getNome());
        }
        return eventoRepository.save(evento);
    }

    public Evento atualizarEvento(Long id, Evento eventoAtualizado) {
        // Busca o evento (ou falha com 404)
        Evento eventoExistente = buscarPorId(id);

        // Validação de nome duplicado (ignorando o próprio ID)
        Optional<Evento> conflito = eventoRepository.findByNome(eventoAtualizado.getNome());
        if (conflito.isPresent() && !conflito.get().getId().equals(id)) {
            throw new ValidacaoException(
                    "O nome '" + eventoAtualizado.getNome() + "' já está em uso por outro evento.");
        }

        // Atualiza os dados
        eventoExistente.setNome(eventoAtualizado.getNome());
        eventoExistente.setDescricao(eventoAtualizado.getDescricao());

        return eventoRepository.save(eventoExistente);
    }

    public void deletarEvento(Long id) {
        // Verifica se existe antes de deletar (ou falha com 404)
        Evento eventoParaDeletar = buscarPorId(id);
        eventoRepository.delete(eventoParaDeletar);
    }
}