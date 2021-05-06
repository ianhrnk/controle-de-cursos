package com.trabalho.controledecursos.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "aluno")
public class Aluno {
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "nome_aluno")
    public String nomeAluno;

    public String cpf;
    public String email;
    public String telefone;
}

/*
@Entity(foreignKeys = {@ForeignKey(entity = ParentClass.class,
        parentColumns = "parentClassColumn",
        childColumns = "childClassColumn",
        onDelete = ForeignKey.CASCADE)})
*/