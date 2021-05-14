package com.trabalho.controledecursos.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CursoDao {
    @Insert
    void inserirCurso(Curso curso);

    @Update
    void atualizarCurso(Curso curso);

    @Delete
    void deletarCurso(Curso curso);

    @Query("SELECT * FROM curso ORDER BY nome_curso COLLATE NOCASE")
    List<Curso> selecionarTodos();

    @Query("SELECT curso_id FROM curso ORDER BY nome_curso COLLATE NOCASE")
    int[] selecionarTodosId();

    @Query("SELECT nome_curso FROM curso ORDER BY nome_curso COLLATE NOCASE")
    String[] selecionarTodosNomes();

    @Query("SELECT * FROM curso WHERE curso_id = :id")
    Curso selecionarCurso(int id);

    @Query("SELECT nome_curso FROM curso WHERE curso_id = :id")
    String selecionarNome(int id);
}