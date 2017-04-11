package Zad1;

import javax.swing.*;
import java.awt.*;

/**
 * Created by ikownacki on 08.04.2017.
 */
public class ClientGui {

    public ClientGui() {
        JFrame mainFrame = new JFrame();
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setTitle("Klient");
        mainFrame.setSize(400,300);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);


        JPanel subsManageingPanel = new JPanel(new GridLayout(0,2));

        JPanel subjectsPanel = new JPanel(new BorderLayout());
        JLabel subjectsLable = new JLabel("Dostępne tematy:");
        JScrollPane subjectsScrollPane = new JScrollPane();
        DefaultListModel<String> defaultListModel = new DefaultListModel<>();
        JList<String> subjectsJList = new JList<>(defaultListModel);
        subjectsScrollPane.setViewportView(subjectsJList);

        JButton subscribeButton = new JButton("Subskrybuj");
        subscribeButton.addActionListener(e -> {});

        subjectsPanel.add(subjectsLable, BorderLayout.NORTH);
        subjectsPanel.add(subjectsScrollPane, BorderLayout.CENTER);
        subjectsPanel.add(subscribeButton, BorderLayout.SOUTH);

        subsManageingPanel.add(subjectsPanel);


        JPanel subscriptionsPanel = new JPanel(new BorderLayout());
        JLabel subscriptionsLable = new JLabel("Twoje tematy:");
        JScrollPane subscriptionsScrollPane = new JScrollPane();
        DefaultListModel<String> subscriptionsListModel = new DefaultListModel<>();
        JList<String> subscriptionsJList = new JList<>(subscriptionsListModel);
        subscriptionsScrollPane.setViewportView(subscriptionsJList);

        JButton unsubscriptionsButton = new JButton("Wypisz");
        subscribeButton.addActionListener(e -> {});

        subscriptionsPanel.add(subscriptionsLable, BorderLayout.NORTH);
        subscriptionsPanel.add(subscriptionsScrollPane, BorderLayout.CENTER);
        subscriptionsPanel.add(unsubscriptionsButton, BorderLayout.SOUTH);

        subsManageingPanel.add(subscriptionsPanel);


        JPanel messagePanel = new JPanel(new BorderLayout());

        JLabel currentSubject = new JLabel();
        messagePanel.add(currentSubject, BorderLayout.NORTH);

        JTextArea messageTextArea = new JTextArea();
        messageTextArea.setVisible(true);
        messageTextArea.setEnabled(false);
        messagePanel.add(messageTextArea, BorderLayout.CENTER);



        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("Tematy", subsManageingPanel);
        tabbedPane.add("Wiadomości", messagePanel);

        mainFrame.add(tabbedPane);

    }

    public static void main(String[] args) {
        new ClientGui();
    }


}
