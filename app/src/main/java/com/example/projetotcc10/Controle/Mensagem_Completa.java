package com.example.projetotcc10.Controle;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.projetotcc10.R;

import org.w3c.dom.Text;

public class Mensagem_Completa extends AppCompatActivity {

    private TextView aliasMsgCompleta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensagem_completa);

        ActionBar actionBar = getSupportActionBar();
        aliasMsgCompleta = findViewById(R.id.txt_msg_completa);

        Intent intent  =getIntent();

        String mMsg = intent.getStringExtra("Mensagem");


        actionBar.setTitle(mMsg);


        aliasMsgCompleta.setText(mMsg);




    }
}
