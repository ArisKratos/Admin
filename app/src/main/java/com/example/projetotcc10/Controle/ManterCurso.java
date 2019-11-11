package com.example.projetotcc10.Controle;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projetotcc10.Modelo.Curso;
import com.example.projetotcc10.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ManterCurso extends AppCompatActivity {


   // ListView listCursos;
   private EditText aliasCurso;
    private Button aliasBtnCadastrarCurso;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manter_curso);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Cadastrar curso");

        aliasCurso= findViewById(R.id.editNomeCurso);
        aliasBtnCadastrarCurso = findViewById(R.id.buttonCadastrarCurso);



        aliasBtnCadastrarCurso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveCursoInFirebase();

                Intent intent = new Intent(ManterCurso.this, Listar_Curso.class);
                startActivity(intent);

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


    private void saveCursoInFirebase() {

        String uid = FirebaseAuth.getInstance().getUid();

        String nomeCurso = aliasCurso.getText().toString();



        Curso curso = new Curso();
        curso.setId(uid);
        curso.setCurso(nomeCurso);
        FirebaseFirestore.getInstance().collection("cursos")
                .add(curso)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        Log.i ("Teste \n", documentReference.getId());

                        Intent intent = new Intent(ManterCurso.this, Listar_Curso.class);
                        startActivity(intent);

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("Teste \n", e.getMessage());
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
