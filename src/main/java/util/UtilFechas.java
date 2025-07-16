package util;

import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.util.Calendar;
import java.util.Date;
import javax.swing.*;

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

    public static void establecerFechasEntradaYSalida(JDateChooser jdcEntrada, JDateChooser jdcSalida, JFrame parent) {
        Calendar cal = Calendar.getInstance();
        Date ahora = new Date();
        cal.setTime(ahora);

        int horaActual = cal.get(Calendar.HOUR_OF_DAY);

        if (horaActual >= 20) {
            cal.add(Calendar.DATE, 1);
            JOptionPane.showMessageDialog(parent,
                    """
                La hora actual supera las 10:00 p.m.
                La reserva se registrará con fecha de entrada mañana y salida pasado mañana.
                El huésped puede ingresar hoy como cortesía del hotel.
                """,
                    "Reserva ajustada por horario nocturno",
                    JOptionPane.WARNING_MESSAGE);
        }

        Date entrada = cal.getTime();
        jdcEntrada.setDate(entrada);

        cal.add(Calendar.DATE, 1);
        Date salida = cal.getTime();
        jdcSalida.setDate(salida);

        //
        JTextField txtEntrada = (JTextField) jdcEntrada.getDateEditor().getUiComponent();
        txtEntrada.setEditable(false);
        txtEntrada.setBackground(Color.WHITE);
        txtEntrada.setForeground(Color.BLACK);
        txtEntrada.setDisabledTextColor(Color.BLACK);

        JTextField txtSalida = (JTextField) jdcSalida.getDateEditor().getUiComponent();
        txtSalida.setEditable(false);
        txtSalida.setBackground(Color.WHITE);
        txtSalida.setForeground(Color.BLACK);
        txtSalida.setDisabledTextColor(Color.BLACK);
    }
}
