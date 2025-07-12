package util;

import com.toedter.calendar.JDateChooser;
import java.util.Date;

public class UtilFechas {
    public static void setMinSelectable(JDateChooser entrada, JDateChooser salida) {
        entrada.addPropertyChangeListener("date", evt -> {
            Date fechaEntrada = entrada.getDate();
            if (fechaEntrada != null) {
                salida.setMinSelectableDate(fechaEntrada);
            }
        });
    }

    public static long calcularDias(Date entrada, Date salida) {
        long diff = salida.getTime() - entrada.getTime();
        return diff / (1000 * 60 * 60 * 24);
    }
}
