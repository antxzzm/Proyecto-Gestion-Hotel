package dao;

import modelo.Encargado;

public interface IEncargadoDAO {

    boolean validarLogin(Encargado encargado);

    boolean registrarEncargado(Encargado encargado);

    boolean usuarioExiste(String usuario);
}
