package com.example.projetotcc10.Controle;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.projetotcc10.R;

import java.util.ArrayList;
import java.util.List;

public class MeuAdapter extends RecyclerView.Adapter  {


    private List<Mensagem> mensagens;
    private Context context;






    public MeuAdapter(List<Mensagem> m, Context context){


      Mensagem mensagem = new Mensagem("ola", "Marcelo");
        Mensagem mensagem1 = new Mensagem("tudo bem", "Emilio");
//      this.mensagens.add(mensagem);
//      this.mensagens.add(mensagem1);

        this.mensagens = m;
      this.context = context;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.activity_listar_mensagens, parent, false);

        MeuViewHolder holder = new MeuViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        MeuViewHolder holder = (MeuViewHolder) viewHolder;
        Mensagem mensagem = mensagens.get(position);

        holder.professor.setText(mensagem.getProfessor());
        holder.mensagem.setText(mensagem.getMensagem());

    }

    @Override
    public int getItemCount() {
        return mensagens == null ? 0 : mensagens.size();
    }



}


