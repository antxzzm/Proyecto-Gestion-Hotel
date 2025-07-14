package servicio;

import dao.IHabitacionDAO;
import modelo.Habitacion;
import java.util.List;

public class HabitacionService {
    
    private IHabitacionDAO habitacionDAO;

    public HabitacionService(IHabitacionDAO habitacionDAO) {
        this.habitacionDAO = habitacionDAO;
    }

    public List<Habitacion> obtenerHabitaciones() {
        return habitacionDAO.obtenerHabitaciones();
    }

    public boolean cambiarEstado(String numero, boolean estado) {
        return habitacionDAO.cambiarEstado(numero, estado);
    }

    public boolean agregarHabitacion(Habitacion h) {
        return habitacionDAO.agregarHabitacion(h);
    }

    public Habitacion buscarPorNumero(String numero) {
        return habitacionDAO.buscarPorNumero(numero);
    }
    
    public boolean actualizarPrecio(String numeroHabitacion, double nuevoPrecio){
        return habitacionDAO.actualizarPrecio(numeroHabitacion, nuevoPrecio);
    }
}
