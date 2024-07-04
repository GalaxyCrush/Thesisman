package pt.ul.fc.css.thesisman.handlers.use_case;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pt.ul.fc.css.thesisman.entities.AbsTema;
import pt.ul.fc.css.thesisman.entities.Mestrado;
import pt.ul.fc.css.thesisman.repositories.TemaRepositorio;

@Component
public class ListarTemasHandler {

  @Autowired private TemaRepositorio temaRepositorio;

  public List<AbsTema> listarTemasAnoLetivo(String anoLetivo) {
    return temaRepositorio.findAllByAno(anoLetivo).stream()
        .filter(AbsTema::isDisponivel)
        .collect(Collectors.toList());
  }

  public List<AbsTema> listarTemasDisponiveisParaMestrado(Mestrado m) {
    return temaRepositorio.findAllCompativeis(m);
  }
}
