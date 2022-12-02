package pt.ipleiria.estg.dei.rentallcar.vistas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.rentallcar.MenuMainActivity;
import pt.ipleiria.estg.dei.rentallcar.R;
import pt.ipleiria.estg.dei.rentallcar.adaptadores.ListaLivroAdaptador;
import pt.ipleiria.estg.dei.rentallcar.modelo.Livro;
import pt.ipleiria.estg.dei.rentallcar.modelo.SingletonGestorLivros;

public class ListaVeiculoFragment extends Fragment {

    private ListView lvLivros;
    private ArrayList<Livro> livros;
    private FloatingActionButton fabLista;
    private SearchView searchView;
    public static final int DETALHES = 1;

    public ListaVeiculoFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_lista_veiculo, container, false);
        setHasOptionsMenu(true);
        lvLivros = view.findViewById(R.id.lvLivros);
        fabLista = view.findViewById(R.id.fabLista);
        livros = SingletonGestorLivros.getInstance(getContext()).getLivrosBD();
        lvLivros.setAdapter(new ListaLivroAdaptador(getContext(), livros));

        lvLivros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // Toast.makeText(getContext(), livros.get(position).getTitulo(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), DetalhesVeiculoActivity.class);
                intent.putExtra(DetalhesVeiculoActivity.IDLIVRO, (int) id);
                startActivityForResult(intent, DETALHES);
            }
        });

        fabLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DetalhesVeiculoActivity.class);
                startActivityForResult(intent, DETALHES);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        if (resultCode == Activity.RESULT_OK && requestCode == DETALHES) {
            livros = SingletonGestorLivros.getInstance(getContext()).getLivrosBD();
            lvLivros.setAdapter(new ListaLivroAdaptador(getContext(), livros));
            switch (intent.getIntExtra(MenuMainActivity.OPERACAO, 0)) {
                case MenuMainActivity.ADD:
                    Toast.makeText(getContext(), "Livro adicionado com sucesso", Toast.LENGTH_SHORT).show();
                    break;
                case MenuMainActivity.EDIT:
                    Toast.makeText(getContext(), "Livro modificado com sucesso", Toast.LENGTH_SHORT).show();
                    break;
                case MenuMainActivity.DELETE:
                    Toast.makeText(getContext(), "Livro eliminado com sucesso", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_pesquisa, menu);
        MenuItem itemPesquisa = menu.findItem(R.id.itemPesquisa);
        searchView = (SearchView) itemPesquisa.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                ArrayList<Livro> listaTempLivros = new ArrayList<>();
                for (Livro l : livros)
                    if (l.getTitulo().toLowerCase().contains(s.toLowerCase()))
                        listaTempLivros.add(l);
                lvLivros.setAdapter(new ListaLivroAdaptador(getContext(), listaTempLivros));
                return true;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onResume() {
        if (searchView != null)
            searchView.onActionViewCollapsed();
        super.onResume();
    }
}

