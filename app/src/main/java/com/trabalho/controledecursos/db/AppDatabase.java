package com.trabalho.controledecursos.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Aluno.class, Curso.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public static /*volatile*/ AppDatabase INSTANCE;
    public abstract AlunoDao alunoDao();
    public abstract CursoDao cursoDao();
    public abstract CursoAlunoDao cursoAlunoDao();

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            //synchronized (AppDatabase.class) {
            //    if (INSTANCE == null) {
                    // TODO: Remove allowMainThreadQueries
                    // Solution: Use executor to run database operations asynchronously on a background thread.
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, "app_database").allowMainThreadQueries().build();
            //    }
            //}
        }
        return INSTANCE;
    }
}
