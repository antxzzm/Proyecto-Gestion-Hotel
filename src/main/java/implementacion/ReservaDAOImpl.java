package implementacion;

import dao.Conexion;
import dao.IReservaDAO;
import modelo.Cliente;
import modelo.Habitacion;
import modelo.Reserva;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAOImpl implements IReservaDAO {

    @Override
    public boolean registrarReserva(Reserva r) {
        String sql = "INSERT INTO Reservas (dni_cliente, numero_habitacion, fecha_entrada, fecha_salida, estado) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, r.getCliente().getDni());
            ps.setString(2, r.getHabitacion().getNumero_habitacion());
            ps.setDate(3, new java.sql.Date(r.getFechaEntrada().getTime()));
            ps.setDate(4, new java.sql.Date(r.getFechaSalida().getTime()));
            ps.setString(5, r.getEstado());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Reserva> listarReservas() {
        List<Reserva> lista = new ArrayList<>();
        String sql = "SELECT r.id, r.dni_cliente, r.numero_habitacion, r.fecha_entrada, r.fecha_salida, r.estado, "
                + "c.nombre, c.apellido FROM Reservas r JOIN Clientes c ON r.dni_cliente = c.dni";

        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Cliente c = new Cliente(rs.getString("dni_cliente"), rs.getString("nombre"), rs.getString("apellido"), "");
                Habitacion h = new Habitacion(rs.getString("numero_habitacion"), false, 0.0);
                Reserva r = new Reserva(rs.getInt("id"), c, h, rs.getDate("fecha_entrada"), rs.getDate("fecha_salida"), rs.getString("estado"));
                lista.add(r);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    @Override
    public void actualizarReservasFinalizadas() {
        String sql1 = "UPDATE Reservas SET estado = 'Finalizada' WHERE fecha_salida < GETDATE() AND estado = 'Activa'";
        String sql2 = "UPDATE Habitaciones SET estado = 1 WHERE numero_habitacion IN (SELECT numero_habitacion FROM Reservas WHERE estado = 'Finalizada')";

        try (Connection con = Conexion.getConexion(); Statement stmt = con.createStatement()) {
            stmt.executeUpdate(sql1);
            stmt.executeUpdate(sql2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean clienteTieneReservaActiva(String dni) {
        try (Connection conn = Conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(
                "SELECT COUNT(*) FROM Reservas WHERE dni_cliente = ? AND estado = 'Activa'"
        )) {
            stmt.setString(1, dni);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean cancelarReserva(int idReserva) {
        String sql1 = "UPDATE Reservas SET estado = 'Cancelada' WHERE id = ?";
        String sql2 = "UPDATE Habitaciones SET estado = 1 WHERE numero_habitacion = "
                + "(SELECT numero_habitacion FROM Reservas WHERE id = ?)";

        try (Connection con = Conexion.getConexion(); PreparedStatement ps1 = con.prepareStatement(sql1); PreparedStatement ps2 = con.prepareStatement(sql2)) {

            ps1.setInt(1, idReserva);
            ps2.setInt(1, idReserva);

            ps1.executeUpdate();
            ps2.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Reserva> obtenerReservasFinalizadasPorDni(String dni) {
        List<Reserva> lista = new ArrayList<>();

        String sql = "SELECT r.*, c.nombre, c.apellido, h.precio_por_dia "
                + "FROM Reservas r "
                + "JOIN Clientes c ON r.dni_cliente = c.dni "
                + "JOIN Habitaciones h ON r.numero_habitacion = h.numero_habitacion "
                + "WHERE r.estado = 'Finalizada' AND r.dni_cliente = ?";

        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, dni);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String numero = rs.getString("numero_habitacion");
                Date entrada = rs.getDate("fecha_entrada");
                Date salida = rs.getDate("fecha_salida");
                double precio = rs.getDouble("precio_por_dia");
                String estado = rs.getString("estado");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");

                Cliente cliente = new Cliente(dni, nombre, apellido, "");
                Habitacion habitacion = new Habitacion(numero, true, precio);
                Reserva reserva = new Reserva(id, cliente, habitacion, entrada, salida, estado);

                lista.add(reserva);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    @Override
    public Reserva buscarPorId(int idReserva) {
        String sql = "SELECT r.id, c.dni, c.nombre, h.numero_habitacion, h.precioPorDia, r.fechaEntrada, r.fechaSalida, r.estado "
                + "FROM reservas r "
                + "JOIN clientes c ON r.dni_cliente = c.dni "
                + "JOIN habitaciones h ON r.numero_habitacion = h.numero_habitacion "
                + "WHERE r.id = ?";

        try (Connection conn = Conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idReserva);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Armar objetos Cliente, Habitacion y Reserva
                Cliente cliente = new Cliente(rs.getString("dni"), rs.getString("nombre"), "", "");
                Habitacion habitacion = new Habitacion(rs.getString("numero_habitacion"), true, rs.getDouble("precioPorDia"));
                Date entrada = rs.getDate("fechaEntrada");
                Date salida = rs.getDate("fechaSalida");
                String estado = rs.getString("estado");

                return new Reserva(idReserva, cliente, habitacion, entrada, salida, estado);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean actualizarReserva(Reserva reserva) {
        String sql = "UPDATE reservas SET estado = ? WHERE id = ?";

        try (Connection conn = Conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, reserva.getEstado());
            stmt.setInt(2, reserva.getId());

            int filasActualizadas = stmt.executeUpdate();
            return filasActualizadas > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
