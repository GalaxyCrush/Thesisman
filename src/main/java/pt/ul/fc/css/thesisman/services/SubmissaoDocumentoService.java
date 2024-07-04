package pt.ul.fc.css.thesisman.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pt.ul.fc.css.thesisman.dtos.EntregaDTO;
import pt.ul.fc.css.thesisman.handlers.use_case.SubmissaoDocumentoHandler;
import pt.ul.fc.css.thesisman.mappers.EntregaMapper;

@Service
public class SubmissaoDocumentoService {

  @Autowired
  private SubmissaoDocumentoHandler submissaoDocumentoHandler;

  @Autowired
  private EntregaMapper entregaMapper;

  public SubmissaoDocumentoService() {
    super();
  }

  public List<EntregaDTO> submeterDocumentoPropostaTese(Long alunoId, byte[] fc, String fileName) {
    return submissaoDocumentoHandler.submeterDocumentoPropostaTese(alunoId, fc, fileName).stream()
        .map(entregaMapper::toDTO).toList();
  }

  public EntregaDTO submeterDocumentoFinalTese(Long alunoId, byte[] fc, String fileName) {
    return entregaMapper.toDTO(submissaoDocumentoHandler.submeterDocumentoFinalTese(alunoId, fc, fileName));
  }
}
