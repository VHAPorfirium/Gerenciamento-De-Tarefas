package br.com.vhaporfiro.gerenciamentodetarefas.model;

public class Tarefa {
    private int id;
    private String titulo;
    private String status;

    public Tarefa() {
    }

    public Tarefa(String titulo, String status) {
        this.titulo = titulo;
        this.status = status;
    }

    public Tarefa(int id, String titulo, String status) {
        this.id = id;
        this.titulo = titulo;
        this.status = status;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return id + " - " + titulo + " (" + status + ")";
    }
}

