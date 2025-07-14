package dao;

import modelo.Reserva;
import java.util.List;

public interface IReservaDAO {

    boolean registrarReserva(Reserva reserva);
    List<Reserva> listarReservas();
    void actualizarReservasFinalizadas();
    boolean clienteTieneReservaActiva(String dni);
    boolean cancelarReserva(int idReserva);
    List<Reserva> obtenerReservasFinalizadasPorDni(String dni);
    public Reserva buscarPorId(int idReserva);
    public boolean actualizarReserva(Reserva reserva);
    public List<Reserva> obtenerTodasReservasFinalizadas();

}
