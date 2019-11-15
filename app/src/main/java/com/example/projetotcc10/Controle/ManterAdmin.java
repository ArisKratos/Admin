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

import com.example.projetotcc10.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.example.projetotcc10.Modelo.Admin;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;

public class ManterAdmin extends AppCompatActivity {

   private EditText aliasEmailAdmin, aliasSenhaAdmin, aliasNomeAdmin;
   private Button aliasBtncadastrarAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manter_admin);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Cadastrar administrador");


        aliasEmailAdmin = findViewById(R.id.editEmailAdmin);
        aliasSenhaAdmin = findViewById(R.id.editSenhaAdmin);
        aliasNomeAdmin = findViewById(R.id.editNomeAdmin);

        aliasBtncadastrarAdmin = findViewById(R.id.buttonActionCadastrarAdmin);



        FloatingActionButton voltar = findViewById(R.id.buttonActionVoltar);
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
     public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Listar_Admin.class);
                startActivity(intent);
     }
        });


        aliasBtncadastrarAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              createAdmin();
            }
        });

    }
    private void createAdmin() {

        String email = aliasEmailAdmin.getText().toString();
        String senha = aliasSenhaAdmin.getText().toString();
        String nome = aliasNomeAdmin.getText().toString();

        if (email == null || email.isEmpty() || senha == null || senha.isEmpty() || nome == null || nome.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Email, nome e senha devem ser preenchidos", Toast.LENGTH_SHORT).show();
            return;
        }
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {

                                saveAdminInFirebase();

                            } else{
                            Toast.makeText(ManterAdmin.this, "Authentication failed.", Toast.LENGTH_SHORT).show();

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

    private void saveAdminInFirebase() {

       // String filename = UUID.randomUUID().toString();

        String uid = UUID.randomUUID().toString();
        String emailAdmin = aliasEmailAdmin.getText().toString();
        String nomeAdmin = aliasNomeAdmin.getText().toString();
        String senhaAdmin = aliasSenhaAdmin.getText().toString();


        Admin admin = new Admin(uid, nomeAdmin, emailAdmin);
        admin.setSenhaAdmin(senhaAdmin);
        FirebaseFirestore.getInstance().collection("administradores").document(admin.getId())
                .set(admin)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void v) {

                        // Log.i ("Teste \n", documentReference.getId());
                        Intent intent = new Intent(ManterAdmin.this, Listar_Admin.class);
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
                //startActivity(new Intent(ManterAdmin.this, Admin.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                Intent intent = new Intent(ManterAdmin.this, Listar_Admin.class);
                startActivity(intent);
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            default:break;
        }
        return true;
    }
}
