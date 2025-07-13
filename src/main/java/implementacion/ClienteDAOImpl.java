package implementacion;

import dao.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.Cliente;
import dao.IClienteDAO;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClienteDAOImpl implements IClienteDAO {
    private static final Logger logger = LogManager.getLogger(ClienteDAOImpl.class);
    private final QueryRunner qr = new QueryRunner();
    @Override
    public boolean agregarCliente(Cliente c) {
        String sql = "INSERT INTO Clientes (dni, nombre, apellido, sexo) VALUES (?, ?, ?, ?)";
        /*try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getDni());
            ps.setString(2, c.getNombre());
            ps.setString(3, c.getApellido());
            ps.setString(4, c.getSexo());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }*/
        //LIBRERIA DButils y logger
        try (Connection con = Conexion.getConexion()) {
            int filas = qr.update(con, sql, c.getDni(), c.getNombre(), c.getApellido(), c.getSexo());
            logger.info("Cliente agregado con DNI: {}", c.getDni());
            return filas > 0;
        } catch (SQLException e) {
            logger.error("Error al agregar cliente: " + c.toString(), e);
            return false;
        }
    }

    @Override
    public boolean eliminarCliente(String dni, String nombre, String apellido) {
        String sql = "DELETE FROM Clientes WHERE dni = ? AND nombre = ? AND apellido = ?";
        /*try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, dni);
            ps.setString(2, nombre);
            ps.setString(3, apellido);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }*/
        try (Connection con = Conexion.getConexion()) {
        int filas = qr.update(con, sql, dni, nombre, apellido);
            logger.info("Cliente eliminado: DNI={}, Nombre={}, Apellido={}", dni, nombre, apellido);
        return filas > 0;
        }catch (SQLException e) {
            logger.error("Error al eliminar cliente: DNI={}, Nombre={}, Apellido={}", dni, nombre, apellido, e);
        return false;
        }
    }

    @Override
    public Cliente buscarPorDNI(String dni) {
        String sql = "SELECT * FROM Clientes WHERE dni = ?";
        /*try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, dni);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Cliente(
                        rs.getString("dni"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("sexo")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;*/
        //LIBRERÍA DButils y LOGGER
        try (Connection con = Conexion.getConexion()) {
        Cliente cliente = qr.query(con, sql, new BeanHandler<>(Cliente.class), dni);
            if (cliente != null) {
                logger.debug("Cliente encontrado: {}", cliente);
            } else {
                logger.warn("No se encontró cliente con DNI: {}", dni);
            }
        return cliente;
        } catch (SQLException e) {
            logger.error("Error al buscar cliente por DNI: {}", dni, e);
        return null;
        }
    }

    @Override
    public List<Cliente> obtenerClientes() {
        //List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM Clientes";
        /*try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Cliente cliente = new Cliente(
                        rs.getString("dni"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("sexo")
                );
                lista.add(cliente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;*/
        //LIBRERÍA DButils y LOGGER
        try (Connection con = Conexion.getConexion()) {
            List<Cliente> lista = qr.query(con, sql, new BeanListHandler<>(Cliente.class));
            logger.debug("Clientes obtenidos: {} registros", lista.size());
        return lista;
        } catch (SQLException e) {
            logger.error("Error al obtener lista de clientes", e);
            return new ArrayList<>();
        }
    }
}
