package littleboxd.windows;

import littleboxd.controller.ListaController;
import littleboxd.model.Usuario;

import javax.swing.*;
import java.awt.*;

public class MenuWindow extends JFrame {

    private JButton btnLists;
    private JButton btnFilms;
    private ListaController listaController;
    private Usuario usuarioCadastrado;

    public MenuWindow(Usuario usuarioCadastrado) {
        this.listaController = new ListaController();
        this.usuarioCadastrado = usuarioCadastrado;
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("LittleBoxd");
        setSize(350, 250);
        setResizable(false);
        setLocationRelativeTo(null);

        ImageIcon icon = new ImageIcon(getClass().getResource("/icon/box.png"));
        setIconImage(icon.getImage());

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        JLabel titleLabel = new JLabel("LITTLEBOXD", JLabel.CENTER);
        titleLabel.setFont(new java.awt.Font("Segoe UI", 1, 18));

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(titleLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        btnFilms = new JButton("Filmes");
        btnFilms.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 12));
        btnFilms.setPreferredSize(new Dimension(250, 60));
        btnFilms.setMargin(new Insets(10,50,10,50));
        btnFilms.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnFilms.addActionListener(e -> openFilmsWindow());
        centerPanel.add(btnFilms);

        btnLists = new JButton("Listas");
        btnLists.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 12));
        btnLists.setPreferredSize(new Dimension(250, 60));
        btnLists.setMargin(new Insets(10,55,10,50));
        btnLists.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLists.addActionListener(e -> openListsWindow());
        centerPanel.add(btnLists);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        setContentPane(mainPanel);
        setVisible(true);
    }

    private void openListsWindow() {
        ListasWindow listasWindow = new ListasWindow(listaController, usuarioCadastrado);
        listasWindow.setVisible(true);
    }

    private void openFilmsWindow() {
        FilmesWindow filmesWindow = new FilmesWindow(usuarioCadastrado);
        filmesWindow.setVisible(true);
    }
}
