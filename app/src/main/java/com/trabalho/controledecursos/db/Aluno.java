package com.trabalho.controledecursos.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
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
}