package servicio;

import dao.IReservaDAO;
import modelo.Reserva;

import java.util.List;

public class ReservaService {

    private final IReservaDAO reservaDAO;

    public ReservaService(IReservaDAO reservaDAO) {
        this.reservaDAO = reservaDAO;
    }

    public boolean registrar(Reserva reserva) {
        return reservaDAO.registrarReserva(reserva);
    }

    public List<Reserva> listar() {
        return reservaDAO.listarReservas();
    }

    public void actualizarFinalizadas() {
        reservaDAO.actualizarReservasFinalizadas();
    }

    public boolean tieneReservaActiva(String dni) {
        return reservaDAO.clienteTieneReservaActiva(dni);
    }

    public boolean cancelar(int idReserva) {
        return reservaDAO.cancelarReserva(idReserva);
    }

    public List<Reserva> obtenerReservasFinalizadasPorDni(String dni) {
        return reservaDAO.obtenerReservasFinalizadasPorDni(dni);
    }

    public Reserva buscarPorId(int idReserva) {
        return reservaDAO.buscarPorId(idReserva);
    }

    public boolean actualizar(Reserva reserva) {
        return reservaDAO.actualizarReserva(reserva);
    }
}
