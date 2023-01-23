package pt.ipleiria.estg.dei.rentallcar.listeners;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.rentallcar.modelo.Favorito;


public interface FavoritosListener {


    void onRefreshListaFavoritos(ArrayList<Favorito> favoritos);
}
