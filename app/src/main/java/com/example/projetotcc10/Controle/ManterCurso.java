package com.example.projetotcc10.Controle;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.projetotcc10.Modelo.Curso;
import com.example.projetotcc10.R;

import java.util.List;

public class ManterCurso extends AppCompatActivity {


   // ListView listCursos;
   private EditText aliasEditCurso;
    private Button aliasBtnCadastrarCurso;
    private List<Curso> cursos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manter_curso);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Cadastrar curso");

        aliasEditCurso= findViewById(R.id.editNomeCurso);
        aliasBtnCadastrarCurso = findViewById(R.id.buttonCadastrarCurso);



        aliasBtnCadastrarCurso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext() ,"Curso cadastrado com sucesso!" , Toast.LENGTH_SHORT).show();


            }
        });


        FloatingActionButton voltar = findViewById(R.id.buttonActionVoltar);
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),Listar_Curso.class);
                startActivity(intent);
            }
        });

    }

    /* private void limparCampos() {
        curso.setText("");
    }

    private void carregalistview(){

        ArrayAdapter<Curso> adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cursos);
        listCursos.setAdapter(adaptador);
        adaptador.notifyDataSetChanged();

    }*/

    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                startActivity(new Intent(this, Admin.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            default:break;
        }
        return true;
    }
}
