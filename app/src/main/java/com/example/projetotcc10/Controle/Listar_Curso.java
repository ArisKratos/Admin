



package com.example.projetotcc10.Controle;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.projetotcc10.Modelo.Professor;
import com.example.projetotcc10.R;

import java.util.List;

public class Listar_Curso extends AppCompatActivity {


    private ListView listaCurso;
    private List<Professor> cursos;
    private Button aliasCadastrarCurso;


    private String[] ArrayCursos = new String[]{"Informática\n","Agropecuária\n", "TADS\n","Engenharia\n","Direito\n","Elétrica\n"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_listar_curso);


        listaCurso = findViewById(R.id.listCurso);



        carregalistview();

        FloatingActionButton cadastrar = findViewById(R.id.buttonActionCadastrarCurso);
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),ManterCurso.class);
                startActivity(intent);
            }
        });


       listaCurso.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                // TODO Auto-generated method stub



                listaCurso.getItemAtPosition(position);

                Toast.makeText(getApplicationContext(), "Item Deleted", Toast.LENGTH_LONG).show();

                return true;
            }

        });
    }


    private void carregalistview(){

        ArrayAdapter<String> adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ArrayCursos);
        listaCurso.setAdapter(adaptador);
        adaptador.notifyDataSetChanged();

    }
}
