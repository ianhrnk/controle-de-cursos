package com.trabalho.controledecursos.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CursoDao {
    @Insert
    public long inserirCurso(Curso curso);

    @Delete
    public void deletarCurso(Curso curso);

    @Query("SELECT * FROM curso")
    public List<Aluno> selecionarTodos();
}