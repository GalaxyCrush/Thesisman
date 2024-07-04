package pt.ul.fc.css.thesisman.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pt.ul.fc.css.thesisman.dtos.CandidaturaDTO;
import pt.ul.fc.css.thesisman.entities.Candidatura;
import pt.ul.fc.css.thesisman.handlers.ListarCandidaturasHandler;
import pt.ul.fc.css.thesisman.mappers.CandidaturaMapper;

@Service
public class ListarCandidaturasService {

    @Autowired
    private ListarCandidaturasHandler listarCandidaturasHandler;
    @Autowired
    private CandidaturaMapper candidaturaMapper;

    public ListarCandidaturasService() {
        super();
    }

    public List<CandidaturaDTO> listarCandidaturasAluno(Long alunoId) {
        List<Candidatura> candidaturas = listarCandidaturasHandler.listarCandidaturasAluno(alunoId);
        return candidaturas.stream().map(c -> candidaturaMapper.toDTO(c)).collect(Collectors.toList());
    }
}
