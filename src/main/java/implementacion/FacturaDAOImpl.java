package implementacion;

import dao.IFacturaDAO;
import dao.Conexion;
import modelo.Factura;
import modelo.Reserva;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import modelo.Cliente;
import modelo.Habitacion;

public class FacturaDAOImpl implements IFacturaDAO {

    @Override
    public boolean registrarFactura(Factura factura) {
        String sql = "INSERT INTO Facturas (id_reserva, fecha_emision, total) VALUES (?, ?, ?)";

        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, factura.getReserva().getId());
            ps.setDate(2, new java.sql.Date(factura.getFechaEmision().getTime()));
            ps.setDouble(3, factura.getTotal());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Factura> obtenerFacturas() {
        List<Factura> lista = new ArrayList<>();

        String sql = "SELECT f.id, f.fecha_emision, f.total, "
                + "r.id AS id_reserva, r.dni_cliente, r.numero_habitacion, r.fecha_entrada, r.fecha_salida, "
                + "c.nombre, c.apellido, h.precio_por_dia "
                + "FROM Facturas f "
                + "JOIN Reservas r ON f.id_reserva = r.id "
                + "JOIN Clientes c ON r.dni_cliente = c.dni "
                + "JOIN Habitaciones h ON r.numero_habitacion = h.numero_habitacion";

        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Cliente cliente = new Cliente(
                        rs.getString("dni_cliente"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        ""
                );

                Habitacion habitacion = new Habitacion(
                        rs.getString("numero_habitacion"),
                        false,
                        rs.getDouble("precio_por_dia")
                );

                Reserva reserva = new Reserva(
                        rs.getInt("id_reserva"),
                        cliente,
                        habitacion,
                        rs.getDate("fecha_entrada"),
                        rs.getDate("fecha_salida"),
                        "Finalizada"
                );

                Factura factura = new Factura(
                        rs.getInt("id"),
                        reserva,
                        rs.getDate("fecha_emision"),
                        rs.getDouble("total")
                );

                lista.add(factura);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
