package com.trabalho.controledecursos.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
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

    public Curso() {

    }

    @Ignore
    public Curso(String nome, int horas) {
        this.nomeCurso = nome;
        this.qntdHoras = horas;
    }
}