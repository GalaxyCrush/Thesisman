package pt.ul.fc.css.thesisman.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pt.ul.fc.css.thesisman.exceptions.ParametroException;
import pt.ul.fc.css.thesisman.services.RegistarEmpresarialService;

@Controller
public class WebRegistarEmpresarialController {

  private static final Logger log = LoggerFactory.getLogger(WebRegistarEmpresarialController.class);
  @Autowired RegistarEmpresarialService registarEmpresarialService;

  public WebRegistarEmpresarialController() {
    super();
  }

  @GetMapping("/criar-empresarial")
  public String criarEmpresarial() {
    return "criar_empresarial";
  }

  @PostMapping("/criar-empresarial")
  public String registarEmpresarial(
      final Model model,
      @RequestParam("nome") String nome,
      @RequestParam("email") String email,
      @RequestParam("password") String password,
      @RequestParam("descricao") String descricao) {

    // FIXME - precisamos verificar se é um email?
    try {
      registarEmpresarialService.registarEmpresarial(nome, email, password, descricao);
    } catch (ParametroException e) {
      model.addAttribute("error", e.getMessage());
      return "criar_empresarial";
    }
    return "redirect:/menu";
  }
}
