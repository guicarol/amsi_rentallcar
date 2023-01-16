package pt.ipleiria.estg.dei.rentallcar.vistas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import pt.ipleiria.estg.dei.rentallcar.R;
import pt.ipleiria.estg.dei.rentallcar.listeners.PerfilListener;
import pt.ipleiria.estg.dei.rentallcar.modelo.Perfil;
import pt.ipleiria.estg.dei.rentallcar.modelo.SingletonGestorVeiculos;

public class Utilizador extends Fragment implements PerfilListener {

    private String email;
    private String username;
    private int id;

    private TextView logout, etNome, etApelido, etTelefone, etNif, etEmail, etUsername;

    private Perfil perfil;

    public Utilizador() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_utilizador, container, false);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username", "");
        email = sharedPreferences.getString("email", "");
        id=sharedPreferences.getInt("id",-1);

        SingletonGestorVeiculos.getInstance(getContext()).setDadosPessoaisListener(this);
        SingletonGestorVeiculos.getInstance(getContext()).getDadosPessoaisAPI(getContext(), id);

        logout = (TextView) view.findViewById(R.id.LogOut);
        etNome = view.findViewById(R.id.etNome);
        etApelido = view.findViewById(R.id.etApelido);
        etTelefone = view.findViewById(R.id.etTelefone);
        etNif = view.findViewById(R.id.etNif);
        etEmail = view.findViewById(R.id.etEmail);
        etUsername = view.findViewById(R.id.etUsername);


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
        if (perfil != null) {
            etNome.setText(perfil.getNome());
            etApelido.setText(perfil.getApelido());
            etTelefone.setText(perfil.getTelemovel());
            etNif.setText(perfil.getNif());
            etEmail.setText(email);
            etUsername.setText(username);
            //etNome.setText(" " + perfil.getNome() + " " + perfil.getApelido());


        }
    }
}