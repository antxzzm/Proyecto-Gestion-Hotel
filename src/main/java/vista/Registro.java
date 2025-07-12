package vista;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import modelo.Encargado;
import implementacion.EncargadoDAOImpl;
import servicio.EncargadoService;

public class Registro extends javax.swing.JFrame {

    private JFrame ventanaAnterior;
    private final EncargadoService encargadoService = new EncargadoService(new EncargadoDAOImpl());

    private boolean validarUsuario(String usuario) {
        if (usuario.length() < 8) {
            return false;
        }

        int numeros = 0;
        for (char c : usuario.toCharArray()) {
            if (Character.isDigit(c)) {
                numeros++;
            }
        }

        return numeros >= 2;
    }

    private boolean validarPassword(String password) {
        return password.length() >= 8;
    }

    public Registro(JFrame anterior) {
        this.ventanaAnterior = anterior;
        initComponents();

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                if (ventanaAnterior != null) {
                    ventanaAnterior.setVisible(true); // volvemos a Login
                }
                dispose();
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        txtPassword = new javax.swing.JPasswordField();
        btnRegistrarse = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtValiPassword = new javax.swing.JPasswordField();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Registro");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/login2.png"))); // NOI18N

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 255));
        jLabel3.setText("Usuario");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 255));
        jLabel4.setText("Contraseña");

        txtUsuario.setBackground(new java.awt.Color(255, 255, 255));

        txtPassword.setBackground(new java.awt.Color(255, 255, 255));

        btnRegistrarse.setBackground(new java.awt.Color(0, 0, 204));
        btnRegistrarse.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnRegistrarse.setForeground(new java.awt.Color(255, 255, 255));
        btnRegistrarse.setText("Registrarse");
        btnRegistrarse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarseActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 255));
        jLabel6.setText("Validar Contraseña");

        txtValiPassword.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(135, 135, 135))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(btnRegistrarse, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(95, 95, 95))))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtValiPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(txtUsuario)
                    .addComponent(txtPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(txtValiPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnRegistrarse, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(279, 30, 310, 370));

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/login3.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addComponent(jLabel5)
                .addContainerGap(183, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(129, 129, 129)
                .addComponent(jLabel5)
                .addContainerGap(151, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 400, 430));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/login.jpg"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 0, 240, 430));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegistrarseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarseActionPerformed
        String usuario = txtUsuario.getText();
        String contrasena = new String(txtPassword.getPassword());
        String confirmar = new String(txtValiPassword.getPassword());

        // Validar que los campos no estén vacíos
        if (usuario.isEmpty() || contrasena.isEmpty() || confirmar.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.");
            return;
        }

        // Validar usuario
        if (!validarUsuario(usuario)) {
            JOptionPane.showMessageDialog(this,
                    "El usuario debe tener al menos 8 caracteres y contener mínimo 2 números.",
                    "Usuario inválido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validar contraseña
        if (!validarPassword(contrasena)) {
            JOptionPane.showMessageDialog(this,
                    "La contraseña debe tener al menos 8 caracteres (letras o números).",
                    "Contraseña inválida", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validar confirmación
        if (!contrasena.equals(confirmar)) {
            JOptionPane.showMessageDialog(this,
                    "Las contraseñas no coinciden.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Intentar registrar
        Encargado nuevo = new Encargado(usuario, contrasena);
        boolean registrado = encargadoService.registrarNuevoEncargado(nuevo);

        if (registrado) {
            JOptionPane.showMessageDialog(this, "✅ Usuario registrado con éxito.");
            Login login = new Login();
            login.setVisible(true);
            login.setLocationRelativeTo(null);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "⚠️ El usuario ya existe o ocurrió un error.");
        }
    }//GEN-LAST:event_btnRegistrarseActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRegistrarse;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsuario;
    private javax.swing.JPasswordField txtValiPassword;
    // End of variables declaration//GEN-END:variables

}
