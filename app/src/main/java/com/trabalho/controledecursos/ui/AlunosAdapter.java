package com.trabalho.controledecursos.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.trabalho.controledecursos.R;
import com.trabalho.controledecursos.db.entity.Aluno;

import java.util.ArrayList;
import java.util.List;

public class AlunosAdapter extends RecyclerView.Adapter<AlunosAdapter.ViewHolder> {
    private final Context context;
    private List<Aluno> alunos = new ArrayList<>();

    public AlunosAdapter(Context context) {
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ConstraintLayout constraintLayout;
        private final TextView txtNomeAluno;

        public ViewHolder(View view) {
            super(view);
            txtNomeAluno = view.findViewById(R.id.txt_main_nomealuno);
            constraintLayout = view.findViewById(R.id.constraintlayout_linha_aluno);
        }
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
        Aluno aluno = alunos.get(position);

        holder.txtNomeAluno.setText(aluno.getNome());
        holder.constraintLayout.setOnClickListener(v -> {
            Intent intent = new Intent(context, DadosAlunoActivity.class);
            intent.putExtra("ver_dados", true);
            intent.putExtra("posAluno", position);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return alunos.size();
    }

    public void setAlunos(List<Aluno> alunos) {
        this.alunos = alunos;
        notifyDataSetChanged();
    }
}
