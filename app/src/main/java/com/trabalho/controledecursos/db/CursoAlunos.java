package com.trabalho.controledecursos.db;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.trabalho.controledecursos.db.entity.Aluno;
import com.trabalho.controledecursos.db.entity.Curso;

import java.util.List;

public class CursoAlunos {
    @Embedded
    public Curso curso;
    @Relation(
            parentColumn = "id",
            entityColumn = "curso_id"
    )
    public List<Aluno> alunos;
}
