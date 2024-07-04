package pt.ul.fc.css.thesisman.handlers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import pt.ul.fc.css.thesisman.entities.Candidatura;
import pt.ul.fc.css.thesisman.repositories.CandidaturaRepositorio;

@Component
public class ListarCandidaturasHandler {

    @Autowired
    private CandidaturaRepositorio candidaturaRepositorio;

    @Transactional(readOnly = true)
    public List<Candidatura> listarCandidaturasAluno(Long alunoId) {
        return candidaturaRepositorio.findAllByAlunoId(alunoId);
    }
}
