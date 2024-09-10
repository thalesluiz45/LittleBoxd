package littleboxd.windows;

import littleboxd.model.Lista;

import javax.swing.*;
import java.awt.*;

public class VerFilmesWindow extends JFrame {

    public VerFilmesWindow(Lista lista) {
        initComponents(lista);
    }

    private void initComponents(Lista lista) {
        setTitle("Descrição da Lista");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 320);
        setLocationRelativeTo(null);
        setResizable(false);

        ImageIcon icon = new ImageIcon(getClass().getResource("/icon/box.png"));
        setIconImage(icon.getImage());

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Adiciona espaçamento ao redor do painel principal

        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> listaView = new JList<>(listModel);

        for (String nome : lista.getNomes()) {
            listModel.addElement(nome);
        }

        JScrollPane scrollPane = new JScrollPane(listaView);
        scrollPane.setPreferredSize(new Dimension(250, 240));

        JPanel scrollPanel = new JPanel();
        scrollPanel.setLayout(new BorderLayout());
        scrollPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        scrollPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(scrollPanel, BorderLayout.CENTER);

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.add(btnVoltar, BorderLayout.CENTER);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }
}
