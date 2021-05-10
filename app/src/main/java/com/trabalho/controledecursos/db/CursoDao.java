package com.trabalho.controledecursos.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CursoDao {
    @Insert
    void inserirCurso(Curso curso);

    @Delete
    void deletarCurso(Curso curso);

    @Query("SELECT * FROM curso ORDER BY nome_curso COLLATE NOCASE")
    List<Curso> selecionarTodos();
}