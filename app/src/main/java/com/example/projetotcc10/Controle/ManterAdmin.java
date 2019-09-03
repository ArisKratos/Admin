package com.example.projetotcc10.Controle;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.projetotcc10.R;

import java.util.List;

public class ManterAdmin extends AppCompatActivity {




   private EditText aliasEmailAdmin, aliasSenhaAdmin;
    private Button aliasBtncadastrarAdmin;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manter_admin);


       aliasEmailAdmin = findViewById(R.id.editEmailAdmin);
        aliasSenhaAdmin = findViewById(R.id.editSenhaAdmin);
        aliasBtncadastrarAdmin = findViewById(R.id.buttonCadastrarAdmin);

        FloatingActionButton voltar = findViewById(R.id.buttonActionVoltar);
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
     public void onClick(View view) {
      onBackPressed();
     }
        });


        aliasBtncadastrarAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext() ,"Admin cadastrado com sucesso!" , Toast.LENGTH_SHORT).show();

            }
        });


    }


}
