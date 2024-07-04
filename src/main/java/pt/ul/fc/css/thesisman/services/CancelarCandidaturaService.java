package pt.ul.fc.css.thesisman.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pt.ul.fc.css.thesisman.dtos.CandidaturaDTO;
import pt.ul.fc.css.thesisman.handlers.use_case.CancelarCandidaturaHandler;
import pt.ul.fc.css.thesisman.mappers.CandidaturaMapper;

@Service
public class CancelarCandidaturaService {

  @Autowired
  private CancelarCandidaturaHandler cancelarCandidaturaHandler;
  @Autowired
  private CandidaturaMapper candidaturaMapper;

  public CancelarCandidaturaService() {
    super();
  }

  public List<CandidaturaDTO> cancelarCandidatura(Long candidaturaId) {
    return cancelarCandidaturaHandler.cancelarCandidatura(candidaturaId).stream()
        .map(candidaturaMapper::toDTO)
        .collect(Collectors.toList());
  }
}
