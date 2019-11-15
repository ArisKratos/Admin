package com.example.projetotcc10.Controle;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.projetotcc10.Modelo.Curso;
import com.example.projetotcc10.Modelo.Turma;
import com.example.projetotcc10.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Listar_Turma extends AppCompatActivity {

    private ListView aliasListTurma;
    private Spinner aliasSpnCursos;
    private List<Curso> cursos;
    private  Button aliasBtnSeeTurmas;
    private List <Turma> turmas;
    private AlertDialog alerta;
    private final static String TAG  = "Firelog";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Lista turmas");


        setContentView(R.layout.activity_listar_turma);
        aliasListTurma = findViewById(R.id.listTurma);
        aliasSpnCursos = findViewById(R.id.editSpnCurso);
        aliasBtnSeeTurmas = findViewById(R.id.editBtnSeeTurmas);

        cursos = new ArrayList <>();
        turmas = new ArrayList <>();

         carregarSpinnerCurso();

        FloatingActionButton cadastrar = findViewById(R.id.buttonActionCadastrarTurma);
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),ManterIngressoDeTurma.class);
                startActivity(intent);
            }
        });

        aliasBtnSeeTurmas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carregalistview();
            }
        });


        aliasListTurma.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Alerta!");
                builder.setMessage("Deseja mesmo excluir essa turma");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {


                        final Turma turma = turmas.get(position);
                        Curso curso = (Curso) aliasSpnCursos.getSelectedItem();


                        FirebaseFirestore.getInstance().collection("cursos").document(curso.getId()).collection("turmas").document(turma.getId()).delete()
                                .addOnSuccessListener(new OnSuccessListener <Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(Listar_Turma.this, "Turma excluida com sucesso!", Toast.LENGTH_SHORT).show();
                                carregalistview();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Listar_Turma.this, "Erro ao deletar turma", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Toast.makeText(getApplicationContext(), "Ação cancelada", Toast.LENGTH_SHORT).show();
                    }
                });
                alerta = builder.create();
                alerta.show();
                return true;
            }
        });
    }



        public void carregarSpinnerCurso() {

            FirebaseFirestore.getInstance().collection("cursos")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener <QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task <QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                cursos.clear();
                                for (QueryDocumentSnapshot document : task.getResult()) {

                                    String id = document.getId();
                                    String nomeCurso = document.getString("curso");



                                    Curso u = new Curso();
                                    u.setId(id);
                                    u.setCurso(nomeCurso);

                                    cursos.add(u);
                                    Log.d(TAG, nomeCurso);

                                }

                                final ArrayAdapter <Curso> adaptador = new ArrayAdapter <>(getBaseContext(), android.R.layout.simple_spinner_item, cursos);
                                adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                aliasSpnCursos.setAdapter(adaptador);
                                adaptador.notifyDataSetChanged();

                            } else {
                                Log.w(TAG, "Error getting documents.", task.getException());
                            }

                        }
                    });
        }


    @Override
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

    private void carregalistview(){

        Curso curso = (Curso) aliasSpnCursos.getSelectedItem();

        FirebaseFirestore.getInstance().collection("cursos").document(curso.getId()).collection("turmas")
                .get()
                .addOnCompleteListener(new OnCompleteListener <QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task <QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            turmas.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {



                                // nao sei pq ele nao carrega a lista!!!
                                String id = document.getId();
                                String ano = document.getString("ano");
                                String semestre = document.getString("semestre");

                                Turma u = new Turma();

                                u.setId(id);
                                u.setAno(ano);
                                u.setSemestre(semestre);


                                turmas.add(u);
                                Log.d(TAG, id);
                            }

                            ArrayAdapter <Turma> adaptador = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, turmas);
                            aliasListTurma.setAdapter(adaptador);
                            adaptador.notifyDataSetChanged();

                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }

                    }
                });
    }
}
