package com.trabalho.controledecursos.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "aluno")
public class Aluno {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "aluno_id")
    public int alunoId;

    @ColumnInfo(name = "curso_id")
    public int cursoId;

    @ColumnInfo(name = "nome_aluno")
    public String nomeAluno;

    public String cpf;
    public String email;
    public String telefone;

    public Aluno() {

    }

    @Ignore
    public Aluno(String nome, String cpf, String email, String telefone, int cursoId) {
        this.nomeAluno = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.cursoId = cursoId;
    }
}