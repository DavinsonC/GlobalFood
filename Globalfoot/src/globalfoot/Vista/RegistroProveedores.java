/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package globalfoot.Vista;

import aplicacionglobalfoodtrading.Controlador.ControladorCiudades;
import aplicacionglobalfoodtrading.Controlador.ControladorPais;
import aplicacionglobalfoodtrading.Controlador.ControladorProveedor;
import aplicacionglobalfoodtrading.Controlador.Controlador_Empleado_Directo;
import aplicacionglobalfoodtrading.Modelo.Ciudad;
import aplicacionglobalfoodtrading.Modelo.Empleado_Directo;
import aplicacionglobalfoodtrading.Modelo.Pais;
import aplicacionglobalfoodtrading.Modelo.Proveedor;
import static globalfoot.Vista.Registro_Empleados_Directos.encodeToString;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.image.BufferedImage;
import static java.awt.image.ImageObserver.ALLBITS;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
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
 * @author Ricardo Carmona
 */
public class RegistroProveedores extends javax.swing.JFrame {

    private JPanel contentPane;
    File fichero = null;
    DecimalFormat df = new DecimalFormat("#.00");
    ControladorCiudades cc = new ControladorCiudades();
    ControladorPais cp = new ControladorPais();
    // Controlador_Empleado_Directo ced = new Controlador_Empleado_Directo();
    ControladorProveedor cpr = new ControladorProveedor();
    Pais pac;
    private static Connection con;
    private static ResultSet rs;
    private static Statement st;

    public RegistroProveedores() {
        initComponents();
        this.setTitle("Gestion de Proveedores");
        CargarComboPaises();
        pac = cp.CodPaisXNombre(cmbPais.getSelectedItem().toString());
        CargarComboCiudadesXPais(pac.getCodigo());
    }

    public RegistroProveedores(Proveedor p) {
        initComponents();
        CargarComboPaises();
        pac = cp.CodPaisXNombre(cmbPais.getSelectedItem().toString());
        CargarComboCiudadesXPais(pac.getCodigo());
        CargarForm(p);
    }

