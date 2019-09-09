package com.example.projetotcc10.Controle;

public class Mensagem  {

    public Mensagem(String mensagem, String professor){
        this.mensagem = mensagem; this.professor = professor;
    }

    private String mensagem;
    private String professor;

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String nomeProfessor) {
        this.professor = nomeProfessor;
    }



}
