package servicio;

import dao.IEncargadoDAO;
import modelo.Encargado;

public class EncargadoService {

    private final IEncargadoDAO encargadoDAO;

    public EncargadoService(IEncargadoDAO encargadoDAO) {
        this.encargadoDAO = encargadoDAO;
    }

    public boolean login(Encargado encargado) {
        return encargadoDAO.validarLogin(encargado);
    }

    public boolean registrarNuevoEncargado(Encargado encargado) {
        return encargadoDAO.registrarEncargado(encargado);
    }

    public boolean usuarioYaExiste(String usuario) {
        return encargadoDAO.usuarioExiste(usuario);
    }

}
