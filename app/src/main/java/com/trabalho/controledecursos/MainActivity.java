package com.trabalho.controledecursos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.trabalho.controledecursos.db.AppDatabase;

public class MainActivity extends AppCompatActivity {
    private AppDatabase db;
    private Toolbar tbrMain;
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private FloatingActionButton fabAddCurso;
    private FloatingActionButton fabAddAluno;
    //private int tabPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = AppDatabase.getDatabase(this);

        tbrMain = findViewById(R.id.tbr_main);
        setSupportActionBar(tbrMain);

        fabAddCurso = findViewById(R.id.fab_main_addcurso);
        fabAddCurso.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, DadosCursoActivity.class)));

        fabAddAluno = findViewById(R.id.fab_main_addaluno);
        fabAddAluno.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, DadosAlunoActivity.class)));

        tabLayout = findViewById(R.id.tablayout_main);
        viewPager = findViewById(R.id.viewpager_main);
        viewPager.setAdapter(new ViewPagerAdapter(this));
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    if (position == 0)
                        tab.setText("Cursos");
                    else
                        tab.setText("Alunos");
                }
        ).attach();
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            public void onPageSelected(int position) {
                changeFab(position);
            }
        });
    }

    /*@Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("tabPosition", tabLayout.getSelectedTabPosition());
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_busca:
                // TODO
                return true;
            case R.id.sobre_nos:
                Snackbar.make(this,
                        findViewById(R.id.coordinatorlayout_main),
                        "Desenvolvido por Ian Haranaka e Rodrigo Seiti",
                        Snackbar.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        // Não faz nada ao pressionar o botão de voltar
    }

    public class ViewPagerAdapter extends FragmentStateAdapter {
        private final int NUM_ABAS = 2;

        public ViewPagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            if (position == 0)
                return new CursosFragment(db.cursoDao());
            else
                return new AlunosFragment(db.alunoDao());
        }

        @Override
        public int getItemCount() {
            return NUM_ABAS;
        }
    }

    private void changeFab(int position) {
        if (position == 0) {
            fabAddCurso.show();
            fabAddAluno.hide();
        }
        else {
            fabAddCurso.hide();
            fabAddAluno.show();
        }
    }
}