package pt.ipleiria.estg.dei.rentallcar.vistas;

import static android.content.Context.MODE_PRIVATE;

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
import pt.ipleiria.estg.dei.rentallcar.adaptadores.ListaReservasAdaptador;
import pt.ipleiria.estg.dei.rentallcar.listeners.ReservasListener;
import pt.ipleiria.estg.dei.rentallcar.modelo.Reserva;
import pt.ipleiria.estg.dei.rentallcar.modelo.SingletonGestorVeiculos;


public class ListaReservaFragment extends Fragment implements ReservasListener {
    private ListView lvReservas;
    public static final int DETALHES = 2;



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

        lvReservas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Intent intent = new Intent(getContext(), DetalhesReservaActivity.class);
                intent.putExtra(DetalhesReservaActivity.IDRESERVA, (int) id);
                startActivityForResult(intent, DETALHES);
            }
        });
        return view;
    }

    @Override
    public void onRefreshListaReservas(ArrayList<Reserva> reservas) {
        if (reservas != null)
            lvReservas.setAdapter(new ListaReservasAdaptador(getContext(), reservas));

    }
}