package com.trabalho.controledecursos.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trabalho.controledecursos.R;
import com.trabalho.controledecursos.viewmodel.AppViewModel;


public class AlunosFragment extends Fragment {
    private AlunosAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        AppViewModel viewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);
        viewModel.getTodosAlunos().observe(getViewLifecycleOwner(), alunos -> {
            adapter.setAlunos(alunos);
            DadosAlunoActivity.atualizarAlunos(alunos);
        });
        viewModel.getNomeCursos().observe(getViewLifecycleOwner(),
                DadosAlunoActivity::atualizarNomeCursos);
        viewModel.getIdCursos().observe(getViewLifecycleOwner(),
                DadosAlunoActivity::atualizarIdCursos);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alunos, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview_alunos);

        adapter = new AlunosAdapter(view.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        return view;
    }
}