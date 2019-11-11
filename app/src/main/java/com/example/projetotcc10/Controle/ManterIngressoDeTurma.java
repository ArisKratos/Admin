package com.example.projetotcc10.Controle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.projetotcc10.R;


public class ManterIngressoDeTurma extends AppCompatActivity {



    private Spinner aliasSpinnerCursos;
    private EditText alisAnoTurma;
    private Spinner aliasSemestres;
    private ListView aliasListTurma;

    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manter_ingresso_de_turma);



       alisAnoTurma = findViewById(R.id.editAnoTurmaIngresso);
       aliasSemestres = findViewById(R.id.spinnerSemestreTurma);
       aliasSpinnerCursos = findViewById(R.id.spinnerCursoTurma);
       aliasListTurma = findViewById(R.id.listTurmas);











    }

}
