package com.example.projetotcc10.Controle;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Toast;

import com.example.projetotcc10.Adapters.MeuAdapter;
import com.example.projetotcc10.Adapters.MeuViewHolder;
import com.example.projetotcc10.Modelo.Mensagem;
import com.example.projetotcc10.R;
import com.google.firebase.auth.FirebaseAuth;

import java.net.Inet4Address;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Admin extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ArrayList<Mensagem> mensagens;




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


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.myRecycler);

        mensagens = new ArrayList <>();




        SimpleDateFormat formataData = new SimpleDateFormat("dd-MM-yyyy");
        Date data = new Date();
        String dataFormatada = formataData.format(data);


        Mensagem mensagem = new Mensagem("Pessoal, amanhã tragam os trabalhos prontos, valerá nota!!", "Marcelo", "2016/2", dataFormatada);
        Mensagem mensagem1 = new Mensagem("Bom dia turma, só para avisar que amanhã no lugar de Desenvolvimento terá Ed.Física", "Emilio", "2018/1", dataFormatada);
        Mensagem mensagem2 = new Mensagem("Bom dia turma, só para avisar que amanhã no lugar de Desenvolvimento terá Ed.Física", "Emilio", "2018/1", dataFormatada);
        Mensagem mensagem3 = new Mensagem("Bom dia turma, só para avisar que amanhã no lugar de Desenvolvimento terá Ed.Física", "Emilio", "2018/1", dataFormatada);
        Mensagem mensagem4 = new Mensagem("Pessoal, amanhã tragam os trabalhos prontos, valerá nota!!", "Marcelo", "2016/2", dataFormatada);

        mensagens.add(mensagem);
        mensagens.add(mensagem1);
        mensagens.add(mensagem2);
        mensagens.add(mensagem3);
        mensagens.add(mensagem4);


        RecyclerView.LayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(layout);

        recyclerView.setAdapter(new MeuAdapter(mensagens, this));


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


            } else if (id == R.id.admin_acoes) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
