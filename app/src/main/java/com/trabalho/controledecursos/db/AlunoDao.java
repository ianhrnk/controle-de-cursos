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
    void deletarAluno(Aluno aluno);

    @Query("SELECT * FROM aluno")
    List<Aluno> selecionarTodos();
}