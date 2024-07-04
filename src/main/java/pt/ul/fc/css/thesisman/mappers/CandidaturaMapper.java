package pt.ul.fc.css.thesisman.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pt.ul.fc.css.thesisman.dtos.CandidaturaDTO;
import pt.ul.fc.css.thesisman.entities.Candidatura;

@Component
public class CandidaturaMapper {

    @Autowired
    AlunoMapper alunoMapper;

    public CandidaturaDTO toDTO(Candidatura d) {
        return new CandidaturaDTO(d.getId(), d.getTema().getTitulo(), alunoMapper.toDTO(d.getAluno()),
                d.isAceite());
    }

}
