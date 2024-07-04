package pt.ul.fc.css.thesisman.services;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ul.fc.css.thesisman.dtos.DefesaDTO;
import pt.ul.fc.css.thesisman.entities.Defesa;
import pt.ul.fc.css.thesisman.handlers.use_case.RegistarNotaDefesaHandler;
import pt.ul.fc.css.thesisman.mappers.DefesaMapper;

@Service
public class RegistarNotaDefesaService {

  @Autowired private RegistarNotaDefesaHandler registarNotaDefesaHandler;

  @Autowired private DefesaMapper defesaMapper;

  public RegistarNotaDefesaService() {
    super();
  }

  public void registarNotaDefesa(Long defesaId, int nota, Long docenteId) {
    registarNotaDefesaHandler.registarNotaDefesa(defesaId, nota, docenteId);
  }

  public List<DefesaDTO> listarDefesasParaAvaliar(Long id) {
    List<Defesa> paraAvaliar = registarNotaDefesaHandler.listarDefesasParaAvaliar(id);
    return paraAvaliar.stream().map(d -> defesaMapper.toDTO(d)).collect(Collectors.toList());
  }
}
