
package modelo;

public class Cliente {
    private String dni;
    private String nombre;
    private String apellido;
    private String sexo;

    public Cliente() {
    }
    
    public Cliente(String dni, String nombre, String apellido, String sexo) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.sexo = sexo;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

   
    
    @Override
    public String toString() {
        return apellido + ", " + nombre + " - DNI: " + dni;
    }
    
}
