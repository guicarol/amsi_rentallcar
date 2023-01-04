package pt.ipleiria.estg.dei.rentallcar.listeners;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.rentallcar.modelo.Veiculo;

public interface VeiculosListener {


    void onRefreshListaVeiculos(ArrayList<Veiculo> veiculos);
}
