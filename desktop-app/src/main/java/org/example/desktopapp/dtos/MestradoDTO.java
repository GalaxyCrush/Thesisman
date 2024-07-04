package org.example.desktopapp.dtos;

public class MestradoDTO {

    private Long id;
    private String nome;
    private String departamento;

    protected MestradoDTO() {}

    public MestradoDTO(Long id, String nome, String departamento) {
        this.id = id;
        this.nome = nome;
        this.departamento = departamento;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getDepartamento() {
        return departamento;
    }

    @Override
    public String toString() {
        return "MestradoDTO{" + "id=" + id +
                ", nome='" + nome + '\'' +
                ", departamento='" + departamento + '\'' +
                '}';
    }
}