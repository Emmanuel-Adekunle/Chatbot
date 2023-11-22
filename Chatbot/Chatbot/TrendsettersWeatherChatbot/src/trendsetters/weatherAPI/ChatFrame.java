package trendsetters.weatherAPI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

public class ChatFrame extends JFrame {

    private JTextArea chatArea;
    private JTextField chatInput;

    public ChatFrame() {
        super("Trendsetters chat frame");

        // Create components
        chatArea = new JTextArea();
        chatInput = new JTextField();
        JButton sendButton = new JButton("Send");

        // Add components to frame
        setLayout(new BorderLayout());
        add(new JScrollPane(chatArea), BorderLayout.CENTER);
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(chatInput, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        add(inputPanel, BorderLayout.SOUTH);

        //Set image icon
        Image icon = Toolkit.getDefaultToolkit().getImage("C:\\Users\\Dell\\OneDrive - Griffith College\\Desktop\\ALL FILES\\the-trendsetters\\chat-png.png");
        setIconImage(icon);

        // Add action listener to send button
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        // Add key listener to chat input field
        chatInput.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendMessage();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });

        // Set frame properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);



        setVisible(true);
    }

    public void sendMessage() {
        String message = chatInput.getText().toLowerCase();
        chatInput.setText("");
        chatArea.append("You: " + message + "\n\n");
        String botResponse = chatReply(message);
        chatArea.append("Bot: "+botResponse+"\n\n");
    }

    public String chatReply(String message){
        //add valid days
        String[] day = {"monday","tuesday","wednesday","thursday","friday"};

        //add keywords for trip
        String[] keywords = {"trip","journey","voyage","cruise","fly","holiday","travel"};

        String reply = null;
        if (message.contains("hi") || message.contains("hello")) {
            reply="Hi, what can I help you";
        }
        else if (message.contains("thanks")||message.contains("thank you")){
            reply="Happy to help. Any question?";
        }
        else if (Arrays.stream(keywords).anyMatch(message::contains)){
            reply="Sure, I can help you plan your clothing requirements for your upcoming trip! Can you please provide me with the names of the 3 destination cities separated by commas, as well as the dates of your visit?";
        }
        else if (Arrays.asList(day).contains(message)&&WeatherForecastApi.checkCity(message)){
            reply="Valid city name and days";
        }
        else {
            reply = "I'm sorry, I didn't understand. Can you please rephrase your question?";
        }
        return reply;
    }


    public static void main(String[] args) {
        new ChatFrame();
    }
}
