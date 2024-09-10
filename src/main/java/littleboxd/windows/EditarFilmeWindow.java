package littleboxd.windows;

import littleboxd.controller.FilmesController;
import littleboxd.model.Filme;
import littleboxd.model.Usuario;

import javax.swing.*;
import java.awt.*;

public class EditarFilmeWindow extends JFrame {

    private JButton btnCancelar;
    private JButton btnSalvar;
    private JComboBox<String> comboxNotas;
    private JScrollPane jScrollPane1;
    private JLabel lblComentario;
    private JLabel lblNomeFilme;
    private JLabel lblNota;
    private JPanel mainPanel;
    private JTextArea txtComentario;
    private JTextField txtNomeFilme;
    private Usuario usuario;

    private FilmesWindow filmesWindow;
    private Filme filmeOriginal;
    private FilmesController filmeController;

    public EditarFilmeWindow(Filme filmeOriginal, FilmesController filmeController, Usuario usuario, FilmesWindow filmesWindow) {
        this.filmeOriginal = filmeOriginal;
        this.filmeController = filmeController;
        this.usuario = usuario;
        this.filmesWindow = filmesWindow;
        initComponents();
    }

    private void initComponents() {
        setTitle("Editar Filme");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setResizable(false);

        ImageIcon icon = new ImageIcon(getClass().getResource("/icon/box.png"));
        setIconImage(icon.getImage());

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        lblNomeFilme = new JLabel("Nome do filme:");
        lblComentario = new JLabel("Comentário:");
        lblNota = new JLabel("Nota:");

        txtNomeFilme = new JTextField(20);
        txtComentario = new JTextArea(5, 20);
        jScrollPane1 = new JScrollPane(txtComentario);
        comboxNotas = new JComboBox<>(new String[]{"0", "1", "2", "3", "4", "5"});

        txtNomeFilme.setText(filmeOriginal.getTitulo());
        txtComentario.setText(filmeOriginal.getComentario());
        comboxNotas.setSelectedItem(String.valueOf(filmeOriginal.getNota()));

        Dimension textFieldSize = new Dimension(200, 30);
        txtNomeFilme.setPreferredSize(textFieldSize);
        comboxNotas.setPreferredSize(textFieldSize);
        txtComentario.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        btnSalvar = new JButton("Salvar");
        btnCancelar = new JButton("Cancelar");

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonPanel.add(lblNomeFilme, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        buttonPanel.add(txtNomeFilme, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        buttonPanel.add(lblComentario, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        buttonPanel.add(jScrollPane1, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        buttonPanel.add(lblNota, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        buttonPanel.add(comboxNotas, gbc);

        JPanel buttonsPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        btnSalvar.setPreferredSize(new Dimension(95, 40));
        btnCancelar.setPreferredSize(new Dimension(95, 40));
        buttonsPanel.add(btnSalvar);
        buttonsPanel.add(btnCancelar);

        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        buttonPanel.add(buttonsPanel, gbc);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        setContentPane(mainPanel);

        btnSalvar.addActionListener(evt -> btnSalvarActionPerformed(evt));
        btnCancelar.addActionListener(evt -> btnCancelarActionPerformed(evt));
    }

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {
        String nomeFilme = txtNomeFilme.getText().trim();
        String comentario = txtComentario.getText().trim();
        int nota = Integer.parseInt((String) comboxNotas.getSelectedItem());

        if (nomeFilme.isEmpty()) {
            JOptionPane.showMessageDialog(this, "O nome do filme não pode estar vazio.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        filmeOriginal.setTitulo(nomeFilme);
        filmeOriginal.setComentario(comentario);
        filmeOriginal.setNota(nota);

        filmeController.atualizarFilme(usuario.getNomeUsuario(), filmeOriginal);

        JOptionPane.showMessageDialog(this, "Filme atualizado com sucesso!");

        if (filmesWindow != null) {
            filmesWindow.atualizarTabela();
        }

        filmesWindow.setVisible(true);
        dispose();
    }

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {
        if (filmesWindow != null) {
            filmesWindow.setVisible(true);
        }
        dispose();
    }
}
