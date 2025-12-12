package org.example.tp2pb.controller;

import jakarta.validation.Valid;
import org.example.tp2pb.model.Evento;
import org.example.tp2pb.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Controller responsável pelas operações CRUD de Eventos.
 * Gerencia interações entre interface web e repositório de dados.
 */
@Controller
@RequestMapping("/eventos") // Todos os métodos nesta classe começarão com /eventos
public class EventoController {

    @Autowired // O Spring vai injetar uma instância do repositório aqui
    private EventoRepository eventoRepository;

    /**
     * Lista todos os eventos cadastrados.
     *
     * @param model modelo para passar dados à view
     * @return nome da view de listagem
     */
    // Método para LISTAR todos os eventos
    @GetMapping
    public String listarEventos(Model model) {
        model.addAttribute("eventos", eventoRepository.findAll());
        return "index"; // Retorna o arquivo templates/index.html
    }

    /**
     * Exibe formulário para cadastro de novo evento.
     *
     * @param model modelo para passar dados à view
     * @return nome da view do formulário
     */
    // Método para mostrar o FORMULÁRIO de novo evento
    @GetMapping("/novo")
    public String mostrarFormularioNovo(Model model) {
        model.addAttribute("evento", new Evento());
        return "form-evento"; // Retorna o arquivo templates/form-evento.html
    }

    /**
     * Salva um evento (novo ou existente) com validação.
     *
     * @param evento objeto evento com dados do formulário
     * @param result resultado da validação
     * @return redirect para listagem ou retorna ao formulário se houver erros
     */
    // Método para SALVAR um evento (novo ou existente)
    @PostMapping("/salvar")
    public String salvarEvento(@Valid @ModelAttribute Evento evento, BindingResult result) {
        if (result.hasErrors()) {
            return "form-evento";
        }
        eventoRepository.save(evento);
        return "redirect:/eventos"; // Redireciona para a lista após salvar
    }

    /**
     * Exibe formulário para edição de evento existente.
     *
     * @param id    identificador do evento
     * @param model modelo para passar dados à view
     * @return nome da view do formulário
     * @throws IllegalArgumentException se evento não for encontrado
     */
    // Método para mostrar o formulário de EDIÇÃO
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de evento inválido: " + id));
        model.addAttribute("evento", evento);
        return "form-evento";
    }

    /**
     * Exclui um evento pelo ID.
     *
     * @param id identificador do evento a ser excluído
     * @return redirect para listagem
     */
    // Método para EXCLUIR um evento
    @GetMapping("/excluir/{id}")
    public String excluirEvento(@PathVariable Long id) {
        eventoRepository.deleteById(id);
        return "redirect:/eventos";
    }
}