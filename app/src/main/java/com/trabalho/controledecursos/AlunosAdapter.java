package com.trabalho.controledecursos;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.trabalho.controledecursos.db.Aluno;
import com.trabalho.controledecursos.db.AlunoDao;

import java.util.List;

public class AlunosAdapter extends RecyclerView.Adapter<AlunosAdapter.ViewHolder> {
    private final Context context;
    private final List<Aluno> alunos;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ConstraintLayout constraintLayout;
        private final TextView txtNomeAluno;

        public ViewHolder(View view) {
            super(view);
            txtNomeAluno = view.findViewById(R.id.txt_main_nomealuno);
            constraintLayout = view.findViewById(R.id.constraintlayout_linha_aluno);
        }
    }

    public AlunosAdapter(Context context, AlunoDao alunoDao) {
        this.context = context;
        alunos = alunoDao.selecionarTodos();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.aluno, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlunosAdapter.ViewHolder holder, int position) {
        holder.txtNomeAluno.setText(alunos.get(position).nomeAluno);
        holder.constraintLayout.setOnClickListener(v -> {
            Intent intent = new Intent(context, AlunoActivity.class);
            intent.putExtra("id", alunos.get(position).alunoId);
            intent.putExtra("nome", alunos.get(position).nomeAluno);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return alunos.size();
    }
}
