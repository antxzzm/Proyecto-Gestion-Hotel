package implementacion;

import dao.Conexion;
import dao.IHabitacionDAO;
import modelo.Habitacion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HabitacionDAOImpl implements IHabitacionDAO {

    @Override
    public List<Habitacion> obtenerHabitaciones() {
        List<Habitacion> lista = new ArrayList<>();
        String sql = "SELECT numero_habitacion, estado, precio_por_dia FROM Habitaciones";

        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Habitacion(
                        rs.getString("numero_habitacion"),
                        rs.getBoolean("estado"),
                        rs.getDouble("precio_por_dia")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    @Override
    public boolean cambiarEstado(String numero_habitacion, boolean nuevoEstado) {
        String sql = "UPDATE Habitaciones SET estado = ? WHERE numero_habitacion = ?";

        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setBoolean(1, nuevoEstado);
            ps.setString(2, numero_habitacion);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean agregarHabitacion(Habitacion h) {
        String sql = "INSERT INTO Habitaciones (numero_habitacion, estado, precio_por_dia) VALUES (?, ?, ?)";

        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, h.getNumero_habitacion());
            ps.setBoolean(2, h.isEstado());
            ps.setDouble(3, h.getPrecioPorDia());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Habitacion buscarPorNumero(String numero) {
        String sql = "SELECT * FROM Habitaciones WHERE numero_habitacion = ?";

        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, numero);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Habitacion(
                        rs.getString("numero_habitacion"),
                        rs.getBoolean("estado"),
                        rs.getDouble("precio_por_dia")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean actualizarPrecio(String numeroHabitacion, double nuevoPrecio) {
        String sql = "UPDATE Habitaciones SET precio_por_dia = ? WHERE numero_habitacion = ?";

        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, nuevoPrecio);
            ps.setString(2, numeroHabitacion);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
