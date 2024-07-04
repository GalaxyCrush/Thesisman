package pt.ul.fc.css.thesisman.services;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ul.fc.css.thesisman.dtos.CriadorTemaDTO;
import pt.ul.fc.css.thesisman.dtos.MestradoDTO;
import pt.ul.fc.css.thesisman.entities.Docente;
import pt.ul.fc.css.thesisman.entities.Mestrado;
import pt.ul.fc.css.thesisman.handlers.ListarDocentesHandler;
import pt.ul.fc.css.thesisman.handlers.use_case.SubmissaoTemaHandler;
import pt.ul.fc.css.thesisman.mappers.CriadorTemaMapper;
import pt.ul.fc.css.thesisman.mappers.MestradoMapper;

@Service
public class SubmissaoTemaService {

  @Autowired private SubmissaoTemaHandler submissaoTemaHandler;

  @Autowired private ListarDocentesHandler listarDocentesHandler;

  @Autowired private MestradoMapper mestradoMapper;

  @Autowired private CriadorTemaMapper criadorTemaMapper;

  public SubmissaoTemaService() {
    super();
  }

  public void submeterTemaDissertacao(
      String titulo,
      String descricao,
      Double renumeracaoMensal,
      List<Long> mestradosCompativeisIds,
      Long criadorTemaId) {

    submissaoTemaHandler.submeterTemaDissertacao(
        titulo, descricao, renumeracaoMensal, mestradosCompativeisIds, criadorTemaId);
  }

  public void submeterTemaProjeto(
      String titulo,
      String descricao,
      Double renumeracaoMensal,
      List<Long> mestradosCompativeisIds,
      Long criadorTemaId,
      Long docenteId) {

    submissaoTemaHandler.submeterTemaProjeto(
        titulo, descricao, renumeracaoMensal, mestradosCompativeisIds, criadorTemaId, docenteId);
  }

  public List<MestradoDTO> listarMestrados() {
    List<Mestrado> mestrados = submissaoTemaHandler.listarMestrados();
    return mestrados.stream().map(m -> mestradoMapper.toDTO(m)).collect(Collectors.toList());
  }

  public List<CriadorTemaDTO> listarDocentes() {
    List<Docente> docentes = listarDocentesHandler.listarDocentes();

    return docentes.stream().map(d -> criadorTemaMapper.toDTO(d)).collect(Collectors.toList());
  }
}
