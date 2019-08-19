package com.example.projetotcc10.Controle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetotcc10.R;

public class ManterGrades extends AppCompatActivity {


    TextView linkUparGrade;
    EditText anoTurma, semestreTurma;
    Button buttonUparGrade;
    Spinner cursos;
    Spinner semestre;

    Integer numeroSemestre;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manter_grades);


         String[] semestres = new String[] {"1 semestre", "2 semestre"};




        linkUparGrade = findViewById(R.id.textSelecionarArquivo);
        anoTurma = findViewById(R.id.editAnoGrades);
        semestre = findViewById(R.id.spinnerSemestre);
        buttonUparGrade = findViewById(R.id.uparGrade);


        final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, semestres);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        semestre.setAdapter(spinnerAdapter);





       buttonUparGrade.setOnClickListener(new View.OnClickListener() {
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
