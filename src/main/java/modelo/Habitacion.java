package modelo;

public class Habitacion {
    private String numero_habitacion;
    private boolean estado;
    private double precioPorDia;

    public Habitacion() {
    }
    
    public Habitacion(String numero_habitacion, boolean estado, double precioPorDia) {
        this.numero_habitacion = numero_habitacion;
        this.estado = estado;
        this.precioPorDia = precioPorDia;
    }

    public String getNumero_habitacion() {
        return numero_habitacion;
    }

    public void setNumero_habitacion(String numero_habitacion) {
        this.numero_habitacion = numero_habitacion;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public double getPrecioPorDia() {
        return precioPorDia;
    }

    public void setPrecioPorDia(double precioPorDia) {
        this.precioPorDia = precioPorDia;
    }
    
    public String getEstadoTexto() {
        return estado ? "Disponible" : "Ocupada";
    }

    @Override
    public String toString() {
        return "Habitación " + numero_habitacion + " - " + getEstadoTexto();
    }
    
}
