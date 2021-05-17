package com.trabalho.controledecursos;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.trabalho.controledecursos.db.AppDatabase;
import com.trabalho.controledecursos.db.CursoAlunos;
import com.trabalho.controledecursos.db.dao.AlunoDao;
import com.trabalho.controledecursos.db.dao.CursoDao;
import com.trabalho.controledecursos.db.entity.Aluno;
import com.trabalho.controledecursos.db.entity.Curso;

import java.util.List;

public class DataRepository {
    private final AlunoDao alunoDao;
    private final CursoDao cursoDao;
    private final LiveData<List<CursoAlunos>> cursoAlunos;
    private final LiveData<List<Aluno>> todosAlunos;
    private final LiveData<List<Curso>> todosCursos;
    private final LiveData<String[]> nomeCursos;
    private final LiveData<int[]> idCursos;

    public DataRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        alunoDao = db.alunoDao();
        cursoDao = db.cursoDao();

        cursoAlunos = cursoDao.obterAlunos();
        todosAlunos = alunoDao.selecionarTodos();
        todosCursos = cursoDao.selecionarTodos();
        nomeCursos = cursoDao.selecionarNomes();
        idCursos = cursoDao.selecionarIds();
    }

    public void inserirAluno(Aluno aluno) {
        AppDatabase.databaseWriteExecutor.execute(() -> alunoDao.inserir(aluno));
    }

    public void atualizarAluno(Aluno aluno) {
        AppDatabase.databaseWriteExecutor.execute(() -> alunoDao.atualizar(aluno));
    }

    public void removerAluno(Aluno aluno) {
        AppDatabase.databaseWriteExecutor.execute(() -> alunoDao.remover(aluno));
    }

    public void inserirCurso(Curso curso) {
        AppDatabase.databaseWriteExecutor.execute(() -> cursoDao.inserir(curso));
    }

    public void atualizarCurso(Curso curso) {
        AppDatabase.databaseWriteExecutor.execute(() -> cursoDao.atualizar(curso));
    }

    public void removerCurso(Curso curso) {
        AppDatabase.databaseWriteExecutor.execute(() -> cursoDao.remover(curso));
    }

    public LiveData<List<CursoAlunos>> getCursoAlunos() { return cursoAlunos; }

    public LiveData<List<Aluno>> getAlunos() { return todosAlunos; }

    public LiveData<List<Curso>> getCursos() { return todosCursos; }

    public LiveData<String[]> getNomeCursos() { return nomeCursos; }

    public LiveData<int[]> getIdCursos() { return idCursos; }
}
