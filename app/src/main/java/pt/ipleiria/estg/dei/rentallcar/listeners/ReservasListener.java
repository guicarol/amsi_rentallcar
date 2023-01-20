package pt.ipleiria.estg.dei.rentallcar.listeners;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.rentallcar.modelo.Reserva;


public interface ReservasListener {


    void onRefreshListaReservas(ArrayList<Reserva> reservas);
}
