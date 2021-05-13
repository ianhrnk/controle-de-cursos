package com.trabalho.controledecursos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.trabalho.controledecursos.db.Aluno;
import com.trabalho.controledecursos.db.AppDatabase;

public class DadosAlunoActivity extends AppCompatActivity {
    AppDatabase db;
    TextInputLayout txtNome, txtCpf, txtEmail, txtTelefone, txtCurso;
    TextInputEditText edtNome, edtCpf, edtEmail, edtTelefone;
    AutoCompleteTextView actCurso;
    String nome, cpf, email, telefone;
    int id_curso;
    MaterialButton button;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_aluno);
        intent = getIntent();
        db = AppDatabase.getDatabase(this);

        Toolbar toolbar = findViewById(R.id.tbr_dadosaluno);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        txtNome = findViewById(R.id.txt_dadosaluno_nome);
        txtCpf = findViewById(R.id.txt_dadosaluno_cpf);
        txtEmail = findViewById(R.id.txt_dadosaluno_email);
        txtTelefone = findViewById(R.id.txt_dadosaluno_telefone);
        txtCurso = findViewById(R.id.txt_dadosaluno_curso);

        edtNome = findViewById(R.id.edt_dadosaluno_nome);
        edtCpf = findViewById(R.id.edt_dadosaluno_cpf);
        edtEmail = findViewById(R.id.edt_dadosaluno_email);
        edtTelefone = findViewById(R.id.edt_dadosaluno_telefone);

        actCurso = findViewById(R.id.act_dadosaluno_curso);
        button = findViewById(R.id.btn_dadosaluno);

        if (intent.hasExtra("ver_dados")) {
            mostrarDados(intent.getIntExtra("id", 0));
            button.setVisibility(View.GONE);
        }
        else {  // Cadastrar ou editar aluno
            if (intent.hasExtra("editar_dados")) {
                button.setText(getString(R.string.concluir));
                button.setIconResource(R.drawable.outline_done_24);
            }
            else {
                button.setText(getString(R.string.cadastrar_aluno));
                button.setIconResource(R.drawable.baseline_add_24);
            }

            int[] idCursos = db.cursoDao().selecionarTodosId();
            String[] nomeCursos = db.cursoDao().selecionarTodosNomes();

            ArrayAdapter<String> adapter =
                    new ArrayAdapter<>(this, R.layout.dm_curso, nomeCursos);
            actCurso.setAdapter(adapter);
            actCurso.setOnItemClickListener((parent, view, position, id) ->
                                            id_curso = idCursos[position]);

            button.setOnClickListener(v -> {
                boolean erro = false;

                nome = edtNome.getText().toString();
                cpf = edtCpf.getText().toString();
                email = edtEmail.getText().toString();
                telefone = edtTelefone.getText().toString();

                // TODO: Checar erros da entrada do usuário

                if (!erro) {
                    Aluno aluno = new Aluno();
                    aluno.nomeAluno = nome;
                    aluno.cpf = cpf;
                    aluno.email = email;
                    aluno.telefone = telefone;
                    aluno.cursoId = id_curso;
                    db.alunoDao().inserirAluno(aluno);

                    Toast.makeText(this, "Aluno cadastrado com sucesso", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(DadosAlunoActivity.this, MainActivity.class));
                }
            });
        }
    }

    // Recebe a id do aluno para mostrar as informações dele
    private void mostrarDados(int id) {
        Aluno aluno = db.alunoDao().selecionarAluno(id);
        desabilitarCampos(txtNome, edtNome, aluno.nomeAluno);
        desabilitarCampos(txtCpf, edtCpf, aluno.cpf);
        desabilitarCampos(txtEmail, edtEmail, aluno.email);
        desabilitarCampos(txtTelefone, edtTelefone, aluno.telefone);
        desabilitarCampoCurso(txtCurso, actCurso, db.cursoDao().selecionarCurso(aluno.cursoId).nomeCurso);
    }

    // Desabilita o campo curso e mostra o curso do aluno
    private void desabilitarCampoCurso(TextInputLayout textInputLayout, AutoCompleteTextView actTextView, String text) {
        textInputLayout.setEnabled(true);
        textInputLayout.setEndIconMode(TextInputLayout.END_ICON_NONE);
        actTextView.setText(text);
        actTextView.setEnabled(false);
    }

    // Desabilita os demais campos e mostra o dado daquele campo
    private void desabilitarCampos(TextInputLayout textInputLayout, TextInputEditText editText, String text) {
        textInputLayout.setEndIconMode(TextInputLayout.END_ICON_NONE);
        textInputLayout.setCounterEnabled(false);
        textInputLayout.setEnabled(true);
        editText.setText(text);
        editText.setEnabled(false);
    }
}