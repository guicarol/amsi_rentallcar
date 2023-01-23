package pt.ipleiria.estg.dei.rentallcar.vistas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import pt.ipleiria.estg.dei.rentallcar.R;


public class Camara_GestorFragment extends Fragment {


    public Camara_GestorFragment() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_camara__gestor, container, false);


    }
}