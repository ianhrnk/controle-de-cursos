package com.trabalho.controledecursos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.trabalho.controledecursos.db.Aluno;
import com.trabalho.controledecursos.db.AppDatabase;

public class DadosAlunoActivity extends AppCompatActivity {
    AppDatabase db;
    private TextInputLayout txtNome, txtCpf, txtEmail, txtTelefone, txtCurso;
    private TextInputEditText edtNome, edtCpf, edtEmail, edtTelefone;
    private AutoCompleteTextView actCurso;
    private String nome, cpf, email, telefone;
    private int idCurso, idAluno;
    private boolean showMenu = false;
    MaterialButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_aluno);
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

        Intent intent = getIntent();
        idAluno = intent.getIntExtra("id", 0);

        if (idAluno != 0) {
            mostrarDadosAluno(idAluno);
        }

        if (intent.hasExtra("ver_dados")) {
            showMenu = true;
            desativarCampos();
            button.setVisibility(View.GONE);
        }
        else {  // Editar ou cadastrar aluno
            if (intent.hasExtra("editar_dados")) {
                button.setText(getString(R.string.concluir));
                button.setIconResource(R.drawable.outline_done_24);

                button.setOnClickListener(v -> {
                    if (!possuiErrosEntrada()) {
                        Aluno aluno = new Aluno(nome, cpf, email, telefone, idCurso);
                        aluno.alunoId = idAluno;
                        db.alunoDao().atualizarAluno(aluno);
                        Toast.makeText(this,
                                "Edição realizada com sucesso",
                                Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(DadosAlunoActivity.this,
                                MainActivity.class));
                        finish();
                    }
                });
            }
            else {
                button.setText(getString(R.string.cadastrar_aluno));
                button.setIconResource(R.drawable.baseline_add_24);
                button.setOnClickListener(v -> {
                    if (!possuiErrosEntrada()) {
                        Aluno aluno = new Aluno(nome, cpf, email, telefone, idCurso);
                        db.alunoDao().inserirAluno(aluno);

                        Toast.makeText(this,
                                "Aluno cadastrado com sucesso",
                                Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(DadosAlunoActivity.this,
                                MainActivity.class));
                        finish();
                    }
                });
            }

            // Para mapear o curso selecionado no dropdown menu
            int[] idCursos = db.cursoDao().selecionarTodosId();
            String[] nomeCursos = db.cursoDao().selecionarTodosNomes();

            ArrayAdapter<String> adapter =
                    new ArrayAdapter<>(this, R.layout.dm_curso, nomeCursos);
            actCurso.setAdapter(adapter);
            actCurso.setOnItemClickListener((parent, view, position, id) ->
                    idCurso = idCursos[position]);
        }
    }

    // Retorna true se tiver erros na entrada do usuário, caso contrário, retorna falso
    private boolean possuiErrosEntrada() {
        boolean erro = false;
        txtNome.setError(null);
        txtCpf.setError(null);
        txtTelefone.setError(null);

        nome = edtNome.getText().toString();
        cpf = edtCpf.getText().toString();
        email = edtEmail.getText().toString();
        telefone = edtTelefone.getText().toString();

        if (nome.isEmpty() || nome.length() > 50)
        {
            erro = true;
            txtNome.setError(getString(R.string.formato_incorreto));
        }
        if (cpf.length() != 11)
        {
            erro = true;
            txtCpf.setError(getString(R.string.formato_incorreto));
        }
        if (telefone.length() > 11)
        {
            erro = true;
            txtTelefone.setError(getString(R.string.formato_incorreto));
        }

        return erro;
    }

    // Recebe a id do aluno para mostrar as informações dele
    private void mostrarDadosAluno(int id) {
        Aluno aluno = db.alunoDao().selecionarAluno(id);
        edtNome.setText(aluno.nomeAluno);
        edtCpf.setText(aluno.cpf);
        edtEmail.setText(aluno.email);
        edtTelefone.setText(aluno.telefone);
        actCurso.setText(db.cursoDao().selecionarNome(aluno.cursoId));
    }

    // Desativa todos os campos para não permitir a edição
    private void desativarCampos() {
        desativarCampo(txtNome, edtNome);
        desativarCampo(txtCpf, edtCpf);
        desativarCampo(txtEmail, edtEmail);
        desativarCampo(txtTelefone, edtTelefone);

        txtCurso.setEnabled(true);
        txtCurso.setEndIconMode(TextInputLayout.END_ICON_NONE);
        actCurso.setEnabled(false);
    }

    // Desativa um único campo
    private void desativarCampo(TextInputLayout textInputLayout, TextInputEditText editText) {
        textInputLayout.setEndIconMode(TextInputLayout.END_ICON_NONE);
        textInputLayout.setCounterEnabled(false);
        textInputLayout.setEnabled(true);
        editText.setEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dadosaluno_menu, menu);
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
            case R.id.editar_aluno:
                Intent intent = new Intent(DadosAlunoActivity.this,
                        DadosAlunoActivity.class);
                intent.putExtra("editar_dados", true);
                intent.putExtra("id", idAluno);
                startActivity(intent);
                return true;
            case R.id.remover_aluno:
                new MaterialAlertDialogBuilder(this)
                        .setMessage("Tem certeza que deseja remover este aluno?")
                        .setPositiveButton("Sim", (dialog, i) -> {
                            Aluno aluno = db.alunoDao().selecionarAluno(idAluno);
                            db.alunoDao().removerAluno(aluno);
                            startActivity(new Intent(DadosAlunoActivity.this,
                                    MainActivity.class));
                            finish();
                        })
                        .setNegativeButton("Não", null)
                        .show();
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}