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
import pt.ul.fc.css.thesisman.dtos.DefesaDTO;
import pt.ul.fc.css.thesisman.services.RegistarNotaDefesaService;

@Controller
public class WebRegistarNotaController {

  private static final Logger log = LoggerFactory.getLogger(WebRegistarNotaController.class);

  @Autowired RegistarNotaDefesaService registarNotaDefesaService;

  @GetMapping("/registar-nota-defesa")
  public String listarDefesas(final Model model, HttpSession session) {
    Long id = (Long) session.getAttribute("userId");
    if (id == null) {
      return "redirect:/login";
    }

    List<DefesaDTO> defesas = registarNotaDefesaService.listarDefesasParaAvaliar(id);

    model.addAttribute("defesas", defesas);
    model.addAttribute("isDocente", session.getAttribute("isDocente"));
    model.addAttribute("isAdmin", session.getAttribute("isAdmin"));

    return "registar_nota_defesa";
  }

  @PostMapping("/registar-nota-defesa")
  public String registarNotaDefesa(
      final Model model,
      @RequestParam("defesaId") Long defesaId,
      @RequestParam("nota") int nota,
      HttpSession session) {

    if (nota < 0 || nota > 20) {
      model.addAttribute("isDocente", session.getAttribute("isDocente"));
      model.addAttribute("isAdmin", session.getAttribute("isAdmin"));
      model.addAttribute("error", "A nota deve ser um inteiro entre 0 e 20");
      model.addAttribute(
          "defesas",
          registarNotaDefesaService.listarDefesasParaAvaliar(
              (Long) session.getAttribute("userId")));
      return "registar_nota_defesa";
    }

    Long id = (Long) session.getAttribute("userId");
    if (id == null) {
      return "redirect:/login";
    }
    try {
      registarNotaDefesaService.registarNotaDefesa(defesaId, nota, id);
    } catch (Exception e) {
      model.addAttribute("isDocente", session.getAttribute("isDocente"));
      model.addAttribute("isAdmin", session.getAttribute("isAdmin"));
      model.addAttribute("error", e.getMessage());
      model.addAttribute("defesas", registarNotaDefesaService.listarDefesasParaAvaliar(id));
      return "registar_nota_defesa";
    }

    return "redirect:/menu";
  }
}
