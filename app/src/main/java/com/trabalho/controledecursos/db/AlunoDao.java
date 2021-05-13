package com.trabalho.controledecursos.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AlunoDao {
    @Insert
    long inserirAluno(Aluno aluno);

    @Delete
    void removerAluno(Aluno aluno);

    @Query("SELECT * FROM aluno ORDER BY nome_aluno COLLATE NOCASE")
    List<Aluno> selecionarTodos();

    @Query("SELECT * FROM aluno WHERE aluno_id = :id")
    Aluno selecionarAluno(int id);
}