package com.example.projetotcc10.Controle;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.projetotcc10.Modelo.Curso;
import com.example.projetotcc10.Modelo.Mensagem;
import com.example.projetotcc10.Modelo.Turma;
import com.example.projetotcc10.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class MandarMensagem extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private EditText textMensagem;
    private List <Curso> cursos;
    private List<Turma> turmas;
    com.example.projetotcc10.Modelo.Admin admin;
    private List <com.example.projetotcc10.Modelo.Admin> administradores;
    private Spinner spnCursos;
    private Spinner spnTurmas;
    private String nomeAdmin;
    private CheckBox checkSendForAll;
    private CheckBox checkMudancaDeHorario;
    private boolean mudancaHorario;
    private boolean paraTodos;
    private String txtTurmaAno, txtTurmaSemestre;
    private Button aliasBtnSeeTurmas;
    private Button enviarMensagem;
    private String idMsg;
    private String nomeCurso;
    private final static String TAG  = "Firelog";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mandar_mensagem);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Enviar mensagem");


        spnCursos = findViewById(R.id.spinnerCurso);
        checkMudancaDeHorario = findViewById(R.id.editCheckAlertHorario);
        checkSendForAll = findViewById(R.id.editCheckSendAll);
        enviarMensagem = findViewById(R.id.buttonEnviarMensagem);
        textMensagem = findViewById(R.id.editMensagem);
        spnTurmas = findViewById(R.id.spinnerTurma);


        spnCursos.setOnItemSelectedListener(this);


        cursos = new ArrayList<>();
        turmas = new ArrayList<>();
        administradores = new ArrayList<>();

        carregarSpinnerCurso();


        enviarMensagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String uid = UUID.randomUUID().toString();

                idMsg = uid;

                sendMassage();


                Curso curso = (Curso) spnCursos.getSelectedItem();
                nomeCurso = curso.getCurso();

            }

        });


    }


    public void carregarSpinnerTurma(){

        Curso curso = (Curso) spnCursos.getSelectedItem();

        FirebaseFirestore.getInstance().collection("cursos").document(curso.getId()).collection("turmas")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    turmas.clear();
                    for (QueryDocumentSnapshot document : task.getResult()) {



                        String anoTurma = document.getString("ano");
                        String semestreTurma = document.getString("semestre");


                        Turma u = new Turma();
                        u.setId(document.getId());
                        u.setAno(anoTurma);
                        u.setSemestre(semestreTurma);
                        turmas.add(u);


                    }

                    final ArrayAdapter<Turma> adaptador = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_spinner_item, turmas);
                    adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnTurmas.setAdapter(adaptador);
                } else {
                    Log.w(TAG, "Error getting documents.", task.getException());
                }
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

                                String nomeCurso = document.getString("curso");


                                Curso u = new Curso();
                                u.setId(document.getId());
                                u.setCurso(nomeCurso);

                                cursos.add(u);
                                Log.d(TAG, nomeCurso);

                            }

                            final ArrayAdapter <Curso> adaptador = new ArrayAdapter <>(getBaseContext(), android.R.layout.simple_spinner_item, cursos);
                            adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spnCursos.setAdapter(adaptador);
                            adaptador.notifyDataSetChanged();

                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }

                    }
                });
    }

    public void sendMassage(){

        if(spnTurmas.getSelectedItem() != null) {

            if(checkSendForAll.isChecked()){
                paraTodos = true;
            }
            else {
                paraTodos = false;
            }

            if (checkMudancaDeHorario.isChecked()){
                mudancaHorario = true;
            }
            else{
                mudancaHorario = false;
            }

            if(paraTodos == false) {


                final Curso curso = (Curso) spnCursos.getSelectedItem();
                nomeCurso = curso.getCurso();

                Turma turma = (Turma) spnTurmas.getSelectedItem();

                txtTurmaAno = turma.getAno();
                txtTurmaSemestre = turma.getSemestre();

                String textMsg = textMensagem.getText().toString();

                textMensagem.setText(null);

                String idAdmilson = FirebaseAuth.getInstance().getCurrentUser().getUid();


                SimpleDateFormat formataData = new SimpleDateFormat("dd-MM-yyyy");
                Date data = new Date();
                String dataFormatada = formataData.format(data);

                long timeStamp = System.currentTimeMillis();

//
//                FirebaseFirestore.getInstance().collection("administradores")
//                        .get()
//                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                            @Override
//                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                if (task.isSuccessful()) {
//                                    //  administradores.clear();
//                                    for (QueryDocumentSnapshot document : task.getResult()) {
//
//                                       ;
//
//
//
//                                        if (document.getId().equals(FirebaseAuth.getInstance().getUid())) {
//
//
//                                            String id = document.getString("id");
//                                            String nome = document.getString("nomeAdmin");
//                                            String email = document.getString("emailAdmin");
//
//
//
//                                           com.example.projetotcc10.Modelo.Admin u = new com.example.projetotcc10.Modelo.Admin(id, nome, email);
//
//                                           u.setNomeAdmin(nome);
//
//                                           nomeAdmin = nome;
//
//                                        }
//                                    }
//
//                                } else {
//                                    Log.w(TAG, "Error getting documents.", task.getException());
//                                }
//                            }
//                        });



                nomeAdmin = "Cordenadoria";





                Mensagem mensagem = new Mensagem(idMsg, idAdmilson, textMsg, nomeAdmin, txtTurmaAno, txtTurmaSemestre, dataFormatada, timeStamp, paraTodos, mudancaHorario);

                if (!mensagem.getMensagem().isEmpty()) {
                    FirebaseFirestore.getInstance().collection("cursos").document(curso.getId()).collection("turmas").document(turma.getId())
                            .collection("mensagens").document(idMsg).set(mensagem);

                    Toast.makeText(getApplicationContext(), "mensagem enviada com sucesso", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "mensagem vazia", Toast.LENGTH_SHORT).show();
                }
            }

            else {
                final Curso curso = (Curso) spnCursos.getSelectedItem();
                nomeCurso = curso.getCurso();

                Turma turma = (Turma) spnTurmas.getSelectedItem();

                txtTurmaAno = turma.getAno();
                txtTurmaSemestre = turma.getSemestre();

                String textMsg = textMensagem.getText().toString();

                textMensagem.setText(null);

                String idAdmilson = FirebaseAuth.getInstance().getCurrentUser().getUid();


                SimpleDateFormat formataData = new SimpleDateFormat("dd-MM-yyyy");
                Date data = new Date();
                String dataFormatada = formataData.format(data);

                long timeStamp = System.currentTimeMillis();


                FirebaseFirestore.getInstance().collection("administradores")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    //  administradores.clear();
                                    for (QueryDocumentSnapshot document : task.getResult()) {


                                        if (document.getId().equals(FirebaseAuth.getInstance().getUid())) {


                                            String id = document.getString("id");
                                            String nome2 = document.getString("nomeAdmin");
                                            String email = document.getString("emailAdmin");



                                            com.example.projetotcc10.Modelo.Admin u = new com.example.projetotcc10.Modelo.Admin(id, nome2, email);

                                            u.setNomeAdmin(nome2);

                                            nomeAdmin = nome2;

                                        }
                                    }

                                } else {
                                    Log.w(TAG, "Error getting documents.", task.getException());
                                }
                            }
                        });



                Mensagem mensagem = new Mensagem(idMsg, idAdmilson, textMsg, nomeAdmin, txtTurmaAno, txtTurmaSemestre, dataFormatada, timeStamp, paraTodos, mudancaHorario);

                if (!mensagem.getMensagem().isEmpty()) {
                    FirebaseFirestore.getInstance().collection("cursos").document(curso.getId()).collection("mensagens")
                            .document(idMsg).set(mensagem);
                         //   .collection("mensagens").document(idAdmilson).set(mensagem);

                    Toast.makeText(getApplicationContext(), "mensagem enviada com sucesso", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "mensagem vazia", Toast.LENGTH_SHORT).show();
                }
            }


        }
        else{

            Toast.makeText(getApplicationContext(), "Escolha uma turma para enviar a mensagem", Toast.LENGTH_SHORT).show();

        }
    }


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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        carregarSpinnerTurma();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
