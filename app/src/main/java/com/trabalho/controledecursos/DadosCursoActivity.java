package com.trabalho.controledecursos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.trabalho.controledecursos.db.AppDatabase;
import com.trabalho.controledecursos.db.Curso;

public class DadosCursoActivity extends AppCompatActivity {
    AppDatabase db;
    TextInputLayout txtNome, txtQntdHoras;
    TextInputEditText edtNome, edtQntdHoras;
    MaterialButton button;
    private boolean showMenu = false;
    String nome, qntdHoras;
    int idCurso;
    String[] nomeAlunos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_curso);
        db = AppDatabase.getDatabase(this);

        Toolbar toolbar = findViewById(R.id.tbr_dadoscurso);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        txtNome = findViewById(R.id.txt_dadoscurso_nome);
        txtQntdHoras = findViewById(R.id.txt_dadoscurso_ch);
        edtNome = findViewById(R.id.edt_dadoscurso_nome);
        edtQntdHoras = findViewById(R.id.edt_dadoscurso_ch);
        button = findViewById(R.id.btn_dadoscurso);

        Intent intent = getIntent();
        idCurso = intent.getIntExtra("id", 0);
        nomeAlunos = db.alunoDao().selecionarAlunosCurso(idCurso);

        if (idCurso != 0) {
            mostrarDadosCurso(idCurso);
        }

        if (intent.hasExtra("ver_dados")) {
            showMenu = true;
            desativarCampos();
            button.setVisibility(View.GONE);
        }
        else {  // Editar ou cadastrar curso
            if (intent.hasExtra("editar_dados")) {
                button.setText(getString(R.string.concluir));
                button.setIconResource(R.drawable.outline_done_24);

                button.setOnClickListener(v -> {
                    if (!possuiErrosEntrada()) {
                        Curso curso = new Curso(nome, Integer.parseInt(qntdHoras));
                        curso.cursoId = idCurso;
                        db.cursoDao().atualizarCurso(curso);
                        Toast.makeText(this,
                                "Edição realizada com sucesso",
                                Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(DadosCursoActivity.this,
                                MainActivity.class));
                        finish();
                    }
                });
            }
            else {
                button.setText(getString(R.string.cadastrar_curso));
                button.setIconResource(R.drawable.baseline_add_24);
                button.setOnClickListener(v -> {
                    if (!possuiErrosEntrada()) {
                        Curso curso = new Curso(nome, Integer.parseInt(qntdHoras));
                        db.cursoDao().inserirCurso(curso);

                        Toast.makeText(this,
                                "Curso cadastrado com sucesso",
                                Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(DadosCursoActivity.this,
                                MainActivity.class));
                        finish();
                    }
                });
            }
        }
    }

    // Recebe a id do curso para mostrar as informações dele
    private void mostrarDadosCurso(int id) {
        Curso curso = db.cursoDao().selecionarCurso(id);
        edtNome.setText(curso.nomeCurso);
        edtQntdHoras.setText(String.valueOf(curso.qntdHoras));
    }

    // Desativa todos os campos para não permitir a edição
    private void desativarCampos() {
        desativarCampo(txtNome, edtNome);
        desativarCampo(txtQntdHoras, edtQntdHoras);
    }

    // Desativa um único campo
    private void desativarCampo(TextInputLayout textInputLayout, TextInputEditText editText) {
        textInputLayout.setEndIconMode(TextInputLayout.END_ICON_NONE);
        textInputLayout.setCounterEnabled(false);
        textInputLayout.setEnabled(true);
        editText.setEnabled(false);
    }

    // Retorna true se tiver erros na entrada do usuário, caso contrário, retorna falso
    private boolean possuiErrosEntrada() {
        boolean erro = false;
        txtNome.setError(null);
        txtQntdHoras.setError(null);

        nome = edtNome.getText().toString();
        qntdHoras = edtQntdHoras.getText().toString();

        if (nome.isEmpty() || nome.length() > 25) {
            erro = true;
            txtNome.setError(getString(R.string.formato_incorreto));
        }
        if (qntdHoras.isEmpty() || qntdHoras.length() > 4) {
            erro = true;
            txtQntdHoras.setError(getString(R.string.formato_incorreto));
        }

        return erro;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dadoscurso_menu, menu);
        if (!showMenu) {
            for (int i = 0; i < menu.size(); i++)
                menu.getItem(i).setVisible(false);
        }
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.editar_curso:
                Intent intent = new Intent(DadosCursoActivity.this,
                        DadosCursoActivity.class);
                intent.putExtra("editar_dados", true);
                intent.putExtra("id", idCurso);
                startActivity(intent);
                return true;
            case R.id.remover_curso:
                // TODO: Verificar se há alunos cadastrados, avisar o usuário e não permitir a remoção
                // if (nomeAlunos.length == 0)
                /*new MaterialAlertDialogBuilder(this)
                        .setMessage("Tem certeza que deseja excluir este curso?")
                        .setPositiveButton("Sim", (dialog, i) -> {
                            Curso curso = db.cursoDao().selecionarCurso(idCurso);
                            db.cursoDao().removerCurso(curso);
                            startActivity(new Intent(DadosCursoActivity.this,
                                    MainActivity.class));
                            finish();
                        })
                        .setNegativeButton("Não", null)
                        .show();*/
                // Se não {
                //  Snackbar.make(this, R.string.text_label, Snackbar.LENGTH_SHORT)
                //    .show()
                //}
                return true;
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}