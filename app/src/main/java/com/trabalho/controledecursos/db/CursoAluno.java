package com.trabalho.controledecursos.db;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class CursoAluno {
    @Embedded public Curso curso;
    @Relation (
        parentColumn = "cursoId",
        entityColumn = "cursoId"
    )
    public List<Aluno> alunos;
}