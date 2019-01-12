/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package globalfoot.Vista;

import aplicacionglobalfoodtrading.Controlador.ControladorSubcategoria;
import aplicacionglobalfoodtrading.Modelo.Categoria;
import aplicacionglobalfoodtrading.Modelo.Subcategoria;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Ricardo Andres
 */
public class Subcategoria_Menu extends javax.swing.JFrame {

    DefaultTableModel modelo = new DefaultTableModel();
    ControladorSubcategoria cs = new ControladorSubcategoria();

    public Subcategoria_Menu() {
        initComponents();
        CrearTabla();
        CargarTabla();
    }

    public void CrearTabla() {
        String titulos[] = {"Codigo", "Subcategoria"};
        modelo.setColumnIdentifiers(titulos);
        TablaSubcategorias.setModel(modelo);
    }

    public void LimpiarTabla() {
        int filas = TablaSubcategorias.getRowCount();
        for (int i = 0; filas > i; i++) {
            modelo.removeRow(0);
        }
    }

    public void CargarTabla() {

        ArrayList<Subcategoria> ls = new ArrayList();
        LimpiarTabla();
        ls = cs.ListadoSubcategorias();

        for (Subcategoria s : ls) {
            String datos[] = {s.getCodigo(), s.getNombre()};
            modelo.addRow(datos);
        }

    }

    public void LimpiarForm() {
        this.txtfk_categoria.setText("");
        this.txtCodigo.setText("");
        txtNombre.setText("");
    }

