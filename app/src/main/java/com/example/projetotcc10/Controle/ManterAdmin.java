package com.example.projetotcc10.Controle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.projetotcc10.Modelo.Professor;
import com.example.projetotcc10.R;

import java.util.List;

public class ManterAdmin extends AppCompatActivity {




    EditText emailAdmin, senhaAdmin;
    Button cadastrarAdmin;
    ListView listAdmin;
    List<Admin>admins;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manter_admin);


        emailAdmin = findViewById(R.id.editEmailAdmin);
        senhaAdmin = findViewById(R.id.editSenhaAdmin);
        cadastrarAdmin = findViewById(R.id.buttonCadastrarAdmin);
        listAdmin = findViewById(R.id.listAdmins);



        cadastrarAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



    }
    private void carregalistview(){

        ArrayAdapter<Admin> adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, admins);
        listAdmin.setAdapter(adaptador);
        adaptador.notifyDataSetChanged();

    }
}
