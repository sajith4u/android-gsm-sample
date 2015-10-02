package dev.innova.sajith;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class MainClass {

    public static void main(String[] args) {
        System.out.println("starting gcm-server Application");
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                init();
            }
        });

    }

    /**
     *  Send Notification to server
     * @param apiKey
     * @param registrationId
     * @param title
     * @param messagetext
     * @return
     */
    public static String send(String apiKey, String registrationId,String title,String messagetext) {
        Sender sender = new Sender(apiKey);
        Result result = null;
        Message message = new Message.Builder()
                .addData("message",messagetext)
                .addData("title", title)
                .build();
        try {
            result = sender.send(message, registrationId, 2);
            System.out.println("Result : " + result.getMessageId());
            System.out.println("Result ErrorCode : " + result.getErrorCodeName());
            System.out.println("Result : " + result.getCanonicalRegistrationId());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * Initialize the Swing form
     */
    public static void init() {
        JFrame frame = new JFrame("Notification Sender");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    /**
     *  Add Items to Swing form
     * @param panel
     */
    private static void placeComponents(JPanel panel) {

        panel.setLayout(null);

        JLabel apiKeyLabel = new JLabel("ApiKey");
        apiKeyLabel.setBounds(50, 20, 100, 40);
        panel.add(apiKeyLabel);

       final JTextField apiKeyText = new JTextField(20);
        apiKeyText.setBounds(160, 20, 400, 40);
        panel.add(apiKeyText);

        JLabel registrationLabel = new JLabel("Registraion ID");
        registrationLabel.setBounds(50, 70, 100, 40);
        panel.add(registrationLabel);

        final JTextField registrationText = new JTextField(20);
        registrationText.setBounds(160, 70, 400, 40);
        panel.add(registrationText);

        JLabel titleLabel = new JLabel("Title :");
        titleLabel.setBounds(50, 120, 100, 40);
        panel.add(titleLabel);

        final JTextField titleText = new JTextField(20);
        titleText.setBounds(160, 120, 400, 40);
        panel.add(titleText);

        JLabel messageLabel = new JLabel("Message :");
        messageLabel.setBounds(50, 170, 100, 40);
        panel.add(messageLabel);

        final JTextField messageText = new JTextField(20);
        messageText.setBounds(160, 170, 400, 40);
        panel.add(messageText);

        JButton resetButton = new JButton("Reset");
        resetButton.setBounds(50, 220, 120, 40);
        panel.add(resetButton);

        JButton sendNotification = new JButton("Send");
        sendNotification.setBounds(250, 220, 120, 40);
        panel.add(sendNotification);

        final JLabel statusLabel = new JLabel("status :");
        statusLabel.setBounds(50, 270, 400, 100);
        panel.add(statusLabel);

        sendNotification.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusLabel.setText("");
                String message = messageText.getText();
                String apiKey = apiKeyText.getText();
                String registrationKey = registrationText.getText();
                String titleMessage = titleText.getText();
                if((registrationKey.equals("")||registrationKey.equals(null))||(apiKey.equals("")||apiKey.equals(null))||(message.equals("")||message.equals(null))){
                    statusLabel.setText("Please Fill the above three Fields");
                }else {
                    statusLabel.setText("sending message ...........");
                    String response = send(apiKey,registrationKey,titleMessage,message);
                    statusLabel.setText(response);
                }

            }
        });

        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusLabel.setText("");
                messageText.setText("");
                apiKeyText.setText("");
                registrationText.setText("");
                titleText.setText("");
            }
        });



    }


}
