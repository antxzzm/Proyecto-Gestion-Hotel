package servicio;

import dao.IClienteDAO;
import modelo.Cliente;
import java.util.List;

public class ClienteService {

    private final IClienteDAO clienteDAO;

    public ClienteService(IClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    public List<Cliente> listarClientes() {
        return clienteDAO.obtenerClientes();
    }

    public Cliente buscarPorDNI(String dni) {
        return clienteDAO.buscarPorDNI(dni);
    }
    
    public boolean agregarCliente(Cliente cliente) {
        return clienteDAO.agregarCliente(cliente);
    }

    public boolean eliminarCliente(String dni, String nombre, String apellido) {
        return clienteDAO.eliminarCliente(dni, nombre, apellido);
    }
}
