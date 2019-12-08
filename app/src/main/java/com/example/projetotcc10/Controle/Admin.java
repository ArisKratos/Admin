package com.example.projetotcc10.Controle;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.projetotcc10.Adapters.MeuAdapter;
import com.example.projetotcc10.Adapters.MeuViewHolder;
import com.example.projetotcc10.Modelo.Curso;
import com.example.projetotcc10.Modelo.Mensagem;
import com.example.projetotcc10.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.net.Inet4Address;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Admin extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

        private List<Mensagem> mensagens;



        MeuAdapter meuAdapter;
        FirebaseFirestore mFirestore;


    @Override
    protected void onStart() {
        super.onStart();

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.myRecycler);

        RecyclerView.LayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(layout);

        mensagens = new ArrayList <>();

        FirebaseFirestore.getInstance().collection("mensagens").orderBy("timeMassage", Query.Direction.DESCENDING).limit(20)
                .get()
                .addOnCompleteListener(new OnCompleteListener <QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task <QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            mensagens.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                String remetente = document.getString("remetenteMsg");
                                String data = document.getString("dataMensagem");
                                String id = document.getString("id");
                                String idRemetente = document.getString("idRemetente");
                                String mensagem = document.getString("mensagem");
                                Boolean mudanca = document.getBoolean("mudancaHorario");
                                Boolean paraTodos = document.getBoolean("paraTodos");
                                String semestre = document.getString("semestreMensagem");
                                long time = document.getLong("timeMassage");
                                String turmaAno = document.getString("turmaAnoMensagem");
                                String hora = document.getString("hora_atual");
                                String curso = document.getString("cursoMsg");


                                Mensagem u = new Mensagem(id, idRemetente, mensagem, remetente, turmaAno, semestre, data, time, paraTodos, mudanca, hora, curso);


                                mensagens.add(u);
                            }

                            meuAdapter = new MeuAdapter(mensagens, Admin.this);
                            recyclerView.setAdapter(meuAdapter);

                        } else {
                            Toast.makeText(Admin.this, "leitura falhou", Toast.LENGTH_SHORT).show();

                        }

                    }

                });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), MandarMensagem.class);
                startActivity(intent);
            }
        });


        getSupportActionBar().setTitle("Últimas mensagens");

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);






        // recyclerView.setAdapter(new MeuAdapter(mensagens, this));








//        SimpleDateFormat formataData = new SimpleDateFormat("dd-MM-yyyy");
//
//        Date data = new Date();
//
//        String dataFormatada = formataData.format(data);








//Mensagem mensagem = new Mensagem("asdsad","Pessoal, amanhã tragam os trabalhos prontos, valerá nota!!", "Marcelo", "2016/2",
//        dataFormatada, "2",dataFormatada, 342344654, false, false, "141");

//mensagens.add(mensagem);

    }

    public void carregarListMensagens(){


    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(Admin.this, LoginAdmin.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.manter_admin) {
                Intent intent = new Intent(this,Listar_Admin.class);
                startActivity(intent);


        } else if (id == R.id.manter_professor) {

                Intent intent = new Intent(this,Listar_Professor.class);
                startActivity(intent);

        } else if (id == R.id.manter_curso) {
                Intent intent = new Intent(this,Listar_Curso.class);
                startActivity(intent);

        } else if (id == R.id.admin_mensagem) {

                Intent intent = new Intent(this, MandarMensagem.class);
                startActivity(intent);
            } else if (id == R.id.manter_grades) {

                Intent intent = new Intent(this, ManterGrades.class);
                startActivity(intent);


            } else if (id == R.id.admin_turma) {


            Intent intent = new Intent(this, Listar_Turma.class);

            startActivity(intent);

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
