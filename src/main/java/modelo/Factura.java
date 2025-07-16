package modelo;

import java.util.Date;

public class Factura {
    private int id;
    private Reserva reserva;
    private Date fechaEmision;
    private double total;

    public Factura() {
    }
    
    public Factura(int id, Reserva reserva, Date fechaEmision, double total) {
        this.id = id;
        this.reserva = reserva;
        this.fechaEmision = fechaEmision;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
    @Override
    public String toString() {
        return "Factura #" + id + " - Total: S/." + total;
    }

}
