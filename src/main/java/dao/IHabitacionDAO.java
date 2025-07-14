package dao;

import java.util.List;
import modelo.Habitacion;

public interface IHabitacionDAO {

    List<Habitacion> obtenerHabitaciones();
    boolean cambiarEstado(String numero_habitacion, boolean nuevoEstado);
    boolean agregarHabitacion(Habitacion h);
    Habitacion buscarPorNumero(String numero);
    boolean actualizarPrecio(String numeroHabitacion, double nuevoPrecio);

}
