package com.example.projetotcc10.Controle;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class ManterIngressoDeTurma extends AppCompatActivity {



    private Spinner aliasSpinnerCursos;
    private EditText alisAnoTurma;
    private Button aliasBtnCadastrar;
    private Integer anoTurmaNumero;
    private List<Curso> cursos;
    private Spinner aliasSpinnerSemestres;
    private String nomeCurso;
    private ListView aliasListTurma;
    private Integer numeroSemestre;
    private final static String TAG  = "Firelog";

    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manter_ingresso_de_turma);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Cadastrar turmas novas");


        alisAnoTurma = findViewById(R.id.editAnoTurmaIngresso);
        aliasSpinnerSemestres = findViewById(R.id.spinnerSemestreTurma);
        aliasSpinnerCursos = findViewById(R.id.spinnerCursoTurma);
        aliasListTurma = findViewById(R.id.listTurmas);
        aliasBtnCadastrar = findViewById(R.id.btnCadastrarTurma);

        cursos = new ArrayList<>();

        carregarSpinners();


        aliasBtnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (aliasSpinnerSemestres.getSelectedItemPosition()) {
                    case 0:
                        numeroSemestre = 1;
                        break;
                    case 1:
                        numeroSemestre = 2;
                        break;

                    default:
                }
                try {
                    if (alisAnoTurma.getText().length() == 0) {
                        alisAnoTurma.setError("Precisa inserir \n o ano de ingresso\n da turma");
                    } else {

                        anoTurmaNumero = Integer.parseInt(alisAnoTurma.getText().toString());
                        if (anoTurmaNumero < 2000 || anoTurmaNumero > 2100) {
                            alisAnoTurma.setError("Ano inválido");
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

//                String id =  FirebaseFirestore.getInstance().collection("cursos").document(nomeCurso).collection("turmas").getId();

                final Turma turma = new Turma();
                turma.setId(UUID.randomUUID().toString());
                turma.setAno(anoTurmaNumero);
                turma.setSemestre(numeroSemestre);

                final Curso curso = (Curso) aliasSpinnerCursos.getSelectedItem();
                nomeCurso = curso.getCurso();
                //String xyzvariavel=aliasCurso.getitemselected;

                //String id =  FirebaseFirestore.getInstance().collection("cursos").document(curso.getId()).collection("turmas").document(curso.getId()).getId();

                FirebaseFirestore.getInstance().collection("cursos").document(curso.getId()).collection("turmas").document(turma.getId())
                        .set(turma)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void v) {

                                //Log.i ("Teste \n", documentReference.getId());
                                String id = FirebaseFirestore.getInstance().collection("cursos").document(curso.getId()).collection("turmas").document(curso.getId()).getId();
                                //turma.setId(id);
                                Toast.makeText(getApplicationContext(), "///" +id ,Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("Teste \n", e.getMessage());
                    }
                });
            }

            });

    }
    private void carregarSpinners() {

            FirebaseFirestore.getInstance().collection("cursos")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                cursos.clear();
                                for (QueryDocumentSnapshot document : task.getResult()) {

                                    String nomeCurso = document.getString("curso");


                                    Curso u = new Curso();
                                    u.setId(document.getId());
                                    u.setCurso(nomeCurso);

                                    cursos.add(u);
                                    Log.d(TAG, nomeCurso);

                                }

                                final ArrayAdapter <Curso> adaptador = new ArrayAdapter <>(getBaseContext(), android.R.layout.simple_spinner_item, cursos);
                                adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                aliasSpinnerCursos.setAdapter(adaptador);
                                adaptador.notifyDataSetChanged();

                            } else {
                                Log.w(TAG, "Error getting documents.", task.getException());
                            }

                        }
                    });

        String[] ArraySemestres = new String[] {"1", "2"};


        ArrayAdapter<String> spinnerSemestres = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ArraySemestres);

        spinnerSemestres.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        aliasSpinnerSemestres.setAdapter(spinnerSemestres);

    }

}
