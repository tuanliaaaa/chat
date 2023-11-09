import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Properties;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class Chat extends javax.swing.JFrame {
    private ArrayList<String> receivedMessages = new ArrayList<>();
    public Chat() {
        initComponents();
        startSending();
    }


    @SuppressWarnings("unchecked")
 
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Send");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jButton2.setText("Call");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(jScrollPane1))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout
                                                .createSequentialGroup()
                                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 63,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, Short.MAX_VALUE)
                                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 241,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jButton1)))
                                .addGap(19, 19, 19)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButton1)
                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButton2))
                                .addGap(18, 18, 18)));

        pack();
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        sendMessage(); 
    }
    public static void main(String args[]) {
    
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Chat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Chat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Chat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Chat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
      
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Chat().setVisible(true);
            }
        });
    }

    private void sendMessage() {
        String message = jTextField1.getText();
        jTextArea1.append("Me: " + message + "\n");
        jTextField1.setText(""); // Clear the input field after sending a message
    }

    private void receiveMessage(String message) {
        receivedMessages.add(message);
        jTextArea1.append("User:" + message + "\n");
    }

    private void startSending() {
        Runnable sendTask = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    // Thực hiện gửi yêu cầu ở đây
                    // Ví dụ, gọi phương thức sendMessage()
                    // sendMessage();

                    // Ngủ một khoảng thời gian trước khi gửi yêu cầu tiếp theo
                    try {
                        Thread.sleep(00); // Ngủ 2 giây
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        };

        // Bắt đầu luồng để gửi yêu cầu
        Thread sendingThread = new Thread(sendTask);
        sendingThread.start();
    }

    private void saveMessageOff(String message, String ID) {
        Properties properties = new Properties();
        try {

            properties.load(getClass().getResourceAsStream("app.properties"));

            // Tạo chuỗi theo định dạng "token: userID"
            // String formattedUserID = String.format("token:%s", userID);
            properties.setProperty("", message + "," + ID);

            FileOutputStream fileOut = new FileOutputStream("app.properties");
            properties.store(fileOut, "");
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Variables declaration - do not modify
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration
}
