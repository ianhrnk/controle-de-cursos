package com.trabalho.controledecursos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.trabalho.controledecursos.db.AppDatabase;
import com.trabalho.controledecursos.db.Curso;

public class DadosCursoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_curso);

        Toolbar toolbar = findViewById(R.id.tbr_dadoscurso);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        TextInputLayout txtNomeCurso = findViewById(R.id.txt_dadoscurso_nome);
        TextInputLayout txtQntdHoras = findViewById(R.id.txt_dadoscurso_ch);
        TextInputEditText edtNomeCurso = findViewById(R.id.edt_dadoscurso_nome);
        TextInputEditText edtQntdHoras = findViewById(R.id.edt_dadoscurso_ch);

        Button btnAdicionarCurso = findViewById(R.id.btn_dadoscurso_addcurso);
        btnAdicionarCurso.setOnClickListener(v -> {
            boolean erro = false;

            txtNomeCurso.setError(null);
            txtQntdHoras.setError(null);

            String nomeCurso = edtNomeCurso.getText().toString();
            String qntdHoras = edtQntdHoras.getText().toString();

            if (nomeCurso.isEmpty() || nomeCurso.length() > 25)  {
                erro = true;
                txtNomeCurso.setError(getString(R.string.formato_incorreto));
            }
            if (qntdHoras.isEmpty() || qntdHoras.length() > 4) {
                erro = true;
                txtQntdHoras.setError(getString(R.string.formato_incorreto));
            }

            if (!erro) {
                AppDatabase db = AppDatabase.getDatabase(this);
                Curso curso = new Curso();
                curso.nomeCurso = nomeCurso;
                curso.qntdHoras = Integer.parseInt(qntdHoras);
                db.cursoDao().inserirCurso(curso);

                Toast.makeText(this, "Curso cadastrado com sucesso", Toast.LENGTH_SHORT).show();

                Intent it = new Intent(DadosCursoActivity.this, MainActivity.class);
                startActivity(it);
            }
        });
    }
    /*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                return false;
        }
        return super.onOptionsItemSelected(item);
    }*/
}