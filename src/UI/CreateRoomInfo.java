/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import GameInfo.Player;
import GameInfo.Room;
import JudgeStatus.JudgeStatus;
import SendingData.SSLClient;
import static UI.Registration.STATUS;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Wei
 */
public class CreateRoomInfo extends javax.swing.JFrame {

    private String cre_title;
    private int cre_num;
    private int cre_type;
    private int cre_wager;
    private int userid;
    private String KEY_RES = "result";
    private String KEY_ROOMID = "roomid";
    private int room_id;

    private int status;
    public static String STATUS = "status";

    /**
     * Creates new form CreateRoomInfo
     */
    public CreateRoomInfo() {
        initComponents();
        jTitle.setText(Player.GetPlayer().GetNickName() + "'s room");
        jMax.setText("3");
        jWager.setText("50");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTitle = new javax.swing.JTextField();
        jMax = new javax.swing.JTextField();
        jWager = new javax.swing.JTextField();
        jType = new javax.swing.JComboBox();
        jCreate = new javax.swing.JButton();
        jCancel = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel1.setLayout(null);

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Room Title:");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(70, 40, 80, 14);

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Max Players: ");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(70, 80, 90, 14);

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Game Type:");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(70, 120, 90, 14);

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Wager:");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(70, 160, 60, 20);

        jTitle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTitleActionPerformed(evt);
            }
        });
        jPanel1.add(jTitle);
        jTitle.setBounds(170, 40, 90, 20);
        jPanel1.add(jMax);
        jMax.setBounds(170, 80, 90, 20);
        jPanel1.add(jWager);
        jWager.setBounds(170, 160, 90, 20);

        jType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "BlackJack", "ShowHand" }));
        jPanel1.add(jType);
        jType.setBounds(170, 120, 90, 20);

        jCreate.setText("Create");
        jCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCreateActionPerformed(evt);
            }
        });
        jPanel1.add(jCreate);
        jCreate.setBounds(70, 230, 90, 23);

        jCancel.setText("Cancel");
        jCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCancelActionPerformed(evt);
            }
        });
        jPanel1.add(jCancel);
        jCancel.setBounds(190, 230, 90, 23);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/img/HomePage_bg.jpg"))); // NOI18N
        jPanel1.add(jLabel1);
        jLabel1.setBounds(0, 0, 400, 300);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTitleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTitleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTitleActionPerformed

    private void jCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCreateActionPerformed
        // TODO add your handling code here:
        if (jTitle.toString() != null && jMax.toString() != null && jWager.toString() != null && !jTitle.toString().equals("") && !jMax.toString().equals("") && !jWager.toString().equals("")) {
            cre_title = jTitle.getText();
            cre_num = Integer.parseInt(jMax.getText());
            cre_wager = Integer.parseInt(jWager.getText());
            cre_type = jType.getSelectedIndex() + 1;
        } else {
            JOptionPane.showMessageDialog(null, "Please do not leave blanks");
            return;
        }
        JSONObject response = null;
        try {
            response = SSLClient.postMessage(getMessge());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            status = response.getInt(STATUS);
        } catch (JSONException ex) {
            Logger.getLogger(Registration.class.getName()).log(Level.SEVERE, null, ex);
        }
        //static method
        if (JudgeStatus.OutputStatus(status) == false) {
            return;
        }

        //System.out.println(response);
        JSONObject result = new JSONObject();
        try {
            result = response.getJSONObject(KEY_RES);
        } catch (JSONException ex) {
            Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            room_id = result.getInt(KEY_ROOMID);
        } catch (JSONException ex) {
            Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
        }
        Room.initial(room_id, cre_title, cre_num, cre_wager, cre_type);
        Player.GetPlayer().SetIsCreator(true);
        CreateRoom C = new CreateRoom();
        C.setSize(800, 450);
        C.setLocation(0, 0);
        C.setVisible(true);

        this.dispose();
    }//GEN-LAST:event_jCreateActionPerformed

    private void jCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCancelActionPerformed
        // TODO add your handling code here:
        HomePage homePage;
        try {
            homePage = new HomePage();
            homePage.setVisible(true);
        } catch (JSONException ex) {
            Logger.getLogger(CreateRoom.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.dispose();
    }//GEN-LAST:event_jCancelActionPerformed

    public JSONObject getMessge() {
        JSONObject test = new JSONObject();
        try {
            test.put("opt", "createroom");
            userid = Player.GetPlayer().GetUserId();
            test.put("userid", userid);
            JSONObject info = new JSONObject();
            info.put("title", cre_title);
            info.put("number", cre_num);
            info.put("type", cre_type);
            info.put("wager", cre_wager);
            test.put("info", info);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(test);
        return test;
    }

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
            java.util.logging.Logger.getLogger(CreateRoomInfo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CreateRoomInfo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CreateRoomInfo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CreateRoomInfo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CreateRoomInfo().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jCancel;
    private javax.swing.JButton jCreate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JTextField jMax;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTitle;
    private javax.swing.JComboBox jType;
    private javax.swing.JTextField jWager;
    // End of variables declaration//GEN-END:variables
}
