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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetotcc10.R;

public class ManterGrades extends AppCompatActivity {


    private TextView linkUparGrade;
    private EditText anoTurma;
    private Button buttonUparGrade;
    private Spinner cursos;
    private Spinner semestres;
    private String nomeCurso;
    private Integer numeroSemestre;
    private Integer anoTurmaNumero;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manter_grades);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Enviar grades");


        String[] ArraySemestres = new String[] {"1", "2"};
         String[] ArrayCursos = new String[]{"Informática","Agropecuária"};




        linkUparGrade = findViewById(R.id.textSelecionarArquivo);
        anoTurma = findViewById(R.id.editAnoTurmaGrades);
        semestres = findViewById(R.id.spinnerSemestreGrades);
        buttonUparGrade = findViewById(R.id.uparGrade);
        cursos = findViewById(R.id.spinnerCursoGrades);


        ArrayAdapter<String> spinnerSemestres = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ArraySemestres);

        spinnerSemestres.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        semestres.setAdapter(spinnerSemestres);


        final ArrayAdapter<String> spinnerCursos = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ArrayCursos);

        spinnerCursos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cursos.setAdapter(spinnerCursos);


        FloatingActionButton voltar = findViewById(R.id.buttonActionVoltar);
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Admin.class);
                startActivity(intent);
            }
        });


       buttonUparGrade.setOnClickListener(new View.OnClickListener() {
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
               switch (cursos.getSelectedItemPosition()) {

                   case 0:

                       nomeCurso = spinnerCursos.getItem(0);
                       break;
                   case 1:
                       nomeCurso = spinnerCursos.getItem(1);

                       break;

                   default:
               }
               try {


                   if (anoTurma.getText().length() == 0) {
                       anoTurma.setError("Precisa inserir \n o ano de ingresso\n da turma");


                   } else {

                       anoTurmaNumero = Integer.parseInt(anoTurma.getText().toString());
                       if (anoTurmaNumero < 2000|| anoTurmaNumero > 2100) {
                           anoTurma.setError("Ano inválido");
                       } else {

                               Toast.makeText(getApplicationContext(), "Grade enviada com sucesso para: \n"+"Semestre: " + numeroSemestre + "\nCurso: " + nomeCurso + "\nAno: " + anoTurmaNumero, Toast.LENGTH_SHORT).show();
                           }
                       }




               }catch (Exception e){
                   e.printStackTrace();
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
