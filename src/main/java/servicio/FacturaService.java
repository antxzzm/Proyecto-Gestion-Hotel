package servicio;

import dao.IFacturaDAO;
import modelo.Factura;

import java.util.List;

public class FacturaService {

    private final IFacturaDAO facturaDAO;

    public FacturaService(IFacturaDAO facturaDAO) {
        this.facturaDAO = facturaDAO;
    }

    public boolean registrarFactura(Factura factura) {
        return facturaDAO.registrarFactura(factura);
    }

    public List<Factura> obtenerFacturas() {
        return facturaDAO.obtenerFacturas();
    }
}
