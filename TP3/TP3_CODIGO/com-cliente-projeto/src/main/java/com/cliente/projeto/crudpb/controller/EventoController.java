package com.cliente.projeto.crudpb.controller;

import com.cliente.projeto.crudpb.dto.EventoDTO;
import com.cliente.projeto.crudpb.exception.ValidacaoException;
import com.cliente.projeto.crudpb.model.Evento;
import com.cliente.projeto.crudpb.service.EventoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/eventos")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    // READ (Listagem) - Mapeia para /eventos
    @GetMapping
    public String listarEventos(Model model) {
        model.addAttribute("eventos", eventoService.listarTodos());
        return "lista-eventos"; // Renderiza o arquivo 'lista-eventos.html'
    }

    // CREATE (Mostrar formulário) - Mapeia para /eventos/novo
    @GetMapping("/novo")
    public String mostrarFormularioNovo(Model model) {
        model.addAttribute("eventoDTO", new EventoDTO("", ""));
        model.addAttribute("pageTitle", "Novo Evento");
        return "form-evento"; // Renderiza o arquivo 'form-evento.html'
    }

    // CREATE (Salvar) - Mapeia para POST /eventos
    @PostMapping
    public String salvarEvento(@Valid @ModelAttribute("eventoDTO") EventoDTO eventoDTO,
                               BindingResult bindingResult, // Pega erros de validação do DTO
                               Model model,
                               RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", "Novo Evento");
            return "form-evento";
        }

        try {
            // FAIL EARLY: Validação de regra de negócio (ex: nome duplicado)
            eventoService.criarEvento(eventoDTO.toEntity());

            redirectAttributes.addFlashAttribute("mensagemSucesso", "Evento criado com sucesso!");
            return "redirect:/eventos";

        } catch (ValidacaoException ex) {
            model.addAttribute("pageTitle", "Novo Evento");
            model.addAttribute("mensagemErro", ex.getMessage());
            return "form-evento";
        }
    }

    // UPDATE (Mostrar formulário de edição) - Mapeia para /eventos/editar/{id}
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Evento evento = eventoService.buscarPorId(id); // Já lida com 404
        EventoDTO dto = new EventoDTO(evento.getNome(), evento.getDescricao());

        model.addAttribute("eventoDTO", dto);
        model.addAttribute("eventoId", id);
        model.addAttribute("pageTitle", "Editar Evento");
        return "form-evento";
    }

    // UPDATE (Atualizar) - Mapeia para POST /eventos/{id}
    @PostMapping("/{id}")
    public String atualizarEvento(@PathVariable Long id,
                                  @Valid @ModelAttribute("eventoDTO") EventoDTO eventoDTO,
                                  BindingResult bindingResult,
                                  Model model,
                                  RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", "Editar Evento");
            model.addAttribute("eventoId", id);
            return "form-evento";
        }

        try {
            eventoService.atualizarEvento(id, eventoDTO.toEntity());
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Evento atualizado com sucesso!");
            return "redirect:/eventos";

        } catch (ValidacaoException ex) {
            model.addAttribute("pageTitle", "Editar Evento");
            model.addAttribute("eventoId", id);
            model.addAttribute("mensagemErro", ex.getMessage());
            return "form-evento";
        }
    }

    // DELETE - Mapeia para /eventos/deletar/{id}
    @GetMapping("/deletar/{id}")
    public String deletarEvento(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        eventoService.deletarEvento(id);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Evento deletado com sucesso!");
        return "redirect:/eventos";
    }
}