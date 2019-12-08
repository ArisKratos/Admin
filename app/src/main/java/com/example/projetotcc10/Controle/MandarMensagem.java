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
import com.example.projetotcc10.Modelo.Professor;
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
    private String nomeRemetente;
    private CheckBox checkSendForAll;
    private CheckBox checkMudancaDeHorario;
    private boolean mudancaHorario;
    private boolean paraTodos;
    private String para;
    private  String todos;
    private String txtTurmaAno, txtTurmaSemestre;

    private String idRemetente;

    private Button enviarMensagem;
    private String idMsg;
    private String nomeCurso;
    private Curso curso;
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

        nomeRemetente = "Coordenadoria";


        idRemetente = FirebaseAuth.getInstance().getCurrentUser().getUid();

        carregarSpinnerCurso();

        enviarMensagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String uid = UUID.randomUUID().toString();

                idMsg = uid;

                sendMassage();

//                curso = (Curso) spnCursos.getSelectedItem();
//                nomeCurso = curso.getCurso();
            }

        });

    }

    public void enviarParaTodos(){

        para = "para";
        todos = "todos";

        curso = (Curso) spnCursos.getSelectedItem();

        nomeCurso = curso.getCurso();

        final String textMsg = textMensagem.getText().toString();

        if(textMsg!=null){

        textMensagem.setText(null);


        SimpleDateFormat dateFormat_hora = new SimpleDateFormat("HH:mm");

        SimpleDateFormat formataData = new SimpleDateFormat("dd-MM-yyyy");

        Date data = new Date();

        final String dataFormatada = formataData.format(data);
        final String hora_atual = dateFormat_hora.format(data);

        final long timeStamp = System.currentTimeMillis();

        //pra cada curso
        for( int j = 0; j < cursos.size(); j++) {
            final int finalJ = j;
            FirebaseFirestore.getInstance().collection("cursos").document(cursos.get(j).getId()).collection("turmas").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                turmas.clear();
                                for (QueryDocumentSnapshot document : task.getResult()) {

                                    String id = document.getId();
                                    String ano = document.getString("ano");
                                    String semestre = document.getString("semestre");
                                    String curso = document.getString("curso");


                                    Turma u = new Turma();

                                    u.setId(id);
                                    u.setAno(ano);
                                    u.setSemestre(semestre);
                                    u.setCurso(curso);

                                    turmas.add(u);
                                    Log.d(TAG, id);
                                }
                                for (int i = 0; i < turmas.size(); i++) {

                                    txtTurmaAno = turmas.get(i).getAno();
                                    txtTurmaSemestre = turmas.get(i).getSemestre();

                                    Mensagem mensagem = new Mensagem(idMsg, idRemetente, textMsg, nomeRemetente, txtTurmaAno,
                                            txtTurmaSemestre, dataFormatada, timeStamp, paraTodos, mudancaHorario, hora_atual, nomeCurso );

                                    final Task<Void> set = FirebaseFirestore.getInstance().collection("cursos").document(cursos.get(finalJ).getId())
                                            .collection("turmas").document(turmas.get(i).getId()).collection("mensagens").document(mensagem.getId())
                                            .set(mensagem);
                                }
//                            }
                            }
                        }

                    });
        }

            Mensagem mensagem = new Mensagem(idMsg, idRemetente, textMsg, nomeRemetente, "para",
                    "todos", dataFormatada, timeStamp, paraTodos, mudancaHorario, hora_atual, nomeCurso);

            FirebaseFirestore.getInstance().collection("mensagens").document(idMsg).set(mensagem);



            Toast.makeText(this, "mensagem enviadas para todos com sucesso!!", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this, "mensagem vazia", Toast.LENGTH_SHORT).show();
        }
    }

    public void carregarSpinnerTurma() {

       curso = (Curso) spnCursos.getSelectedItem();

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

                SimpleDateFormat dateFormat_hora = new SimpleDateFormat("HH:mm");

                SimpleDateFormat formataData = new SimpleDateFormat("dd-MM-yyyy");

                Date data = new Date();

                final String dataFormatada = formataData.format(data);
                final String hora_atual = dateFormat_hora.format(data);

                final long timeStamp = System.currentTimeMillis();



                Mensagem mensagem = new Mensagem(idMsg, idAdmilson, textMsg, nomeRemetente, txtTurmaAno,
                        txtTurmaSemestre, dataFormatada, timeStamp, paraTodos, mudancaHorario,hora_atual, nomeCurso);

                if (!mensagem.getMensagem().isEmpty()) {

                    FirebaseFirestore.getInstance().collection("cursos").document(curso.getId()).collection("turmas").document(turma.getId())
                            .collection("mensagens").document(idMsg).set(mensagem);


                        FirebaseFirestore.getInstance().collection("mensagens").document(idMsg).set(mensagem);


                    Toast.makeText(getApplicationContext(), "mensagem enviada com sucesso!!", Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(getApplicationContext(), "mensagem vazia", Toast.LENGTH_SHORT).show();
                }
            }
//
            else {
                    enviarParaTodos();
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
