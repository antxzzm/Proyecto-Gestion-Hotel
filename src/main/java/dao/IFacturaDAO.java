package dao;

import java.util.List;
import modelo.Factura;

public interface IFacturaDAO {

    boolean registrarFactura(Factura factura);
    List<Factura> obtenerFacturas();
}
