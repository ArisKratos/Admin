package com.example.projetotcc10.Controle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.projetotcc10.R;

import java.util.List;

public class Listar_Admin extends AppCompatActivity {

    private ListView listaAdmin;
    private List<Admin> admins;


    private String[] ArrayAdmins = new String[]{"Joao\njoao@gmail.com","Lucas\nlucas@gmail.com", "Joao\njoao@gmail.com","Lucas\nlucas@gmail.com","Joao\njoao@gmail.com","Lucas\nlucas@gmail.com"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_admin);

        listaAdmin = findViewById(R.id.listAdmin);
        carregalistview();

    }

    private void carregalistview(){

        ArrayAdapter <String> adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ArrayAdmins);
        listaAdmin.setAdapter(adaptador);
        adaptador.notifyDataSetChanged();

    }

}
