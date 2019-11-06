



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

import com.example.projetotcc10.Modelo.Curso;
import com.example.projetotcc10.Modelo.Professor;
import com.example.projetotcc10.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Listar_Curso extends AppCompatActivity {


    private ListView listaCurso;
    private List<Curso> cursos;
    private Button aliasCadastrarCurso;
    private  AlertDialog alerta;
    private final static String TAG  = "Firelog";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Lista cursos");


        setContentView(R.layout.activity_listar_curso);
        listaCurso = findViewById(R.id.listCurso);


         cursos = new ArrayList<>();

        carregalistview();

        FloatingActionButton cadastrar = findViewById(R.id.buttonActionCadastrarCurso);
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),ManterCurso.class);
                startActivity(intent);
            }
        });


       listaCurso.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

           @Override
           public boolean onItemLongClick(AdapterView<?> parent, View view,
                                          final int position, long id) {
               AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
               builder.setTitle("Alerta!");
               builder.setMessage("Deseja mesmo excluir esse curso?");
               builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface arg0, int arg1) {

                      Curso curso = new Curso();
                      curso.setCurso(cursos.get(position).getId());
                      curso.setCurso(cursos.get(position).getCurso());

                      FirebaseFirestore.getInstance().collection("cursos").document(curso.getId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                          @Override
                          public void onSuccess(Void aVoid) {
                              Toast.makeText(Listar_Curso.this, "Curso excluido com sucesso!", Toast.LENGTH_SHORT).show();
                              carregalistview();
                          }
                      }).addOnFailureListener(new OnFailureListener() {
                          @Override
                          public void onFailure(@NonNull Exception e) {
                              Toast.makeText(Listar_Curso.this, "Erro ao deletar administrador", Toast.LENGTH_SHORT).show();
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

    private void carregalistview(){

        FirebaseFirestore.getInstance().collection("cursos")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
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

                            ArrayAdapter<Curso> adaptador = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, cursos);
                            listaCurso.setAdapter(adaptador);
                            adaptador.notifyDataSetChanged();

                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }

                    }
                });
    }
}
