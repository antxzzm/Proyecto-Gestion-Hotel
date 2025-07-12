package vista;

// LIBRERÍAS ESTÁNDAR
import java.awt.Color;
import java.awt.Component;
import java.util.Date;


// SWING
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

// MODELO
import modelo.Cliente;
import modelo.Habitacion;
import modelo.Reserva;

// DAO
import dao.IClienteDAO;
import dao.IHabitacionDAO;
import dao.IReservaDAO;

// IMPLEMENTACIONES
import implementacion.ClienteDAOImpl;
import implementacion.HabitacionDAOImpl;
import implementacion.ReservaDAOImpl;

// SERVICIOS
import servicio.ClienteService;
import servicio.HabitacionService;
import servicio.ReservaService;

// UTILS
import util.UtilFechas;
import util.UtilHabitacion;
import util.UtilTabla;

public class SistemaCliente extends javax.swing.JFrame {

    // ------------------------------------------------------------------------
    // ATRIBUTOS
    // ------------------------------------------------------------------------
    
    // Cliente
    IClienteDAO clienteDAO = new ClienteDAOImpl();                // DAO de Cliente
    ClienteService clienteService = new ClienteService(clienteDAO);  // Servicio de Cliente

    // Habitación
    IHabitacionDAO habitacionDAO = new HabitacionDAOImpl();      // DAO de Habitación
    HabitacionService habitacionService = new HabitacionService(habitacionDAO); // Servicio

    // Reserva
    IReservaDAO reservaDAO = new ReservaDAOImpl();               // DAO de Reserva
    ReservaService reservaService = new ReservaService(reservaDAO); // Servicio


    private final JFrame ventanaAnterior;
    private boolean permitirCambio = false;
    private int indiceActual = 0;
    
    // ------------------------------------------------------------------------
    // CONSTRUCTOR
    // ------------------------------------------------------------------------
    public SistemaCliente(JFrame anterior) {
        this.ventanaAnterior = anterior;
        initComponents();

        reservaService.actualizarFinalizadas(); // ✅ Actualizar reservas vencidas
        
        cargarEstadosHabitaciones();      // Carga estados de habitaciones
        configurarEventosVentana();       // Volver a login al cerrar
        configurarCambioDePestañas();     // Controlar tab activo
        cargarClientesEnTabla();          // Mostrar clientes registrados
        desactivarComponentesIniciales(); // Desactivar botones/calendarios
        UtilTabla.agregarFiltroTiempoReal(txtClientes, tblClientes, 0, 1);
        UtilFechas.setMinSelectable(jdcEntrada, jdcSalida);

        jTabbedPane1.setSelectedIndex(0); // Mostrar pestaña principal
    }

    // ------------------------------------------------------------------------
    // EVENTOS DE VENTANA
    // ------------------------------------------------------------------------
    private void formWindowOpened(java.awt.event.WindowEvent evt) {

        // Desactivar habitaciones (por seguridad adicional)
        for (Component comp : panelHabitaciones.getComponents()) {
            if (comp instanceof JToggleButton boton) {
                boton.setEnabled(false);
            }
        }

        jdcEntrada.setEnabled(false);
        jdcSalida.setEnabled(false);
        btnReservar.setEnabled(false);
    }

