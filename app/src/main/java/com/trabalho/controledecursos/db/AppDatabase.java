package com.trabalho.controledecursos.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.trabalho.controledecursos.db.dao.AlunoDao;
import com.trabalho.controledecursos.db.dao.CursoDao;
import com.trabalho.controledecursos.db.entity.Aluno;
import com.trabalho.controledecursos.db.entity.Curso;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Aluno.class, Curso.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract AlunoDao alunoDao();
    public abstract CursoDao cursoDao();

    public static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "app_database").build();
                }
            }
        }
        return INSTANCE;
    }
}
