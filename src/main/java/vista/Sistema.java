package vista;

// LIBRERÍAS ESTÁNDAR
import java.awt.Color;
import java.awt.Component;
import java.util.Date;
import java.util.List;

// SWING
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

// MODELO
import modelo.Cliente;
import modelo.Factura;
import modelo.Habitacion;
import modelo.Reserva;

// DAO
import dao.IClienteDAO;
import dao.IFacturaDAO;
import dao.IHabitacionDAO;
import dao.IReservaDAO;

// IMPLEMENTACIONES
import implementacion.ClienteDAOImpl;
import implementacion.FacturaDAOImpl;
import implementacion.HabitacionDAOImpl;
import implementacion.ReservaDAOImpl;

// SERVICIOS
import servicio.ClienteService;
import servicio.FacturaService;
import servicio.HabitacionService;
import servicio.ReservaService;

// UTILS
import util.UtilFechas;
import util.UtilHabitacion;
import util.UtilTabla;

public class Sistema extends javax.swing.JFrame {

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

    // Factura
    IFacturaDAO facturaDAO = new FacturaDAOImpl();               // DAO de Factura
    FacturaService facturaService = new FacturaService(facturaDAO); // Servicio

    private final JFrame ventanaAnterior;
    private boolean permitirCambio = false;
    private int indiceActual = 0;
    private int idReservaFactura = -1;

    // ------------------------------------------------------------------------
    // CONSTRUCTOR
    // ------------------------------------------------------------------------
    public Sistema(JFrame anterior) {
        this.ventanaAnterior = anterior;
        initComponents();

        reservaService.actualizarFinalizadas(); // ✅ Actualizar reservas vencidas

        cargarFacturasEnTabla();
        desactivarCamposFactura();
        cargarEstadosHabitaciones();      // Carga estados de habitaciones
        configurarEventosVentana();       // Volver a login al cerrar
        configurarCambioDePestañas();     // Controlar tab activo
        cargarClientesEnTabla();          // Mostrar clientes registrados
        desactivarComponentesIniciales(); // Desactivar botones/calendarios
        cargarReservasEnTabla();          // Mostrar reservas en tabla
        UtilTabla.agregarFiltroTiempoReal(txtClientes, tblClientes, 0, 1);
        UtilTabla.agregarFiltroTiempoReal(txtReservaCliente, tblReservas, 0, 1);
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

    // ------------------------------------------------------------------------
    // RESERVAS
    // ------------------------------------------------------------------------
    private void cargarReservasEnTabla() {
        String[] titulos = {"ID", "DNI", "Nombre", "Apellido", "Habitación", "Entrada", "Salida", "Estado"};
        DefaultTableModel modelo = new DefaultTableModel(null, titulos);

        for (Reserva r : reservaService.listar()) {
            modelo.addRow(new Object[]{
                r.getId(),
                r.getCliente().getDni(),
                r.getCliente().getNombre(),
                r.getCliente().getApellido(),
                r.getHabitacion().getNumero_habitacion(),
                r.getFechaEntrada(),
                r.getFechaSalida(),
                r.getEstado()
            });
        }

        tblReservas.setModel(modelo);
    }


    // ------------------------------------------------------------------------
    // FATURACION
    // ------------------------------------------------------------------------
    private void desactivarCamposFactura() {
        txtDNIFactura.setEnabled(false);
        txtNombreFactura.setEnabled(false);
        txtHabitacionFactura.setEnabled(false);
        txtDiasFactura.setEnabled(false);
        jdcEntradaFactura.setEnabled(false);
        jdcSalidaFactura.setEnabled(false);
        txtPrecioDiaFactura.setEnabled(false);
        txtTotalFactura.setEnabled(false);
    }

    private void cargarFacturasEnTabla() {
        List<Factura> lista = facturaService.obtenerFacturas();
        DefaultTableModel modelo = (DefaultTableModel) tblFacturas.getModel();
        modelo.setRowCount(0);

        for (Factura f : lista) {
            modelo.addRow(new Object[]{
                f.getReserva().getId(),
                f.getReserva().getCliente().getDni(),
                f.getReserva().getHabitacion().getNumero_habitacion(),
                f.getReserva().getFechaEntrada(),
                f.getReserva().getFechaSalida(),
                f.getTotal(),
                f.getFechaEmision()
            });
        }
    }

    private void desbloquearCamposFactura() {
        txtDNIFactura.setEnabled(true);
        txtNombreFactura.setEnabled(true);
        txtHabitacionFactura.setEnabled(true);
        txtDiasFactura.setEnabled(true);
        jdcEntradaFactura.setEnabled(true);
        jdcSalidaFactura.setEnabled(true);
        txtPrecioDiaFactura.setEnabled(true);
        txtTotalFactura.setEnabled(true);
    }

    private void limpiarCamposFactura() {
        txtFacturaCliente.setText("");
        txtDNIFactura.setText("");
        txtNombreFactura.setText("");
        txtHabitacionFactura.setText("");
        txtDiasFactura.setText("");
        jdcEntradaFactura.setDate(null);
        jdcSalidaFactura.setDate(null);
        txtPrecioDiaFactura.setText("");
        txtTotalFactura.setText("");
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
        jPanel4 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblReservas = new javax.swing.JTable();
        jLabel18 = new javax.swing.JLabel();
        txtReservaCliente = new javax.swing.JTextField();
        btnCancelarReserva = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txtFacturaCliente = new javax.swing.JTextField();
        btnBuscarFactura = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblFacturas = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        txtDNIFactura = new javax.swing.JTextField();
        txtHabitacionFactura = new javax.swing.JTextField();
        txtPrecioDiaFactura = new javax.swing.JTextField();
        txtTotalFactura = new javax.swing.JTextField();
        txtDiasFactura = new javax.swing.JTextField();
        txtNombreFactura = new javax.swing.JTextField();
        jdcEntradaFactura = new com.toedter.calendar.JDateChooser();
        jdcSalidaFactura = new com.toedter.calendar.JDateChooser();
        jLabel21 = new javax.swing.JLabel();
        btnGenerarFactura = new javax.swing.JButton();

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

        jPanel4.setToolTipText("");

        jLabel17.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel17.setText("RESERVAS");

        tblReservas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "DNI", "Nombre", "Apellido", "Habitacion", "Entrada", "Salida", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblReservas);

        jLabel18.setForeground(new java.awt.Color(255, 0, 0));
        jLabel18.setText("CLIENTE:");

        btnCancelarReserva.setText("CANCELAR RESERVA");
        btnCancelarReserva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarReservaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtReservaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCancelarReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 688, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(33, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel17)
                .addGap(327, 327, 327))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtReservaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelarReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Reservas", jPanel4);

