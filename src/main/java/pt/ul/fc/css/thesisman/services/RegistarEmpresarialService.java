package pt.ul.fc.css.thesisman.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ul.fc.css.thesisman.handlers.use_case.RegistarEmpresarialHandler;

@Service
public class RegistarEmpresarialService {

  @Autowired private RegistarEmpresarialHandler registarEmpresarialHandler;

  public RegistarEmpresarialService() {
    super();
  }

  public void registarEmpresarial(String nome, String email, String password, String descricao) {
    registarEmpresarialHandler.registarEmpresarial(nome, email, password, descricao);
  }
}
