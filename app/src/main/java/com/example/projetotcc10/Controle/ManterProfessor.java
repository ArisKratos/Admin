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

import com.example.projetotcc10.Modelo.Professor;
import com.example.projetotcc10.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;

public class ManterProfessor extends AppCompatActivity {


    EditText aliasNomeProfessor, aliasSenhaProfessor, aliasEmailProfessor;
    Button aliasBtnCadastrarProf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manter_professor);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Cadastrar professor");

        aliasNomeProfessor = findViewById(R.id.editNomeProfessor);
        aliasEmailProfessor = findViewById(R.id.editEmailProfessor);
        aliasSenhaProfessor = findViewById(R.id.editSenhaProfessor);

        aliasBtnCadastrarProf = findViewById(R.id.buttonCadastrarProfessor);

        FloatingActionButton voltar = findViewById(R.id.buttonActionVoltar);
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Listar_Professor.class);
                startActivity(intent);
            }
        });

        aliasBtnCadastrarProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createProf();
            }
        });
    }
       private void createProf() {


            String email = aliasEmailProfessor.getText().toString();
            String senha = aliasSenhaProfessor.getText().toString();
            String nome = aliasNomeProfessor.getText().toString();

            if (email == null || email.isEmpty() || senha == null || senha.isEmpty() || nome == null || nome.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Email, nome e senha devem ser preenchidos", Toast.LENGTH_SHORT).show();
                return;
            }
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {

                                saveProfInFirebase();

                            } else{
                                Toast.makeText(ManterProfessor.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Log.i("Teste" ,  e.getMessage());

                        }
                    });
        }
        private void saveProfInFirebase() {


            String emailProf = aliasEmailProfessor.getText().toString();
            String nomeProf = aliasNomeProfessor.getText().toString();
            String senhaProf = aliasSenhaProfessor.getText().toString();
            String uid = FirebaseAuth.getInstance().getUid();

            Professor professor = new Professor();
            professor.setId(uid);
            professor.setEmailProfessor(emailProf);
            professor.setNomeProfessor(nomeProf);
            professor.setSenhaProfessor(senhaProf);

            FirebaseFirestore.getInstance().collection("professores").document(professor.getId())
                    .set(professor)
                    .addOnSuccessListener(new OnSuccessListener <Void>() {
                        @Override
                        public void onSuccess(Void v) {
                           Toast.makeText(getApplicationContext(), "Professor cadastrado com sucesso", Toast.LENGTH_SHORT).show();
                           Intent intent = new Intent(ManterProfessor.this , Listar_Professor.class);
                           startActivity(intent);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.i("Teste \n", e.getMessage());
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
