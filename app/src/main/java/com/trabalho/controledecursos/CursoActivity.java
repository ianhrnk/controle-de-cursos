package com.trabalho.controledecursos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class CursoActivity extends AppCompatActivity {

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

        TextView txtNomeCurso = findViewById(R.id.txt_curso_nomecurso);
        TextView txtCH = findViewById(R.id.txt_curso_ch);

        Intent intent = getIntent();
        int ch = intent.getIntExtra("ch", 0);

        txtNomeCurso.setText(intent.getStringExtra("nome"));
        txtCH.setText(getString(R.string.ch, ch));
    }
}