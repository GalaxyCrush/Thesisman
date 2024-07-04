package pt.ul.fc.css.thesisman.handlers;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pt.ul.fc.css.thesisman.entities.Docente;
import pt.ul.fc.css.thesisman.repositories.CriadorTemaRepositorio;

@Component
public class ListarDocentesHandler {

  @Autowired private CriadorTemaRepositorio criadorTemaRepositorio;

  @Transactional(readOnly = true)
  public List<Docente> listarDocentes() {
    return criadorTemaRepositorio.findAllDocentes().stream()
        .map(c -> (Docente) c)
        .collect(Collectors.toList());
  }
}
