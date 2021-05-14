package com.trabalho.controledecursos.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AlunoDao {
    @Insert
    void inserirAluno(Aluno aluno);

    @Update
    void atualizarAluno(Aluno aluno);

    @Delete
    void removerAluno(Aluno aluno);

    @Query("SELECT * FROM aluno ORDER BY nome_aluno COLLATE NOCASE")
    List<Aluno> selecionarTodos();

    @Query("SELECT * FROM aluno WHERE aluno_id = :id")
    Aluno selecionarAluno(int id);

    @Query("SELECT nome_aluno FROM Aluno WHERE curso_id = :id ORDER BY nome_aluno COLLATE NOCASE")
    String[] selecionarAlunosCurso(int id);
}