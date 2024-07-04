package pt.ul.fc.css.thesisman.controller;

import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pt.ul.fc.css.thesisman.dtos.EstatisticasInfo;
import pt.ul.fc.css.thesisman.services.EstatisticasService;

@Controller
public class WebEstatisticasController {

  private static final Logger log = LoggerFactory.getLogger(WebLoginController.class);

  @Autowired EstatisticasService estatisticasService;

  public WebEstatisticasController() {
    super();
  }

  @GetMapping("/estatisticas")
  public String obterEstatisticas(final Model model, HttpSession session) {
    Long id = (Long) session.getAttribute("userId");
    if (id == null) {
      return "redirect:/login";
    }

    model.addAttribute("isDocente", session.getAttribute("isDocente"));
    model.addAttribute("isAdmin", session.getAttribute("isAdmin"));

    System.out.println("\n\n\nObter estatisticas\n\n\n");
    EstatisticasInfo info = estatisticasService.obterEstatisticasAlunos();
    System.out.println("\n\n\ninfo: " + info + "\n\n\n");

    model.addAttribute("info", info);

    return "estatisticas";
  }
}
