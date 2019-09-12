package com.example.projetotcc10.Controle;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.projetotcc10.R;

public class MeuViewHolder extends RecyclerView.ViewHolder {

 final TextView mensagem;
 final TextView professor;
 final TextView data;
 final TextView turma;


 public MeuViewHolder(View view) {
     super(view);
     mensagem = (TextView)
             view.findViewById(R.id.mensagem_Prof);
     professor = (TextView)
             view.findViewById(R.id.nome_Prof);

     data = (TextView)
             view.findViewById(R.id.dataMsg);

     turma= (TextView)
             view.findViewById(R.id.turmaMsg);

 }



}