        jLabel19.setForeground(new java.awt.Color(255, 0, 0));
        jLabel19.setText("DNI:");

        jLabel20.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel20.setText("METODO DE FACTURACION");

        btnBuscarFactura.setText("BUSCAR");
        btnBuscarFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarFacturaActionPerformed(evt);
            }
        });

        tblFacturas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "DNI", "Habitacion", "Entrada", "Salida", "Total", "Fecha Emision"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tblFacturas);

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel22.setForeground(new java.awt.Color(255, 0, 0));
        jLabel22.setText("Nombre:");

        jLabel23.setForeground(new java.awt.Color(255, 0, 0));
        jLabel23.setText("Habitacion:");

        jLabel24.setForeground(new java.awt.Color(255, 0, 0));
        jLabel24.setText("Dias:");

        jLabel25.setForeground(new java.awt.Color(255, 0, 0));
        jLabel25.setText("Salida:");

        jLabel26.setForeground(new java.awt.Color(255, 0, 0));
        jLabel26.setText("Precio/Dia:");

        jLabel27.setForeground(new java.awt.Color(255, 0, 0));
        jLabel27.setText("Entrada:");

        jLabel28.setForeground(new java.awt.Color(255, 0, 0));
        jLabel28.setText("DNI:");

        jLabel29.setForeground(new java.awt.Color(255, 0, 0));
        jLabel29.setText("Total:");

        txtDNIFactura.setEditable(false);

        txtHabitacionFactura.setEditable(false);

        txtPrecioDiaFactura.setEditable(false);

        txtTotalFactura.setEditable(false);
        txtTotalFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalFacturaActionPerformed(evt);
            }
        });

        txtDiasFactura.setEditable(false);

        txtNombreFactura.setEditable(false);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPrecioDiaFactura)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDNIFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtHabitacionFactura)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jdcEntradaFactura, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTotalFactura)
                    .addComponent(txtDiasFactura)
                    .addComponent(txtNombreFactura)
                    .addComponent(jdcSalidaFactura, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDNIFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombreFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtHabitacionFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDiasFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jdcEntradaFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jdcSalidaFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPrecioDiaFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTotalFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jLabel21.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel21.setText("TABLA DE FACTURAS");

        btnGenerarFactura.setText("GENERAR FACTURA");
        btnGenerarFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarFacturaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 721, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel8Layout.createSequentialGroup()
                                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtFacturaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel8Layout.createSequentialGroup()
                                        .addGap(129, 129, 129)
                                        .addComponent(jLabel20)))
                                .addGap(23, 23, 23)
                                .addComponent(btnBuscarFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel21)
                                .addGap(28, 28, 28)))
                        .addGap(51, 51, 51)
                        .addComponent(btnGenerarFactura)))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20)
                .addGap(5, 5, 5)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFacturaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscarFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(btnGenerarFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Facturacion", jPanel8);

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

    //BOTON PRINCIPAL PARA IR A PESTAÑA CONSUMO
    private void btnReservasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReservasActionPerformed
        permitirCambio = true;
        jTabbedPane1.setSelectedIndex(2);
        permitirCambio = false;
    }//GEN-LAST:event_btnReservasActionPerformed

    //BOTON PRINCIPAL PARA IR A PESTAÑA FACTURACION
    private void btnFacturacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFacturacionActionPerformed
        permitirCambio = true;
        jTabbedPane1.setSelectedIndex(3);
        permitirCambio = false;
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
        cargarReservasEnTabla();
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

    // PESTAÑA RESERVAS - BOTON CANCELAR
    private void btnCancelarReservaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarReservaActionPerformed
        int fila = tblReservas.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una reserva para cancelar.");
            return;
        }

        DefaultTableModel modelo = (DefaultTableModel) tblReservas.getModel();
        int idReserva = (int) modelo.getValueAt(fila, 0);
        String estado = modelo.getValueAt(fila, 7).toString();

        if (!estado.equalsIgnoreCase("Activa")) {
            JOptionPane.showMessageDialog(this, "⚠️ Solo se pueden cancelar reservas activas.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Estás seguro de cancelar la reserva seleccionada?",
                "Confirmar cancelación", JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        if (reservaService.cancelar(idReserva)) {
            JOptionPane.showMessageDialog(this, "✅ Reserva cancelada exitosamente.");
            cargarReservasEnTabla();
            cargarEstadosHabitaciones();
        } else {
            JOptionPane.showMessageDialog(this, "❌ Error al cancelar la reserva.");
        }
    }//GEN-LAST:event_btnCancelarReservaActionPerformed

    private void txtTotalFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalFacturaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalFacturaActionPerformed

    private void btnBuscarFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarFacturaActionPerformed
        String dni = txtFacturaCliente.getText().trim();

        if (dni.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠️ Ingresa un DNI para buscar.");
            return;
        }

        // Buscar reservas finalizadas por DNI
        List<Reserva> reservas = reservaService.obtenerReservasFinalizadasPorDni(dni);

        if (reservas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "❌ No hay reservas finalizadas para este DNI.");
            return;
        }

        // Suponemos que solo hay una reserva finalizada activa para facturar
        Reserva reserva = reservas.get(0);
        Habitacion hab = reserva.getHabitacion();
        Cliente cli = reserva.getCliente();

        // Guardar ID para la factura
        idReservaFactura = reserva.getId();

        // Llenar campos (y desbloquear)
        txtDNIFactura.setText(cli.getDni());
        txtNombreFactura.setText(cli.getNombre());
        txtHabitacionFactura.setText(hab.getNumero_habitacion());

        jdcEntradaFactura.setDate(reserva.getFechaEntrada());
        jdcSalidaFactura.setDate(reserva.getFechaSalida());
        jdcEntradaFactura.setEnabled(false);
        jdcSalidaFactura.setEnabled(false);

        // Calcular días
        long dias = UtilFechas.calcularDias(reserva.getFechaEntrada(), reserva.getFechaSalida());
        txtDiasFactura.setText(String.valueOf(dias));

        txtPrecioDiaFactura.setText(String.valueOf(hab.getPrecioPorDia()));
        double total = dias * hab.getPrecioPorDia();
        txtTotalFactura.setText(String.format("%.2f", total));

        // Habilitar campos visuales si estaban bloqueados
        desbloquearCamposFactura();
    }//GEN-LAST:event_btnBuscarFacturaActionPerformed

    private void btnGenerarFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarFacturaActionPerformed
        if (idReservaFactura == -1) {
            JOptionPane.showMessageDialog(this, "⚠️ Primero debes buscar una reserva finalizada.");
            return;
        }

        try {
            // Verificar si la reserva ya fue facturada
            Reserva reservaExistente = reservaService.buscarPorId(idReservaFactura);
            if (reservaExistente != null && "Facturada".equalsIgnoreCase(reservaExistente.getEstado())) {
                JOptionPane.showMessageDialog(this, "⚠️ Esta reserva ya ha sido facturada.");
                return;
            }

            // Construir reserva y factura
            Cliente cliente = new Cliente(txtDNIFactura.getText(), txtNombreFactura.getText(), "", "");
            Habitacion habitacion = new Habitacion(txtHabitacionFactura.getText(), true, Double.parseDouble(txtPrecioDiaFactura.getText()));

            Date entrada = jdcEntradaFactura.getDate();
            Date salida = jdcSalidaFactura.getDate();
            int dias = Integer.parseInt(txtDiasFactura.getText());
            double total = Double.parseDouble(txtTotalFactura.getText());

            // Cambiar estado a Facturada
            Reserva reserva = new Reserva(idReservaFactura, cliente, habitacion, entrada, salida, "Facturada");
            Factura factura = new Factura(0, reserva, new Date(), total);

            boolean exito = facturaService.registrarFactura(factura);

            if (exito) {
                // Actualizar estado de reserva en la base de datos
                reservaService.actualizar(reserva);

                JOptionPane.showMessageDialog(this, "✅ Factura generada y guardada correctamente.");
                cargarFacturasEnTabla();
                cargarReservasEnTabla(); // << Asegúrate de que esta recargue la lista desde reservaService.listar()
                limpiarCamposFactura();
                desactivarCamposFactura();
                idReservaFactura = -1;
            } else {
                JOptionPane.showMessageDialog(this, "❌ Error al guardar la factura.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "❌ Ocurrió un error al generar la factura.");
        }
    }//GEN-LAST:event_btnGenerarFacturaActionPerformed

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
    private javax.swing.JButton btnBuscarFactura;
    private javax.swing.JButton btnCancelarReserva;
    private javax.swing.JButton btnClientes;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnFacturacion;
    private javax.swing.JButton btnGenerarFactura;
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
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private com.toedter.calendar.JDateChooser jdcEntrada;
    private com.toedter.calendar.JDateChooser jdcEntradaFactura;
    private com.toedter.calendar.JDateChooser jdcSalida;
    private com.toedter.calendar.JDateChooser jdcSalidaFactura;
    private javax.swing.JPanel panelHabitaciones;
    private javax.swing.JTable tblClientes;
    private javax.swing.JTable tblFacturas;
    private javax.swing.JTable tblReservas;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtBuscarDNI;
    private javax.swing.JTextField txtBuscarNombre;
    private javax.swing.JTextField txtClientes;
    private javax.swing.JTextField txtDNIFactura;
    private javax.swing.JTextField txtDiasFactura;
    private javax.swing.JTextField txtDni;
    private javax.swing.JTextField txtFacturaCliente;
    private javax.swing.JTextField txtHabitacionFactura;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNombreFactura;
    private javax.swing.JTextField txtPrecioDiaFactura;
    private javax.swing.JTextField txtReservaCliente;
    private javax.swing.JTextField txtSexo;
    private javax.swing.JTextField txtTotalFactura;
    // End of variables declaration//GEN-END:variables
}
