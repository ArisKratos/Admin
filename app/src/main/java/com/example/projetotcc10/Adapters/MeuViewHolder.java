package com.example.projetotcc10.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.projetotcc10.R;

public class MeuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

 final TextView mensagem;
 final TextView remetente;
 final TextView data;
 final TextView hora;
 final TextView turma;
 final TextView semestre;

 ItemClickListener itemClickListener;


 public MeuViewHolder(View view) {
     super(view);


     hora = (TextView)
     view.findViewById(R.id.horaMsg);

     semestre = (TextView)
             view.findViewById(R.id.semestreMsg);

     mensagem = (TextView)
             view.findViewById(R.id.mensagem_remetente);

     remetente = (TextView)
             view.findViewById(R.id.nome_remetente);

     data = (TextView)
             view.findViewById(R.id.dataMsg);

     turma= (TextView)
             view.findViewById(R.id.turmaAnoMsg);




     itemView.setOnClickListener(this);

 }


    @Override
    public void onClick(View v) {
         this.itemClickListener.onItemClickListener(v, getLayoutPosition());
    }

    public void setItemClickListener(ItemClickListener ic){
     this.itemClickListener = ic;
    }
}
