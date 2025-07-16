package vista;

import dao.IHabitacionDAO;
import implementacion.HabitacionDAOImpl;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Habitacion;
import servicio.HabitacionService;

public class PrecioHabitaciones extends javax.swing.JFrame {

    IHabitacionDAO habitacionDAO = new HabitacionDAOImpl();
    HabitacionService habitacionService = new HabitacionService(habitacionDAO);

    public PrecioHabitaciones() {
        initComponents();
        cargarTablaHabitaciones();
    }

    private void cargarTablaHabitaciones() {
        DefaultTableModel modelo = (DefaultTableModel) tblHabitaciones.getModel();
        modelo.setRowCount(0);

        for (Habitacion h : habitacionService.obtenerHabitaciones()) {
            modelo.addRow(new Object[]{
                h.getNumero_habitacion(),
                h.getPrecioPorDia(),
            });
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHabitaciones = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtPrecioActual = new javax.swing.JTextField();
        txtPrecioNuevo = new javax.swing.JTextField();
        btnGuardarPrecio = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtNumeroH = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("PrecioHabitaciones");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 153, 255));

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("PRECIO DE HABITACIONES");

        tblHabitaciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Habitacion", "Precio"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHabitaciones.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHabitacionesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblHabitaciones);
        if (tblHabitaciones.getColumnModel().getColumnCount() > 0) {
            tblHabitaciones.getColumnModel().getColumn(0).setResizable(false);
            tblHabitaciones.getColumnModel().getColumn(1).setResizable(false);
        }

        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Precio Actual");

        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Precio Nuevo:");

        txtPrecioActual.setEditable(false);
        txtPrecioActual.setForeground(new java.awt.Color(0, 0, 0));

        txtPrecioNuevo.setBackground(new java.awt.Color(255, 255, 255));
        txtPrecioNuevo.setForeground(new java.awt.Color(0, 0, 0));

        btnGuardarPrecio.setBackground(new java.awt.Color(255, 255, 255));
        btnGuardarPrecio.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnGuardarPrecio.setText("MODIFICAR");
        btnGuardarPrecio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarPrecioActionPerformed(evt);
            }
        });

        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Número H.");

        txtNumeroH.setEditable(false);
        txtNumeroH.setForeground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addGap(112, 112, 112))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 458, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtPrecioActual, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtPrecioNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnGuardarPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(56, 56, 56))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtNumeroH, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNumeroH, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPrecioActual, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPrecioNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, 23))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnGuardarPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44))))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 470, 380));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarPrecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarPrecioActionPerformed
        String numero = txtNumeroH.getText().trim();
        String textoPrecio = txtPrecioNuevo.getText().trim();

        if (numero.isEmpty() || textoPrecio.isEmpty()) {
            JOptionPane.showMessageDialog(this, " Ingresa el número de habitación y el nuevo precio.");
            return;
        }

        try {
            double nuevoPrecio = Double.parseDouble(textoPrecio);

            if (nuevoPrecio <= 0) {
                JOptionPane.showMessageDialog(this, " El precio debe ser mayor que 0.");
                return;
            }

            Habitacion habitacion = habitacionService.buscarPorNumero(numero);
            if (habitacion == null) {
                JOptionPane.showMessageDialog(this, " La habitación no existe.");
                return;
            }

            boolean exito = habitacionService.actualizarPrecio(numero, nuevoPrecio);
            if (exito) {
                JOptionPane.showMessageDialog(this, " Precio actualizado.");
                cargarTablaHabitaciones();
                txtNumeroH.setText("");
                txtPrecioActual.setText("");
                txtPrecioNuevo.setText("");
                txtPrecioNuevo.requestFocus();
            } else {
                JOptionPane.showMessageDialog(this, " No se pudo actualizar el precio.");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, " Ingresa un precio válido.");
        }
    }//GEN-LAST:event_btnGuardarPrecioActionPerformed

    private void tblHabitacionesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHabitacionesMouseClicked
        int fila = tblHabitaciones.getSelectedRow();
        if (fila != -1) {
            String numero = tblHabitaciones.getValueAt(fila, 0).toString();
            String precio = tblHabitaciones.getValueAt(fila, 1).toString();

            txtNumeroH.setText(numero);
            txtPrecioActual.setText(precio);
        }
    }//GEN-LAST:event_tblHabitacionesMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuardarPrecio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblHabitaciones;
    private javax.swing.JTextField txtNumeroH;
    private javax.swing.JTextField txtPrecioActual;
    private javax.swing.JTextField txtPrecioNuevo;
    // End of variables declaration//GEN-END:variables
}
