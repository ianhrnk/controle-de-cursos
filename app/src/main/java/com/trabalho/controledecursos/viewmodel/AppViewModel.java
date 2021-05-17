package com.trabalho.controledecursos.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.trabalho.controledecursos.DataRepository;
import com.trabalho.controledecursos.db.CursoAlunos;
import com.trabalho.controledecursos.db.entity.Aluno;
import com.trabalho.controledecursos.db.entity.Curso;

import java.util.List;

public class AppViewModel extends AndroidViewModel {
    private final DataRepository repository;
    private LiveData<List<CursoAlunos>> cursoAlunos;
    private LiveData<List<Aluno>> todosAlunos;
    private LiveData<List<Curso>> todosCursos;
    private LiveData<String[]> nomeCursos;
    private LiveData<int[]> idCursos;

    public AppViewModel (Application application) {
        super(application);
        repository = new DataRepository(getApplication());
    }

    public void inserirAluno(Aluno aluno) { repository.inserirAluno(aluno); }

    public void atualizarAluno(Aluno aluno) { repository.atualizarAluno(aluno); }

    public void removerAluno(Aluno aluno) { repository.removerAluno(aluno); }

    public void inserirCurso(Curso curso) { repository.inserirCurso(curso); }

    public void atualizarCurso(Curso curso) { repository.atualizarCurso(curso); }

    public void removerCurso(Curso curso) { repository.removerCurso(curso); }

    public LiveData<List<CursoAlunos>> getCursoAlunos() {
        if (cursoAlunos == null)
            cursoAlunos = repository.getCursoAlunos();
        return cursoAlunos;
    }

    public LiveData<List<Aluno>> getTodosAlunos() {
        if (todosAlunos == null)
            todosAlunos = repository.getAlunos();
        return todosAlunos;
    }

    public LiveData<List<Curso>> getTodosCursos() {
        if (todosCursos == null)
            todosCursos = repository.getCursos();
        return todosCursos;
    }

    public LiveData<String[]> getNomeCursos() {
        if (nomeCursos == null)
            nomeCursos = repository.getNomeCursos();
        return nomeCursos;
    }

    public LiveData<int[]> getIdCursos() {
        if (idCursos == null)
            idCursos = repository.getIdCursos();
        return idCursos;
    }
}
