package implementacion;

import dao.Conexion;
import java.sql.*;
import modelo.Encargado;
import dao.IEncargadoDAO;

public class EncargadoDAOImpl implements IEncargadoDAO {

    @Override
    public boolean validarLogin(Encargado encargado) {
        String sql = "SELECT * FROM usuarios WHERE usuario = ? AND contrasena = ?";

        try (Connection conn = Conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, encargado.getUsuario());
            stmt.setString(2, encargado.getContrasena());

            ResultSet rs = stmt.executeQuery();

            return rs.next(); // true si hay un resultado (usuario válido)

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean registrarEncargado(Encargado encargado) {
        if (usuarioExiste(encargado.getUsuario())) {
            return false;
        }

        String sql = "INSERT INTO usuarios (usuario, contrasena) VALUES (?, ?)";

        try (Connection conn = Conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, encargado.getUsuario());
            stmt.setString(2, encargado.getContrasena());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean usuarioExiste(String usuario) {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE usuario = ?";

        try (Connection conn = Conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
