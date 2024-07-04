package org.example.desktopapp.dtos;

public class AlunoDTO {

    private Long id;
    private String nome;
    private String email;
    private Double media;

    public AlunoDTO() {
    }

    public AlunoDTO(Long id, String nome, String email, Double media) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.media = media;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public Double getMedia() {
        return media;
    }

    @Override
    public String toString() {
        return "AlunoDTO{" + "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", media=" + media +
                '}';
    }
}