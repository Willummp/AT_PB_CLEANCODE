package org.example.tp2pb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * Handler global de exceções para tratamento centralizado de erros.
 * Captura exceções lançadas pelos controllers e fornece respostas apropriadas.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

  /**
   * Trata exceções de argumento ilegal (ex: evento não encontrado).
   *
   * @param ex    exceção lançada
   * @param model modelo para passar dados à view
   * @return view de erro com mensagem apropriada
   */
  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ModelAndView handleIllegalArgumentException(IllegalArgumentException ex, Model model) {
    ModelAndView mav = new ModelAndView("error");
    mav.addObject("errorMessage", ex.getMessage());
    mav.addObject("errorCode", HttpStatus.NOT_FOUND.value());
    return mav;
  }

  /**
   * Trata exceções genéricas não capturadas especificamente.
   *
   * @param ex exceção lançada
   * @return view de erro com mensagem genérica
   */
  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ModelAndView handleGenericException(Exception ex) {
    ModelAndView mav = new ModelAndView("error");
    mav.addObject("errorMessage", "Ocorreu um erro inesperado. Por favor, tente novamente.");
    mav.addObject("errorCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
    mav.addObject("errorDetails", ex.getMessage());
    return mav;
  }
}
