package com.trabalho.controledecursos.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "curso")
public class Curso {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String nome;

    @ColumnInfo(name = "qntd_horas")
    private int qntdHoras;

    public Curso() {

    }

    @Ignore
    public Curso(String nome, int qntdHoras) {
        this.nome = nome;
        this.qntdHoras = qntdHoras;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setQntdHoras(int qntdHoras) {
        this.qntdHoras = qntdHoras;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getQntdHoras() {
        return qntdHoras;
    }
}