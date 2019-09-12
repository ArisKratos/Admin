package com.example.projetotcc10.Controle;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projetotcc10.R;

public class ManterAdmin extends AppCompatActivity {




   private EditText aliasEmailAdmin, aliasSenhaAdmin;
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
                Toast.makeText(getApplicationContext() ,"Admin cadastrado com sucesso!" , Toast.LENGTH_SHORT).show();

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
