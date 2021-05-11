package com.trabalho.controledecursos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class AlunoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curso);

        Toolbar toolbar = findViewById(R.id.tbr_curso);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        TextView txtNomeAluno = findViewById(R.id.txt_aluno_nomealuno);
        //TextView txtCH = findViewById(R.id.txt_curso_ch);

        Intent intent = getIntent();
        txtNomeAluno.setText(intent.getStringExtra("nome"));
    }
}