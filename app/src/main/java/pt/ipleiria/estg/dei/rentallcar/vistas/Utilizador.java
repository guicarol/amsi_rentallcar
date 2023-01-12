package pt.ipleiria.estg.dei.rentallcar.vistas;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import pt.ipleiria.estg.dei.rentallcar.R;
import pt.ipleiria.estg.dei.rentallcar.listeners.PerfilListener;
import pt.ipleiria.estg.dei.rentallcar.modelo.Perfil;
import pt.ipleiria.estg.dei.rentallcar.modelo.SingletonGestorVeiculos;

public class Utilizador extends Fragment implements PerfilListener {

    private Button usado;

    private TextView logout, etNome, etCodPostal, etTelemovel;

    private Perfil perfil;

    public Utilizador() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_utilizador, container, false);

        SingletonGestorVeiculos.getInstance(getContext()).setDadosPessoaisListener(this);
        SingletonGestorVeiculos.getInstance(getContext()).getDadosPessoaisAPI(getContext(), 2);

        logout = (TextView) view.findViewById(R.id.LogOut);

        etNome = view.findViewById(R.id.etNome);
        etCodPostal = view.findViewById(R.id.etApelido);
        etTelemovel = view.findViewById(R.id.etTelefone);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }

        });

        perfil = SingletonGestorVeiculos.getInstance(getContext()).getDadosPessoaisAPI(getContext(), 12);

        return view;
    }


    @Override
    public void onRefreshPerfil(Perfil perfil) {
        if(perfil != null) {
            etNome.setText(" " + perfil.getNome() + " " + perfil.getApelido());
            etCodPostal.setText(perfil.getCodPostal());
            etTelemovel.setText(perfil.getTelemovel());
        }
    }
}