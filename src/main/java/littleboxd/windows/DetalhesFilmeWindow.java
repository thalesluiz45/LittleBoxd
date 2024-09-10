package littleboxd.windows;

import javax.swing.*;
import java.awt.*;

public class DetalhesFilmeWindow extends JFrame {

    public DetalhesFilmeWindow(String tituloFilme, String comentario, String nota) {
        initComponents(tituloFilme, comentario, nota);
    }

    private void initComponents(String tituloFilme, String comentario, String nota) {
        setTitle("Detalhes da Avaliação");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 350);
        setLocationRelativeTo(null);
        setResizable(false);

        ImageIcon icon = new ImageIcon(getClass().getResource("/icon/box.png"));
        setIconImage(icon.getImage());

        setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.add(new JLabel("Título do Filme: " + tituloFilme));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10)); // Margens: top, left, bottom, right
        add(titlePanel, BorderLayout.NORTH);

        JPanel comentarioPanel = new JPanel(new BorderLayout());
        comentarioPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 5, 10)); // Margens: top, left, bottom, right
        comentarioPanel.add(new JLabel("Comentário: "), BorderLayout.WEST);

        JTextArea comentarioArea = new JTextArea(10, 30);
        comentarioArea.setText(comentario);
        comentarioArea.setEditable(false); // Torna o texto não editável
        comentarioArea.setLineWrap(true); // Quebra de linha automática
        comentarioArea.setWrapStyleWord(true); // Quebra de linha nas palavras

        JScrollPane comentarioScrollPane = new JScrollPane(comentarioArea);
        comentarioPanel.add(comentarioScrollPane, BorderLayout.CENTER);

        add(comentarioPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10)); // Margens: top, left, bottom, right

        JPanel notaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        notaPanel.add(new JLabel("Nota: " + nota));
        bottomPanel.add(notaPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(evt -> dispose()); // Fecha a janela quando clicado
        buttonPanel.add(btnVoltar);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}
