package com.trabalho.controledecursos.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "aluno",
        foreignKeys = {@ForeignKey
                (entity = Curso.class,
                parentColumns = "id",
                childColumns = "curso_id",
                onDelete = ForeignKey.RESTRICT)
})
public class Aluno {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "aluno_id")
    private int alunoId;

    @ColumnInfo(name = "curso_id")
    private int cursoId;

    private String nome;
    private String cpf;
    private String email;
    private String telefone;

    public Aluno() {

    }

    @Ignore
    public Aluno(String nome, String cpf, String email, String telefone, int cursoId) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.cursoId = cursoId;
    }

    public void setAlunoId(int alunoId) {
        this.alunoId = alunoId;
    }

    public void setCursoId(int cursoId) {
        this.cursoId = cursoId;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public int getAlunoId() {
        return alunoId;
    }

    public int getCursoId() {
        return cursoId;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }
}