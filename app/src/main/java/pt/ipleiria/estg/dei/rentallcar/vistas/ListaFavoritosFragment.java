package pt.ipleiria.estg.dei.rentallcar.vistas;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.rentallcar.R;
import pt.ipleiria.estg.dei.rentallcar.adaptadores.ItemAdapter;
import pt.ipleiria.estg.dei.rentallcar.listeners.FavoritosListener;
import pt.ipleiria.estg.dei.rentallcar.modelo.Favorito;
import pt.ipleiria.estg.dei.rentallcar.modelo.SingletonGestorVeiculos;

public class ListaFavoritosFragment extends Fragment implements FavoritosListener {

    private ListView lvFavoritos;
    private ArrayList<Favorito> favoritos;
    public static final int DETALHES = 2;

    public ListaFavoritosFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_favoritos, container, false);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_info", MODE_PRIVATE);

        lvFavoritos = view.findViewById(R.id.lvFavoritos);


        favoritos = SingletonGestorVeiculos.getInstance(getContext()).getFavoritos();
        lvFavoritos.setAdapter(new ItemAdapter((Activity) getContext(),favoritos));


        SingletonGestorVeiculos.getInstance(getContext()).setVeiculosListener(this);
        SingletonGestorVeiculos.getInstance(getContext()).getAllVeiculosAPI(getContext());

        lvFavoritos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Intent intent = new Intent(getContext(), DetalhesVeiculoActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }


    @Override
    public void onRefreshListaFavoritos(ArrayList<Favorito> favoritos) {
        if (favoritos != null)
            lvFavoritos.setAdapter(new ItemAdapter(getActivity(), favoritos));
    }
}