package pt.ul.fc.css.thesisman.controller;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pt.ul.fc.css.thesisman.exceptions.CriarTemaException;
import pt.ul.fc.css.thesisman.exceptions.EntidadeNotFoundException;
import pt.ul.fc.css.thesisman.exceptions.ParametroException;
import pt.ul.fc.css.thesisman.exceptions.TemaFoundException;
import pt.ul.fc.css.thesisman.services.SubmissaoTemaService;

@Controller
public class WebSubmissaoTemaController {

  private static final Logger log = LoggerFactory.getLogger(WebSubmissaoTemaController.class);

  @Autowired SubmissaoTemaService submeterTemaService;

  @GetMapping("/submeter-tema")
  public String submeterTema(final Model model, HttpSession session) {
    Long id = (Long) session.getAttribute("userId");
    if (id == null) {
      return "redirect:/login";
    }
    try {
      model.addAttribute("mestrados", submeterTemaService.listarMestrados());
      if (!(Boolean) session.getAttribute("isDocente")) {
        model.addAttribute("docentes", submeterTemaService.listarDocentes());
      }
    } catch (Exception e) {
      model.addAttribute("error", e.getMessage());
      return "menu";
    }
    model.addAttribute("isAdmin", session.getAttribute("isAdmin"));
    model.addAttribute("isDocente", session.getAttribute("isDocente"));
    return "submeter_tema";
  }

  @PostMapping("/submeter-tema")
  public String submeterTema(
      final Model model,
      @RequestParam("titulo") String titulo,
      @RequestParam("descricao") String descricao,
      @RequestParam("renumeracaoMensal") Double renumeracaoMensal,
      @RequestParam(value = "mestradosCompativeis", required = false)
          List<Long> mestradosCompativeis,
      @RequestParam(value = "docenteId", required = false) Long docenteId,
      HttpSession session) {

    if (titulo.isEmpty()) {
      model.addAttribute("error", "O título do tema não pode ser vazio.");
      return "submeter_tema";
    }

    if (descricao.isEmpty()) {
      model.addAttribute("error", "A descrição do tema não pode ser vazia.");
      return "submeter_tema";
    }

    if (renumeracaoMensal == null) {
      model.addAttribute("error", "A renumeração mensal do tema não pode ser vazia.");
      return "submeter_tema";
    }

    if (mestradosCompativeis == null || mestradosCompativeis.isEmpty()) {
      model.addAttribute("error", "O tema deve ser compatível com pelo menos um mestrado.");
      return "submeter_tema";
    }

    Long id = (Long) session.getAttribute("userId");
    if (id == null) {
      return "redirect:/login";
    }
    try {
      if ((Boolean) session.getAttribute("isDocente")) {
        submeterTemaService.submeterTemaDissertacao(
            titulo, descricao, renumeracaoMensal, mestradosCompativeis, id);
      } else {
        submeterTemaService.submeterTemaProjeto(
            titulo, descricao, renumeracaoMensal, mestradosCompativeis, id, docenteId);
      }
    } catch (ParametroException
        | TemaFoundException
        | EntidadeNotFoundException
        | CriarTemaException e) {
      model.addAttribute("mestrados", submeterTemaService.listarMestrados());
      model.addAttribute("docentes", submeterTemaService.listarDocentes());
      model.addAttribute("isDocente", session.getAttribute("isDocente"));
      model.addAttribute("isAdmin", session.getAttribute("isAdmin"));
      model.addAttribute("error", e.getMessage());
      return "submeter_tema";
    }
    return "redirect:/menu";
  }
}
