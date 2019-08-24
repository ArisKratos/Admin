package com.example.projetotcc10.Controle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.projetotcc10.Modelo.Curso;
import com.example.projetotcc10.Modelo.Professor;
import com.example.projetotcc10.R;

import java.util.List;

public class ManterProfessor extends AppCompatActivity {


    EditText nomeProfessor, senhaProfessor;
    // ListView listProf;
  //  List<Professor> professores;
    Button buttonCadastarProf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manter_professor);


        buttonCadastarProf = findViewById(R.id.buttonCadastrarProfessor);
        nomeProfessor = findViewById(R.id.editNomeProfessor);
        senhaProfessor = findViewById(R.id.editSenhaProfessor);
       // listProf = findViewById(R.id.ListaProfessores);



        buttonCadastarProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

    }
  /*  private void carregalistview(){

        ArrayAdapter<Professor> adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, professores);
        listProf.setAdapter(adaptador);
        adaptador.notifyDataSetChanged();

    }
    */
}
