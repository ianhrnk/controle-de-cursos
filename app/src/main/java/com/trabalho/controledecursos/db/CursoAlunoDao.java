package com.trabalho.controledecursos.db;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface CursoAlunoDao {
    @Transaction
    @Query("SELECT * FROM Curso")
    List<CursoAluno> obterAlunos();
}