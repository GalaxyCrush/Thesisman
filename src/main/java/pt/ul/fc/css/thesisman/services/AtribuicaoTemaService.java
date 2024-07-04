package pt.ul.fc.css.thesisman.services;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ul.fc.css.thesisman.dtos.AlunoDTO;
import pt.ul.fc.css.thesisman.dtos.AtribuicaoTemaInfo;
import pt.ul.fc.css.thesisman.dtos.CriadorTemaDTO;
import pt.ul.fc.css.thesisman.dtos.TemaDTO;
import pt.ul.fc.css.thesisman.entities.AbsTema;
import pt.ul.fc.css.thesisman.entities.Aluno;
import pt.ul.fc.css.thesisman.entities.Docente;
import pt.ul.fc.css.thesisman.entities.Mestrado;
import pt.ul.fc.css.thesisman.handlers.ListarDocentesHandler;
import pt.ul.fc.css.thesisman.handlers.use_case.AtribuicaoTemaHandler;
import pt.ul.fc.css.thesisman.handlers.use_case.ListarTemasHandler;
import pt.ul.fc.css.thesisman.mappers.AlunoMapper;
import pt.ul.fc.css.thesisman.mappers.CriadorTemaMapper;
import pt.ul.fc.css.thesisman.mappers.TemaMapper;

@Service
public class AtribuicaoTemaService {

  @Autowired private AtribuicaoTemaHandler atribuicaoTemaHandler;

  @Autowired private ListarDocentesHandler listarDocentesHandler;

  @Autowired private ListarTemasHandler listarTemasHandler;

  @Autowired private AlunoMapper alunoMapper;

  @Autowired private TemaMapper temaMapper;

  @Autowired private CriadorTemaMapper criadorTemaMapper;

  public AtribuicaoTemaService() {
    super();
  }

  public void atribuirTemaParaAluno(Long alunoId, Long temaId, Long docenteId) {
    atribuicaoTemaHandler.atribuirTemaParaAluno(alunoId, temaId, docenteId);
  }

  public void atribuirTemaParaAluno(Long alunoId, Long temaId) {
    atribuicaoTemaHandler.atribuirTemaParaAluno(alunoId, temaId);
  }

  public AtribuicaoTemaInfo obterInfoAtribuicaoTemas(Long id) {
    Mestrado m = obterMestradoAdmin(id);
    List<TemaDTO> temas = listarTemasDisponiveisParaMestrado(m);
    List<AlunoDTO> alunos = listarAlunosDeMestradoSemTema(m);
    List<CriadorTemaDTO> docentes = listarDocentes();
    return new AtribuicaoTemaInfo(temas, alunos, docentes);
  }

  private Mestrado obterMestradoAdmin(Long adminId) {
    return atribuicaoTemaHandler.obterMestradoAdmin(adminId);
  }

  private List<TemaDTO> listarTemasDisponiveisParaMestrado(Mestrado m) {
    List<AbsTema> temas = listarTemasHandler.listarTemasDisponiveisParaMestrado(m);
    return temas.stream().map(t -> temaMapper.toDTO(t)).collect(Collectors.toList());
  }

  private List<AlunoDTO> listarAlunosDeMestradoSemTema(Mestrado m) {
    List<Aluno> alunos = atribuicaoTemaHandler.listarAlunosDeMestradoSemTema(m);
    return alunos.stream().map(a -> alunoMapper.toDTO(a)).collect(Collectors.toList());
  }

  private List<CriadorTemaDTO> listarDocentes() {
    List<Docente> docentes = listarDocentesHandler.listarDocentes();
    return docentes.stream().map(d -> criadorTemaMapper.toDTO(d)).collect(Collectors.toList());
  }

  public boolean hasOrientador(Long temaId) {
    return atribuicaoTemaHandler.hasOrientador(temaId);
  }
}
