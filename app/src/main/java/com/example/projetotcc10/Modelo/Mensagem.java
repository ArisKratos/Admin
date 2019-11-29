package com.example.projetotcc10.Modelo;

import android.icu.text.SimpleDateFormat;

import java.util.Date;

public class Mensagem  {

    public Mensagem(String id, String idAdmin,String mensagem, String adminMsg, String turmaAnoMensagem, String semestreMensagem, String dataMensagem , long timeMessage,
    boolean paraTodos, boolean mudancaHorario){



        this.id = id;
        this.idAdmin = idAdmin;
        // fazaer o boolean e só
        this.mensagem = mensagem;
        this.adminMsg = adminMsg;
        this.turmaAnoMensagem = turmaAnoMensagem;
        this.semestreMensagem = semestreMensagem;
        this.dataMensagem = dataMensagem;
        this.timeMassage = timeMessage;
        this.paraTodos = paraTodos;
        this.mudancaHorario = mudancaHorario;
    }

    private String id;
    private String idAdmin;
    private long timeMassage;
    private String mensagem;

    private String adminMsg;



    public boolean isMudancaHorario() {
        return mudancaHorario;
    }

    public void setMudancaHorario(boolean mudancaHorario) {
        this.mudancaHorario = mudancaHorario;
    }

    private String turmaAnoMensagem;
    private String semestreMensagem;
    private String dataMensagem;
    private boolean paraTodos;
    private boolean mudancaHorario;
    public String getDataMensagem()
    {
        return dataMensagem;
    }

    public void setDataMensagem(String dataMensagem)
    {
        this.dataMensagem = dataMensagem;
    }

    public String getTurmaAnoMensagem()
    {
    return turmaAnoMensagem;
   }

   public void setTurmaAnoMensagem(String turmaAnoMensagem) {
    this.turmaAnoMensagem = turmaAnoMensagem;
   }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }


    public long getTimeMassage() {
        return timeMassage;
    }

    public void setTimeMassage(long timeMassage) {
        this.timeMassage = timeMassage;
    }

    public String getAdminMsg() {
        return adminMsg;
    }

    public void setAdminMsg(String adminMsg) {
        this.adminMsg = adminMsg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSemestreMensagem() {
        return semestreMensagem;
    }

    public void setSemestreMensagem(String semestreMensagem) {
        this.semestreMensagem = semestreMensagem;
    }
    public boolean isParaTodos() {
        return paraTodos;
    }

    public void setParaTodos(boolean paraTodos) {
        this.paraTodos = paraTodos;
    }

    public String getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(String idAdmin) {
        this.idAdmin = idAdmin;
    }
}
