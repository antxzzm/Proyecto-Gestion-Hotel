
package modelo;

public class Cliente {
    private String dni;
    private String nombre;
    private String apellido;
    private String sexo;

    public Cliente(String dni, String nombre, String apellido, String sexo) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.sexo = sexo;
    }

    public String getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getSexo() {
        return sexo;
    }
    
    @Override
    public String toString() {
        return apellido + ", " + nombre + " - DNI: " + dni;
    }
    
}
