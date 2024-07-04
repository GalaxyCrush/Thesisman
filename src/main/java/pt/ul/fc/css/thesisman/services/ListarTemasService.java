package pt.ul.fc.css.thesisman.services;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ul.fc.css.thesisman.dtos.TemaDTO;
import pt.ul.fc.css.thesisman.entities.AbsTema;
import pt.ul.fc.css.thesisman.handlers.use_case.ListarTemasHandler;
import pt.ul.fc.css.thesisman.mappers.TemaMapper;

@Service
public class ListarTemasService {

  @Autowired private ListarTemasHandler listarTemasHandler;
  @Autowired private TemaMapper temaMapper;

  public ListarTemasService() {
    super();
  }

  public List<TemaDTO> listarTemasAnoLetivo(String anoLetivo) {
    List<AbsTema> temas = listarTemasHandler.listarTemasAnoLetivo(anoLetivo);
    return temas.stream().map(t -> temaMapper.toDTO(t)).collect(Collectors.toList());
  }
}
