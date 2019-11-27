package com.example.projetotcc10.Modelo;

import android.icu.text.SimpleDateFormat;

import java.util.Date;

public class Mensagem  {

    public Mensagem(String id,String mensagem, String adminMsg, String turmaMensagem, String dataMensagem , long timeMessage){

        this.id = id;
        this.mensagem = mensagem;
        this.adminMsg = adminMsg;
        this.turmaMensagem = turmaMensagem;
        this.dataMensagem = dataMensagem;
        this.timeMassage = timeMessage;
    }

    private String id;
    private long timeMassage;
    private String mensagem;



    private String adminMsg;
    private String turmaMensagem;
    private String dataMensagem;

    public String getDataMensagem()
    {
        return dataMensagem;
    }

    public void setDataMensagem(String dataMensagem)
    {
        this.dataMensagem = dataMensagem;
    }

    public String getTurmaMensagem()
    {
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


}
