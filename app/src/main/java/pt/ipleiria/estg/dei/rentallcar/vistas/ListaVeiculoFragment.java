package pt.ipleiria.estg.dei.rentallcar.vistas;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.rentallcar.R;
import pt.ipleiria.estg.dei.rentallcar.adaptadores.ListaVeiculoAdaptador;
import pt.ipleiria.estg.dei.rentallcar.listeners.VeiculosListener;
import pt.ipleiria.estg.dei.rentallcar.modelo.SingletonGestorVeiculos;
import pt.ipleiria.estg.dei.rentallcar.modelo.Veiculo;

public class ListaVeiculoFragment extends Fragment implements VeiculosListener {

    private ListView lvVeiculos;
    private ArrayList<Veiculo> veiculos;
    private SearchView searchView;
    public static final int DETALHES = 1;

    public ListaVeiculoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_lista_veiculo, container, false);

        setHasOptionsMenu(true);

        lvVeiculos = view.findViewById(R.id.lvLivros);
        SingletonGestorVeiculos.getInstance(getContext()).setVeiculosListener(this);

        SingletonGestorVeiculos.getInstance(getContext()).getAllVeiculosAPI(getContext());

        lvVeiculos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // Toast.makeText(getContext(), livros.get(position).getTitulo(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), DetalhesVeiculoActivity.class);
                intent.putExtra(DetalhesVeiculoActivity.IDVEICULO, (int) id);
                startActivityForResult(intent, DETALHES);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        if (searchView != null)
            searchView.onActionViewCollapsed();
        super.onResume();
    }

    @Override
    public void onRefreshListaVeiculos(ArrayList<Veiculo> listaVeiculos) {
        if (listaVeiculos != null)
            lvVeiculos.setAdapter(new ListaVeiculoAdaptador(getContext(), listaVeiculos));

    }
}

