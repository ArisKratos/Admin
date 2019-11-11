package com.example.projetotcc10.Controle;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.projetotcc10.Modelo.Curso;
import com.example.projetotcc10.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MandarMensagem extends AppCompatActivity {

    private EditText anoTurma, mensagem;
    private List <Curso> cursos;
    private Spinner spnCursos;
    private Spinner semestres;
    private CheckBox checkSendForAll;
    private Button enviarMensagem;
    private Integer numeroSemestre;
    private String nomeCurso;
    private Integer anoTurmaNumero;
    private final static String TAG  = "Firelog";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mandar_mensagem);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Enviar mensagem");


       ;

        anoTurma = findViewById(R.id.editAnoTurmaMsg);
        spnCursos= findViewById(R.id.spinnerCursoTurma);
        semestres = findViewById(R.id.spinnerSemestreTurma);
        checkSendForAll = findViewById(R.id.checkSendAll);
        enviarMensagem = findViewById(R.id.buttonEnviarMensagem);

        String[] ArraySemestres = new String[] {"1", "2"};

        cursos = new ArrayList <>();

        carregarSpinnerCurso();


        final ArrayAdapter<String> spinnerSemestres = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ArraySemestres);

        spinnerSemestres.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        semestres.setAdapter(spinnerSemestres);


        enviarMensagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (semestres.getSelectedItemPosition()) {

                    case 0:

                        numeroSemestre = 1;
                        break;
                    case 1:
                        numeroSemestre = 2;
                        break;

                    default:
                }

                Curso curso = (Curso) spnCursos.getSelectedItem();
                nomeCurso = curso.getCurso();


                try {
                    if (anoTurma.getText().length() == 0) {
                        anoTurma.setError("Precisa inserir \n o ano de ingresso\n da turma");


                    } else {

                        anoTurmaNumero = Integer.parseInt(anoTurma.getText().toString());
                        if (anoTurmaNumero < 2000 || anoTurmaNumero > 2100) {
                            anoTurma.setError("Ano inválido");
                        } else {

                            if (checkSendForAll.isChecked()) {
                                Toast.makeText(getApplicationContext(), "Mensagem enviada com sucesso para: todos!", Toast.LENGTH_SHORT).show();
                            } else {

                                Toast.makeText(getApplicationContext()," Mensagem enviada com sucesso para:\n"+"Semestre: " + numeroSemestre + "\nCurso: " + nomeCurso + "\nAno: " + anoTurmaNumero, Toast.LENGTH_SHORT).show();
                            }
                        }

                    }


                }catch (Exception e){
                    e.printStackTrace();
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
