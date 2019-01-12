/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package globalfoot.Vista;

import aplicacionglobalfoodtrading.Controlador.ControladorImpuesto;
import aplicacionglobalfoodtrading.Controlador.ControladorProducto;
import aplicacionglobalfoodtrading.Controlador.Controlador_Dolar;
import aplicacionglobalfoodtrading.Controlador.ControladorCategoria;
import aplicacionglobalfoodtrading.Controlador.ControladorSubcategoria;
import aplicacionglobalfoodtrading.Controlador.ControladorBodega;
import aplicacionglobalfoodtrading.Controlador.ControladorOtroImp;
import aplicacionglobalfoodtrading.Controlador.ControladorProveedor;
import aplicacionglobalfoodtrading.Modelo.Bodega;
import aplicacionglobalfoodtrading.Modelo.Categoria;
import aplicacionglobalfoodtrading.Modelo.Impuesto;
import aplicacionglobalfoodtrading.Modelo.OtroImp;
import aplicacionglobalfoodtrading.Modelo.Producto;
import aplicacionglobalfoodtrading.Modelo.Proveedor;
import aplicacionglobalfoodtrading.Modelo.Subcategoria;
import com.sun.glass.events.KeyEvent;
import java.awt.Color;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;

/**
 *
 * @author eldoc
 */
public class RegistroProductos extends javax.swing.JFrame {

    ControladorCategoria cc = new ControladorCategoria();
    ControladorSubcategoria cs = new ControladorSubcategoria();
    ControladorBodega cb = new ControladorBodega();
    ControladorProducto cp = new ControladorProducto();
    ControladorImpuesto ci = new ControladorImpuesto();
    Controlador_Dolar cd = new Controlador_Dolar();
    ControladorProveedor cprov = new ControladorProveedor();
    DecimalFormat df3 = new DecimalFormat("##.##");
    ControladorOtroImp cot = new ControladorOtroImp();
    public RegistroProductos() {
        initComponents();
        this.setTitle("GlobalSoft -- Gestion de Productos");
    }

    public RegistroProductos(Producto p) {
        initComponents();
        this.setTitle("GlobalSoft -- Gestion de Productos");
        CargarForm(p);
    }
    
    public void CargarForm(Producto p) {
        this.txtCodigo.setText(p.getCod_prod());
        this.txtNombreProducto.setText(p.getNombre_prod());
        this.txtContiene_Compra.setText(String.valueOf(p.getCompra_contiene()));
        txtSeCompra_x.setText(p.getSe_Compra_x());
        txtSe_vende_x.setText(String.valueOf(p.getSe_vende_x()));
        txtSe_vende_x.setText(p.getSe_vende_x());
        txtContiene_Vende.setText(String.valueOf(p.getVende_contiene()));
        Bodega b = cb.RetornarBodegaxCod(p.getFk_bodega());
        txtBodega.setText(b.getNombre_b());
        Categoria c = cc.RetornarCategoriaxCod(p.getFk_categoria());
        txtCategoriaform.setText(c.getCategoria());
        Subcategoria s = cs.RetornarSubcategoriaxCod(p.getFk_subcategoria());
        txtSubCategoria.setText(s.getNombre());
        txtCostoproducto.setText(String.valueOf(p.getPrec_cost_prod()));
        txtCostoEnUss.setText(String.valueOf(p.getPrec_cost_prod_dolar()));
        txtUtilidadProducto.setText(String.valueOf(p.getUtilidad_prod()));
        txtPrecio_neto_venta.setText(String.valueOf(p.getPrecio_neto_prod()));
        txtPrecio_neto_venta.setText(String.valueOf(p.getPrecio_mas_impuesto()));
        txtImpuesto.setText(String.valueOf(p.getFk_impuesto1()));
        txtOtroImp.setText(String.valueOf(p.getFk_impuesto2()));
        Proveedor prov = cprov.ProvedorxCodigo(p.getFk_proveedor());
        txtProveedor.setText(prov.getNombre());
        
        if (p.getEsta_activo() == 1) {
            checkActivo.setSelected(true);
        } else {
            checkActivo.setSelected(false);
        }
        
        if (p.getImpuesto1_aplic_compra() == 1) {
            check_imp1_Compra.setSelected(true);
        } else {
            check_imp1_Compra.setSelected(false);
        }
        
        if (p.getImpuesto1_aplic_venta() == 1) {
            check_imp1_Venta.setSelected(true);
        } else {
            check_imp1_Venta.setSelected(false);
        }
        
        if (p.getImpuesto2_aplic_venta() == 1) {
            check_imp2_Venta.setSelected(true);
        } else {
            check_imp2_Venta.setSelected(false);
        }
        
        if (p.getImpuesto2_aplic_compra() == 1) {
            check_imp2_Compra.setSelected(true);
        } else {
            check_imp2_Compra.setSelected(false);
        }
        
        if (p.getTipo_prod().equals("Fisico")) {
            rdoFIsico.setSelected(true);
            rdoOcacional.setSelected(false);
            rdoServicio.setSelected(false);
        } else if (p.getTipo_prod().equals("Ocasional")) {
            rdoFIsico.setSelected(false);
            rdoOcacional.setSelected(true);
            rdoServicio.setSelected(false);
        } else {
            rdoFIsico.setSelected(false);
            rdoOcacional.setSelected(false);
            rdoServicio.setSelected(true);
        }
        
        
        //Calculo del precio + impuestos en caso de que contengan
        Impuesto imp1 = ci.RetornarImpuestoxCod(p.getFk_impuesto1());
        OtroImp imp2 = cot.RetornarOtroImpuestoxCod(p.getFk_impuesto2());
        float preciop = p.getPrec_vent_prod();
        if(imp1 != null){
            preciop = preciop+(preciop*imp1.getValor()/100);
            System.err.println("precio producto : "+preciop);
            System.err.println("Valor imp 1 : "+imp1.getValor());
        }
        
        if(imp2 != null){
            preciop = preciop+(preciop*imp2.getValor()/100);
            System.err.println("Valor imp 2 : "+imp2.getValor());
        }
        
        txtPreciomasimp.setText(String.valueOf(preciop));
        
    }
    