    private void configurarEventosVentana() {
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                if (ventanaAnterior != null) {
                    ventanaAnterior.setVisible(true);
                }
                dispose();
            }
        });
    }

    // ------------------------------------------------------------------------
    // CONFIGURACIÓN DE COMPONENTES
    // ------------------------------------------------------------------------
    private void configurarCambioDePestañas() {
        jTabbedPane1.addChangeListener(e -> {
            if (!permitirCambio) {
                jTabbedPane1.setSelectedIndex(indiceActual);
            } else {
                indiceActual = jTabbedPane1.getSelectedIndex();
            }
        });
    }

    private void desactivarComponentesIniciales() {
        for (Component comp : panelHabitaciones.getComponents()) {
            if (comp instanceof JToggleButton boton) {
                boton.setEnabled(false);
            }
        }

        jdcEntrada.setEnabled(false);
        jdcSalida.setEnabled(false);
        btnReservar.setEnabled(false);
    }

    // ------------------------------------------------------------------------
    // CLIENTES
    // ------------------------------------------------------------------------
    private void cargarClientesEnTabla() {
        DefaultTableModel modelo = (DefaultTableModel) tblClientes.getModel();
        for (Cliente c : clienteService.listarClientes()) {
            modelo.addRow(new Object[]{
                c.getDni(),
                c.getNombre(),
                c.getApellido(),
                c.getSexo()
            });
        }
    }

    public void limpiarCampos() {
        txtDni.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtSexo.setText("");
        txtDni.requestFocus();
    }

    // ------------------------------------------------------------------------
    // HABITACIONES
    // ------------------------------------------------------------------------
    private void cargarEstadosHabitaciones() {
        reservaService.actualizarFinalizadas();
        UtilHabitacion.actualizarEstadoHabitaciones(panelHabitaciones, habitacionService.obtenerHabitaciones());
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnClientes = new javax.swing.JButton();
        btnReservas = new javax.swing.JButton();
        btnHabitaciones = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        btnFacturacion = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtDni = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        txtApellido = new javax.swing.JTextField();
        txtSexo = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        jLabel16 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        btnEliminar = new javax.swing.JButton();
        btnRegistrar = new javax.swing.JButton();
        txtClientes = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        panelHabitaciones = new javax.swing.JPanel();
        btn101 = new javax.swing.JToggleButton();
        btn102 = new javax.swing.JToggleButton();
        btn103 = new javax.swing.JToggleButton();
        btn104 = new javax.swing.JToggleButton();
        btn105 = new javax.swing.JToggleButton();
        btn106 = new javax.swing.JToggleButton();
        btn107 = new javax.swing.JToggleButton();
        btn108 = new javax.swing.JToggleButton();
        btn109 = new javax.swing.JToggleButton();
        btn110 = new javax.swing.JToggleButton();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        btnReservar = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        txtBuscarDNI = new javax.swing.JTextField();
        jdcEntrada = new com.toedter.calendar.JDateChooser();
        jLabel12 = new javax.swing.JLabel();
        txtBuscarNombre = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jdcSalida = new com.toedter.calendar.JDateChooser();
        jLabel15 = new javax.swing.JLabel();
        btnVerificarCliente = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Gallery Hoteles");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 0, 255));

        btnClientes.setText("CLIENTES");
        btnClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClientesActionPerformed(evt);
            }
        });

        btnReservas.setText("RESERVAS");
        btnReservas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReservasActionPerformed(evt);
            }
        });

        btnHabitaciones.setText("HABITACIONES");
        btnHabitaciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHabitacionesActionPerformed(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/login3.png"))); // NOI18N

        btnFacturacion.setText("FACTURACION");
        btnFacturacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFacturacionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnClientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnHabitaciones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnReservas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 8, Short.MAX_VALUE))
                    .addComponent(btnFacturacion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 108, Short.MAX_VALUE)
                .addComponent(btnClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnHabitaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnReservas, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnFacturacion, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(128, 128, 128))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 170, 610));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/sistema.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 0, 760, 170));

        jLabel3.setForeground(new java.awt.Color(255, 0, 0));
        jLabel3.setText("DNI:");

        jLabel4.setForeground(new java.awt.Color(255, 0, 0));
        jLabel4.setText("APELLIDO");

        jLabel6.setForeground(new java.awt.Color(255, 0, 0));
        jLabel6.setText("NOMBRE:");

        jLabel7.setForeground(new java.awt.Color(255, 0, 0));
        jLabel7.setText("SEXO");

        txtApellido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtApellidoActionPerformed(evt);
            }
        });

        txtSexo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSexoActionPerformed(evt);
            }
        });

        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "DNI", "NOMBRE", "APELLIDO", "SEXO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblClientes);

        jLabel16.setForeground(new java.awt.Color(255, 0, 0));
        jLabel16.setText("CLIENTE:");

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        btnEliminar.setBackground(new java.awt.Color(255, 255, 255));
        btnEliminar.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnEliminar.setForeground(new java.awt.Color(0, 0, 0));
        btnEliminar.setText("ELIMINAR");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnRegistrar.setBackground(new java.awt.Color(255, 255, 255));
        btnRegistrar.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnRegistrar.setForeground(new java.awt.Color(0, 0, 0));
        btnRegistrar.setText("REGISTRAR");
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66)
                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnRegistrar, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txtClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtClientesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtDni, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtApellido, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
                            .addComponent(txtSexo))
                        .addGap(125, 125, 125))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(131, 131, 131))
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDni, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 28, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        jTabbedPane1.addTab("Clientes", jPanel2);

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel8.setText("ESTADO DE HABITACIONES");

        panelHabitaciones.setBackground(new java.awt.Color(255, 255, 255));
        panelHabitaciones.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btn101.setBackground(new java.awt.Color(0, 204, 0));
        btn101.setText("101");
        btn101.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn101ActionPerformed(evt);
            }
        });

        btn102.setBackground(new java.awt.Color(0, 204, 0));
        btn102.setText("102");

        btn103.setBackground(new java.awt.Color(0, 204, 0));
        btn103.setText("103");

        btn104.setBackground(new java.awt.Color(0, 204, 0));
        btn104.setText("104");

        btn105.setBackground(new java.awt.Color(0, 204, 0));
        btn105.setText("105");

        btn106.setBackground(new java.awt.Color(0, 204, 0));
        btn106.setText("106");

        btn107.setBackground(new java.awt.Color(0, 204, 0));
        btn107.setText("107");

        btn108.setBackground(new java.awt.Color(0, 204, 0));
        btn108.setText("108");

        btn109.setBackground(new java.awt.Color(0, 204, 0));
        btn109.setText("109");

        btn110.setBackground(new java.awt.Color(0, 204, 0));
        btn110.setText("110");

        javax.swing.GroupLayout panelHabitacionesLayout = new javax.swing.GroupLayout(panelHabitaciones);
        panelHabitaciones.setLayout(panelHabitacionesLayout);
        panelHabitacionesLayout.setHorizontalGroup(
            panelHabitacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHabitacionesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelHabitacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelHabitacionesLayout.createSequentialGroup()
                        .addComponent(btn101, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn102, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn103, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn104, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn105, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelHabitacionesLayout.createSequentialGroup()
                        .addComponent(btn106, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn107, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn108, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn109, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn110, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelHabitacionesLayout.setVerticalGroup(
            panelHabitacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHabitacionesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelHabitacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn101, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn102, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn103, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn104, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn105, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelHabitacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn106, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn107, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn108, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn109, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn110, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(163, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 45, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 45, Short.MAX_VALUE)
        );

        jPanel7.setBackground(new java.awt.Color(0, 204, 0));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 48, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 45, Short.MAX_VALUE)
        );

        jLabel10.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel10.setText("Habitacion Disponible");

        jLabel11.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel11.setText("Habitacion Reservada");

        btnReservar.setBackground(new java.awt.Color(255, 255, 255));
        btnReservar.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnReservar.setForeground(new java.awt.Color(0, 0, 0));
        btnReservar.setText("RESERVAR");
        btnReservar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReservarActionPerformed(evt);
            }
        });

        jLabel9.setForeground(new java.awt.Color(255, 0, 0));
        jLabel9.setText("NOMBRE:");

        jLabel12.setForeground(new java.awt.Color(255, 0, 0));
        jLabel12.setText("DNI:");

        jLabel13.setForeground(new java.awt.Color(255, 0, 0));
        jLabel13.setText("FECHA DE ENTRADA");

        jLabel14.setForeground(new java.awt.Color(255, 0, 0));
        jLabel14.setText("FECHA DE SALIDA");

        jLabel15.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel15.setText("RESERVAR");

        btnVerificarCliente.setBackground(new java.awt.Color(255, 255, 255));
        btnVerificarCliente.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnVerificarCliente.setForeground(new java.awt.Color(0, 0, 0));
        btnVerificarCliente.setText("VERIFICAR");
        btnVerificarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerificarClienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(113, 113, 113)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(96, 96, 96))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel11))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(panelHabitaciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtBuscarDNI, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtBuscarNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(jdcEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jdcSalida, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                        .addComponent(btnVerificarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(50, 50, 50))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                        .addComponent(btnReservar)
                                        .addGap(62, 62, 62)))))))
                .addContainerGap(39, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(panelHabitaciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10)
                            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addContainerGap(20, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtBuscarDNI, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtBuscarNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(btnVerificarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jdcEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jdcSalida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnReservar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        jTabbedPane1.addTab("Habitaciones", jPanel3);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 170, 760, 440));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //BOTON PRINCIPAL PARA IR A PESTAÑA HABITACIONES
    private void btnHabitacionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHabitacionesActionPerformed
        permitirCambio = true;
        jTabbedPane1.setSelectedIndex(1);
        permitirCambio = false;
    }//GEN-LAST:event_btnHabitacionesActionPerformed

    //BOTON PRINCIPAL PARA IR A PESTAÑA CLIENTES
    private void btnClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClientesActionPerformed
        permitirCambio = true;
        jTabbedPane1.setSelectedIndex(0);
        permitirCambio = false;
    }//GEN-LAST:event_btnClientesActionPerformed

    //BOTON PRINCIPAL PARA IR A PESTAÑA RESERVA
    private void btnReservasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReservasActionPerformed

    }//GEN-LAST:event_btnReservasActionPerformed

    //BOTON PRINCIPAL PARA IR A PESTAÑA FACTURACION
    private void btnFacturacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFacturacionActionPerformed

    }//GEN-LAST:event_btnFacturacionActionPerformed

    //PESTAÑA HABITACIONES - BOTON VERIFICAR
    private void btnVerificarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerificarClienteActionPerformed
        String dni = txtBuscarDNI.getText().trim();
        String nombre = txtBuscarNombre.getText().trim();

        if (dni.isEmpty() || nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el DNI y el nombre del cliente.");
            return;
        }

        // Verificar si existe el cliente en la base de datos
        Cliente cliente = clienteService.buscarPorDNI(dni);

        String nombreCompletoIngresado = nombre.toLowerCase().trim();
        String nombreCompletoBD = (cliente.getNombre() + " " + cliente.getApellido()).toLowerCase().trim();

        if (cliente != null && nombreCompletoBD.equals(nombreCompletoIngresado)) {

            if (reservaService.tieneReservaActiva(dni)) {
                JOptionPane.showMessageDialog(this, "⚠️ Este cliente ya tiene una reserva activa.");
                return;
            }

            JOptionPane.showMessageDialog(this, "✅ Cliente verificado correctamente.");

            // Habilitar calendarios y botón de reservar
            jdcEntrada.setEnabled(true);
            jdcSalida.setEnabled(true);
            btnReservar.setEnabled(true);

            // Evitar seleccionar fechas pasadas
            Date hoy = new Date();
            jdcEntrada.setMinSelectableDate(hoy);
            jdcSalida.setMinSelectableDate(hoy);

            // Opcional: bloquear los campos para que no se cambien
            txtBuscarDNI.setEnabled(false);
            txtBuscarNombre.setEnabled(false);

            // Habilitar solo las habitaciones disponibles (verdes)
            UtilHabitacion.configurarBotonesDisponibles(panelHabitaciones);

        } else {
            JOptionPane.showMessageDialog(this, "⚠️ Cliente no encontrado. Verifique el DNI y nombre.");
        }
    }//GEN-LAST:event_btnVerificarClienteActionPerformed

    //PESTAÑA HABITACIONES - BOTON RESERVAR
    private void btnReservarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReservarActionPerformed
        String dni = txtBuscarDNI.getText().trim();
        String nombre = txtBuscarNombre.getText().trim();
        Date entrada = jdcEntrada.getDate();
        Date salida = jdcSalida.getDate();
        Date hoy = new Date();

        // Buscar cliente
        Cliente cliente = clienteService.buscarPorDNI(dni);

        if (cliente == null) {
            JOptionPane.showMessageDialog(this, "Cliente no encontrado.");
            return;
        }

        // Validar fechas
        if (entrada == null || salida == null) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar fechas válidas.");
            return;
        }

        if (entrada.before(hoy)) {
            JOptionPane.showMessageDialog(this, "La fecha de entrada no puede ser en el pasado.");
            return;
        }

        if (!salida.after(entrada)) {
            JOptionPane.showMessageDialog(this, "La fecha de salida debe ser posterior a la de entrada.");
            return;
        }

        // Buscar habitación seleccionada
        String numeroSeleccionado = null;
        for (Component comp : panelHabitaciones.getComponents()) {
            if (comp instanceof JToggleButton boton && boton.isSelected()) {
                numeroSeleccionado = boton.getText();
                break;
            }
        }

        // Validar selección de habitación
        if (numeroSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una habitación.");
            return;
        }

        // Buscar habitación
        Habitacion habitacion = habitacionService.buscarPorNumero(numeroSeleccionado);
        if (habitacion == null) {
            JOptionPane.showMessageDialog(this, "❌ Habitación no encontrada: " + numeroSeleccionado);
            return;
        }

        if (!habitacion.isEstado()) {
            JOptionPane.showMessageDialog(this, "La habitación ya está ocupada.");
            return;
        }

        // Crear reserva
        Reserva reserva = new Reserva(cliente, habitacion, entrada, salida, "Activa");

        if (reservaService.registrar(reserva)) {
            // Marcar habitación como ocupada en BD
            habitacionService.cambiarEstado(numeroSeleccionado, false);

            // Actualizar botón de habitación
            for (Component comp : panelHabitaciones.getComponents()) {
                if (comp instanceof JToggleButton boton && boton.getText().equals(numeroSeleccionado)) {
                    boton.setBackground(Color.RED);
                    boton.setEnabled(false);
                    boton.setSelected(false);
                    break;
                }
            }

            JOptionPane.showMessageDialog(this, "✅ Reserva registrada con éxito.");

            // Restaurar formulario tras reserva
            txtBuscarDNI.setText("");
            txtBuscarNombre.setText("");
            txtBuscarDNI.setEnabled(true);
            txtBuscarNombre.setEnabled(true);
            jdcEntrada.setDate(null);
            jdcSalida.setDate(null);
            jdcEntrada.setEnabled(false);
            jdcSalida.setEnabled(false);
            btnReservar.setEnabled(false);

            // Restaurar botones de habitación (los ocupados ya fueron deshabilitados)
            for (Component comp : panelHabitaciones.getComponents()) {
                if (comp instanceof JToggleButton boton) {
                    boton.setSelected(false); // Deseleccionar todos
                    if (boton.getBackground().equals(Color.GREEN)) {
                        boton.setEnabled(false); // Solo se habilitan tras nueva verificación
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "❌ Error al registrar reserva.");
        }
    }//GEN-LAST:event_btnReservarActionPerformed

    private void btn101ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn101ActionPerformed

    }//GEN-LAST:event_btn101ActionPerformed

    //PESTAÑA CLIENTES - BOTON REGISTRAR
    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        String dni = txtDni.getText().trim();
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String sexo = txtSexo.getText().trim();

        if (dni.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || sexo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor complete todos los campos.");
            return;
        }

        //️ Validar si el cliente ya existe
        if (clienteService.buscarPorDNI(dni) != null) {
            JOptionPane.showMessageDialog(this, "⚠️ El cliente con ese DNI ya está registrado.");
            return;
        }

        Cliente nuevo = new Cliente(dni, nombre, apellido, sexo);

        // Registrar en la base de datos
        if (clienteService.agregarCliente(nuevo)) {
            JOptionPane.showMessageDialog(this, "✅ Cliente registrado correctamente.");

            // Agregar a la tabla
            DefaultTableModel modelo = (DefaultTableModel) tblClientes.getModel();
            modelo.addRow(new Object[]{
                nuevo.getDni(),
                nuevo.getNombre(),
                nuevo.getApellido(),
                nuevo.getSexo()
            });

            // Limpiar campos
            limpiarCampos();

        } else {
            JOptionPane.showMessageDialog(this, "❌ El cliente ya existe o hubo un error.");
        }
    }//GEN-LAST:event_btnRegistrarActionPerformed

    //PESTAÑA CLIENTES - BOTON ELIMINAR
    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed

        String dni = txtDni.getText().trim();
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();

        if (dni.isEmpty() || nombre.isEmpty() || apellido.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Debes completar DNI, nombre y apellido para eliminar.");
            return;
        }

        // Verificar si el cliente tiene reservas activas
        if (reservaService.tieneReservaActiva(dni)) {
            JOptionPane.showMessageDialog(this,
                    "❌ No se puede eliminar. El cliente tiene una reserva activa.");
            return;
        }

        // Verificar si existe el cliente con esos tres datos
        DefaultTableModel modelo = (DefaultTableModel) tblClientes.getModel();
        int filaEncontrada = -1;

        for (int i = 0; i < modelo.getRowCount(); i++) {
            String dniTbl = modelo.getValueAt(i, 0).toString();
            String nombreTbl = modelo.getValueAt(i, 1).toString();
            String apellidoTbl = modelo.getValueAt(i, 2).toString();

            if (dniTbl.equals(dni)
                    && nombreTbl.equalsIgnoreCase(nombre)
                    && apellidoTbl.equalsIgnoreCase(apellido)) {
                filaEncontrada = i;
                break;
            }
        }

        if (filaEncontrada == -1) {
            JOptionPane.showMessageDialog(this,
                    "No se encontró un cliente que coincida con esos datos.");
            return;
        }

        int resp = JOptionPane.showConfirmDialog(this,
                "¿Seguro que deseas eliminar al cliente con DNI: " + dni + "?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

        if (resp != JOptionPane.YES_OPTION) {
            return;
        }

        if (clienteService.eliminarCliente(dni, nombre, apellido)) {
            modelo.removeRow(filaEncontrada);
            JOptionPane.showMessageDialog(this, "Cliente eliminado correctamente.");
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this,
                    "No se pudo eliminar en la base de datos.");
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void txtSexoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSexoActionPerformed

    }//GEN-LAST:event_txtSexoActionPerformed

    private void txtApellidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtApellidoActionPerformed

    }//GEN-LAST:event_txtApellidoActionPerformed

    private void txtClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtClientesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtClientesActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton btn101;
    private javax.swing.JToggleButton btn102;
    private javax.swing.JToggleButton btn103;
    private javax.swing.JToggleButton btn104;
    private javax.swing.JToggleButton btn105;
    private javax.swing.JToggleButton btn106;
    private javax.swing.JToggleButton btn107;
    private javax.swing.JToggleButton btn108;
    private javax.swing.JToggleButton btn109;
    private javax.swing.JToggleButton btn110;
    private javax.swing.JButton btnClientes;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnFacturacion;
    private javax.swing.JButton btnHabitaciones;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JButton btnReservar;
    private javax.swing.JButton btnReservas;
    private javax.swing.JButton btnVerificarCliente;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private com.toedter.calendar.JDateChooser jdcEntrada;
    private com.toedter.calendar.JDateChooser jdcSalida;
    private javax.swing.JPanel panelHabitaciones;
    private javax.swing.JTable tblClientes;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtBuscarDNI;
    private javax.swing.JTextField txtBuscarNombre;
    private javax.swing.JTextField txtClientes;
    private javax.swing.JTextField txtDni;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtSexo;
    // End of variables declaration//GEN-END:variables
}
