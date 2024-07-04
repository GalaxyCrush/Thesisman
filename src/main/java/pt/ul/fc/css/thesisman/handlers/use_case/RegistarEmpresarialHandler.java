package pt.ul.fc.css.thesisman.handlers.use_case;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pt.ul.fc.css.thesisman.entities.Empresarial;
import pt.ul.fc.css.thesisman.exceptions.ParametroException;
import pt.ul.fc.css.thesisman.exceptions.RegistarEmpresarialException;
import pt.ul.fc.css.thesisman.repositories.CriadorTemaRepositorio;

@Component
public class RegistarEmpresarialHandler {

  @Autowired private CriadorTemaRepositorio criadorTemaRepositorio;

  private static final int MAX_RETRIES = 3;

  @Transactional
  public void registarEmpresarial(String nome, String email, String password, String descricao) {

    if (!isFilled(nome)) throw new ParametroException("Username inválido");
    if (!isFilled(email)) throw new ParametroException("Email inválido");
    if (!isFilled(password)) throw new ParametroException("Password is empty");
    if (!isFilled(descricao)) throw new ParametroException("Descricao is empty");
    if (criadorTemaRepositorio.existsByEmail(email))
      throw new ParametroException("Email já se encontra associado a um utilizador");

    int retries = 0;
    boolean successful = false;

    while (!successful && retries < MAX_RETRIES) {
      try {
        Empresarial empresarial = new Empresarial(nome, email, password, descricao);
        criadorTemaRepositorio.save(empresarial);
        successful = true;
      } catch (DataIntegrityViolationException e) {
        throw new ParametroException("Email já se encontra associado a um utilizador");
      } catch (ObjectOptimisticLockingFailureException e) {
        retries++;
        if (retries == MAX_RETRIES) {
          throw new RegistarEmpresarialException(
              "Conflito de registro. Por favor, tente novamente.");
        }
      }
    }
  }

  private boolean isFilled(String param) {
    return param != null && !param.isEmpty();
  }
}
