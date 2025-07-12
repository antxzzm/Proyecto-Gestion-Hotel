package util;

import javax.swing.*;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class UtilTabla {
    public static void agregarFiltroTiempoReal(JTextField campo, JTable tabla, int... columnas) {
        campo.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { filtrar(); }
            @Override
            public void removeUpdate(DocumentEvent e) { filtrar(); }
            @Override
            public void changedUpdate(DocumentEvent e) { filtrar(); }

            private void filtrar() {
                DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
                TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelo);
                tabla.setRowSorter(sorter);

                String texto = campo.getText().trim();
                RowFilter<Object, Object> filtro = RowFilter.regexFilter("(?i)" + texto, columnas);
                sorter.setRowFilter(filtro);
            }
        });
    }
}
