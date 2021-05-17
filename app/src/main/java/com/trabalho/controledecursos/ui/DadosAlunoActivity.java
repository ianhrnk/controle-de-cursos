package com.trabalho.controledecursos.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

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
import com.trabalho.controledecursos.R;
import com.trabalho.controledecursos.db.entity.Aluno;
import com.trabalho.controledecursos.viewmodel.AppViewModel;

import java.util.List;
import java.util.Objects;

public class DadosAlunoActivity extends AppCompatActivity {
    private AppViewModel viewModel;
    private TextInputLayout txtNome, txtCpf, txtEmail, txtTelefone, txtCurso;
    private TextInputEditText edtNome, edtCpf, edtEmail, edtTelefone;
    private AutoCompleteTextView actCurso;

    private String nome, cpf, email, telefone;
    private boolean mostrarMenu = false;
    private int posAluno, idCurso;

    private static List<Aluno> alunos;
    private static String[] nomeCursos;
    private static int[] idCursos;
    private Aluno aluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_aluno);
        viewModel = new ViewModelProvider(this).get(AppViewModel.class);

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
        MaterialButton button = findViewById(R.id.btn_dadosaluno);

        Intent intent = getIntent();

        // Recebe a posição do aluno no RecyclerView para mostrar os dados dele na atividade.
        posAluno = intent.getIntExtra("posAluno", 0);

        // Salvar as informação do aluno e seu curso é útil
        // quando é preciso exibir e/ou editar os dados.
        if (!alunos.isEmpty()) {
            aluno = alunos.get(posAluno);
            idCurso = aluno.getCursoId();
        }

        if (intent.hasExtra("ver_dados")) {
            mostrarDadosAluno();
            desativarCampos();
            mostrarMenu = true;
            button.setVisibility(View.GONE);
        }
        else {  // Editar ou cadastrar um aluno
            if (intent.hasExtra("editar_dados")) {
                mostrarDadosAluno();
                button.setText(getString(R.string.salvar));
                button.setIconResource(R.drawable.baseline_save_24);

                button.setOnClickListener(v -> {
                    if (!possuiErrosEntrada()) {
                        Aluno novoAluno = new Aluno(nome, cpf, email, telefone, idCurso);
                        novoAluno.setAlunoId(aluno.getAlunoId());
                        viewModel.atualizarAluno(novoAluno);

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
                        viewModel.inserirAluno(aluno);

                        Toast.makeText(this,
                                "Aluno cadastrado com sucesso",
                                Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }

            // Lista todos os cursos no dropdown menu de escolha de curso
            ArrayAdapter<String> adapter =
                    new ArrayAdapter<>(this, R.layout.dm_curso, nomeCursos);
            actCurso.setAdapter(adapter);
            actCurso.setOnItemClickListener((parent, view, position, id) ->
                idCurso = idCursos[position]
            );
        }
    }

    /**
     * Método usado pelo observer em AlunosFragment para atualizar a lista de alunos
     * sempre que houver mudanças.
     * @param listaAlunos Lista contendo todos os alunos.
     */
    public static void atualizarAlunos(List<Aluno> listaAlunos) {
        alunos = listaAlunos;
    }

    /**
     * Método usado pelo observer em AlunosFragment para atualizar o vetor com os nomes dos cursos
     * sempre que houver mudanças.
     * @param nomeCursosArray Vetor de String contendo os nomes de todos os cursos.
     */
    public static void atualizarNomeCursos(String[] nomeCursosArray) {
        nomeCursos = nomeCursosArray;
    }

    /**
     * Método usado pelo observer em AlunosFragment para atualizar o vetor com os id's dos cursos
     * sempre que houver mudanças.
     * @param idCursosArray Vetor de int contendo os id's de todos os cursos.
     */
    public static void atualizarIdCursos(int[] idCursosArray) {
        idCursos = idCursosArray;
    }

    /**
     * Método usado para mostrar os dados do aluno em seus respectivos campos (TextField).
     */
    private void mostrarDadosAluno() {
        edtNome.setText(aluno.getNome());
        edtCpf.setText(aluno.getCpf());
        edtEmail.setText(aluno.getEmail());
        edtTelefone.setText(aluno.getTelefone());

        int i = 0;
        while (idCursos[i] != aluno.getCursoId())
            ++i;
        actCurso.setText(nomeCursos[i]);
    }

    /**
     * Método usado para desativar todos os campos para não permitir a edição.
     */
    private void desativarCampos() {
        desativarCampo(txtNome, edtNome);
        desativarCampo(txtCpf, edtCpf);
        desativarCampo(txtEmail, edtEmail);
        desativarCampo(txtTelefone, edtTelefone);

        txtCurso.setEnabled(true);
        txtCurso.setEndIconMode(TextInputLayout.END_ICON_NONE);
        actCurso.setEnabled(false);
    }

    /**
     * Método usado para desativar um único campo para não permitir a edição.
     * @param textInputLayout Define o layout do campo.
     * @param editText Recebe os dados de entrada do usuário.
     */
    private void desativarCampo(TextInputLayout textInputLayout, TextInputEditText editText) {
        textInputLayout.setEndIconMode(TextInputLayout.END_ICON_NONE);
        textInputLayout.setCounterEnabled(false);
        textInputLayout.setEnabled(true);
        editText.setEnabled(false);
    }

    /**
     * Método usado para verificação de dados de entrada do usuário.
     * É avisado ao usuário se houver erro em cada um dos campos.
     * @return Se possuir erros na entrada, retorna true, senão, false.
     */
    private boolean possuiErrosEntrada() {
        boolean erro = false;
        txtNome.setError(null);
        txtCpf.setError(null);
        txtTelefone.setError(null);
        txtCurso.setError(null);

        nome = Objects.requireNonNull(edtNome.getText()).toString();
        cpf = Objects.requireNonNull(edtCpf.getText()).toString();
        email = Objects.requireNonNull(edtEmail.getText()).toString();
        telefone = Objects.requireNonNull(edtTelefone.getText()).toString();

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
        if (idCurso == 0)
        {
            erro = true;
            txtCurso.setError(getString(R.string.curso_obrigatorio));
        }

        return erro;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dadosaluno_menu, menu);
        if (!mostrarMenu) {
            for (int i = 0; i < menu.size(); ++i)
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
                intent.putExtra("posAluno", posAluno);
                startActivity(intent);
                return true;

            case R.id.remover_aluno:
                new MaterialAlertDialogBuilder(this)
                        .setMessage("Tem certeza que deseja remover este aluno?")
                        .setPositiveButton("Sim", (dialog, i) -> {
                            viewModel.removerAluno(aluno);
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