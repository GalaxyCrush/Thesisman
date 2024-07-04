package pt.ul.fc.css.thesisman.dtos;

import java.util.List;

public class RespostaLoginAluno {

    private AlunoDTO aluno;

    private List<CandidaturaDTO> candidaturas;

    private TeseDTO tese;

    protected RespostaLoginAluno() {
    }

    public RespostaLoginAluno(AlunoDTO aluno, TeseDTO tese,
            List<CandidaturaDTO> candidaturas) {
        this.aluno = aluno;
        this.tese = tese;
        this.candidaturas = candidaturas;
    }

    public AlunoDTO getAluno() {
        return aluno;
    }

    public TeseDTO getTese() {
        return tese;
    }

    public List<CandidaturaDTO> getCandidaturas() {
        return candidaturas;
    }

    public void setAluno(AlunoDTO aluno) {
        this.aluno = aluno;
    }

    public void setTese(TeseDTO tese) {
        this.tese = tese;
    }

    public void setCandidaturas(List<CandidaturaDTO> candidaturas) {
        this.candidaturas = candidaturas;
    }

}
