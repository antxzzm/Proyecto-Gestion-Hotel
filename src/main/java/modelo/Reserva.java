    
package modelo;

import java.util.Date;

public class Reserva {
    private int id;
    private Cliente cliente;
    private Habitacion habitacion;
    private Date fechaEntrada;
    private Date fechaSalida;
    private String estado;

    
    public Reserva(int id, Cliente cliente, Habitacion habitacion, Date fechaEntrada, Date fechaSalida, String estado) {
        this.id = id;
        this.cliente = cliente;
        this.habitacion = habitacion;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.estado = estado;
    }

    public Reserva(Cliente cliente, Habitacion habitacion, Date fechaEntrada, Date fechaSalida, String estado) {
        this.cliente = cliente;
        this.habitacion = habitacion;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public Date getFechaEntrada() {
        return fechaEntrada;
    }

    public Date getFechaSalida() {
        return fechaSalida;
    }

    public String getEstado() {
        return estado;
    }
    
    
    
}
