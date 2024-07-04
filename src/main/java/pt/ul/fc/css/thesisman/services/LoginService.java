package pt.ul.fc.css.thesisman.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pt.ul.fc.css.thesisman.dtos.CandidaturaDTO;
import pt.ul.fc.css.thesisman.dtos.RespostaLogin;
import pt.ul.fc.css.thesisman.dtos.RespostaLoginAluno;
import pt.ul.fc.css.thesisman.entities.Aluno;
import pt.ul.fc.css.thesisman.entities.Candidatura;
import pt.ul.fc.css.thesisman.entities.CriadorTema;
import pt.ul.fc.css.thesisman.handlers.use_case.LoginHandler;
import pt.ul.fc.css.thesisman.mappers.AlunoMapper;
import pt.ul.fc.css.thesisman.mappers.TeseMapper;

@Service
public class LoginService {

    @Autowired
    private LoginHandler loginHandler;

    @Autowired
    private AlunoMapper alunoMapper;

    @Autowired
    private TeseMapper teseMapper;

    public LoginService() {
        super();
    }

    public RespostaLogin loginCriadorTema(String email, String password) {
        CriadorTema c = loginHandler.loginCriadorTema(email, password);
        boolean isAdmin = loginHandler.isAdmin(c.getId());
        boolean isDocente = loginHandler.isDocente(c.getId());
        return new RespostaLogin(c.getId(), isAdmin, isDocente);
    }

    public RespostaLoginAluno loginAluno(String email, String password) {
        Aluno a = loginHandler.loginAluno(email, password);
        if (a.getTese() != null) {
            return new RespostaLoginAluno(alunoMapper.toDTO(a), teseMapper.toDTO(a.getTese()), null);
        } else {
            List<CandidaturaDTO> candidaturas = new ArrayList<>();
            for (Candidatura c : a.getCandidaturas()) {
                candidaturas.add(new CandidaturaDTO(c.getId(), c.getTema().getTitulo(), alunoMapper.toDTO(c.getAluno()),
                        c.isAceite()));
            }
            return new RespostaLoginAluno(alunoMapper.toDTO(a), null, candidaturas);
        }
    }
}
