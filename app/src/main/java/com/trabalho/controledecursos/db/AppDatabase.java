package com.trabalho.controledecursos.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Aluno.class, Curso.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract AlunoDao alunoDao();
    public abstract CursoDao cursoDao();
}
