package Zad1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

/**
 * Created by ikownacki on 07.04.2017.
 */
public class AdminGui {

    public AdminGui(AdminLogic adminLogic) throws IOException {
        JFrame mainFrame = new JFrame();
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    adminLogic.closeConnection();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setTitle("Administrator");
        mainFrame.setSize(400, 300);


        JPanel mainFramePanel = new JPanel(new GridLayout(0, 2));
        JTabbedPane jTabbedPane = new JTabbedPane();


        JScrollPane scrollPane = new JScrollPane();
        DefaultListModel<String> model = new DefaultListModel<>();
        JList<String> subjectsJList = new JList<>(model);
        subjectsJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollPane.setViewportView(subjectsJList);


        JPanel subjectsOptionJPanel = new JPanel(new GridLayout(6, 1));
        JTextField subjectTextField = new JTextField();
        JButton addButton = new JButton("Dodaj");
        addButton.addActionListener(e -> {
            String string = subjectTextField.getText();
            if (string == null || string.equals("") || model.contains(string)) {
                return;
            }
            model.addElement(string);
            subjectTextField.setText("");
            try {
                adminLogic.addSubject(string);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        JButton deleteButton = new JButton("Usuń");
        deleteButton.addActionListener(e -> {
            String string = subjectTextField.getText();
            if (string == null || string.equals("")) {
                return;
            }
            if (model.contains(string)) {
                model.removeElement(string);
                adminLogic.deleteSubject(string);
            }

        });
        subjectsOptionJPanel.add(subjectTextField);
        subjectsOptionJPanel.add(addButton);
        subjectsOptionJPanel.add(deleteButton);

        final String[] selectedSubject = new String[1];

        JPanel sendingOptionPanel = new JPanel(new BorderLayout());
        JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true);
        JButton sendButton = new JButton("Wyślij");
        sendButton.addActionListener(e -> {
            String subject = subjectsJList.getSelectedValue();
            String message = textArea.getText();
            adminLogic.sendMessageToSubscribers(subject, message);
        });
        sendingOptionPanel.add(textArea, BorderLayout.CENTER);
        sendingOptionPanel.add(sendButton, BorderLayout.SOUTH);


        subjectsJList.addListSelectionListener(e -> {
            String string = subjectsJList.getSelectedValue();
            subjectTextField.setText(string);
            selectedSubject[0] = string;
        });


        jTabbedPane.addTab("Tematy", subjectsOptionJPanel);
        jTabbedPane.addTab("Wysyłanie", sendingOptionPanel);

        mainFramePanel.add(scrollPane);
        mainFramePanel.add(jTabbedPane);

        mainFrame.add(mainFramePanel);
        mainFrame.setVisible(true);
    }


}
