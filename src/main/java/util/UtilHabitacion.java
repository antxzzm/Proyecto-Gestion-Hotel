package util;

import modelo.Habitacion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class UtilHabitacion {
    public static void actualizarEstadoHabitaciones(JPanel panel, List<Habitacion> habitaciones) {
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JToggleButton boton) {
                String numero = boton.getText();
                for (Habitacion h : habitaciones) {
                    if (h.getNumero_habitacion().equals(numero)) {
                        if (h.isEstado()) {
                            boton.setBackground(Color.GREEN);
                            boton.setEnabled(true);
                        } else {
                            boton.setBackground(Color.RED);
                            boton.setEnabled(false);
                        }
                        break;
                    }
                }
            }
        }
    }

    public static void configurarBotonesDisponibles(JPanel panel) {
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JToggleButton boton) {
                if (boton.getBackground().equals(Color.GREEN)) {
                    boton.setEnabled(true);
                    // Evitar duplicar listeners
                    for (ActionListener al : boton.getActionListeners()) {
                        boton.removeActionListener(al);
                    }

                    boton.addActionListener(e -> {
                        for (Component c : panel.getComponents()) {
                            if (c instanceof JToggleButton otro && otro != boton) {
                                otro.setSelected(false);
                            }
                        }
                    });
                } else {
                    boton.setEnabled(false);
                }
            }
        }
    }
}
