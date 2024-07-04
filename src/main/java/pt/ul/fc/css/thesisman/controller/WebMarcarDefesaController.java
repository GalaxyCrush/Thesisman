package pt.ul.fc.css.thesisman.controller;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pt.ul.fc.css.thesisman.dtos.TeseDTO;
import pt.ul.fc.css.thesisman.services.MarcacaoDefesaService;

@Controller
public class WebMarcarDefesaController {

  private static final Logger log = LoggerFactory.getLogger(WebMarcarDefesaController.class);
  @Autowired MarcacaoDefesaService marcacaoDefesaService;

  public WebMarcarDefesaController() {
    super();
  }

  @GetMapping("/listar-teses")
  public String listarTeses(final Model model, RedirectAttributes rd, HttpSession session) {
    System.out.println("\n\n\n listar-teses \n");
    System.out.println("sessao id: " + session.getAttribute("userId") + "\n\n\n");

    Long id = (Long) session.getAttribute("userId");
    if (id == null) {
      return "redirect:/login";
    }

    System.out.println("ID: " + id + "\n\n\n");

    try {
      List<TeseDTO> teses = marcacaoDefesaService.listarTesesDeOrientador(id);
      model.addAttribute("isDocente", session.getAttribute("isDocente"));
      model.addAttribute("isAdmin", session.getAttribute("isAdmin"));
      model.addAttribute("teses", teses);
      return "listar_teses";
    } catch (Exception e) {
      rd.addFlashAttribute("error", e.getMessage());
      return "redirect:/menu";
    }
  }

  @PostMapping("selecionar-tese")
  public String selecionarTese(
      final Model model,
      @RequestParam("selectedTese") String teseId_status,
      RedirectAttributes redirectAttributes,
      HttpSession session) {

    Long id = (Long) session.getAttribute("userId");
    if (id == null) {
      return "redirect:/login";
    }

    if (teseId_status == null || teseId_status.isEmpty()) {
      redirectAttributes.addFlashAttribute("error", "Tese não selecionada");
      return "redirect:/listar_teses";
    }

    String[] teseInfo = teseId_status.split("__");
    Long teseId = Long.parseLong(teseInfo[0]);
    String teseStatus = teseInfo[1];

    if (teseStatus.equals("Proposta Entregue")) {
      redirectAttributes.addFlashAttribute("teseId", teseId);
      return "redirect:/marcar-defesa-proposta";
    } else if (teseStatus.equals("Final Entregue")) {
      redirectAttributes.addFlashAttribute("teseId", teseId);
      return "redirect:/marcar-defesa-final";
    }

    redirectAttributes.addFlashAttribute("error", "Tese não selecionada");
    return "redirect:/menu";
  }

  @GetMapping("/marcar-defesa-proposta")
  public String prepararFormProposta(
      final Model model, @ModelAttribute("teseId") Long teseId, HttpSession session) {

    Long id = (Long) session.getAttribute("userId");
    if (id == null) {
      return "redirect:/login";
    }

    model.addAttribute("isDocente", session.getAttribute("isDocente"));
    model.addAttribute("isAdmin", session.getAttribute("isAdmin"));
    model.addAttribute("salas", marcacaoDefesaService.listarSalas());
    model.addAttribute(
        "docentes",
        marcacaoDefesaService.listarOutrosDocentes((Long) session.getAttribute("userId")));
    model.addAttribute("teseId", teseId);

    return "marcar_defesa_proposta";
  }

  @GetMapping("marcar-defesa-final")
  public String prepararFormFinal(
      final Model model, @ModelAttribute("teseId") Long teseId, HttpSession session) {

    Long id = (Long) session.getAttribute("userId");
    if (id == null) {
      return "redirect:/login";
    }

    model.addAttribute("isDocente", session.getAttribute("isDocente"));
    model.addAttribute("isAdmin", session.getAttribute("isAdmin"));
    model.addAttribute("salas", marcacaoDefesaService.listarSalas());
    model.addAttribute(
        "docentes",
        marcacaoDefesaService.listarOutrosDocentes((Long) session.getAttribute("userId")));
    model.addAttribute("teseId", teseId);

    return "marcar_defesa_final";
  }

  @PostMapping("/marcar-defesa-proposta")
  public String marcarDefesaProposta(
      final Model model,
      @RequestParam("teseId") Long teseId,
      @RequestParam("tipoPresenca") String tipoPresenca,
      @RequestParam("salaId") Long salaId,
      @RequestParam("data") String data,
      @RequestParam("horaInicio") String horaInicio,
      @RequestParam("arguenteId") Long arguenteId,
      HttpSession session) {

    Long id = (Long) session.getAttribute("userId");
    if (id == null) {
      return "redirect:/login";
    }

    if (tipoPresenca.equals("REMOTA")) {
      salaId = -1L;
    }

    try {
      marcacaoDefesaService.marcarDefesaProposta(teseId, salaId, data, horaInicio, id, arguenteId);
    } catch (Exception e) {
      e.printStackTrace();
      model.addAttribute("salas", marcacaoDefesaService.listarSalas());
      model.addAttribute(
          "docentes",
          marcacaoDefesaService.listarOutrosDocentes((Long) session.getAttribute("userId")));
      model.addAttribute("teseId", teseId);
      model.addAttribute("isDocente", session.getAttribute("isDocente"));
      model.addAttribute("isAdmin", session.getAttribute("isAdmin"));
      model.addAttribute("error", e.getMessage());
      return "marcar_defesa_proposta";
    }

    return "redirect:/listar-teses";
  }

  @PostMapping("/marcar-defesa-final")
  public String marcarDefesaFinal(
      final Model model,
      @RequestParam("teseId") Long teseId,
      @RequestParam("tipoPresenca") String tipoPresenca,
      @RequestParam("salaId") Long salaId,
      @RequestParam("data") String data,
      @RequestParam("horaInicio") String horaInicio,
      @RequestParam("arguenteId") Long arguenteId,
      @RequestParam("presidenteJuriId") Long presidenteJuriId,
      HttpSession session) {

    Long id = (Long) session.getAttribute("userId");
    if (id == null) {
      return "redirect:/login";
    }

    if (tipoPresenca.equals("REMOTA")) {
      salaId = -1L;
    }

    try {
      marcacaoDefesaService.marcarDefesaFinal(
          teseId, salaId, data, horaInicio, id, arguenteId, presidenteJuriId);
    } catch (Exception e) {
      e.printStackTrace();
      model.addAttribute("salas", marcacaoDefesaService.listarSalas());
      model.addAttribute(
          "docentes",
          marcacaoDefesaService.listarOutrosDocentes((Long) session.getAttribute("userId")));
      model.addAttribute("teseId", teseId);
      model.addAttribute("isDocente", session.getAttribute("isDocente"));
      model.addAttribute("isAdmin", session.getAttribute("isAdmin"));
      model.addAttribute("error", e.getMessage());
      return "marcar_defesa_final";
    }

    return "redirect:/listar-teses";
  }
}
