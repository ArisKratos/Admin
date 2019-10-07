package com.example.projetotcc10.Controle;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
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

import java.util.List;

public class Listar_Admin extends AppCompatActivity {

    private ListView listaAdmin;
    private List<Admin> admins;
    private Button aliasCadastrarAdmin;

    private AlertDialog alerta;



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



             listaAdmin.getItemAtPosition(position);
             exemplo_simples();



                return true;
            }

        });
        }
    private void exemplo_simples() {
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //define o titulo
        builder.setTitle("Alerta!");
        //define a mensagem
        builder.setMessage("Deseja mesmo excluir esse administrador?");
        //define um botão como positivo
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Toast.makeText(getApplicationContext(), "Administrador excluido" , Toast.LENGTH_SHORT).show();
            }
        });
        //define um botão como negativo.
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Toast.makeText(getApplicationContext(), "Ação cancelada" , Toast.LENGTH_SHORT).show();
            }
        });
        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
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
