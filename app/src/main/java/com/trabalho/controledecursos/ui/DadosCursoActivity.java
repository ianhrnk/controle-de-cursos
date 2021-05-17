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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.trabalho.controledecursos.R;
import com.trabalho.controledecursos.db.CursoAlunos;
import com.trabalho.controledecursos.db.entity.Aluno;
import com.trabalho.controledecursos.db.entity.Curso;
import com.trabalho.controledecursos.viewmodel.AppViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DadosCursoActivity extends AppCompatActivity {
    private AppViewModel viewModel;
    private TextInputLayout txtNome, txtQntdHoras;
    private TextInputEditText edtNome, edtQntdHoras;
    private MaterialButton button;
    private TextView txtAlunos;
    private ListView listView;
    private boolean mostrarMenu = false;
    private String nome, qntdHoras;

    private int posCurso;
    private static List<CursoAlunos> cursoAlunos = new ArrayList<>();
    private Curso curso;
    private List<Aluno> alunos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_curso);
        viewModel = new ViewModelProvider(this).get(AppViewModel.class);

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
        txtAlunos = findViewById(R.id.txt_dadoscurso_aluno);
        listView = findViewById(R.id.listview_dadoscurso_alunos);

        Intent intent = getIntent();

        // Recebe a posição do curso no RecyclerView para mostrar os dados dele na atividade.
        posCurso = intent.getIntExtra("posCurso", 0);

        if (intent.hasExtra("ver_dados")) {
            mostrarDadosCurso();
            desativarCampos();
            mostrarMenu = true;
            button.setVisibility(View.GONE);
        }
        else {  // Editar ou cadastrar um curso
            txtAlunos.setVisibility(View.GONE);
            listView.setVisibility(View.GONE);

            if (intent.hasExtra("editar_dados")) {
                mostrarDadosCurso();
                button.setText(getString(R.string.salvar));
                button.setIconResource(R.drawable.baseline_save_24);

                button.setOnClickListener(v -> {
                    if (!possuiErrosEntrada()) {
                        curso.setNome(nome);
                        curso.setQntdHoras(Integer.parseInt(qntdHoras));
                        viewModel.atualizarCurso(curso);

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
                        Curso novoCurso = new Curso(nome, Integer.parseInt(qntdHoras));
                        viewModel.inserirCurso(novoCurso);

                        Toast.makeText(this,
                                "Curso cadastrado com sucesso",
                                Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        }
    }

    /**
     * Método usado pelo observer para atualizar a lista de CursoAlunos sempre que houver mudanças.
     * @param CursoAlunos Lista de objetos do tipo CursoAluno
     *                    com todos os cursos e seus alunos agrupados.
     */
    public static void atualizarCursoAlunos(List<CursoAlunos> CursoAlunos) {
        cursoAlunos = CursoAlunos;
    }

    /**
     * Método usado para mostrar os dados do curso em seus respectivos campos (TextField).
     */
    private void mostrarDadosCurso() {
        curso = cursoAlunos.get(posCurso).curso;
        edtNome.setText(curso.getNome());
        edtQntdHoras.setText(String.valueOf(curso.getQntdHoras()));
        listarAlunos();
    }

    /**
     * Método usado para listar os nomes dos alunos do curso na ListView.
     */
    private void listarAlunos() {
        alunos = cursoAlunos.get(posCurso).alunos;
        if (alunos.size() != 0) { // Se tiver alunos cadastrados
            List<String> nomeAlunos = new ArrayList<>();
            for(Aluno aluno : alunos)
                nomeAlunos.add(aluno.getNome());

            ArrayAdapter<String> adapter =
                    new ArrayAdapter<>(this, R.layout.curso_aluno, nomeAlunos);
            listView.setAdapter(adapter);
        }
        else
            txtAlunos.setText(R.string.nao_tem_alunos);
    }

    /**
     * Método usado para desativar todos os campos para não permitir a edição.
     */
    private void desativarCampos() {
        desativarCampo(txtNome, edtNome);
        desativarCampo(txtQntdHoras, edtQntdHoras);
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
        txtQntdHoras.setError(null);

        nome = Objects.requireNonNull(edtNome.getText()).toString();
        qntdHoras = Objects.requireNonNull(edtQntdHoras.getText()).toString();

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
            case R.id.editar_curso:
                Intent intent = new Intent(DadosCursoActivity.this,
                        DadosCursoActivity.class);
                intent.putExtra("editar_dados", true);
                intent.putExtra("posCurso", posCurso);
                startActivity(intent);
                return true;

            case R.id.remover_curso:
                if (alunos.size() == 0) // Verifica se há alunos cadastrados antes de remover
                    new MaterialAlertDialogBuilder(this)
                        .setMessage("Tem certeza que deseja excluir este curso?")
                        .setPositiveButton("Sim", (dialog, i) -> {
                            viewModel.removerCurso(curso);
                            finish();
                        })
                        .setNegativeButton("Não", null)
                        .show();
                else {
                    Snackbar.make(this,
                          findViewById(R.id.constraintlayout_dadoscurso),
                          "Não foi possível deletar este curso, há alunos cadastrados nele",
                          Snackbar.LENGTH_SHORT).show();
                }
                return true;

            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}