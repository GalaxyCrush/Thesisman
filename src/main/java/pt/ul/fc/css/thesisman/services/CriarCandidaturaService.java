package pt.ul.fc.css.thesisman.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pt.ul.fc.css.thesisman.handlers.use_case.CriarCandidaturaHandler;
import pt.ul.fc.css.thesisman.mappers.CandidaturaMapper;

@Service
public class CriarCandidaturaService {

  @Autowired
  private CriarCandidaturaHandler criarCandidaturaHandler;

  @Autowired
  private CandidaturaMapper candidaturaMapper;

  public CriarCandidaturaService() {
    super();
  }

  public void criarCandidatura(Long alunoId, Long temaId) {
    criarCandidaturaHandler.criarCandidatura(alunoId, temaId);
  }
}
