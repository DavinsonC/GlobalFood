/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package globalfoot.Vista;

import aplicacionglobalfoodtrading.Controlador.Controlador_Empleado_Directo;
import aplicacionglobalfoodtrading.Modelo.Cliente;
import aplicacionglobalfoodtrading.Modelo.Empleado_Directo;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Lista_Empleados_Directos extends javax.swing.JFrame {

    Controlador_Empleado_Directo ced = new Controlador_Empleado_Directo();
    DefaultTableModel modelo = new DefaultTableModel();

    public Lista_Empleados_Directos() {
//        this.setDefaultLookAndFeelDecorated(true);
//        SubstanceLookAndFeel.setSkin("org.jvnet.substance.skin.CremeCoffeeSkin");
        initComponents();
        CrearTabla();
        CargarTablaGeneral();
    }

    public void CrearTabla() {
        String titulos[] = {"Tipo ID", "Identificacion", "Nombres", "Apellidos", "Genero", "Pais", "Ciudad", "Direccion", "Celular", "E-mail", "Cargo", "Salario"};
        modelo.setColumnIdentifiers(titulos);
        TablaEmpleadosDirectos.setModel(modelo);
    }

    public void CargarTablaGeneral() {
        ArrayList<Empleado_Directo> led = new ArrayList();
        try {
            LimpiarTabla();
            led = this.ced.ListaEmpleadosDirectos();

//                 for(Empleado_Directo d : led){
//                     System.out.println("Cedula : "+d.getIdentificacion());
//                 }
            if (led.isEmpty()) {
                System.out.println("Esta vacio el arraylist");
            }

            for (Empleado_Directo e : led) {
                String datos[] = {e.getTipoid(), e.getIdentificacion(), e.getNombre(), e.getApellido(), e.getGenero(), e.getPais(), e.getCiudad(), e.getDireccion(), e.getTel_movil(), e.getEmail(), e.getCargo(), String.valueOf(e.getSalario())};
                modelo.addRow(datos);
            }
        } catch (java.lang.NullPointerException ex) {
            JOptionPane.showMessageDialog(null, "Error de carga de datos en tabla " + ex.getMessage());
        }
    }

    public void CargarTablaxIdentificacion() {
        ArrayList<Empleado_Directo> led = new ArrayList();
        try {
            LimpiarTabla();
            led = this.ced.ListaEmpleadosDirectosOrderByIdentificacion(this.txtbuscar.getText());

//                 for(Empleado_Directo d : led){
//                     System.out.println("Cedula : "+d.getIdentificacion());
//                 }
            for (Empleado_Directo e : led) {
                String datos[] = {e.getTipoid(), e.getIdentificacion(), e.getNombre(), e.getApellido(), e.getGenero(), e.getPais(), e.getCiudad(), e.getDireccion(), e.getTel_movil(), e.getEmail(), e.getCargo(), String.valueOf(e.getSalario())};
                modelo.addRow(datos);
            }
        } catch (java.lang.NullPointerException ex) {
            JOptionPane.showMessageDialog(null, "Error de carga de datos en tabla " + ex.getMessage());
        }
    }

    public void CargarTablaxPais() {
        ArrayList<Empleado_Directo> led = new ArrayList();
        try {
            LimpiarTabla();
            led = this.ced.ListaEmpleadosDirectosOrderByPais(this.txtbuscar.getText());

//                 for(Empleado_Directo d : led){
//                     System.out.println("Cedula : "+d.getIdentificacion());
//                 }
            for (Empleado_Directo e : led) {
                String datos[] = {e.getTipoid(), e.getIdentificacion(), e.getNombre(), e.getApellido(), e.getGenero(), e.getPais(), e.getCiudad(), e.getDireccion(), e.getTel_movil(), e.getEmail(), e.getCargo(), String.valueOf(e.getSalario())};
                modelo.addRow(datos);
            }
        } catch (java.lang.NullPointerException ex) {
            JOptionPane.showMessageDialog(null, "Error de carga de datos en tabla " + ex.getMessage());
        }
    }

    public void CargarTablaxCargo() {
        ArrayList<Empleado_Directo> led = new ArrayList();
        try {
            LimpiarTabla();
            led = this.ced.ListaEmpleadosDirectosOrderByCargo(this.txtbuscar.getText());

//                 for(Empleado_Directo d : led){
//                     System.out.println("Cedula : "+d.getIdentificacion());
//                 }
            for (Empleado_Directo e : led) {
                String datos[] = {e.getTipoid(), e.getIdentificacion(), e.getNombre(), e.getApellido(), e.getGenero(), e.getPais(), e.getCiudad(), e.getDireccion(), e.getTel_movil(), e.getEmail(), e.getCargo(), String.valueOf(e.getSalario())};
                modelo.addRow(datos);
            }
        } catch (java.lang.NullPointerException ex) {
            JOptionPane.showMessageDialog(null, "Error de carga de datos en tabla " + ex.getMessage());
        }
    }

    public void LimpiarTabla() {
        int filas = TablaEmpleadosDirectos.getRowCount();
        for (int i = 0; filas > i; i++) {
            modelo.removeRow(0);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaEmpleadosDirectos = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        CboOpcion = new javax.swing.JComboBox<>();
        btnBuscar = new javax.swing.JButton();
        txtbuscar = new javax.swing.JTextField();

        jMenuItem1.setText("Ver Informacion Completa");
        jMenuItem1.setToolTipText("");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Listado de Empleados Directos");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        TablaEmpleadosDirectos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        TablaEmpleadosDirectos.setComponentPopupMenu(jPopupMenu1);
        TablaEmpleadosDirectos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaEmpleadosDirectosMouseClicked(evt);
            }
        });
        TablaEmpleadosDirectos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TablaEmpleadosDirectosKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(TablaEmpleadosDirectos);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aplicacionglobalfoodtrading/Iconos/Previous32x32.png"))); // NOI18N
        jButton1.setToolTipText("Volver a la pagina anterior");
        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);
        jButton1.setFocusPainted(false);
        jButton1.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/aplicacionglobalfoodtrading/Iconos/Previous48x48.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel1.setText("LISTADO DE EMPLEADOS DIRECTOS");

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aplicacionglobalfoodtrading/Iconos/Informacion_Adcional_48x48.png"))); // NOI18N
        jButton2.setToolTipText("Informacion mas detallada");
        jButton2.setBorderPainted(false);
        jButton2.setContentAreaFilled(false);
        jButton2.setFocusPainted(false);
        jButton2.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/aplicacionglobalfoodtrading/Iconos/Informacion_Adcional_64x64.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        CboOpcion.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        CboOpcion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cedula", "Apellido", "Pais", "Cargo" }));
        CboOpcion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CboOpcionActionPerformed(evt);
            }
        });

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aplicacionglobalfoodtrading/Iconos/1477811446_Search32x32.png"))); // NOI18N
        btnBuscar.setBorderPainted(false);
        btnBuscar.setContentAreaFilled(false);
        btnBuscar.setFocusPainted(false);
        btnBuscar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/aplicacionglobalfoodtrading/Iconos/1477811460_Search48x48.png"))); // NOI18N
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        txtbuscar.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtbuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscarKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(52, Short.MAX_VALUE)
                .addComponent(txtbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(CboOpcion, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(CboOpcion, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE))
                .addGap(13, 13, 13))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(202, 202, 202)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(115, 115, 115)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(172, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TablaEmpleadosDirectosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TablaEmpleadosDirectosKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TablaEmpleadosDirectosKeyPressed

    private void TablaEmpleadosDirectosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaEmpleadosDirectosMouseClicked
        if (evt.getClickCount() == 1) {
            System.out.println("Se ha hecho un click");
        }
        if (evt.getClickCount() == 2) {
            System.out.println("Se ha hecho doble click");
        }
    }//GEN-LAST:event_TablaEmpleadosDirectosMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        Empleado_Directo ed = new Empleado_Directo();
        int f;
        int c = 1;
        if (TablaEmpleadosDirectos.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una fila con registro valido");
        } else {
            f = TablaEmpleadosDirectos.getSelectedRow();
            String id = String.valueOf(TablaEmpleadosDirectos.getValueAt(f, c));
            ed = ced.Empleadoxid(id);
            Registro_Empleados_Directos re = new Registro_Empleados_Directos(ed);
            re.setVisible(true);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtbuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscarKeyReleased
        // TODO add your handling code here:

        if (CboOpcion.getSelectedItem().toString().equals("Pais")) {
            CargarTablaxPais();
        }
        if (CboOpcion.getSelectedItem().toString().equals("Apellido")) {
            CargarTablaGeneral();
        }
        if (CboOpcion.getSelectedItem().toString().equals("Identificacion")) {
            CargarTablaxIdentificacion();
        }
        if (CboOpcion.getSelectedItem().toString().equals("Cargo")) {
            CargarTablaxCargo();
        }


    }//GEN-LAST:event_txtbuscarKeyReleased

    private void CboOpcionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CboOpcionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CboOpcionActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        
        if(TablaEmpleadosDirectos.getSelectedRow()<0){
            JOptionPane.showMessageDialog(null, "No hay Empleado seleccionado");
        }else{
            int f = TablaEmpleadosDirectos.getSelectedRow();
            String cedula = TablaEmpleadosDirectos.getValueAt(f,1).toString();
            
            Empleado_Directo ed = ced.Empleadoxid(cedula);
            
            Registro_Empleados_Directos reg = new Registro_Empleados_Directos(ed);
            reg.setVisible(true);
            
        }
        
        
    }//GEN-LAST:event_jMenuItem1ActionPerformed

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
                if ("windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Lista_Empleados_Directos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Lista_Empleados_Directos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Lista_Empleados_Directos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Lista_Empleados_Directos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Lista_Empleados_Directos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> CboOpcion;
    private javax.swing.JTable TablaEmpleadosDirectos;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtbuscar;
    // End of variables declaration//GEN-END:variables
}
