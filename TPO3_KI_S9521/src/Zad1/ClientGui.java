package Zad1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

/**
 * Created by ikownacki on 08.04.2017.
 */
public class ClientGui {

    public ClientGui(String host, int port) throws IOException {
        ClientLogic clientLogic = new ClientLogic(host,port);
        JFrame mainFrame = new JFrame();
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setTitle("Klient");
        mainFrame.setSize(400,300);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);

        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    clientLogic.closeConnection();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });


        JPanel subsManagingPanel = new JPanel(new GridLayout(0,2));

        JPanel subjectsPanel = new JPanel(new BorderLayout());
        JLabel subjectsLabel = new JLabel("Dostępne tematy:");
        JScrollPane subjectsScrollPane = new JScrollPane();
        DefaultListModel<String> subjectsListModel = new DefaultListModel<>();
        JList<String> subjectsJList = new JList<>(subjectsListModel);
        subjectsScrollPane.setViewportView(subjectsJList);

        JButton subscribeButton = new JButton("Subskrybuj");


        subjectsPanel.add(subjectsLabel, BorderLayout.NORTH);
        subjectsPanel.add(subjectsScrollPane, BorderLayout.CENTER);
        subjectsPanel.add(subscribeButton, BorderLayout.SOUTH);

        subsManagingPanel.add(subjectsPanel);
        subsManagingPanel.setVisible(true);


        JPanel subscriptionsPanel = new JPanel(new BorderLayout());
        JLabel subscriptionsLabel = new JLabel("Twoje tematy:");
        JScrollPane subscriptionsScrollPane = new JScrollPane();
        DefaultListModel<String> subscriptionsListModel = new DefaultListModel<>();
        JList<String> subscriptionsJList = new JList<>(subscriptionsListModel);
        subscriptionsScrollPane.setViewportView(subscriptionsJList);

        JButton unSubscriptionsButton = new JButton("Wypisz");


        subscriptionsPanel.add(subscriptionsLabel, BorderLayout.NORTH);
        subscriptionsPanel.add(subscriptionsScrollPane, BorderLayout.CENTER);
        subscriptionsPanel.add(unSubscriptionsButton, BorderLayout.SOUTH);

        subsManagingPanel.add(subscriptionsPanel);

        subscribeButton.addActionListener(e -> {
            String string = subjectsJList.getSelectedValue();
            if (string == null) {
                return;
            }
            subscriptionsListModel.addElement(string);
            clientLogic.subscribe(string);

        });

        unSubscriptionsButton.addActionListener(e -> {
            String string = subjectsJList.getSelectedValue();
            if (string == null) {
                return;
            }
            subscriptionsListModel.addElement(string);
            clientLogic.unSubscribe(string);
        });

        JPanel messagePanel = new JPanel(new BorderLayout());

        JLabel currentSubject = new JLabel();
        messagePanel.add(currentSubject, BorderLayout.NORTH);

        JTextArea messageTextArea = new JTextArea();
        messageTextArea.setVisible(true);
        messageTextArea.setEnabled(false);
        messagePanel.add(messageTextArea, BorderLayout.CENTER);



        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("Tematy", subsManagingPanel);
        tabbedPane.add("Wiadomości", messagePanel);
        tabbedPane.setVisible(true);

        mainFrame.add(tabbedPane);

        for (String string : clientLogic.getCurrentSubjects()){
            subjectsListModel.addElement(string);
        }


    }

    public static void main(String[] args) {
        try {
            new ClientGui("localhost", 49000);
            System.out.println("Klient stworzony");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
