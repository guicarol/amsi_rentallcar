package pt.ipleiria.estg.dei.rentallcar.vistas;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.rentallcar.R;
import pt.ipleiria.estg.dei.rentallcar.adaptadores.ListaReservasAdaptador;
import pt.ipleiria.estg.dei.rentallcar.listeners.ReservasListener;
import pt.ipleiria.estg.dei.rentallcar.modelo.Reserva;
import pt.ipleiria.estg.dei.rentallcar.modelo.SingletonGestorVeiculos;


public class ListaReservaFragment extends Fragment implements ReservasListener {
    private ListView lvReservas;


    public  ListaReservaFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_reservas, container, false);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_info", MODE_PRIVATE);
        int id = sharedPreferences.getInt("id", -1);
        lvReservas= view.findViewById(R.id.lvReservas);
        SingletonGestorVeiculos.getInstance(getContext()).setReservasListener(this);
        SingletonGestorVeiculos.getInstance(getContext()).getReservaAPI(getContext(),id);

    return view;
    }

    @Override
    public void onRefreshListaReservas(ArrayList<Reserva> reservas) {
        if (reservas != null)
            lvReservas.setAdapter(new ListaReservasAdaptador(getContext(), reservas));

    }
}