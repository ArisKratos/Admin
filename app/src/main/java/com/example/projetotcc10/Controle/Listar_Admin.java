package com.example.projetotcc10.Controle;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.projetotcc10.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.List;

public class Listar_Admin extends AppCompatActivity {

    private ListView listaAdmin;
    private List<com.example.projetotcc10.Modelo.Admin> admins;
    private AlertDialog alerta;
    private final static String TAG  = "Firelog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_admin);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Lista administradores");

        listaAdmin = findViewById(R.id.listAdmin);

        admins = new ArrayList<>();

        carregalistview();

        FloatingActionButton cadastrar = findViewById(R.id.buttonActionCadastrarAdmin);
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ManterAdmin.class);
                startActivity(intent);
            }

        });

        listaAdmin.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Alerta!");
                builder.setMessage("Deseja mesmo excluir esse administrador?");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                        final com.example.projetotcc10.Modelo.Admin admin = new com.example.projetotcc10.Modelo.Admin(admins.get(position).getId(),admins.get(position).getNomeAdmin(), admins.get(position).getEmailAdmin());
                        admin.setSenhaAdmin(admins.get(position).getSenhaAdmin());


                        // pega usuario atual
                        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                        //loga o cara que deve ser apagado
                        AuthCredential credential = EmailAuthProvider.getCredential(admin.getEmailAdmin(), admin.getSenhaAdmin());
                        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                    task.getResult().getUser().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(Listar_Admin.this, "Excluiu", Toast.LENGTH_SHORT).show();

                                            FirebaseFirestore.getInstance().collection("administradores").document(admin.getId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(Listar_Admin.this, "Administrador excluído com sucesso", Toast.LENGTH_SHORT).show();
                                                    carregalistview();
                                                    //recoloca o usuario na sessão
                                                    FirebaseAuth.getInstance().updateCurrentUser(user);
        }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(Listar_Admin.this, "Erro ao deletar administrador", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                    });
                                }
                            }
                        });
                    }
                });
                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Toast.makeText(getApplicationContext(), "Ação cancelada", Toast.LENGTH_SHORT).show();
                    }
                });
                alerta = builder.create();
                alerta.show();
                return true;
            }
        });
    }
    @Override
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
    public void onBackPressed(){ //Botão BACK padrão do android
        startActivity(new Intent(this, Admin.class)); //O efeito ao ser pressionado do botão (no caso abre a activity)
        finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem
        return;
    }
    private void carregalistview(){
        FirebaseFirestore.getInstance().collection("administradores")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                           admins.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                String nome = document.getString("nomeAdmin");
                                String email = document.getString("emailAdmin");
                                String senha = document.getString("senhaAdmin");

                                com.example.projetotcc10.Modelo.Admin u = new com.example.projetotcc10.Modelo.Admin(document.getId(), nome, email);
                                u.setSenhaAdmin(senha);

                                admins.add(u);
                               // Log.d(TAG   , document.getId() + " => " + document.getData());
                                Log.d(TAG   , nome + " => " + email);
                            }

                          ArrayAdapter<com.example.projetotcc10.Modelo.Admin> adaptador = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, admins);
                            listaAdmin.setAdapter(adaptador);
                          adaptador.notifyDataSetChanged();

                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }

                    }
                });
    }
}
