package pt.ul.fc.css.thesisman.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pt.ul.fc.css.thesisman.dtos.RespostaLogin;
import pt.ul.fc.css.thesisman.exceptions.LoginException;
import pt.ul.fc.css.thesisman.exceptions.ParametroException;
import pt.ul.fc.css.thesisman.services.LoginService;
import pt.ul.fc.css.thesisman.services.PopulateService;

@Controller
public class WebLoginController {

  private static final Logger log = LoggerFactory.getLogger(WebLoginController.class);

  @Autowired
  private LoginService loginService;

  @Autowired
  private PopulateService populateService;

  public WebLoginController() {
    super();
  }

  @GetMapping("/")
  public String index() {
    populateService.populate();
    return "login";
  }

  @GetMapping("/login")
  public String login() {
    log.info("GET request to /login");
    return "login";
  }

  @GetMapping("/logout")
  public String logout(HttpServletRequest request) {
    log.info("GET request to /logout");
    HttpSession session = request.getSession(false);
    if (session != null) {
      session.invalidate();
    }
    return "redirect:/login";
  }

  @PostMapping("/login")
  public String login(
      final Model model,
      @RequestParam("email") String email,
      @RequestParam("password") String password,
      HttpSession session) {

    log.info("POST request to /login with email: {}", email);

    if (email.isEmpty()) {
      model.addAttribute("error", "Por favor preencha o email");
      return "login";
    }

    if (password.isEmpty()) {
      model.addAttribute("error", "Por favor preencha a password");
      return "login";
    }

    try {
      RespostaLogin respostaLogin = loginService.loginCriadorTema(email, password);
      session.setAttribute("userId", respostaLogin.getId());
      session.setAttribute("isDocente", respostaLogin.isDocente());
      session.setAttribute("isAdmin", respostaLogin.isAdmin());
      return "redirect:/menu";
    } catch (LoginException | ParametroException e) {
      model.addAttribute("error", e.getMessage());
      return "login";
    }
  }

  @GetMapping("/menu")
  public String menu(final Model model, HttpSession session) {
    log.info("GET request to /menu");
    Long userId = (Long) session.getAttribute("userId");
    if (userId == null) {
      return "redirect:/login";
    }
    model.addAttribute("isAdmin", session.getAttribute("isAdmin"));
    model.addAttribute("isDocente", session.getAttribute("isDocente"));
    return "menu";
  }
}
