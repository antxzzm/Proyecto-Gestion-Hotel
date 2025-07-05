package modelo;

import java.util.Date;

public class Factura {
    private int id;
    private Reserva reserva;
    private Date fechaEmision;
    private double total;

    public Factura(int id, Reserva reserva, Date fechaEmision, double total) {
        this.id = id;
        this.reserva = reserva;
        this.fechaEmision = fechaEmision;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public double getTotal() {
        return total;
    }
    
    @Override
    public String toString() {
        return "Factura #" + id + " - Total: S/." + total;
    }

}
