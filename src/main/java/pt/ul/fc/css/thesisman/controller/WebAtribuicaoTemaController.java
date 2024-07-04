package pt.ul.fc.css.thesisman.controller;

import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pt.ul.fc.css.thesisman.dtos.AtribuicaoTemaInfo;
import pt.ul.fc.css.thesisman.dtos.TemaDTO;
import pt.ul.fc.css.thesisman.services.AtribuicaoTemaService;

@Controller
@SessionAttributes("info")
public class WebAtribuicaoTemaController {

  private static final Logger log = LoggerFactory.getLogger(WebAtribuicaoTemaController.class);

  @Autowired private AtribuicaoTemaService atribuicaoTemaService;

  public WebAtribuicaoTemaController() {
    super();
  }

  @GetMapping("/atribuir-tema")
  public String atribuirTemaParaAluno(final Model model, HttpSession session) {
    Long id = (Long) session.getAttribute("userId");
    if (id == null) {
      return "redirect:/login";
    }
    AtribuicaoTemaInfo atribuicaoInfo = atribuicaoTemaService.obterInfoAtribuicaoTemas(id);

    model.addAttribute("info", atribuicaoInfo);
    model.addAttribute("isDocente", session.getAttribute("isDocente"));
    model.addAttribute("isAdmin", session.getAttribute("isAdmin"));
    return "atribuir_tema";
  }

  @PostMapping("/atribuir-tema")
  public String processAtribuirTema(
      @RequestParam("alunoId") Long alunoId,
      @RequestParam("temaId") Long temaId,
      @ModelAttribute("info") AtribuicaoTemaInfo atribuicaoInfo,
      final Model model,
      RedirectAttributes redirectAttributes) {

    TemaDTO tema =
        atribuicaoInfo.getTemas().stream()
            .filter(t -> t.getId().equals(temaId))
            .findFirst()
            .orElse(null);

    if (tema == null) {
      model.addAttribute("error", "Tema não encontrado");
      return "atribuir_tema";
    }

    try {
      if (tema.getTipoTema().equals("Projeto")) {
        boolean hasOrientador = atribuicaoTemaService.hasOrientador(temaId);
        if (!hasOrientador) {
          redirectAttributes.addFlashAttribute("alunoId", alunoId);
          redirectAttributes.addFlashAttribute("temaId", temaId);
          return "redirect:/escolher_orientador";
        }
      }
      atribuicaoTemaService.atribuirTemaParaAluno(alunoId, temaId);
      return "redirect:/atribuir-tema";
    } catch (Exception e) {
      model.addAttribute("error", e.getMessage());
      return "atribuir_tema";
    }
  }

  @GetMapping("/escolher_orientador")
  public String escolherOrientador(
      final Model model,
      @ModelAttribute("info") AtribuicaoTemaInfo atribuicaoInfo,
      HttpSession session) {
    model.addAttribute("alunoId", model.asMap().get("alunoId"));
    model.addAttribute("temaId", model.asMap().get("temaId"));
    model.addAttribute("docentes", atribuicaoInfo.getDocentes());
    model.addAttribute("isDocente", session.getAttribute("isDocente"));
    model.addAttribute("isAdmin", session.getAttribute("isAdmin"));
    return "escolher_orientador";
  }

  @PostMapping("/escolher_orientador")
  public String processEscolherOrientador(
      @RequestParam("alunoId") Long alunoId,
      @RequestParam("temaId") Long temaId,
      @RequestParam("docenteId") Long docenteId,
      final Model model,
      HttpSession session) {
    try {
      atribuicaoTemaService.atribuirTemaParaAluno(alunoId, temaId, docenteId);
      return "redirect:/atribuir-tema";
    } catch (Exception e) {
      model.addAttribute("error", e.getMessage());
      model.addAttribute("alunoId", alunoId);
      model.addAttribute("temaId", temaId);
      return "escolher_orientador";
    }
  }
}
