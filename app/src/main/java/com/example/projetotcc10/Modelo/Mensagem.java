package com.example.projetotcc10.Modelo;

import android.icu.text.SimpleDateFormat;

import java.util.Date;

public class Mensagem  {

    public Mensagem(String mensagem, String professor, String turmaMensagem, String dataMensagem){

        this.mensagem = mensagem; this.professor = professor;
      this.turmaMensagem = turmaMensagem;
      this.dataMensagem = dataMensagem;
    }

    private String mensagem;
    private String professor;
    private String turmaMensagem;
    private String dataMensagem;

    public String getDataMensagem() {
        return dataMensagem;
    }

    public void setDataMensagem(String dataMensagem) {
        this.dataMensagem = dataMensagem;
    }

    public String getTurmaMensagem() {
    return turmaMensagem;
   }

 public void setTurmaMensagem(String turmaMensagem) {
    this.turmaMensagem = turmaMensagem;
 }

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
