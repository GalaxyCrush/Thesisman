package pt.ul.fc.css.thesisman.services;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ul.fc.css.thesisman.dtos.CriadorTemaDTO;
import pt.ul.fc.css.thesisman.dtos.SalaDTO;
import pt.ul.fc.css.thesisman.dtos.TeseDTO;
import pt.ul.fc.css.thesisman.entities.AbsTese;
import pt.ul.fc.css.thesisman.entities.Docente;
import pt.ul.fc.css.thesisman.entities.Sala;
import pt.ul.fc.css.thesisman.handlers.ListarDocentesHandler;
import pt.ul.fc.css.thesisman.handlers.use_case.MarcacaoDefesaHandler;
import pt.ul.fc.css.thesisman.mappers.CriadorTemaMapper;
import pt.ul.fc.css.thesisman.mappers.SalaMapper;
import pt.ul.fc.css.thesisman.mappers.TeseMapper;

@Service
public class MarcacaoDefesaService {

  @Autowired private MarcacaoDefesaHandler marcacaoDefesaHandler;

  @Autowired private ListarDocentesHandler listarDocentesHandler;

  @Autowired private TeseMapper teseMapper;

  @Autowired private SalaMapper salaMapper;

  @Autowired private CriadorTemaMapper criadorTemaMapper;

  public MarcacaoDefesaService() {
    super();
  }

  public void marcarDefesaProposta(
      Long teseId, Long salaId, String data, String horaInicio, Long docenteId, Long arguenteId) {
    marcacaoDefesaHandler.marcarDefesaProposta(
        teseId, salaId, data, horaInicio, docenteId, arguenteId);
  }

  // FIXME - ainda falta fazer a funcao do handler por isso params podem mudar
  public void marcarDefesaFinal(
      Long teseId,
      Long salaId,
      String data,
      String horaInicio,
      Long docenteId,
      Long arguenteId,
      Long presidenteJuriId) {
    marcacaoDefesaHandler.marcarDefesaFinal(
        teseId, salaId, data, horaInicio, docenteId, arguenteId, presidenteJuriId);
  }

  public List<TeseDTO> listarTesesDeOrientador(Long orientadorId) {
    List<AbsTese> teses = marcacaoDefesaHandler.listarTesesDeOrientador(orientadorId);
    System.out.println("\n\n\nteses: " + teses);
    return teses.stream().map(t -> teseMapper.toDTO(t)).collect(Collectors.toList());
  }

  public List<SalaDTO> listarSalas() {
    List<Sala> salas = marcacaoDefesaHandler.listarSalas();

    return salas.stream().map(s -> salaMapper.toDTO(s)).collect(Collectors.toList());
  }

  public List<CriadorTemaDTO> listarOutrosDocentes(Long userId) {
    List<Docente> docentes = listarDocentesHandler.listarDocentes();
    return docentes.stream()
        .filter(c -> !c.getId().equals(userId))
        .map(d -> criadorTemaMapper.toDTO(d))
        .collect(Collectors.toList());
  }
}
