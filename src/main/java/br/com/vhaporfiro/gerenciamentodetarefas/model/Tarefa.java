package br.com.vhaporfiro.gerenciamentodetarefas.model;

import java.time.LocalDate;

public class Tarefa {
    private int id;
    private String titulo;
    private String descricao;
    private LocalDate dataEntrega;
    private String status;

    public Tarefa() {
    }

    public Tarefa(String titulo, String descricao, LocalDate dataEntrega, String status) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataEntrega = dataEntrega;
        this.status = status;
    }

    public Tarefa(int id, String titulo, String descricao, LocalDate dataEntrega, String status) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataEntrega = dataEntrega;
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
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public LocalDate getDataEntrega() {
        return dataEntrega;
    }
    public void setDataEntrega(LocalDate dataEntrega) {
        this.dataEntrega = dataEntrega;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        String dataStr = "";
        if (dataEntrega != null) {
            dataStr = dataEntrega.format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        }
        return "Tarefa: " + titulo + "\nDescrição: " + descricao + "\nData de entrega: " + dataStr + "\nStatus: (" + status + ")";
    }

}
