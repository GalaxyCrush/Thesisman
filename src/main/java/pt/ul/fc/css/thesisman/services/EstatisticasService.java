package pt.ul.fc.css.thesisman.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ul.fc.css.thesisman.dtos.EstatisticasInfo;
import pt.ul.fc.css.thesisman.handlers.use_case.EstatisticasHandler;

@Service
public class EstatisticasService {

  @Autowired private EstatisticasHandler estatisticasHandler;

  public EstatisticasService() {
    super();
  }

  public EstatisticasInfo obterEstatisticasAlunos() {
    return estatisticasHandler.obterEstatisticasAlunos();
  }
}
