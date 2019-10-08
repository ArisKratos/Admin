package com.example.projetotcc10.Controle;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projetotcc10.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginAdmin extends AppCompatActivity {

    private EditText aliasEmailAdmin, aliasSenhaAdmin;
    private Button aliasBtnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);

        getSupportActionBar().setTitle("Administrador");


        aliasEmailAdmin = findViewById(R.id.editEmailAdmin);
        aliasSenhaAdmin = findViewById(R.id.editSenhaAdmin);


       aliasBtnLogin = findViewById(R.id.buttonCadastrarAdm);
       aliasBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String email = aliasEmailAdmin.getText().toString();
                String senha = aliasSenhaAdmin.getText().toString();

                if (email == null || email.isEmpty() || senha == null || senha.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Email, nome e senha devem ser preenchidos", Toast.LENGTH_SHORT).show();
                    return;
                }

                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha)
                        .addOnCompleteListener(new OnCompleteListener <AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task <AuthResult> task) {
                        Log.i("Teste", task.getResult().getUser().getUid());

                        aliasEmailAdmin.setText("");
                        aliasSenhaAdmin.setText("");
                        
                        Intent intent = new Intent(getApplicationContext(), Admin.class);
                        startActivity(intent);
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Log.i("Teste" ,  e.getMessage());

                            }
                        });

            }
        });
    }


}
