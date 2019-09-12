package com.example.projetotcc10.Controle;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.projetotcc10.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Listar_Admin extends AppCompatActivity {

    private ListView listaAdmin;
    private List<Admin> admins;
    private Button aliasCadastrarAdmin;


    private String[] ArrayAdmins = new String[]{"Joao\njoao@gmail.com","Lucas\n" +
            "lucas@gmail.com", "Joao\njoao@gmail.com","Lucas\n" +
            "lucas@gmail.com","Joao\njoao@gmail.com","Lucas\n" +
            "lucas@gmail.com","Joao\njoao@gmail.com","Lucas\n" +
            "lucas@gmail.com", "Joao\njoao@gmail.com","Lucas\n" +
            "lucas@gmail.com","Joao\njoao@gmail.com","Lucas\n" +
            "lucas@gmail.com"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_admin);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Lista administradores");

        listaAdmin = findViewById(R.id.listAdmin);

        carregalistview();

        FloatingActionButton cadastrar = findViewById(R.id.buttonActionCadastrarAdmin);
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext() ,ManterAdmin.class);
                startActivity(intent);
            }
        });


        listaAdmin.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                // TODO Auto-generated method stub



             listaAdmin.getItemAtPosition(position);

                Toast.makeText(getApplicationContext(), "Item Deleted", Toast.LENGTH_LONG).show();

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

        ArrayAdapter <String> adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ArrayAdmins);
        listaAdmin.setAdapter(adaptador);
        adaptador.notifyDataSetChanged();

    }






}
