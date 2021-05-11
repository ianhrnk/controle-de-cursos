package com.trabalho.controledecursos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trabalho.controledecursos.db.AlunoDao;

public class AlunosFragment extends Fragment {
    private final AlunoDao alunoDao;

    public AlunosFragment(AlunoDao alunoDao) {
        this.alunoDao = alunoDao;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alunos, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview_alunos);
        AlunosAdapter adapter = new AlunosAdapter(view.getContext(), alunoDao);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        return view;
    }
}