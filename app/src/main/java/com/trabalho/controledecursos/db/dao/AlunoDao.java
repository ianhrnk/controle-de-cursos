package com.trabalho.controledecursos.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.trabalho.controledecursos.db.entity.Aluno;

import java.util.List;

@Dao
public interface AlunoDao {
    @Insert
    void inserir(Aluno aluno);

    @Update
    void atualizar(Aluno aluno);

    @Delete
    void remover(Aluno aluno);

    @Query("SELECT * FROM aluno ORDER BY nome COLLATE NOCASE")
    LiveData<List<Aluno>> selecionarTodos();
}