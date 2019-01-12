/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package globalfoot.Vista;

import aplicacionglobalfoodtrading.Controlador.ControladorCiudades;
import aplicacionglobalfoodtrading.Controlador.ControladorPais;
import aplicacionglobalfoodtrading.Controlador.Controlador_Empleado_Directo;
import aplicacionglobalfoodtrading.Modelo.Ciudad;
import aplicacionglobalfoodtrading.Modelo.Empleado_Directo;
import aplicacionglobalfoodtrading.Modelo.Pais;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import static java.sql.JDBCType.BLOB;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static java.sql.Types.BLOB;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 *
 * @author Ricardo Andres
 */
public class Registro_Empleados_Directos extends javax.swing.JFrame {

    private JPanel contentPane;
    File fichero = null;
    DecimalFormat df = new DecimalFormat("#.00");
    ControladorCiudades cc = new ControladorCiudades();
    ControladorPais cp = new ControladorPais();
    Controlador_Empleado_Directo ced = new Controlador_Empleado_Directo();
    Pais pac;
    private static Connection con;
    private static ResultSet rs;
    private static Statement st;

    public Registro_Empleados_Directos() {
        initComponents();
        CargarComboPaises();
        pac = cp.CodPaisXNombre(cboPais.getSelectedItem().toString());
        CargarComboCiudadesXPais(pac.getCodigo());
    }

    public Registro_Empleados_Directos(Empleado_Directo ed) {
        initComponents();
        CargarComboPaises();
        pac = cp.CodPaisXNombre(cboPais.getSelectedItem().toString());
        CargarComboCiudadesXPais(pac.getCodigo());
        CargarDatosForm(ed);
    }

