package com.trabalho.controledecursos.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "curso")
public class Curso {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "curso_id")
    public int cursoId;

    @ColumnInfo(name = "nome_curso")
    public String nomeCurso;

    @ColumnInfo(name = "qntd_horas")
    public int qntdHoras;
}