    public void CargarForm(Subcategoria s) {
        this.txtCodigo.setText(s.getCodigo());
        this.txtfk_categoria.setText(s.getFk_categoria());
        txtNombre.setText(s.getNombre());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PopUpTablaSubCategorias = new javax.swing.JPopupMenu();
        EnviarPopUp = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtfk_categoria = new javax.swing.JTextField();
        jButton7 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        TablaSubcategorias = new javax.swing.JTable();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();

        EnviarPopUp.setText("Copiar");
        EnviarPopUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EnviarPopUpActionPerformed(evt);
            }
        });
        PopUpTablaSubCategorias.add(EnviarPopUp);

        jMenuItem1.setText("Seleccionar Subcategoria");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        PopUpTablaSubCategorias.add(jMenuItem1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Gestion de Subcategorias", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel1.setText("Codigo");

        jLabel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel2.setText("Nombre");

        jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel3.setText("Categoria");

        txtfk_categoria.setEditable(false);
        txtfk_categoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtfk_categoriaActionPerformed(evt);
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
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
                    .addComponent(txtNombre)
                    .addComponent(txtfk_categoria))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel3)
                    .addComponent(txtfk_categoria, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel1)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel2)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aplicacionglobalfoodtrading/Iconos/save32x32.png"))); // NOI18N
        jButton1.setToolTipText("Registrar Nueva Categoria");
        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);
        jButton1.setFocusPainted(false);
        jButton1.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/aplicacionglobalfoodtrading/Iconos/save48x48.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aplicacionglobalfoodtrading/Iconos/1477811446_Search32x32.png"))); // NOI18N
        jButton2.setBorderPainted(false);
        jButton2.setContentAreaFilled(false);
        jButton2.setFocusPainted(false);
        jButton2.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/aplicacionglobalfoodtrading/Iconos/1477811460_Search48x48.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aplicacionglobalfoodtrading/Iconos/1479450025_edit-file32x32.png"))); // NOI18N
        jButton3.setBorderPainted(false);
        jButton3.setContentAreaFilled(false);
        jButton3.setFocusPainted(false);
        jButton3.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/aplicacionglobalfoodtrading/Iconos/1479450710_edit-file48x48.png"))); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aplicacionglobalfoodtrading/Iconos/delete32x32.png"))); // NOI18N
        jButton4.setBorderPainted(false);
        jButton4.setContentAreaFilled(false);
        jButton4.setFocusPainted(false);
        jButton4.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/aplicacionglobalfoodtrading/Iconos/delete48x48.png"))); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        TablaSubcategorias.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        TablaSubcategorias.setComponentPopupMenu(PopUpTablaSubCategorias);
        TablaSubcategorias.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaSubcategoriasMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(TablaSubcategorias);

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aplicacionglobalfoodtrading/Iconos/Previous32x32.png"))); // NOI18N
        jButton5.setBorderPainted(false);
        jButton5.setContentAreaFilled(false);
        jButton5.setFocusPainted(false);
        jButton5.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/aplicacionglobalfoodtrading/Iconos/Previous48x48.png"))); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aplicacionglobalfoodtrading/Iconos/añadir_reg_32x32.png"))); // NOI18N
        jButton6.setBorderPainted(false);
        jButton6.setContentAreaFilled(false);
        jButton6.setFocusPainted(false);
        jButton6.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/aplicacionglobalfoodtrading/Iconos/añadir_Reg_48x48.png"))); // NOI18N
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 463, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:

        if (txtCodigo.getText().isEmpty() || txtNombre.getText().isEmpty() || txtfk_categoria.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe Ingresar todos los campos de informacion");
        } else {
            Subcategoria s = new Subcategoria();
            s.setNombre(txtNombre.getText());
            s.setCodigo(txtCodigo.getText());
            s.setFk_categoria(txtfk_categoria.getText());
            int ex = cs.RegistrarSubcategoria(s);

            if (ex == 0) {
                JOptionPane.showMessageDialog(null, "Error al registrar Subcategoria");
            } else {
                LimpiarForm();
                CargarTabla();
                JOptionPane.showMessageDialog(null, "Subcategoria Registrada exitosamente !!!!");
            }

        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:

        if (txtCodigo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar el codigo de una categoria");
        } else {
            Subcategoria s = null;
            s = cs.RetornarSubcategoriaxCod(txtCodigo.getText());
            if (s == null) {
                JOptionPane.showMessageDialog(null, "No se encontro la Subcategoria  a buscar");
            } else {
                CargarForm(s);
                JOptionPane.showMessageDialog(null, "Subcategoria Encontrada");
            }
        }

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:

        Subcategoria s = new Subcategoria();
        if (txtCodigo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar el codigo de una Subcategoria");
        } else {
            s.setFk_categoria(this.txtfk_categoria.getText());
            s.setNombre(txtNombre.getText());
            s.setCodigo(txtCodigo.getText());
            if (cs.ModificarSubcategoria(s) == 1) {
                LimpiarForm();
                CargarTabla();
                JOptionPane.showMessageDialog(null, "Se modifico correctamente la Subcategoria !!!!");
            } else {
                JOptionPane.showMessageDialog(null, "Error al modificar Subcategoria !!!!");
            }
        }

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        if (txtCodigo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar el codigo de una Subcategoria");
        } else {
            int ex = cs.EliminarSubcategoria(txtCodigo.getText());
            if (ex == 0) {
                JOptionPane.showMessageDialog(null, "No se encontro la Subcategoria  a buscar");
            } else {
                LimpiarForm();
                CargarTabla();
                JOptionPane.showMessageDialog(null, "Subcategoria Eliminada");
            }
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void TablaSubcategoriasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaSubcategoriasMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() >= 2) {
            System.out.println("Ha presionado 2 veces la casilla de la tabla");
        }

    }//GEN-LAST:event_TablaSubcategoriasMouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:

        if (TablaSubcategorias.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una Subcategoria");
        } else {
            int f = TablaSubcategorias.getSelectedRow();
            int c = 1;

            String cate = String.valueOf(TablaSubcategorias.getValueAt(f, c));
            // txtCategoria.setText(cate);

            RegistroProductos.txtSubCategoria.setText(cate);
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        Categoria_Subcategoria cm = new Categoria_Subcategoria();
        cm.setVisible(true);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void txtfk_categoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtfk_categoriaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtfk_categoriaActionPerformed

    private void EnviarPopUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EnviarPopUpActionPerformed
        // TODO add your handling code here:

        if (TablaSubcategorias.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una Subcategoria");
        } else {
            int f = TablaSubcategorias.getSelectedRow();
            int c = 1;

            String cate = String.valueOf(TablaSubcategorias.getValueAt(f, c));
            // txtCategoria.setText(cate);

            RegistroProductos.txtSubCategoria.setText(cate);

        }

    }//GEN-LAST:event_EnviarPopUpActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:

        if (TablaSubcategorias.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una Subcategoria");
        } else {
            int f = TablaSubcategorias.getSelectedRow();
            int c = 1;

            String cate = String.valueOf(TablaSubcategorias.getValueAt(f, c));
            // txtCategoria.setText(cate);

            RegistroProductos.txtSubCategoria.setText(cate);

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
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Subcategoria_Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Subcategoria_Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Subcategoria_Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Subcategoria_Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Subcategoria_Menu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem EnviarPopUp;
    private javax.swing.JPopupMenu PopUpTablaSubCategorias;
    private javax.swing.JTable TablaSubcategorias;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField txtCodigo;
    public static javax.swing.JTextField txtNombre;
    public static javax.swing.JTextField txtfk_categoria;
    // End of variables declaration//GEN-END:variables
}
