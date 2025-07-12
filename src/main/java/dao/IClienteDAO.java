package dao;

import modelo.Cliente;
import java.util.List;

public interface IClienteDAO {

    boolean agregarCliente(Cliente c);
    boolean eliminarCliente(String dni, String nombre, String apellido);
    Cliente buscarPorDNI(String dni);
    List<Cliente> obtenerClientes();

}
