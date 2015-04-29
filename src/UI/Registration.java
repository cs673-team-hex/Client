/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import JudgeStatus.JudgeStatus;
import SendingData.SSLClient;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Wei
 */
public class Registration extends javax.swing.JFrame {

    private String password = "";
    private String username;
    private String repassword = "";
    private String nickname;
    private static final int tempuserid = 0;
    private int status;
    public static String STATUS = "status";

    /**
     * Creates new form Registration
     */
    public Registration() {
        initComponents();
        /*addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                Login LoginPage;
                LoginPage = new Login();
                LoginPage.setVisible(true);
            }
        });*/
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
        user_name = new javax.swing.JTextField();
        nick_name = new javax.swing.JTextField();
        user_pass = new javax.swing.JPasswordField();
        repass = new javax.swing.JPasswordField();
        submit = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel1.setLayout(null);

        user_name.setMinimumSize(new java.awt.Dimension(70, 20));
        jPanel1.add(user_name);
        user_name.setBounds(30, 70, 177, 20);
        jPanel1.add(nick_name);
        nick_name.setBounds(30, 130, 177, 20);

        user_pass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                user_passActionPerformed(evt);
            }
        });
        jPanel1.add(user_pass);
        user_pass.setBounds(30, 190, 177, 20);
        jPanel1.add(repass);
        repass.setBounds(30, 250, 177, 20);

        submit.setText("Submit");
        submit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitActionPerformed(evt);
            }
        });
        jPanel1.add(submit);
        submit.setBounds(40, 290, 90, 23);

        jLabel2.setForeground(new java.awt.Color(255, 0, 51));
        jLabel2.setText("Password Not Match");
        jLabel2.setEnabled(false);
        jPanel1.add(jLabel2);
        jLabel2.setBounds(170, 220, 120, 20);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UI/img/signup-form.png"))); // NOI18N
        jPanel1.add(jLabel1);
        jLabel1.setBounds(0, 0, 320, 380);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void user_passActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_user_passActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_user_passActionPerformed

    private void submitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitActionPerformed
        // TODO add your handling code here:
        password = "";
        repassword = "";
        char[] temp_pass;
        char[] temp_repass;
        temp_pass = user_pass.getPassword();
        temp_repass = repass.getPassword();
        for (int i = 0; i < user_pass.getPassword().length; i++) {
            password = password + temp_pass[i];
            //System.out.print(temp[i]);
        }
        for (int j = 0; j < repass.getPassword().length; j++) {
            repassword = repassword + temp_repass[j];
        }
        System.out.println("password: " + password);
        System.out.println("repassword: " + repassword);
        if (!password.equals(repassword)) {
            jLabel2.setEnabled(true);
            return;
        }
        if (!password.equals(repassword)) {
            JOptionPane.showMessageDialog(null, "Passwords do not match");
        } else {
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(password.getBytes());
                byte[] digest = md.digest();
                StringBuffer sb = new StringBuffer();
                for (byte b : digest) {
                    sb.append(String.format("%02x", b & 0xff));
                }
                password = sb.toString();
                System.out.println("password: " + sb.toString());
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }

            username = user_name.getText();
            System.out.println("username: " + username);
            nickname = nick_name.getText();
            System.out.println("nickname: " + nickname);

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
            System.out.println(response);
            JOptionPane.showMessageDialog(null, "Succesful!!");
            this.dispose();
        }

        //System.out.println();
    }//GEN-LAST:event_submitActionPerformed

    public JSONObject getMessge() {
        JSONObject test = new JSONObject();
        try {
            test.put("opt", "signup");
            test.put("userid", tempuserid);
            JSONObject info = new JSONObject();
            info.put("username", username);
            info.put("passwd", password);
            info.put("nickname", nickname);
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
            java.util.logging.Logger.getLogger(Registration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Registration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Registration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Registration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Registration().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField nick_name;
    private javax.swing.JPasswordField repass;
    private javax.swing.JButton submit;
    private javax.swing.JTextField user_name;
    private javax.swing.JPasswordField user_pass;
    // End of variables declaration//GEN-END:variables
}