    public void LimpiarForm() {     
        this.txtCodigo.setText("");
        this.txtNombreProducto.setText("");
        this.txtContiene_Compra.setText("0");
        txtSeCompra_x.setText("");
        txtSe_vende_x.setText("");
        txtContiene_Vende.setText("0");
        txtPrecio_neto_venta.setText("0");
        txtBodega.setText("");
        txtCategoriaform.setText("");
        txtSubCategoria.setText("");
        txtCostoproducto.setText("0");
        txtCostoEnUss.setText("0");
        txtUtilidadProducto.setText("0");
        txtPrecio_neto_venta.setText("0");
        txtPrecio_neto_venta.setText("0");
        txtImpuesto.setText("");
        txtOtroImp.setText("");
        checkActivo.setSelected(true);
        rdoFIsico.setSelected(true);
        rdoOcacional.setSelected(false);
        rdoServicio.setSelected(false);
        check_imp1_Compra.setSelected(false);
        check_imp1_Venta.setSelected(false);
        check_imp2_Compra.setSelected(false);
        check_imp2_Venta.setSelected(false);
        txtImpuesto.setText("");
        txtOtroImp.setText("");
        txtProveedor.setText("");
        txtPreciomasimp.setText("0");
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jInternalFrame1 = new javax.swing.JInternalFrame();
        jInternalFrame2 = new javax.swing.JInternalFrame();
        jInternalFrame3 = new javax.swing.JInternalFrame();
        jInternalFrame4 = new javax.swing.JInternalFrame();
        jInternalFrame5 = new javax.swing.JInternalFrame();
        grupoRadioTipoServicio = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtNombreProducto = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        txtProveedor = new javax.swing.JTextField();
        jButton7 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtPrecioVenta = new javax.swing.JTextField();
        txtUtilidadProducto = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtCostoproducto = new javax.swing.JTextField();
        txtCostoEnUss = new javax.swing.JTextField();
        jButton10 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtCategoriaform = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        txtSubCategoria = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        txtBodega = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtContiene_Vende = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtContiene_Compra = new javax.swing.JTextField();
        txtSe_vende_x = new javax.swing.JTextField();
        txtSeCompra_x = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        rdoFIsico = new javax.swing.JRadioButton();
        rdoServicio = new javax.swing.JRadioButton();
        rdoOcacional = new javax.swing.JRadioButton();
        jPanel11 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        checkActivo = new javax.swing.JCheckBox();
        jPanel9 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        check_imp1_Compra = new javax.swing.JCheckBox();
        check_imp1_Venta = new javax.swing.JCheckBox();
        jLabel10 = new javax.swing.JLabel();
        txtImpuesto = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        check_imp2_Compra = new javax.swing.JCheckBox();
        check_imp2_Venta = new javax.swing.JCheckBox();
        jLabel25 = new javax.swing.JLabel();
        txtOtroImp = new javax.swing.JTextField();
        jButton9 = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        txtPrecio_neto_venta = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        txtPreciomasimp = new javax.swing.JTextField();
        jPanel14 = new javax.swing.JPanel();
        btnAtras = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        btnLista = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();

        jInternalFrame1.setVisible(true);

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jInternalFrame2.setVisible(true);

        javax.swing.GroupLayout jInternalFrame2Layout = new javax.swing.GroupLayout(jInternalFrame2.getContentPane());
        jInternalFrame2.getContentPane().setLayout(jInternalFrame2Layout);
        jInternalFrame2Layout.setHorizontalGroup(
            jInternalFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jInternalFrame2Layout.setVerticalGroup(
            jInternalFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jInternalFrame3.setVisible(true);

        javax.swing.GroupLayout jInternalFrame3Layout = new javax.swing.GroupLayout(jInternalFrame3.getContentPane());
        jInternalFrame3.getContentPane().setLayout(jInternalFrame3Layout);
        jInternalFrame3Layout.setHorizontalGroup(
            jInternalFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jInternalFrame3Layout.setVerticalGroup(
            jInternalFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jInternalFrame4.setVisible(true);

        javax.swing.GroupLayout jInternalFrame4Layout = new javax.swing.GroupLayout(jInternalFrame4.getContentPane());
        jInternalFrame4.getContentPane().setLayout(jInternalFrame4Layout);
        jInternalFrame4Layout.setHorizontalGroup(
            jInternalFrame4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jInternalFrame4Layout.setVerticalGroup(
            jInternalFrame4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jInternalFrame5.setVisible(true);

        javax.swing.GroupLayout jInternalFrame5Layout = new javax.swing.GroupLayout(jInternalFrame5.getContentPane());
        jInternalFrame5.getContentPane().setLayout(jInternalFrame5Layout);
        jInternalFrame5Layout.setHorizontalGroup(
            jInternalFrame5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 337, Short.MAX_VALUE)
        );
        jInternalFrame5Layout.setVerticalGroup(
            jInternalFrame5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 235, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Informacion de Producto", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel1.setText("Codigo: ");

        txtCodigo.setText(" ");
        txtCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel2.setText("Nombre del Producto: ");

        txtNombreProducto.setText(" ");
        txtNombreProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreProductoActionPerformed(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel24.setText("Proveedor:");

        txtProveedor.setEditable(false);
        txtProveedor.setText(" ");
        txtProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProveedorActionPerformed(evt);
            }
        });

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aplicacionglobalfoodtrading/Iconos/lupa16x16.png"))); // NOI18N
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNombreProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel1)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel24)
                    .addComponent(txtNombreProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Precio de venta en $", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N
        jPanel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel3.setText("Precio $");

        jLabel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel4.setText("Utilidad %");

        txtPrecioVenta.setText("0.00");
        txtPrecioVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrecioVentaActionPerformed(evt);
            }
        });
        txtPrecioVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPrecioVentaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioVentaKeyTyped(evt);
            }
        });

        txtUtilidadProducto.setEditable(false);
        txtUtilidadProducto.setText("0.00");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(txtPrecioVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtUtilidadProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPrecioVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtUtilidadProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Costo del producto en $", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N

        jLabel5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel5.setText("Por Pieza");

        jLabel6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel6.setText("En US$");

        txtCostoproducto.setText("0.0");
        txtCostoproducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCostoproductoActionPerformed(evt);
            }
        });
        txtCostoproducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCostoproductoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCostoproductoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCostoproductoKeyTyped(evt);
            }
        });

        txtCostoEnUss.setEditable(false);
        txtCostoEnUss.setText(" 0.000");

        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aplicacionglobalfoodtrading/Iconos/Config_Editar_16_X_16.png"))); // NOI18N
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCostoproducto)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(txtCostoEnUss, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtCostoproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtCostoEnUss, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ubicacion del Producto", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N

        jLabel11.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel11.setText("Categoria");

        jLabel12.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel12.setText("Bodega");

        txtCategoriaform.setEditable(false);
        txtCategoriaform.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aplicacionglobalfoodtrading/Iconos/lupa16x16.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aplicacionglobalfoodtrading/Iconos/lupa16x16.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        txtSubCategoria.setEditable(false);
        txtSubCategoria.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aplicacionglobalfoodtrading/Iconos/lupa16x16.png"))); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel13.setText("Subcategoria");

        txtBodega.setEditable(false);
        txtBodega.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(10, 10, 10)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(txtBodega)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtCategoriaform)
                            .addComponent(txtSubCategoria))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11)
                        .addComponent(txtCategoriaform, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel13)
                        .addComponent(txtSubCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel12)
                        .addComponent(txtBodega, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(204, 0, 153))); // NOI18N

        jLabel7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel7.setText("Se vende Por");

        jLabel9.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel9.setText("Se compra por ");

        jLabel14.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel14.setText("Y contiene");

        txtContiene_Vende.setText("0");
        txtContiene_Vende.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtContiene_VendeKeyTyped(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel15.setText("Y contiene");

        txtContiene_Compra.setText("0");
        txtContiene_Compra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtContiene_CompraKeyTyped(evt);
            }
        });

        txtSe_vende_x.setEditable(false);
        txtSe_vende_x.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtSe_vende_x.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSe_vende_xActionPerformed(evt);
            }
        });

        txtSeCompra_x.setEditable(false);
        txtSeCompra_x.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aplicacionglobalfoodtrading/Iconos/lupa16x16.png"))); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aplicacionglobalfoodtrading/Iconos/lupa16x16.png"))); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel16.setText("Unid.(s)");

        jLabel21.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel21.setText("Unid.(s)");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel9)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtContiene_Vende, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                                    .addComponent(txtSe_vende_x)))
                            .addComponent(txtSeCompra_x, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtContiene_Compra)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(jLabel21))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtSe_vende_x, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel14)
                    .addComponent(txtContiene_Vende, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtSeCompra_x, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel15)
                    .addComponent(txtContiene_Compra, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tipo de Producto", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));

        jLabel8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel8.setText("Este producto es ");

        rdoFIsico.setBackground(new java.awt.Color(255, 255, 255));
        grupoRadioTipoServicio.add(rdoFIsico);
        rdoFIsico.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        rdoFIsico.setSelected(true);
        rdoFIsico.setText("Fisico");

        rdoServicio.setBackground(new java.awt.Color(255, 255, 255));
        grupoRadioTipoServicio.add(rdoServicio);
        rdoServicio.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        rdoServicio.setText("Servicio");
        rdoServicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoServicioActionPerformed(evt);
            }
        });

        rdoOcacional.setBackground(new java.awt.Color(255, 255, 255));
        grupoRadioTipoServicio.add(rdoOcacional);
        rdoOcacional.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        rdoOcacional.setText("Ocacional");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addComponent(rdoFIsico)
                .addGap(18, 18, 18)
                .addComponent(rdoServicio)
                .addGap(18, 18, 18)
                .addComponent(rdoOcacional)
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel8)
                    .addComponent(rdoFIsico)
                    .addComponent(rdoServicio)
                    .addComponent(rdoOcacional))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));

        jLabel22.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel22.setText("Producto esta activo");

        checkActivo.setBackground(new java.awt.Color(255, 255, 255));
        checkActivo.setSelected(true);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel22)
                .addGap(18, 18, 18)
                .addComponent(checkActivo)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(checkActivo)
                    .addComponent(jLabel22))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Impuestos ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));

        jLabel18.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel18.setText("Aplica en ");

        jLabel19.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel19.setText("Compras");

        jLabel20.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel20.setText("Ventas ");

        check_imp1_Compra.setBackground(new java.awt.Color(255, 255, 255));
        check_imp1_Compra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                check_imp1_CompraActionPerformed(evt);
            }
        });

        check_imp1_Venta.setBackground(new java.awt.Color(255, 255, 255));
        check_imp1_Venta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                check_imp1_VentaActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel10.setText("Impuesto");

        txtImpuesto.setEditable(false);
        txtImpuesto.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                txtImpuestoComponentAdded(evt);
            }
        });
        txtImpuesto.addHierarchyListener(new java.awt.event.HierarchyListener() {
            public void hierarchyChanged(java.awt.event.HierarchyEvent evt) {
                txtImpuestoHierarchyChanged(evt);
            }
        });
        txtImpuesto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtImpuestoActionPerformed(evt);
            }
        });

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aplicacionglobalfoodtrading/Iconos/lupa16x16.png"))); // NOI18N
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        check_imp2_Compra.setBackground(new java.awt.Color(255, 255, 255));
        check_imp2_Compra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                check_imp2_CompraActionPerformed(evt);
            }
        });

        check_imp2_Venta.setBackground(new java.awt.Color(255, 255, 255));
        check_imp2_Venta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                check_imp2_VentaActionPerformed(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel25.setText("Otro. imp");

        txtOtroImp.setEditable(false);

        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aplicacionglobalfoodtrading/Iconos/lupa16x16.png"))); // NOI18N
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel25, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(txtImpuesto))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(txtOtroImp)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jButton6)
                    .addComponent(jButton9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel19)
                    .addComponent(check_imp1_Compra)
                    .addComponent(check_imp2_Compra))
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel20)
                    .addComponent(check_imp1_Venta)
                    .addComponent(check_imp2_Venta)))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel18)
                .addGap(25, 25, 25))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18)
                .addGap(3, 3, 3)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel19)
                    .addComponent(jLabel20))
                .addGap(4, 4, 4)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtImpuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6)
                    .addComponent(check_imp1_Compra)
                    .addComponent(check_imp1_Venta)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(check_imp2_Compra)
                    .addComponent(check_imp2_Venta)
                    .addComponent(jLabel25)
                    .addComponent(txtOtroImp, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton9))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 8, Short.MAX_VALUE))
        );

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Precio Producto", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N

        jLabel17.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel17.setText("Precio");

        txtPrecio_neto_venta.setEditable(false);
        txtPrecio_neto_venta.setText("0.0");

        jLabel23.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel23.setText("Precio + impuesto(s)");

        txtPreciomasimp.setEditable(false);
        txtPreciomasimp.setText("0.0");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel23, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtPreciomasimp, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPrecio_neto_venta, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txtPrecio_neto_venta, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(txtPreciomasimp, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
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
                .addGap(175, 175, 175)
                .addComponent(btnGuardar)
                .addGap(72, 72, 72)
                .addComponent(btnBuscar)
                .addGap(61, 61, 61)
                .addComponent(btnEditar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnLista)
                .addGap(70, 70, 70)
                .addComponent(btnLimpiar)
                .addGap(55, 55, 55)
                .addComponent(jButton8)
                .addGap(39, 39, 39))
            .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel14Layout.createSequentialGroup()
                    .addGap(39, 39, 39)
                    .addComponent(btnAtras)
                    .addContainerGap(838, Short.MAX_VALUE)))
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
                    .addComponent(btnLimpiar, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel14Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(btnAtras, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoActionPerformed

    private void txtPrecioVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecioVentaActionPerformed
        // TODO add your handling code here:
        // txtPrecio_neto_venta.setText(txtPrecioVenta.getText());

    }//GEN-LAST:event_txtPrecioVentaActionPerformed

    private void txtCostoproductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCostoproductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCostoproductoActionPerformed

    private void check_imp1_VentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_check_imp1_VentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_check_imp1_VentaActionPerformed

    private void rdoServicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoServicioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoServicioActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        Categoria_Producto cm = new Categoria_Producto();
        cm.setVisible(true);
        String cat = cm.txtCategoria.getText();
        this.txtCategoriaform.setText(cat);

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        Subcategoria_Menu sc = new Subcategoria_Menu();
        sc.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        Bodega_Menu bm = new Bodega_Menu();
        bm.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        Unidad_Venta uv = new Unidad_Venta();
        uv.setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        Unidad_Compra uc = new Unidad_Compra();
        uc.setVisible(true);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void txtSe_vende_xActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSe_vende_xActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSe_vende_xActionPerformed

    private void check_imp1_CompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_check_imp1_CompraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_check_imp1_CompraActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        Impuesto_Producto ip = new Impuesto_Producto();
        ip.setVisible(true);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void txtCostoproductoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCostoproductoKeyReleased
        // TODO add your handling code here:
        DecimalFormat df = new DecimalFormat("##.#");
        float ganancia;
        float venta;
        float compra;
        float utilidad;
        
        char c = evt.getKeyChar();
        
        if (!Character.isDigit(c)) {
            getToolkit().beep();
            
            evt.consume();
            
        } else {
            if (!txtCostoproducto.getText().isEmpty()) {
                compra = Float.parseFloat(this.txtCostoproducto.getText());
                venta = Float.parseFloat(this.txtPrecioVenta.getText());
                ganancia = (venta - compra);
                utilidad = (ganancia / compra) * 100;
                this.txtUtilidadProducto.setForeground(Color.black);
                if (utilidad < 0) {
                    df.format(utilidad);
                    this.txtUtilidadProducto.setForeground(Color.red);
                    this.txtUtilidadProducto.setText(String.valueOf(df.format(utilidad)));
                } else {
                    df.format(utilidad);
                    this.txtUtilidadProducto.setText(String.valueOf(df.format(utilidad)));
                }
            }
        }
        
        if (Character.isLetter(c)) {
            getToolkit().beep();
            
            evt.consume();
            
            JOptionPane.showMessageDialog(null, "Ingrese Solo numeros");
            
        } else {
            try {
                float val = Float.parseFloat(this.txtCostoproducto.getText());
                float dol = cd.RetornarValorDolar();
                float totalUs = val / dol;
                System.out.println("Sin formato : " + val + "/" + dol + " = " + totalUs);
                System.err.println(df3.format(totalUs));
                this.txtCostoEnUss.setText(String.valueOf(df3.format(totalUs)));
            } catch (java.lang.NumberFormatException ex) {
                // JOptionPane.showMessageDialog(null, ex);
            }
        }
        

    }//GEN-LAST:event_txtCostoproductoKeyReleased

    private void txtPrecioVentaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioVentaKeyReleased
        // TODO add your handling code here:

        try {
            float ganancia;
            float venta;
            float compra;
            float utilidad;
            DecimalFormat df = new DecimalFormat("##.#");
            if (!txtCostoproducto.getText().isEmpty()) {
                compra = Float.parseFloat(this.txtCostoproducto.getText());
                venta = Float.parseFloat(this.txtPrecioVenta.getText());
                ganancia = (venta - compra);
                utilidad = (ganancia / compra) * 100;
                this.txtUtilidadProducto.setForeground(Color.black);
                if (utilidad < 0) {
                    df.format(utilidad);
                    this.txtUtilidadProducto.setForeground(Color.red);
                    this.txtUtilidadProducto.setText(String.valueOf(df.format(utilidad)));
                } else {
                    
                    this.txtUtilidadProducto.setText(String.valueOf(df.format(utilidad)));
                }
            }
        } catch (java.lang.NumberFormatException ex) {
            System.out.println(ex.getMessage());
        }
    }//GEN-LAST:event_txtPrecioVentaKeyReleased

    private void txtCostoproductoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCostoproductoKeyTyped
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtCostoproductoKeyTyped

    private void txtPrecioVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioVentaKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        
        if (Character.isLetter(c)) {
            getToolkit().beep();
            
            evt.consume();
            
            JOptionPane.showMessageDialog(null, "Ingrese solo numeros");
            
        } else {
            
            String sald = this.txtPrecioVenta.getText();
            sald = sald + c;
            if (txtPrecioVenta.getText().isEmpty()) {
                txtPrecio_neto_venta.setText("0.0");
            } else {
                txtPrecio_neto_venta.setText(sald);
            }
            
        }