    public void Conectar() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bd_gft", "root", "");
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al conectar BD");
        }
    }

    public void LimpiarForm() {
        txtCodigo.setText("");
        txtNombre.setText("");
        cmbPais.setSelectedIndex(0);
        cmbCiudad.setSelectedIndex(0);
        txtProvincia.setText("");
        txtCodPostal.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
        txtMovil.setText("");
        txtFAX.setText("");
        txtCorreo.setText("");
        txtOtroCorreo.setText("");
        txtGiroNeg.setText("");
        cmbTipo.setSelectedIndex(0);
        txtNIT.setText("");
        txtRegEMpr.setText("");
        txtVendedor.setText("");
        txtCategoria.setText("");
        lblImagen.setText("");
        lblImagen.setIcon(null);
        checkAct.setState(true);
        checkImp.setState(false);
    }

    public void CargarForm(Proveedor p) {
        txtCodigo.setText(p.getCodigo());
        txtNombre.setText(p.getNombre());
        cmbPais.setSelectedItem(p.getPais());
        cmbCiudad.setSelectedItem(p.getCiudad());
        txtProvincia.setText(p.getProvincia());
        txtCodPostal.setText(p.getCod_postal());
        txtDireccion.setText(p.getDireccion());
        txtTelefono.setText(p.getTelefono());
        txtMovil.setText(p.getMovil());
        txtFAX.setText(p.getFax());
        txtCorreo.setText(p.getEmail1());
        txtOtroCorreo.setText(p.getEmail2());
        txtGiroNeg.setText(p.getFk_girnoneg());
        cmbTipo.setSelectedItem(p.getFk_tipo());
        txtNIT.setText(p.getNit());
        txtRegEMpr.setText(p.getReg_empr());
        txtVendedor.setText(p.getVendedor());
        txtCategoria.setText(p.getFk_categoria());

        if (p.getIs_activo() == 1) {
            checkAct.setState(true);
        } else {
            checkAct.setState(false);
        }

        if (p.getImpuesto_incl() == 1) {
            checkImp.setState(true);
        } else {
            checkImp.setState(false);
        }

        cargar(p.getCodigo());
    }

    public void CargarComboPaises() {
        ArrayList<Pais> lp = new ArrayList();

        try {
            lp = cp.ListaPaises();
            this.cmbPais.removeAllItems();
            for (Pais p : lp) {
                this.cmbPais.addItem(p.getPais());
            }

        } catch (SQLException ex) {
            Logger.getLogger(Registro_Empleados_Directos.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void CargarComboCiudadesXPais(String cod) {

        ArrayList<Ciudad> lc = new ArrayList();

        try {
            lc = cc.ListaCiudadxPais(cod);
            this.cmbCiudad.removeAllItems();
            for (Ciudad c : lc) {
                this.cmbCiudad.addItem(c.getCiudad());
            }

        } catch (SQLException ex) {
            Logger.getLogger(Registro_Empleados_Directos.class.getName()).log(Level.SEVERE, null, ex);
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
        String sql = "SELECT imagen FROM proveedor where codigo = '" + iden + "'";
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
                try{
                img = decodeToImage(imagen_string);
                ImageIcon icon = new ImageIcon(img);
                Icon icono = new ImageIcon(icon.getImage().getScaledInstance(lblImagen.getWidth(), lblImagen.getHeight(), Image.SCALE_DEFAULT));
                lblImagen.setText(null);
                lblImagen.setIcon(icono);
                }catch(java.lang.NullPointerException ex){
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        cmbPais = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        cmbCiudad = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        txtProvincia = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtCodPostal = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtMovil = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtOtroCorreo = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtCorreo = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtFAX = new javax.swing.JTextField();
        jPanel14 = new javax.swing.JPanel();
        btnAtras = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        btnLista = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        txtGiroNeg = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtCategoria = new javax.swing.JTextField();
        jButton7 = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        txtVendedor = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtRegEMpr = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtNIT = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        checkAct = new java.awt.Checkbox();
        jLabel20 = new javax.swing.JLabel();
        checkImp = new java.awt.Checkbox();
        cmbTipo = new javax.swing.JComboBox<>();
        jPanel7 = new javax.swing.JPanel();
        lblImagen = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel1.setText("Codigo");

        jLabel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel2.setText("Nombre del proveedor");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 84, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 407, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Información de Contacto del proveedor"));

        jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel3.setText("Pais");

        cmbPais.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cmbPais.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbPaisItemStateChanged(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel4.setText("Ciudad");

        cmbCiudad.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel5.setText("Cod. Postal");

        txtProvincia.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtProvincia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProvinciaActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel6.setText("Provincia");

        txtCodPostal.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel7.setText("Telefono");

        txtTelefono.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel8.setText("Tel. Movil");

        txtMovil.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel9.setText("Otro Correo");

        txtOtroCorreo.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel10.setText("Direccion");

        txtDireccion.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel11.setText("Correo");

        txtCorreo.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel12.setText("FAX");

        txtFAX.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtFAX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFAXActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbCiudad, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtProvincia, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFAX, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbPais, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(84, 84, 84)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCodPostal, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMovil, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 124, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtOtroCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtCodPostal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(cmbPais, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel5)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel11)
                    .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(cmbCiudad, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel6)
                    .addComponent(jLabel8)
                    .addComponent(txtMovil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(txtOtroCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtProvincia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFAX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

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

        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aplicacionglobalfoodtrading/Iconos/delete32x32.png"))); // NOI18N
        jButton8.setToolTipText("Eliminar Empleado mediante su identificacion");
        jButton8.setBorderPainted(false);
        jButton8.setContentAreaFilled(false);
        jButton8.setFocusPainted(false);
        jButton8.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/aplicacionglobalfoodtrading/Iconos/delete48x48.png"))); // NOI18N
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
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

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnGuardar)
                .addGap(76, 76, 76)
                .addComponent(btnBuscar)
                .addGap(75, 75, 75)
                .addComponent(btnEditar)
                .addGap(79, 79, 79)
                .addComponent(btnLista)
                .addGap(81, 81, 81)
                .addComponent(btnLimpiar)
                .addGap(56, 56, 56)
                .addComponent(jButton8)
                .addGap(48, 48, 48))
            .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel14Layout.createSequentialGroup()
                    .addGap(39, 39, 39)
                    .addComponent(btnAtras)
                    .addContainerGap(937, Short.MAX_VALUE)))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnEditar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLista, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
                    .addComponent(btnLimpiar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel14Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(btnAtras, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Informacion Comercial del Proveedor"));

        jLabel13.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel13.setText("Giro Negocio");

        txtGiroNeg.setEditable(false);
        txtGiroNeg.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtGiroNeg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGiroNegActionPerformed(evt);
            }
        });

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aplicacionglobalfoodtrading/Iconos/lupa16x16.png"))); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel14.setText("Tipo");

        jLabel15.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel15.setText("Categoria");

        txtCategoria.setEditable(false);
        txtCategoria.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCategoriaActionPerformed(evt);
            }
        });

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aplicacionglobalfoodtrading/Iconos/lupa16x16.png"))); // NOI18N
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel16.setText("Vendendor");

        txtVendedor.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtVendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtVendedorActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel17.setText("Reg. Empr");

        txtRegEMpr.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtRegEMpr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRegEMprActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel18.setText("NIT");

        txtNIT.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtNIT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNITActionPerformed(evt);
            }
        });

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(204, 204, 255))); // NOI18N

        jLabel19.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel19.setText("Proveedor esta activo");

        checkAct.setState(true);

        jLabel20.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel20.setText("Incluye Impuesto");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19)
                .addGap(19, 19, 19)
                .addComponent(checkAct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel20)
                .addGap(36, 36, 36)
                .addComponent(checkImp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel19)
                    .addComponent(checkAct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20)
                    .addComponent(checkImp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        cmbTipo.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        cmbTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Persona Juridica", "Persona Natural" }));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(txtGiroNeg, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNIT, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtRegEMpr, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(24, 24, 24))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtGiroNeg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel15)
                    .addComponent(txtCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(txtRegEMpr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(cmbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(jLabel14)
                    .addComponent(txtNIT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Imagen del Proveedor"));

        lblImagen.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jButton1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aplicacionglobalfoodtrading/Iconos/Camara_16_x_16.png"))); // NOI18N
        jButton1.setText("Subir Fotografia");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                    .addComponent(lblImagen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(78, 78, 78))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(lblImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtProvinciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProvinciaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProvinciaActionPerformed

    private void txtFAXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFAXActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFAXActionPerformed

    private void btnAtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtrasActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnAtrasActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // TODO add your handling code here:
        try {
            if (txtCodigo.getText().isEmpty() || txtNombre.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Debe ingresar el codigo y el nombre del proveedor");
            } else {

                try {
                    Proveedor p = new Proveedor();
                    p.setCodigo(txtCodigo.getText());
                    p.setNombre(txtNombre.getText());
                    p.setPais(cmbPais.getSelectedItem().toString());
                    p.setCiudad(cmbCiudad.getSelectedItem().toString());
                    p.setProvincia(txtProvincia.getText());
                    p.setCod_postal(txtCodPostal.getText());
                    p.setDireccion(txtDireccion.getText());
                    p.setTelefono(txtTelefono.getText());
                    p.setMovil(txtMovil.getText());
                    p.setFax(txtFAX.getText());
                    p.setEmail1(txtCorreo.getText());
                    p.setEmail2(txtOtroCorreo.getText());
                    p.setFk_girnoneg(txtGiroNeg.getText());
                    p.setFk_tipo(cmbTipo.getSelectedItem().toString());
                    p.setNit(txtNIT.getText());
                    p.setReg_empr(txtRegEMpr.getText());
                    p.setVendedor(txtVendedor.getText());
                    p.setFk_categoria(txtCategoria.getText());

                    BufferedImage img = ImageIO.read(new File(fichero.toString()));
                    String image_string = encodeToString(img);
//               
//
                    p.setImagen(image_string);
                    String sql2 = "UPDATE `proveedor` SET `nombre`='" + p.getNombre() + "'," + "`pais`='" + p.getPais() + "'," + "`ciudad`='" + p.getCiudad() + "'," + "`provincia`='" + p.getProvincia() + "'," + "`cod_postal`='" + p.getCod_postal() + "'," + "`direccion`='" + p.getDireccion() + "'," + "`telefono`='" + p.getTelefono() + "'," + "`movil`='" + p.getDireccion() + "'," + "`fax`='" + p.getFax() + "'," + "`email_1`='" + p.getEmail1() + "'," + "`email_2`='" + p.getEmail2() + "'," + "`fk_giro_neg`='" + p.getFk_girnoneg() + "'," + "`fk_tipo`='" + p.getFk_tipo() + "'," + "`NIT`='" + p.getNit() + "'," + "`reg_Empr`='" + p.getReg_empr() + "'," + "`is_activo`=" + p.getIs_activo() + "," + "`imp_incl`=" + p.getImpuesto_incl() + "," + "`vendedor`='" + p.getVendedor() + "'," + "`fk_categoria`='" + p.getFk_categoria() + "'," + "`imagen`='" + "null" + "' WHERE codigo = '" + p.getCodigo() + "'";
                    System.out.println(sql2);
                    if (cpr.ModificarProveedor(p) == 1) {
                        JOptionPane.showMessageDialog(null, "Modificacion de proveedor fue Exitosa");
                        LimpiarForm();
                    } else {
                        JOptionPane.showMessageDialog(null, "Modificacion Fallida - fallo vista", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error excepcion " + ex.getMessage());
                }

            }
        } catch (HeadlessException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage() + " 2 - vista ", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
//        try {
//            if (txtCodigo.getText().isEmpty() || txtNombre.getText().isEmpty()) {
//                JOptionPane.showMessageDialog(null, "Ingrese el codigo y el nombre del proveedor");
//            } else {
//                Proveedor p = new Proveedor();
//                p.setCodigo(txtCodigo.getText());
//                p.setNombre(txtNombre.getText());
//                p.setPais(cmbPais.getSelectedItem().toString());
//                p.setCiudad(cmbCiudad.getSelectedItem().toString());
//                p.setProvincia(txtProvincia.getText());
//                p.setCod_postal(txtCodPostal.getText());
//                p.setDireccion(txtDireccion.getText());
//                p.setTelefono(txtTelefono.getText());
//                p.setMovil(txtMovil.getText());
//                p.setFax(txtFAX.getText());
//                p.setEmail1(txtCorreo.getText());
//                p.setEmail2(txtOtroCorreo.getText());
//                p.setFk_girnoneg(txtGiroNeg.getText());
//                p.setFk_tipo(cmbTipo.getSelectedItem().toString());
//                p.setNit(txtNIT.getText());
//                p.setReg_empr(txtRegEMpr.getText());
//                p.setVendedor(txtVendedor.getText());
//                p.setFk_categoria(txtCategoria.getText());
//
//                BufferedImage img = ImageIO.read(new File(fichero.toString()));
//                String image_string = encodeToString(img);
//                // guardar_imagen(image_string, fichero.getName());
//                p.setImagen(image_string);
//
//                if (cpr.RegistrarProveedor(p) == 1) {
//                    JOptionPane.showMessageDialog(null, "Se registro Correctamente el PRoveedor");
//                } else {
//                    JOptionPane.showMessageDialog(null, "Error al registrar  el Proveedor");
//                }
//            }
//        } catch (IOException ex) {
//            JOptionPane.showMessageDialog(null, "Error al Registrar el proveedor");
//        } catch (java.lang.NullPointerException ex) {
//            JOptionPane.showMessageDialog(null, "Error imagen " + ex);
//        }

        try {
            if (txtCodigo.getText().isEmpty() || txtNombre.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "La identificacion es obligatorio", "Informacion", JOptionPane.INFORMATION_MESSAGE);
            } else {

                try {
                    Proveedor p = new Proveedor();
                    p.setCodigo(txtCodigo.getText());
                    p.setNombre(txtNombre.getText());
                    p.setPais(cmbPais.getSelectedItem().toString());
                    p.setCiudad(cmbCiudad.getSelectedItem().toString());
                    p.setProvincia(txtProvincia.getText());
                    p.setCod_postal(txtCodPostal.getText());
                    p.setDireccion(txtDireccion.getText());
                    p.setTelefono(txtTelefono.getText());
                    p.setMovil(txtMovil.getText());
                    p.setFax(txtFAX.getText());
                    p.setEmail1(txtCorreo.getText());
                    p.setEmail2(txtOtroCorreo.getText());
                    p.setFk_girnoneg(txtGiroNeg.getText());
                    p.setFk_tipo(cmbTipo.getSelectedItem().toString());
                    p.setNit(txtNIT.getText());
                    p.setReg_empr(txtRegEMpr.getText());
                    p.setVendedor(txtVendedor.getText());
                    p.setFk_categoria(txtCategoria.getText());

                    if(lblImagen.getIcon()==null){
                            p.setImagen("null");
                            System.err.println("no hubo imagen");
                    } else {
                        BufferedImage img = ImageIO.read(new File(fichero.toString()));
                    String image_string = encodeToString(img);
                    // guardar_imagen(image_string, fichero.getName());

                    p.setImagen(image_string);

                    }
                    
                    if (cpr.RegistrarProveedor(p) == 1) {
                        JOptionPane.showMessageDialog(null, "Registro Exitoso");
                        LimpiarForm();
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

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        if (txtCodigo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar un codigo");
        } else {

            if (cpr.EliminarProveedor(txtCodigo.getText()) == 1) {
                LimpiarForm();
                JOptionPane.showMessageDialog(null, "Se elimino correctamente el proveedor");
            } else {

                JOptionPane.showMessageDialog(null, "Error al eliminar el proveedor");
            }

        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void btnListaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListaActionPerformed
        // TODO add your handling code here:
        ListaProveedores lp = new ListaProveedores();
        lp.setVisible(true);
    }//GEN-LAST:event_btnListaActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:

        if (txtCodigo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar un codigo");
        } else {
            Proveedor p = cpr.ProvedorxCodigo(txtCodigo.getText());

            if (p == null) {
                JOptionPane.showMessageDialog(null, "No se encontro el proveedor a buscar");
            } else {
                CargarForm(p);
                JOptionPane.showMessageDialog(null, "Proveedor Encontrado");
            }
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        // TODO add your handling code here:
        LimpiarForm();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void txtGiroNegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGiroNegActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGiroNegActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        Giro_Negocio_Proveedor gnp = new Giro_Negocio_Proveedor();
        gnp.setVisible(true);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void txtCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCategoriaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCategoriaActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        Categoria_Proveedor_Menu cpm = new Categoria_Proveedor_Menu();
        cpm.setVisible(true);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void txtVendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtVendedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtVendedorActionPerformed

    private void txtRegEMprActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRegEMprActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRegEMprActionPerformed

    private void txtNITActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNITActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNITActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        JFileChooser file = new JFileChooser();
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("*.jpg", "jpg");
        file.setFileFilter(filtro);

        int seleccion = file.showOpenDialog(contentPane);
        //Si el usuario, pincha en aceptar
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            //Seleccionamos el fichero
            fichero = file.getSelectedFile();
            if (!ObtenerExtension(fichero).equals("jpg")) {
                JOptionPane.showMessageDialog(null, "La imagen debe ser formato 'jpg'", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                String ubica = fichero.getAbsolutePath();
                ImageIcon icon = new ImageIcon(fichero.toString());
                System.out.println(fichero.getName());
                Icon icono = new ImageIcon(icon.getImage().getScaledInstance(lblImagen.getWidth(), lblImagen.getHeight(), Image.SCALE_DEFAULT));
                lblImagen.setText(null);
                lblImagen.setIcon(icono);
            }

        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void cmbPaisItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbPaisItemStateChanged
        // TODO add your handling code here:
        Pais pa = cp.CodPaisXNombre(this.cmbPais.getSelectedItem().toString());
        this.CargarComboCiudadesXPais(pa.getCodigo());
    }//GEN-LAST:event_cmbPaisItemStateChanged

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
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(RegistroProveedores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegistroProveedores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegistroProveedores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegistroProveedores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RegistroProveedores().setVisible(true);
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
    private java.awt.Checkbox checkAct;
    private java.awt.Checkbox checkImp;
    private javax.swing.JComboBox<String> cmbCiudad;
    private javax.swing.JComboBox<String> cmbPais;
    private javax.swing.JComboBox<String> cmbTipo;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JLabel lblImagen;
    public static javax.swing.JTextField txtCategoria;
    private javax.swing.JTextField txtCodPostal;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtFAX;
    public static javax.swing.JTextField txtGiroNeg;
    private javax.swing.JTextField txtMovil;
    private javax.swing.JTextField txtNIT;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtOtroCorreo;
    private javax.swing.JTextField txtProvincia;
    private javax.swing.JTextField txtRegEMpr;
    private javax.swing.JTextField txtTelefono;
    private javax.swing.JTextField txtVendedor;
    // End of variables declaration//GEN-END:variables
}
