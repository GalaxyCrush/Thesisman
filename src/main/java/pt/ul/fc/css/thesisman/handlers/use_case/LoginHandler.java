package pt.ul.fc.css.thesisman.handlers.use_case;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pt.ul.fc.css.thesisman.entities.Aluno;
import pt.ul.fc.css.thesisman.entities.CriadorTema;
import pt.ul.fc.css.thesisman.exceptions.LoginException;
import pt.ul.fc.css.thesisman.exceptions.ParametroException;
import pt.ul.fc.css.thesisman.repositories.AlunoRepositorio;
import pt.ul.fc.css.thesisman.repositories.CriadorTemaRepositorio;
import pt.ul.fc.css.thesisman.repositories.MestradoRepositorio;

@Component
public class LoginHandler {

  @Autowired private AlunoRepositorio alunoRepositorio;

  @Autowired private CriadorTemaRepositorio criadorTemaRepositorio;

  @Autowired private MestradoRepositorio mestradoRepositorio;

  @Transactional(readOnly = true)
  public CriadorTema loginCriadorTema(String email, String password) {
    if (email.isEmpty()) {
      throw new ParametroException("Preencha os campos de login");
    }
    CriadorTema c =
        criadorTemaRepositorio
            .findByEmail(email)
            .orElseThrow(
                () ->
                    new LoginException(
                        "Não existe nenhum empresarial/docente com o email " + email));
    return c;
  }

  @Transactional(readOnly = true)
  public Aluno loginAluno(String email, String password) {
    if (email.isEmpty()) {
      throw new ParametroException("Preencha os campos de login");
    }
    Aluno aluno =
        alunoRepositorio
            .findByEmail(email)
            .orElseThrow(() -> new LoginException("Não existe nenhum aluno com o email " + email));
    return aluno;
  }

  public boolean isDocente(Long id) {
    return criadorTemaRepositorio.existsDocenteById(id);
  }

  public boolean isAdmin(Long id) {
    return mestradoRepositorio.existsAdminById(id);
  }
}
