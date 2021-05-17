package com.trabalho.controledecursos.ui;

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
import com.trabalho.controledecursos.R;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton fabAddCurso;
    private FloatingActionButton fabAddAluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar tbrMain = findViewById(R.id.tbr_main);
        setSupportActionBar(tbrMain);

        fabAddCurso = findViewById(R.id.fab_main_addcurso);
        fabAddCurso.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, DadosCursoActivity.class)));

        fabAddAluno = findViewById(R.id.fab_main_addaluno);
        fabAddAluno.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, DadosAlunoActivity.class)));

        TabLayout tabLayout = findViewById(R.id.tablayout_main);
        ViewPager2 viewPager = findViewById(R.id.viewpager_main);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.sobre_nos) {
            Snackbar.make(this,
                    findViewById(R.id.coordinatorlayout_main),
                    "Desenvolvido por Ian Haranaka e Rodrigo Seiti",
                    Snackbar.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class ViewPagerAdapter extends FragmentStateAdapter {

        public ViewPagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            if (position == 0)
                return new CursosFragment();
            else
                return new AlunosFragment();
        }

        /**
         * Retorna o número total de abas, neste caso, 2.
         */
        @Override
        public int getItemCount() {
            return 2;
        }
    }

    /**
     * Executa a mudança dos botões principais de acordo com a aba selecionada.
     * @param position Index da aba
     */
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