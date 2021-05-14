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

import com.trabalho.controledecursos.db.Curso;
import com.trabalho.controledecursos.db.CursoDao;

import java.util.List;

public class CursosAdapter extends RecyclerView.Adapter<CursosAdapter.ViewHolder> {
    private final Context context;
    private final List<Curso> cursos;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ConstraintLayout constraintLayout;
        private final TextView txtNomeCurso;

        public ViewHolder(View view) {
            super(view);
            txtNomeCurso = view.findViewById(R.id.txt_main_nomecurso);
            constraintLayout = view.findViewById(R.id.constraintlayout_linha_curso);
        }
    }

    public CursosAdapter(Context context, CursoDao cursoDao) {
        this.context = context;
        cursos = cursoDao.selecionarTodos();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                    .inflate(R.layout.curso, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CursosAdapter.ViewHolder holder, int position) {
        holder.txtNomeCurso.setText(cursos.get(position).nomeCurso);
        holder.constraintLayout.setOnClickListener(v -> {
            Intent intent = new Intent(context, DadosCursoActivity.class);
            intent.putExtra("ver_dados", true);
            intent.putExtra("id", cursos.get(position).cursoId);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return cursos.size();
    }
}
