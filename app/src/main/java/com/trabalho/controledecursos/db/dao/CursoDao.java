package com.trabalho.controledecursos.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.trabalho.controledecursos.db.CursoAlunos;
import com.trabalho.controledecursos.db.entity.Curso;

import java.util.List;

@Dao
public interface CursoDao {
    @Insert
    void inserir(Curso curso);

    @Update
    void atualizar(Curso curso);

    @Delete
    void remover(Curso curso);

    @Query("SELECT * FROM curso ORDER BY nome COLLATE NOCASE")
    LiveData<List<Curso>> selecionarTodos();

    @Query("SELECT nome FROM curso ORDER BY nome COLLATE NOCASE")
    LiveData<String[]> selecionarNomes();

    @Query("SELECT id FROM curso ORDER BY nome COLLATE NOCASE")
    LiveData<int[]> selecionarIds();

    @Transaction
    @Query("SELECT * FROM Curso ORDER BY nome COLLATE NOCASE")
    LiveData<List<CursoAlunos>> obterAlunos();
}