    public void Conectar() {

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bd_gft", "root", "");
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al conectar BD");
        }
    }

    public static String encodeToString(BufferedImage image) {
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            ImageIO.write(image, "jpg", bos);
            byte[] imageBytes = bos.toByteArray();

            BASE64Encoder encoder = new BASE64Encoder();
            imageString = encoder.encode(imageBytes);

            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageString;
    }

    public static BufferedImage decodeToImage(String imageString) {

        BufferedImage image = null;
        byte[] imageByte;
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            imageByte = decoder.decodeBuffer(imageString);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

    private String ObtenerExtension(File file) {
        String name = file.getName();
        try {
            return name.substring(name.lastIndexOf(".") + 1);
        } catch (Exception e) {
            return "";
        }
    }

    void cargar(String iden) {
        BufferedImage img = null;
        String sql = "SELECT imagen FROM emp_direc where identificacion = '" + iden + "'";
        String imagen_string = null;

        try {
            Conectar();
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                imagen_string = rs.getString("imagen");
                //lb_nombre.setText(rs.getString("nombre"));
            }
            if (imagen_string == null) {
                //cont = cont - 1;
                //contar();
                JOptionPane.showMessageDialog(null, "Entro al imagen = null");
            } else {

                try {
                    img = decodeToImage(imagen_string);
                    ImageIcon icon = new ImageIcon(img);
                    Icon icono = new ImageIcon(icon.getImage().getScaledInstance(lbfoto.getWidth(), lbfoto.getHeight(), Image.SCALE_DEFAULT));
                    lbfoto.setText(null);
                    lbfoto.setIcon(icono);
                } catch (java.lang.NullPointerException ex) {
                    System.err.println("Imagen nula");
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en el cargar imagen : " + ex.getMessage());
        }
    }

    public ImageIcon getImagen(Blob b) {
        ImageIcon imageIcon = null;
        // String sql = "SELECT imagen FROM tabla WHERE id=" + id;
        // ResultSet resultSet = SQLQuery(sql);
        try {

            Blob bytesImagen = b;
            byte[] bytesLeidos = bytesImagen.getBytes(1, ALLBITS);
            imageIcon = new ImageIcon(bytesLeidos);
            //this.lbfoto.setIcon(imageIcon);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return imageIcon;
    }

    public void CargarComboPaises() {
        ArrayList<Pais> lp = new ArrayList();

        try {
            lp = cp.ListaPaises();
            this.cboPais.removeAllItems();
            for (Pais p : lp) {
                this.cboPais.addItem(p.getPais());
            }

        } catch (SQLException ex) {
            Logger.getLogger(Registro_Empleados_Directos.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void CargarComboCiudadesXPais(String cod) {

        ArrayList<Ciudad> lc = new ArrayList();

        try {
            lc = cc.ListaCiudadxPais(cod);
            this.cboCiudad.removeAllItems();
            for (Ciudad c : lc) {
                this.cboCiudad.addItem(c.getCiudad());
            }

        } catch (SQLException ex) {
            Logger.getLogger(Registro_Empleados_Directos.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void CargarDatosForm(Empleado_Directo e) {
        try {
            cboTipoId.setSelectedItem(e.getTipoid());
            txtidentificacion.setText(e.getIdentificacion());
            txtNombre.setText(e.getNombre());
            txtApellido.setText(e.getApellido());
            cboGenero.setSelectedItem(e.getGenero());
            cboPais.setSelectedItem(e.getPais());
            cboCiudad.setSelectedItem(e.getCiudad());
            txtProvincia.setText(e.getProvincia());
            txtDireccion.setText(e.getDireccion());
            txtMovil.setText(e.getTel_movil());
            txtTelFijo.setText(e.getTel_fijo());
            txtEmail.setText(e.getEmail());
            cboEstadoCivil.setSelectedItem(e.getEst_civil());
            txtCargo.setText(e.getCargo());
            txtBanco.setText(e.getNum_bancario());
            txtSalario.setText(String.valueOf(e.getSalario()));
            txtTotalHorasMensuales.setText(String.valueOf(e.getThm()));
            txtValorHora.setText(String.valueOf(e.getValorhora()));
            txtFechaNac.setText(e.getFecha_nac());
            txtFechaIngreso.setText(e.getFecha_contrat());
            cboAuxTransporte1.setSelectedItem(e.getAux_transport());
            cboC_Compensacion.setSelectedItem(e.getCaja_compens());
            cboPrimas.setSelectedItem(e.getPrima());
            cboPension.setSelectedItem(e.getPension());
            cboCesantias.setSelectedItem(e.getCesantias());
            txtCodEPS.setText(e.getCod_Eps());
            txtNombreEPS.setText(e.getNombre_eps());
            txtCodPension.setText(e.getCod_pension());
            txtNombrePension.setText(e.getNombre_pension());
            txtRef1Nombre.setText(e.getNomb_refe1());
            txtRef1Email.setText(e.getEmail_refe1());
            txtRef1Telefono.setText(e.getDireccion_refe2());
            txtRef1Cargo.setText(e.getCargo_refe1());
            txtRef2Nombres.setText(e.getNomb_refe2());
            txtRef2Email.setText(e.getEmail_refe2());
            txtRef2Telefono.setText(e.getDireccion_refe2());
            txtRef2Cargo.setText(e.getCargo_refe2());
            // BufferedImage img = null;
            // img = decodeToImage(e.getFoto_perfil());
            // ImageIcon icon = new ImageIcon(img);
            // Icon icono = new ImageIcon(icon.getImage().getScaledInstance(lbfoto.getWidth(), lbfoto.getHeight(), Image.SCALE_DEFAULT));
            //lbfoto.setText(null);
            //lbfoto.setIcon(getImagen(ced.retornarBlob(e.getIdentificacion())));
            cargar(e.getIdentificacion());
        } catch (java.lang.NullPointerException ex) {
            System.out.println("Error : " + ex.getMessage());
        }
    }

    public void LimpiarCampos() {
        Calendar c1 = Calendar.getInstance();
        String dia, mes, annio;
        dia = Integer.toString(c1.get(Calendar.DATE));
        mes = Integer.toString(c1.get(Calendar.MONTH));
        annio = Integer.toString(c1.get(Calendar.YEAR));
        cboTipoId.setSelectedIndex(0);
        txtidentificacion.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        cboGenero.setSelectedIndex(0);
        cboPais.setSelectedIndex(0);
        cboCiudad.setSelectedIndex(0);
        txtProvincia.setText("");
        txtDireccion.setText("");
        txtMovil.setText("");
        txtTelFijo.setText("");
        txtEmail.setText("");
        cboEstadoCivil.setSelectedIndex(0);
        txtCargo.setText("");
        txtBanco.setText("");
        txtSalario.setText("0");
        txtTotalHorasMensuales.setText("0");
        txtValorHora.setText("0");
        txtFechaNac.setText("");
        txtFechaIngreso.setText("");
        cboAuxTransporte1.setSelectedIndex(0);
        cboC_Compensacion.setSelectedIndex(0);
        cboPrimas.setSelectedIndex(0);
        cboPension.setSelectedIndex(0);
        cboCesantias.setSelectedIndex(0);
        txtCodEPS.setText("");
        txtNombreEPS.setText("");
        txtCodPension.setText("");
        txtNombrePension.setText("");
        txtRef1Nombre.setText("");
        txtRef1Email.setText("");
        txtRef1Telefono.setText("");
        txtRef1Cargo.setText("");
        txtRef2Nombres.setText("");
        txtRef2Email.setText("");
        txtRef2Telefono.setText("");
        txtRef2Cargo.setText("");
        lbfoto.setText(null);
        lbfoto.setIcon(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cboTipoId = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        txtidentificacion = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtApellido = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        cboGenero = new javax.swing.JComboBox<>();
        cboPais = new javax.swing.JComboBox<>();
        cboCiudad = new javax.swing.JComboBox<>();
        txtProvincia = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtEdad = new javax.swing.JTextField();
        txtMovil = new javax.swing.JTextField();
        txtTelFijo = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        cboEstadoCivil = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtCargo = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtSalario = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtTotalHorasMensuales = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtValorHora = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        txtCodEPS = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        txtNombreEPS = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        txtCodPension = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        txtNombrePension = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        txtBanco = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        cboCesantias = new javax.swing.JComboBox<>();
        jLabel36 = new javax.swing.JLabel();
        cboPrimas = new javax.swing.JComboBox<>();
        jLabel37 = new javax.swing.JLabel();
        cboPension = new javax.swing.JComboBox<>();
        jLabel38 = new javax.swing.JLabel();
        cboC_Compensacion = new javax.swing.JComboBox<>();
        jLabel39 = new javax.swing.JLabel();
        cboAuxTransporte1 = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        lbfoto = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        txtRef1Nombre = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        txtRef1Email = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        txtRef1Telefono = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        txtRef1Cargo = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        txtRef2Cargo = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        txtRef2Nombres = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        txtRef2Email = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        txtRef2Telefono = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        btnAtras = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        btnLista = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Gestion De Empleados Directos");
        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Datos Personales", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel1.setText("Tipo Identificacion");

        cboTipoId.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cboTipoId.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "T.I", "CEDULA", "PASAPORTE", "OTRA" }));

        jLabel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel2.setText("Identificacion");

        txtidentificacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtidentificacionActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel3.setText("Nombres");

        jLabel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel4.setText("Apellidos");

        jLabel5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel5.setText("Genero");

        jLabel6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel6.setText("F.Nacimiento");

        jLabel7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel7.setText("Pais");

        jLabel8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel8.setText("Ciudad");

        jLabel9.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel9.setText("Provincia");

        cboGenero.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cboGenero.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "MASCULINO", "FEMENINO" }));

        cboPais.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboPaisItemStateChanged(evt);
            }
        });

        cboCiudad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboCiudadActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel11.setText("E-mail");

        jLabel12.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel12.setText("Tel. Fijo");

        jLabel13.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel13.setText("Tel. Movil");

        jLabel14.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel14.setText("Direccion");

        txtDireccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDireccionActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel15.setText("Edad");

        jLabel16.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel16.setText("Est. Civil");

        cboEstadoCivil.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        cboEstadoCivil.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SOLTERO(A)", "CASADO(A)", "UNION LIBRE", "VIUDO(A)" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboGenero, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboTipoId, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtidentificacion, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                    .addComponent(cboPais, 0, 100, Short.MAX_VALUE)
                    .addComponent(cboCiudad, 0, 100, Short.MAX_VALUE)
                    .addComponent(txtProvincia, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                .addGap(13, 13, 13)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13)
                    .addComponent(jLabel16)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtMovil, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTelFijo, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboEstadoCivil, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEdad, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel1)
                    .addComponent(jLabel15)
                    .addComponent(cboTipoId, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEdad, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel7)
                    .addComponent(cboPais, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel2)
                    .addComponent(txtMovil, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtidentificacion, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel8)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboCiudad, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(txtTelFijo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel9)
                    .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtProvincia, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(cboEstadoCivil, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboGenero, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(jLabel5))
                .addContainerGap(47, Short.MAX_VALUE))
        );

        txtProvincia.getAccessibleContext().setAccessibleName("");

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, 680, 250));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Datos Laborales", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N

        jLabel10.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel10.setText("Cargo");

        jLabel18.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel18.setText("Salario");

        txtSalario.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtSalario.setText("0");
        txtSalario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSalarioKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSalarioKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSalarioKeyTyped(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel19.setText("H.T.M");

        txtTotalHorasMensuales.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtTotalHorasMensuales.setText("0");
        txtTotalHorasMensuales.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTotalHorasMensualesKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTotalHorasMensualesKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTotalHorasMensualesKeyTyped(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel20.setText("V.HoraDiaria");

        txtValorHora.setEditable(false);
        txtValorHora.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtValorHora.setText("0");

        jLabel21.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel21.setText("Cod. E.P.S");

        jLabel22.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel22.setText("E.P.S");

        jLabel23.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel23.setText("Cod.Pension");

        jLabel24.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel24.setText("Pension");

        jLabel25.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel25.setText("Banco");

        jLabel35.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel35.setText("Cesantias");

        cboCesantias.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cboCesantias.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SI", "NO" }));

        jLabel36.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel36.setText("Primas");

        cboPrimas.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cboPrimas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SI", "NO" }));

        jLabel37.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel37.setText("Pension");

        cboPension.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cboPension.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SI", "NO" }));

        jLabel38.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel38.setText("C.Compens");

        cboC_Compensacion.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cboC_Compensacion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SI", "NO" }));

        jLabel39.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel39.setText("Aux.Transp");

        cboAuxTransporte1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cboAuxTransporte1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SI", "NO" }));

        jLabel17.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel17.setText("F. Ingreso");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(jLabel10)
                        .addGap(6, 6, 6)
                        .addComponent(txtCargo, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16)
                        .addComponent(jLabel25)
                        .addGap(10, 10, 10)
                        .addComponent(txtBanco, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel21)
                        .addGap(13, 13, 13)
                        .addComponent(txtCodEPS, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(jLabel22)
                        .addGap(15, 15, 15)
                        .addComponent(txtNombreEPS, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel23)
                        .addGap(10, 10, 10)
                        .addComponent(txtCodPension, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel24)
                        .addGap(10, 10, 10)
                        .addComponent(txtNombrePension, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel39)
                                .addGap(10, 10, 10)
                                .addComponent(cboAuxTransporte1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(13, 13, 13)
                                .addComponent(jLabel35)
                                .addGap(7, 7, 7)
                                .addComponent(cboCesantias, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(jLabel36)
                                .addGap(7, 7, 7)
                                .addComponent(cboPrimas, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(jLabel37)
                                .addGap(9, 9, 9)
                                .addComponent(cboPension, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(jLabel38)
                                .addGap(6, 6, 6)
                                .addComponent(cboC_Compensacion, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addGap(10, 10, 10)
                                .addComponent(txtSalario, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(jLabel19)
                                .addGap(10, 10, 10)
                                .addComponent(txtTotalHorasMensuales, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(jLabel20)
                                .addGap(10, 10, 10)
                                .addComponent(txtValorHora, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(jLabel17)
                                .addGap(42, 42, 42)))))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel25)
                    .addComponent(txtBanco, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(txtCargo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel18)
                    .addComponent(txtSalario, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19)
                    .addComponent(txtTotalHorasMensuales, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20)
                    .addComponent(jLabel17)
                    .addComponent(txtValorHora, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel35)
                    .addComponent(cboCesantias, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36)
                    .addComponent(cboPrimas, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel37)
                    .addComponent(cboPension, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel38)
                    .addComponent(cboC_Compensacion, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel39)
                    .addComponent(cboAuxTransporte1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtCodEPS, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22)
                    .addComponent(txtNombreEPS, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23)
                    .addComponent(txtCodPension, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24)
                    .addComponent(txtNombrePension, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21)))
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 275, 680, 230));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Fotografia de Perfil\n", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aplicacionglobalfoodtrading/Iconos/Camara_16_x_16.png"))); // NOI18N
        jButton2.setText("Subir Fotografia");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 210, 180, -1));

        lbfoto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel4.add(lbfoto, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 30, 160, 170));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 10, 458, 250));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Referencias Personales", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))))); // NOI18N
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Referencia 1", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N

        jLabel26.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel26.setText("Nombres");

        jLabel27.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel27.setText("E-mail");

        jLabel28.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel28.setText("Telefono");

        jLabel29.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel29.setText("Cargo");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel29, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel26, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel27, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel28, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtRef1Cargo, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                    .addComponent(txtRef1Nombre)
                    .addComponent(txtRef1Email)
                    .addComponent(txtRef1Telefono))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(txtRef1Nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(txtRef1Email, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(txtRef1Telefono, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(txtRef1Cargo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 19, -1, 210));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Referencia 2", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N

        jLabel30.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel30.setText("Cargo");

        jLabel31.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel31.setText("Nombres");

        jLabel32.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel32.setText("E-mail");

        jLabel33.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel33.setText("Telefono");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel30, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel31, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel32, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel33, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtRef2Cargo, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
                    .addComponent(txtRef2Nombres)
                    .addComponent(txtRef2Email)
                    .addComponent(txtRef2Telefono))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(txtRef2Nombres, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(txtRef2Email, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(txtRef2Telefono, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(txtRef2Cargo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(243, 19, 210, 210));

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 270, 460, 235));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnAtras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aplicacionglobalfoodtrading/Iconos/Previous32x32.png"))); // NOI18N
        btnAtras.setToolTipText("Volver a la pagina anterior");
        btnAtras.setBorderPainted(false);
        btnAtras.setContentAreaFilled(false);
        btnAtras.setFocusPainted(false);
        btnAtras.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/aplicacionglobalfoodtrading/Iconos/Previous48x48.png"))); // NOI18N
        btnAtras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtrasActionPerformed(evt);
            }
        });

        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aplicacionglobalfoodtrading/Iconos/1479450025_edit-file32x32.png"))); // NOI18N
        btnEditar.setToolTipText("Reescribir informacion del empleado");
        btnEditar.setBorderPainted(false);
        btnEditar.setContentAreaFilled(false);
        btnEditar.setFocusPainted(false);
        btnEditar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/aplicacionglobalfoodtrading/Iconos/1479450710_edit-file48x48.png"))); // NOI18N
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aplicacionglobalfoodtrading/Iconos/save32x32.png"))); // NOI18N
        btnGuardar.setToolTipText("Registrar Informacion del empleado");
        btnGuardar.setBorderPainted(false);
        btnGuardar.setContentAreaFilled(false);
        btnGuardar.setFocusPainted(false);
        btnGuardar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/aplicacionglobalfoodtrading/Iconos/save48x48.png"))); // NOI18N
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aplicacionglobalfoodtrading/Iconos/delete32x32.png"))); // NOI18N
        jButton7.setToolTipText("Eliminar Empleado mediante su identificacion");
        jButton7.setBorderPainted(false);
        jButton7.setContentAreaFilled(false);
        jButton7.setFocusPainted(false);
        jButton7.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/aplicacionglobalfoodtrading/Iconos/delete48x48.png"))); // NOI18N
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        btnLista.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aplicacionglobalfoodtrading/Iconos/Lista32x32.png"))); // NOI18N
        btnLista.setToolTipText("Ver Lista de Empleados");
        btnLista.setBorderPainted(false);
        btnLista.setContentAreaFilled(false);
        btnLista.setFocusPainted(false);
        btnLista.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/aplicacionglobalfoodtrading/Iconos/Lista48x48.png"))); // NOI18N
        btnLista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListaActionPerformed(evt);
            }
        });

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aplicacionglobalfoodtrading/Iconos/1477811446_Search32x32.png"))); // NOI18N
        btnBuscar.setToolTipText("Buscar  Empleado mediante su identificacion");
        btnBuscar.setBorderPainted(false);
        btnBuscar.setContentAreaFilled(false);
        btnBuscar.setFocusPainted(false);
        btnBuscar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/aplicacionglobalfoodtrading/Iconos/1477811460_Search48x48.png"))); // NOI18N
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        btnLimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aplicacionglobalfoodtrading/Iconos/1477830534_edit-clear32x32.png"))); // NOI18N
        btnLimpiar.setToolTipText("Restaurar Campos por defecto");
        btnLimpiar.setBorderPainted(false);
        btnLimpiar.setContentAreaFilled(false);
        btnLimpiar.setFocusPainted(false);
        btnLimpiar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/aplicacionglobalfoodtrading/Iconos/1477830839_edit-clear48x48.png"))); // NOI18N
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(420, 420, 420)
                .addComponent(btnBuscar)
                .addGap(84, 84, 84)
                .addComponent(btnEditar)
                .addGap(79, 79, 79)
                .addComponent(btnLista)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 228, Short.MAX_VALUE)
                .addComponent(jButton7)
                .addGap(31, 31, 31))
            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel8Layout.createSequentialGroup()
                    .addGap(39, 39, 39)
                    .addComponent(btnAtras)
                    .addContainerGap(995, Short.MAX_VALUE)))
            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel8Layout.createSequentialGroup()
                    .addGap(263, 263, 263)
                    .addComponent(btnGuardar)
                    .addContainerGap(771, Short.MAX_VALUE)))
            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                    .addContainerGap(865, Short.MAX_VALUE)
                    .addComponent(btnLimpiar)
                    .addGap(169, 169, 169)))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEditar, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
                    .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
                    .addComponent(btnLista, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
                    .addComponent(btnBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel8Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(btnAtras, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel8Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(btnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel8Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(btnLimpiar, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jPanel1.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 520, 1100, 90));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1170, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 624, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cboPaisItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboPaisItemStateChanged
        // TODO add your handling code here:
        Pais pa = cp.CodPaisXNombre(this.cboPais.getSelectedItem().toString());
        this.CargarComboCiudadesXPais(pa.getCodigo());
    }//GEN-LAST:event_cboPaisItemStateChanged

    private void txtDireccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDireccionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDireccionActionPerformed

    private void btnAtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtrasActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnAtrasActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        try {
            if (txtidentificacion.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "La identificacion es obligatorio", "Informacion", JOptionPane.INFORMATION_MESSAGE);
            } else {

                try {
                    Empleado_Directo ed = new Empleado_Directo();
                    ed.setTipoid(this.cboTipoId.getSelectedItem().toString());
                    ed.setIdentificacion(this.txtidentificacion.getText());
                    ed.setNombre(this.txtNombre.getText());
                    ed.setApellido(this.txtApellido.getText());
                    ed.setGenero(cboGenero.getSelectedItem().toString());
                    ed.setFecha_nac(txtFechaNac.getText());
                    System.out.println("Fecha de nacimiento : " + ed.getFecha_nac());
                    ed.setPais(cboPais.getSelectedItem().toString());
                    ed.setCiudad(cboCiudad.getSelectedItem().toString());
                    ed.setProvincia(txtProvincia.getText());
                    ed.setDireccion(txtDireccion.getText());
                    ed.setTel_movil(txtMovil.getText());
                    ed.setTel_fijo(txtTelFijo.getText());
                    ed.setEmail(txtEmail.getText());
                    ed.setEst_civil(cboEstadoCivil.getSelectedItem().toString());
                    ed.setCargo(txtCargo.getText());
                    ed.setNum_bancario(txtBanco.getText());
                    ed.setSalario(Double.parseDouble(txtSalario.getText()));
                    ed.setThm(Integer.parseInt(txtTotalHorasMensuales.getText()));
                    String vh = txtValorHora.getText();
                    vh = vh.replaceAll(",", ".");
                    ed.setValorhora(Double.parseDouble(vh));
                    ed.setFecha_contrat(txtFechaIngreso.getText());
                    ed.setAux_transport(cboAuxTransporte1.getSelectedItem().toString());
                    ed.setCesantias(cboCesantias.getSelectedItem().toString());
                    ed.setPrima(cboPrimas.getSelectedItem().toString());
                    ed.setPension(cboPension.getSelectedItem().toString());
                    ed.setCaja_compens(cboC_Compensacion.getSelectedItem().toString());
                    ed.setCod_Eps(txtCodEPS.getText());
                    ed.setNombre_eps(txtNombreEPS.getText());
                    ed.setCod_pension(txtCodPension.getText());
                    ed.setNombre_pension(txtNombrePension.getText());
                    ed.setNomb_refe1(txtRef1Nombre.getText());
                    ed.setEmail_refe1(txtRef1Email.getText());
                    ed.setDireccion_refe1(txtRef1Telefono.getText());
                    ed.setCargo_refe1(txtRef1Cargo.getText());
                    ed.setNomb_refe2(txtRef2Nombres.getText());
                    ed.setEmail_refe2(txtRef2Email.getText());
                    ed.setDireccion_refe2(txtRef2Telefono.getText());
                    ed.setCargo_refe2(txtRef2Cargo.getText());

                    // TODO add your handling code here:
                    BufferedImage img = ImageIO.read(new File(fichero.toString()));
                    String image_string = encodeToString(img);
                    // guardar_imagen(image_string, fichero.getName());

                    ed.setFoto_perfil(image_string);

                    if (ced.RegistrarEmpleadoDirecto(ed) == 1) {
                        JOptionPane.showMessageDialog(null, "Registro Exitoso");
                        LimpiarCampos();
                    } else {
                        JOptionPane.showMessageDialog(null, "Registro Fallido - fallo vista", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }

            }
        } catch (HeadlessException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage() + " 2 - vista ", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        // TODO add your handling code here:
        LimpiarCampos();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void txtTotalHorasMensualesKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTotalHorasMensualesKeyTyped
        // TODO add your handling code here:


    }//GEN-LAST:event_txtTotalHorasMensualesKeyTyped

    private void txtSalarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSalarioKeyTyped
        // TODO add your handling code here:

    }//GEN-LAST:event_txtSalarioKeyTyped

    private void txtSalarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSalarioKeyPressed

    }//GEN-LAST:event_txtSalarioKeyPressed

    private void txtTotalHorasMensualesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTotalHorasMensualesKeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_txtTotalHorasMensualesKeyPressed

    private void txtTotalHorasMensualesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTotalHorasMensualesKeyReleased
        // TODO add your handling code here:
        char car = evt.getKeyChar();
        if ((car < '0' || car > '9') && (car < ',' || car > '.')) {
            evt.consume();
        } else {
            try {
                int total = Integer.parseInt(txtTotalHorasMensuales.getText());
                double salar = Double.parseDouble(txtSalario.getText());
                float valho = (float) (salar / total);
                System.out.println(String.valueOf(salar) + "/" + String.valueOf(total) + " = " + String.valueOf(valho));
                txtValorHora.setText(String.valueOf(""));
                txtValorHora.setText(String.valueOf(df.format(valho)));
            } catch (java.lang.NumberFormatException ex) {
                CargarComboPaises();
                pac = cp.CodPaisXNombre(cboPais.getSelectedItem().toString());
                CargarComboCiudadesXPais(pac.getCodigo());
            }
        }
    }//GEN-LAST:event_txtTotalHorasMensualesKeyReleased

    private void txtSalarioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSalarioKeyReleased
        // TODO add your handling code here:
        char car = evt.getKeyChar();
        if ((car < '0' || car > '9') && (car < ',' || car > '.')) {
            evt.consume();
        } else {
            try {
                int total = Integer.parseInt(txtTotalHorasMensuales.getText());
                double salar = Double.parseDouble(txtSalario.getText());
                float valho = (float) (salar / total);
                System.out.println(String.valueOf(salar) + "/" + String.valueOf(total) + " = " + String.valueOf(valho));
                txtValorHora.setText(String.valueOf(""));
                txtValorHora.setText(String.valueOf(df.format(valho)));
            } catch (java.lang.NumberFormatException ex) {

            }
        }
    }//GEN-LAST:event_txtSalarioKeyReleased

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:
        try {
            if (txtidentificacion.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Debe ingresar una identificacion valida");
            } else {
                Empleado_Directo dir = new Empleado_Directo();
                dir = ced.Empleadoxid(this.txtidentificacion.getText());
                System.out.println(dir.getNombre());
                if (dir == null) {
                    JOptionPane.showMessageDialog(null, "No se encontro el Empleado a buscar", "Advertencia", JOptionPane.WARNING_MESSAGE);
                } else {

                    JOptionPane.showMessageDialog(null, "Se encontro el empleado : " + dir.getNombre());
                    CargarDatosForm(dir);
                }
            }
        } catch (java.lang.NullPointerException ex) {
            JOptionPane.showMessageDialog(null, "No se encontro el Empleado a buscar", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }


    }//GEN-LAST:event_btnBuscarActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        try {
            if (txtidentificacion.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Debe ingresar una identificacion valida");
            } else {
                if (ced.ExisteEmpleado(this.txtidentificacion.getText()) == 'n') {
                    JOptionPane.showMessageDialog(null, "No se encuentra el empleado a eliminar");
                } else {
                    int ex = ced.EliminarEmpleadoDirec(this.txtidentificacion.getText());
                    if (ex == 1) {
                        JOptionPane.showMessageDialog(null, "Empleado directo eliminado correctamente !!!!");
                        LimpiarCampos();
                    } else {
                        JOptionPane.showMessageDialog(null, "No se elimino el empleado directo !!!!");
                    }
                }
            }
        } catch (java.lang.NullPointerException ex) {
            JOptionPane.showMessageDialog(null, "No se encontro el empleado a eliminar");
        }

    }//GEN-LAST:event_jButton7ActionPerformed

    private void btnListaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListaActionPerformed
        // TODO add your handling code here:
        Lista_Empleados_Directos led = new Lista_Empleados_Directos();
        led.setVisible(true);
    }//GEN-LAST:event_btnListaActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // TODO add your handling code here:
        String id;

        if (this.txtidentificacion.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe escribir una identificacion !!!!");
        } else {
            int opc = JOptionPane.showConfirmDialog(null, "Esta seguro que desea actualizar el empleado ?");
            if (opc == JOptionPane.YES_OPTION) {
                try {
                    Empleado_Directo ed = new Empleado_Directo();
                    ed.setTipoid(this.cboTipoId.getSelectedItem().toString());
                    ed.setIdentificacion(this.txtidentificacion.getText());
                    ed.setNombre(this.txtNombre.getText());
                    ed.setApellido(this.txtApellido.getText());
                    ed.setGenero(cboGenero.getSelectedItem().toString());
                    ed.setFecha_nac(txtFechaNac.getText());
                    System.out.println("Fecha de nacimiento : " + ed.getFecha_nac());
                    ed.setPais(cboPais.getSelectedItem().toString());
                    ed.setCiudad(cboCiudad.getSelectedItem().toString());
                    ed.setProvincia(txtProvincia.getText());
                    ed.setDireccion(txtDireccion.getText());
                    ed.setTel_movil(txtMovil.getText());
                    ed.setTel_fijo(txtTelFijo.getText());
                    ed.setEmail(txtEmail.getText());
                    ed.setEst_civil(cboEstadoCivil.getSelectedItem().toString());
                    ed.setCargo(txtCargo.getText());
                    ed.setNum_bancario(txtBanco.getText());
                    ed.setSalario(Double.parseDouble(txtSalario.getText()));
                    ed.setThm(Integer.parseInt(txtTotalHorasMensuales.getText()));
                    String vh = txtValorHora.getText();
                    vh = vh.replaceAll(",", ".");
                    ed.setValorhora(Double.parseDouble(vh));
                    ed.setFecha_contrat(txtFechaIngreso.getText());
                    ed.setAux_transport(cboAuxTransporte1.getSelectedItem().toString());
                    ed.setCesantias(cboCesantias.getSelectedItem().toString());
                    ed.setPrima(cboPrimas.getSelectedItem().toString());
                    ed.setPension(cboPension.getSelectedItem().toString());
                    ed.setCaja_compens(cboC_Compensacion.getSelectedItem().toString());
                    ed.setCod_Eps(txtCodEPS.getText());
                    ed.setNombre_eps(txtNombreEPS.getText());
                    ed.setCod_pension(txtCodPension.getText());
                    ed.setNombre_pension(txtNombrePension.getText());
                    ed.setNomb_refe1(txtRef1Nombre.getText());
                    ed.setEmail_refe1(txtRef1Email.getText());
                    ed.setDireccion_refe1(txtRef1Telefono.getText());
                    ed.setCargo_refe1(txtRef1Cargo.getText());
                    ed.setNomb_refe2(txtRef2Nombres.getText());
                    ed.setEmail_refe2(txtRef2Email.getText());
                    ed.setDireccion_refe2(txtRef2Telefono.getText());
                    ed.setCargo_refe2(txtRef2Cargo.getText());
                    if (lbfoto.getIcon() == null) {
                        System.err.println("No hay foto");
                        ed.setFoto_perfil("null");
                    } else {
                        BufferedImage img = ImageIO.read(new File(fichero.toString()));
                        String image_string = encodeToString(img);

                        ed.setFoto_perfil(image_string);

                    }

                    //String sql = "UPDATE `emp_direc` SET `tipoid`='" + ed.getTipoid() + "',`identificacion`='" + ed.getIdentificacion() + "',`nombre`='" + ed.getNombre() + "',`apellido`='" + ed.getApellido() + "',`genero`='" + ed.getGenero() + "',`fechanac`='" + ed.getFecha_nac() + "',`pais`='" + ed.getPais() + "',`ciudad`='" + ed.getCiudad() + "',`provincia`='" + ed.getProvincia() + "',`direccion`='" + ed.getDireccion() + "',`telmovil`='" + ed.getTel_movil() + "',`telfijo`='" + ed.getTel_fijo() + "',`email`='" + ed.getEmail() + "',`estcivil`='" + ed.getEst_civil() + "',`cargo`='" + ed.getCargo() + "',`banco`='" + ed.getNum_bancario() + "',`salario`='" + ed.getSalario() + "',`htm`=" + ed.getThm() + ",`valorhora`=" + ed.getValorhora() + ",`fechaingre`='" + ed.getFecha_contrat() + "',`auxtransp`='" + ed.getAux_transport() + "',`cesantias`='" + ed.getCesantias() + "',`primas`='" + ed.getPrima() + "',`pension`='" + ed.getPension() + "',`cajacomp`='" + ed.getPension() + "',`codeps`='" + ed.getCod_Eps() + "',`nombeps`='" + ed.getNombre_eps() + "',`codpension`='" + ed.getCod_pension() + "',`nombpension`='" + ed.getNombre_pension() + "',`ref1nomb`='" + ed.getNomb_refe1() + "',`ref1email`='" + ed.getEmail_refe1() + "',`ref1movil`='" + ed.getDireccion_refe1() + "',`ref1cargo`='" + ed.getCargo_refe1() + "',`ref2nomb`='" + ed.getNomb_refe2() + "',`ref2email`='" + ed.getEmail_refe2() + "',`ref2movil`='" + ed.getDireccion_refe2() + "',`ref2cargo`='" + ed.getCargo_refe2() + "', imagen = '" + ed.getFoto_perfil() + "' WHERE identificacion = '" + ed.getIdentificacion() + "'";
                    //System.out.println(sql);
                    if (ced.ActualizarEmpleadoDirec(ed.getIdentificacion(), ed) == 1) {
                        LimpiarCampos();
                        JOptionPane.showMessageDialog(null, "Se actualizo correctamente el empleado directo");
                    } else {
                        JOptionPane.showMessageDialog(null, "No se ectualizo el empleado ");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error en el actualizar : " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        JFileChooser file = new JFileChooser();
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("*.jpg", "jpg");
        file.setFileFilter(filtro);

        int seleccion = file.showOpenDialog(contentPane);
        //Si el usuario, pincha en aceptar
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            //Seleccionamos el fichero
            fichero = file.getSelectedFile();
            //Ecribe la ruta del fichero seleccionado en el campo de texto
            if (!ObtenerExtension(fichero).equals("jpg")) {
                JOptionPane.showMessageDialog(null, "La imagen debe ser formato 'jpg'", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                String ubica = fichero.getAbsolutePath();
                ImageIcon icon = new ImageIcon(fichero.toString());
                System.out.println(fichero.getName());
                Icon icono = new ImageIcon(icon.getImage().getScaledInstance(lbfoto.getWidth(), lbfoto.getHeight(), Image.SCALE_DEFAULT));
                lbfoto.setText(null);
                lbfoto.setIcon(icono);
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtidentificacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtidentificacionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtidentificacionActionPerformed

    private void cboCiudadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboCiudadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboCiudadActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Metal".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Registro_Empleados_Directos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Registro_Empleados_Directos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Registro_Empleados_Directos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Registro_Empleados_Directos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Registro_Empleados_Directos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAtras;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnLista;
    private javax.swing.JComboBox<String> cboAuxTransporte1;
    private javax.swing.JComboBox<String> cboC_Compensacion;
    private javax.swing.JComboBox<String> cboCesantias;
    private javax.swing.JComboBox<String> cboCiudad;
    private javax.swing.JComboBox<String> cboEstadoCivil;
    private javax.swing.JComboBox<String> cboGenero;
    private javax.swing.JComboBox<String> cboPais;
    private javax.swing.JComboBox<String> cboPension;
    private javax.swing.JComboBox<String> cboPrimas;
    private javax.swing.JComboBox<String> cboTipoId;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton7;
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
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
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
    private javax.swing.JLabel lbfoto;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtBanco;
    private javax.swing.JTextField txtCargo;
    private javax.swing.JTextField txtCodEPS;
    private javax.swing.JTextField txtCodPension;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtEdad;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtMovil;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNombreEPS;
    private javax.swing.JTextField txtNombrePension;
    private javax.swing.JTextField txtProvincia;
    private javax.swing.JTextField txtRef1Cargo;
    private javax.swing.JTextField txtRef1Email;
    private javax.swing.JTextField txtRef1Nombre;
    private javax.swing.JTextField txtRef1Telefono;
    private javax.swing.JTextField txtRef2Cargo;
    private javax.swing.JTextField txtRef2Email;
    private javax.swing.JTextField txtRef2Nombres;
    private javax.swing.JTextField txtRef2Telefono;
    private javax.swing.JTextField txtSalario;
    private javax.swing.JTextField txtTelFijo;
    private javax.swing.JTextField txtTotalHorasMensuales;
    private javax.swing.JTextField txtValorHora;
    private javax.swing.JTextField txtidentificacion;
    // End of variables declaration//GEN-END:variables
}
