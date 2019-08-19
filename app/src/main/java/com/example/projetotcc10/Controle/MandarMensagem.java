package com.example.projetotcc10.Controle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.projetotcc10.R;

public class MandarMensagem extends AppCompatActivity {

    EditText anoTurma, mensagem;
    Spinner cursos;
    Spinner semestre;
    CheckBox checkSendForAll;
    Button enviarMensagem;
    Integer numeroSemestre;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mandar_mensagem);

        String[] ArraySemestres = new String[] {"1 semestre", "2 semestre"};

        anoTurma = findViewById(R.id.editAnoTurmaMsg);
        cursos = findViewById(R.id.spinnerCursoMsg);
        semestre = findViewById(R.id.spinnerSemestreMsg);
        checkSendForAll = findViewById(R.id.checkSendAll);
        enviarMensagem = findViewById(R.id.buttonEnviarMensagem);






        final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ArraySemestres);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        semestre.setAdapter(spinnerAdapter);


         enviarMensagem.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 switch (semestre.getSelectedItemPosition()){

                     case 0:

                         numeroSemestre = 1;
                         break;
                     case 1:
                         numeroSemestre = 2;
                         break;

                     default:
                 }

                 Toast.makeText(getApplicationContext() , "semestre: " + numeroSemestre,Toast.LENGTH_SHORT).show();
             }


         });

    }
}