//          if(txtPrecioVenta.getText().isEmpty()){
//             txtPrecioVenta.setText("0"); 
//          }

    }//GEN-LAST:event_txtPrecioVentaKeyTyped

    private void txtNombreProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreProductoActionPerformed

    private void txtProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProveedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProveedorActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        Lista_Proveedores_Productos lpp = new Lista_Proveedores_Productos();
        lpp.setVisible(true);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void btnAtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtrasActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnAtrasActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // TODO add your handling code here:
        if (txtCodigo.getText().isEmpty() || txtBodega.getText().isEmpty() || txtCategoriaform.getText().isEmpty() || txtSubCategoria.getText().isEmpty()
                || txtPrecioVenta.getText().isEmpty() || txtProveedor.getText().isEmpty() || txtPrecioVenta.getText().isEmpty() || txtImpuesto.getText().isEmpty() || txtPrecioVenta.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese todos los campos de Informacion del Producto");
        } else {Producto p = new Producto();
            p.setCod_prod(txtCodigo.getText());
            p.setNombre_prod(txtNombreProducto.getText());
            String bode = cb.RetornarCodigoBodegaxNom(txtBodega.getText());
            p.setFk_bodega(bode);
            String categ = cc.ObtenerCodigoCategoriaxNombre(txtCategoriaform.getText());
            p.setFk_categoria(categ);
            String subca = cs.ObtenerCodigoSubcategoriaxNombre(txtSubCategoria.getText());
            p.setFk_subcategoria(subca);
            String cod = cprov.CodigoProveedorXNombre(txtProveedor.getText());
            p.setFk_proveedor(cod);
            p.setPrec_cost_prod(Float.parseFloat(txtCostoproducto.getText()));
            String dolar = txtCostoEnUss.getText();
            dolar = dolar.replace(",", ".");
            p.setPrec_cost_prod_dolar(Float.parseFloat(dolar));
            p.setPrec_vent_prod(Float.parseFloat(txtPrecioVenta.getText()));
            String utili = txtUtilidadProducto.getText().replace(',', '.');
            p.setUtilidad_prod(Float.parseFloat(utili));
            p.setSe_vende_x(txtSe_vende_x.getText());
            p.setVende_contiene(Integer.parseInt(txtContiene_Vende.getText()));
            p.setSe_Compra_x(txtSeCompra_x.getText());
            p.setCompra_contiene(Integer.parseInt(txtContiene_Compra.getText()));
            p.setFk_impuesto1(txtImpuesto.getText());
            p.setPrecio_neto_prod(Float.parseFloat(txtPrecio_neto_venta.getText()));
            p.setPrecio_mas_impuesto(Float.parseFloat(txtPreciomasimp.getText()));
            
            if (check_imp1_Compra.isSelected()) {
                p.setImpuesto1_aplic_compra(1);
            } else {
                p.setImpuesto1_aplic_compra(0);
            }
            
            if (check_imp1_Venta.isSelected()) {
                p.setImpuesto1_aplic_venta(1);
            } else {
                p.setImpuesto1_aplic_venta(0);
            }
            
            if (txtOtroImp.getText().isEmpty()) {
                p.setFk_impuesto2("Ninguno");
                p.setImpuesto2_aplic_compra(2);
                p.setImpuesto2_aplic_venta(2);
            } else {
                
                p.setFk_impuesto2(txtOtroImp.getText());
                
                if (check_imp2_Compra.isSelected()) {
                    p.setImpuesto2_aplic_compra(1);
                } else {
                    p.setImpuesto2_aplic_compra(0);
                }
                
                if (check_imp2_Venta.isSelected()) {
                    p.setImpuesto2_aplic_venta(1);
                } else {
                    p.setImpuesto2_aplic_venta(0);
                }
                
            }
            
            if (rdoFIsico.isSelected()) {
                p.setTipo_prod("Fisico");
            } else if (rdoOcacional.isSelected()) {
                p.setTipo_prod("Ocasional");
            } else {
                p.setTipo_prod("Servicio");
            }
            
            if (checkActivo.isSelected()) {
                p.setEsta_activo(1);
            } else {
                p.setEsta_activo(0);
            }
            
            if (cp.ModificarProducto(p) == 1) {
                LimpiarForm();
                JOptionPane.showMessageDialog(null, "Se modifico exitoso del producto");
            } else {
                JOptionPane.showMessageDialog(null, "Error al modificar el producto");
            }
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        if (txtCodigo.getText().isEmpty() || txtBodega.getText().isEmpty() || txtCategoriaform.getText().isEmpty() || txtSubCategoria.getText().isEmpty()
                || txtPrecioVenta.getText().isEmpty() || txtProveedor.getText().isEmpty() || txtPrecioVenta.getText().isEmpty() || txtImpuesto.getText().isEmpty() || txtPrecioVenta.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese todos los campos de Informacion del Producto");
        } else {
            Producto p = new Producto();
            p.setCod_prod(txtCodigo.getText());
            p.setNombre_prod(txtNombreProducto.getText());
            String bode = cb.RetornarCodigoBodegaxNom(txtBodega.getText());
            p.setFk_bodega(bode);
            String categ = cc.ObtenerCodigoCategoriaxNombre(txtCategoriaform.getText());
            p.setFk_categoria(categ);
            String subca = cs.ObtenerCodigoSubcategoriaxNombre(txtSubCategoria.getText());
            p.setFk_subcategoria(subca);
            String cod = cprov.CodigoProveedorXNombre(txtProveedor.getText());
            p.setFk_proveedor(cod);
            p.setPrec_cost_prod(Float.parseFloat(txtCostoproducto.getText()));
            String dolar = txtCostoEnUss.getText();
            dolar = dolar.replace(",", ".");
            p.setPrec_cost_prod_dolar(Float.parseFloat(dolar));
            p.setPrec_vent_prod(Float.parseFloat(txtPrecioVenta.getText()));
            String utili = txtUtilidadProducto.getText().replace(',', '.');
            p.setUtilidad_prod(Float.parseFloat(utili));
            p.setSe_vende_x(txtSe_vende_x.getText());
            p.setVende_contiene(Integer.parseInt(txtContiene_Vende.getText()));
            p.setSe_Compra_x(txtSeCompra_x.getText());
            p.setCompra_contiene(Integer.parseInt(txtContiene_Compra.getText()));
            p.setFk_impuesto1(txtImpuesto.getText());
            p.setPrecio_neto_prod(Float.parseFloat(txtPrecio_neto_venta.getText()));
            p.setPrecio_mas_impuesto(Float.parseFloat(txtPreciomasimp.getText()));
            
            if (check_imp1_Compra.isSelected()) {
                p.setImpuesto1_aplic_compra(1);
            } else {
                p.setImpuesto1_aplic_compra(0);
            }
            
            if (check_imp1_Venta.isSelected()) {
                p.setImpuesto1_aplic_venta(1);
            } else {
                p.setImpuesto1_aplic_venta(0);
            }
            
            if (txtOtroImp.getText().isEmpty()) {
                p.setFk_impuesto2("Ninguno");
                p.setImpuesto2_aplic_compra(2);
                p.setImpuesto2_aplic_venta(2);
            } else {
                
                p.setFk_impuesto2(txtOtroImp.getText());
                
                if (check_imp2_Compra.isSelected()) {
                    p.setImpuesto2_aplic_compra(1);
                } else {
                    p.setImpuesto2_aplic_compra(0);
                }
                
                if (check_imp2_Venta.isSelected()) {
                    p.setImpuesto2_aplic_venta(1);
                } else {
                    p.setImpuesto2_aplic_venta(0);
                }
                
            }
            
            if (rdoFIsico.isSelected()) {
                p.setTipo_prod("Fisico");
            } else if (rdoOcacional.isSelected()) {
                p.setTipo_prod("Ocasional");
            } else {
                p.setTipo_prod("Servicio");
            }
            
            if (checkActivo.isSelected()) {
                p.setEsta_activo(1);
            } else {
                p.setEsta_activo(0);
            }
            
            if (cp.RegistrarProducto(p) == 1) {
                LimpiarForm();
                JOptionPane.showMessageDialog(null, "Registro exitoso del producto");
            } else {
                JOptionPane.showMessageDialog(null, "Error en el registro del producto");
            }
        }

    }//GEN-LAST:event_btnGuardarActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
            if(txtCodigo.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Debe ingresar un codigo");
            }else{
                
                int ex = cp.EliminarProducto(txtCodigo.getText());
                
                if(ex==1){
                    LimpiarForm();
                    JOptionPane.showMessageDialog(null,"Se elimino correctamente el producto !!!");
                }else{
                    JOptionPane.showMessageDialog(null,"No se elimino el producto");
                }
                
            }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void btnListaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListaActionPerformed
        // TODO add your handling code here:
        Lista_Productos lp = new Lista_Productos();
        lp.setVisible(true);
    }//GEN-LAST:event_btnListaActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:


    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        // TODO add your handling code here:
        LimpiarForm();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void check_imp2_CompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_check_imp2_CompraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_check_imp2_CompraActionPerformed

    private void check_imp2_VentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_check_imp2_VentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_check_imp2_VentaActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        OtroImp_Menu otm = new OtroImp_Menu();
        otm.setVisible(true);
    }//GEN-LAST:event_jButton9ActionPerformed

    private void txtContiene_VendeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtContiene_VendeKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        
        if (Character.isLetter(c)) {
            getToolkit().beep();
            
            evt.consume();
            
            JOptionPane.showMessageDialog(null, "Ingrese solo numeros");
            
        }
    }//GEN-LAST:event_txtContiene_VendeKeyTyped

    private void txtContiene_CompraKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtContiene_CompraKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        
        if (Character.isLetter(c)) {
            getToolkit().beep();
            
            evt.consume();
            
            JOptionPane.showMessageDialog(null, "Ingrese solo numeros");
            
        }
    }//GEN-LAST:event_txtContiene_CompraKeyTyped

    private void txtUnidad_CompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUnidad_CompraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUnidad_CompraActionPerformed

    private void txtImpuestoHierarchyChanged(java.awt.event.HierarchyEvent evt) {//GEN-FIRST:event_txtImpuestoHierarchyChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_txtImpuestoHierarchyChanged

    private void txtImpuestoComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_txtImpuestoComponentAdded
        // TODO add your handling code here:

        String abre = txtImpuesto.getText();
        
        float imp = ci.RetornarValorxAbrev(abre);
        
        float precimp;
        
        float precnet = Float.parseFloat(txtPrecio_neto_venta.getText());
        
        precimp = precnet + (precnet * imp / 100);
        
        this.txtPreciomasimp.setText(String.valueOf(precimp));

    }//GEN-LAST:event_txtImpuestoComponentAdded

    private void txtImpuestoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtImpuestoActionPerformed
        // TODO add your handling code here:
        String abre = txtImpuesto.getText();
        
        float imp = ci.RetornarValorxAbrev(abre);
        
        float precimp;
        
        float precnet = Float.parseFloat(txtPrecio_neto_venta.getText());
        
        precimp = precnet + (precnet * imp / 100);
        
        this.txtPreciomasimp.setText(String.valueOf(precimp));
    }//GEN-LAST:event_txtImpuestoActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        Config_Valor_Dolar cvd = new Config_Valor_Dolar();
        cvd.setVisible(true);
    }//GEN-LAST:event_jButton10ActionPerformed

    private void txtCostoproductoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCostoproductoKeyPressed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtCostoproductoKeyPressed

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
            java.util.logging.Logger.getLogger(RegistroProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegistroProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegistroProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegistroProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RegistroProductos().setVisible(true);
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
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox checkActivo;
    private javax.swing.JCheckBox check_imp1_Compra;
    private javax.swing.JCheckBox check_imp1_Venta;
    private javax.swing.JCheckBox check_imp2_Compra;
    private javax.swing.JCheckBox check_imp2_Venta;
    private javax.swing.ButtonGroup grupoRadioTipoServicio;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JInternalFrame jInternalFrame2;
    private javax.swing.JInternalFrame jInternalFrame3;
    private javax.swing.JInternalFrame jInternalFrame4;
    private javax.swing.JInternalFrame jInternalFrame5;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JRadioButton rdoFIsico;
    private javax.swing.JRadioButton rdoOcacional;
    private javax.swing.JRadioButton rdoServicio;
    public static javax.swing.JTextField txtBodega;
    public static javax.swing.JTextField txtCategoriaform;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtContiene_Compra;
    private javax.swing.JTextField txtContiene_Vende;
    private javax.swing.JTextField txtCostoEnUss;
    private javax.swing.JTextField txtCostoproducto;
    public static javax.swing.JTextField txtImpuesto;
    private javax.swing.JTextField txtNombreProducto;
    public static javax.swing.JTextField txtOtroImp;
    private javax.swing.JTextField txtPrecioVenta;
    public static javax.swing.JTextField txtPrecio_neto_venta;
    public static javax.swing.JTextField txtPreciomasimp;
    public static javax.swing.JTextField txtProveedor;
    public static javax.swing.JTextField txtSeCompra_x;
    public static javax.swing.JTextField txtSe_vende_x;
    public static javax.swing.JTextField txtSubCategoria;
    private javax.swing.JTextField txtUtilidadProducto;
    // End of variables declaration//GEN-END:variables
}
