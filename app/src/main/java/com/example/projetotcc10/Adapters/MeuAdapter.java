package com.example.projetotcc10.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projetotcc10.Controle.Mensagem_Completa;
import com.example.projetotcc10.Modelo.Mensagem;
import com.example.projetotcc10.R;

import java.util.ArrayList;
import java.util.List;

public class MeuAdapter extends RecyclerView.Adapter implements View.OnClickListener  {


    private List<Mensagem> mensagens;
    public Context context;
    private View.OnClickListener listener;

 public MeuAdapter(List<Mensagem> mensagens, Context context){

   this.mensagens = mensagens;
       this.context = context;
  }




    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(context).inflate(R.layout.activity_listar_mensagens, parent, false);

        MeuViewHolder holder = new MeuViewHolder(view);

        view.setOnClickListener(this);

        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {

        MeuViewHolder holder = (MeuViewHolder) viewHolder;
        final Mensagem mensagem = mensagens.get(position);

        holder.mensagem.setText(mensagem.getMensagem());
        holder.professor.setText(mensagem.getAdminMsg());
        holder.turma.setText(mensagem.getTurmaAnoMensagem());
        holder.turma.setText(mensagem.getSemestreMensagem());
        holder.data.setText(mensagem.getDataMensagem());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {
                String gMsg = mensagens.get(position).getMensagem();


                Intent intent = new Intent(context, Mensagem_Completa.class);
                intent.putExtra("Mensagem", gMsg);

                context.startActivity(intent);
            }
        });

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {
                if(mensagens.get(position).getMensagem().equals("3")){
                    Intent intent = new Intent(v.getContext(), Mensagem_Completa.class);
                    context.startActivity(intent);
                }
            }
        });
    }



    @Override
    public int getItemCount() {

        return mensagens == null ? 0 : mensagens.size();
    }
    public void  setOnclickListener(View.OnClickListener listener){
           this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if(listener != null){
            listener.onClick(v);
        }

    }
}


