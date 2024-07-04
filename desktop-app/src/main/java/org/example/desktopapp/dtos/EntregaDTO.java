package org.example.desktopapp.dtos;

public class EntregaDTO {

    private Long id;

    private Long teseId;

    private String nomeAluno;

    private String data;

    private byte[] ficheiro;

    private String fileName;

    private String estadoTese;

    private String nota;

    public EntregaDTO() {
    }

    public EntregaDTO(Long id, Long tese, String nomeAluno, String data, byte[] ficheiro, String fileName, String estadoTese, String nota) {
        this.id = id;
        this.teseId = tese;
        this.data = data;
        this.ficheiro = ficheiro;
        this.nomeAluno = nomeAluno;
        this.fileName = fileName;
        this.estadoTese = estadoTese;
        this.nota = nota;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getEstadoTese() {
        return estadoTese;
    }

    public void setEstadoTese(String teseEstado) {
        this.estadoTese = teseEstado;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getFicheiro() {
        return ficheiro;
    }

    public Long getId() {
        return id;
    }

    public Long getTeseId() {
        return teseId;
    }

    public String getData() {
        return data;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTeseId(Long teseId) {
        this.teseId = teseId;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getNomeAluno() {
        return nomeAluno;
    }

    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }

    public void setFicheiro(byte[] ficheiro) {
        this.ficheiro = ficheiro;
    }
}