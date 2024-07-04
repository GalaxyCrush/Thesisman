package org.example.desktopapp.dtos;

public class CandidaturaDTO {

    private Long id;

    private String tema;

    private AlunoDTO aluno;

    private Boolean estado;

    public CandidaturaDTO() {
    }

    public CandidaturaDTO(Long id, String tema, AlunoDTO aluno, Boolean estado) {
        this.id = id;
        this.tema = tema;
        this.aluno = aluno;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public String getTema() {
        return tema;
    }

    public AlunoDTO getAluno() {
        return aluno;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public void setAluno(AlunoDTO aluno) {
        this.aluno = aluno;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "CandidaturaDTO{" + "id=" + id +
                ", tema='" + tema + '\'' +
                ", aluno=" + aluno +
                ", estado=" + estado +
                '}';
    }
}
