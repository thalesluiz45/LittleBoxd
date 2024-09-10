package littleboxd.windows;

import littleboxd.controller.ListaController;
import littleboxd.model.Lista;
import littleboxd.model.Usuario;

import javax.swing.*;
import java.awt.*;

public class AdicionarListaWindow extends JFrame {

    private JTextField txtTitulo;
    private JTextField txtNome;
    private DefaultListModel<String> listModel;
    private JList<String> nomeList;
    private ListaController listaController;
    private Usuario usuarioCadastrado;

    public AdicionarListaWindow(ListaController listaController, Usuario usuarioCadastrado) {
        this.listaController = listaController;
        this.usuarioCadastrado = usuarioCadastrado;
        initComponents();
    }

    private void initComponents() {

        txtTitulo = new JTextField(20);
        txtNome = new JTextField(20);
        JButton btnAdicionarNome = new JButton("Adicionar Filme");
        JButton btnSalvar = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");
        listModel = new DefaultListModel<>();
        nomeList = new JList<>(listModel);

        ImageIcon icon = new ImageIcon(getClass().getResource("/icon/box.png"));
        setIconImage(icon.getImage());

        btnAdicionarNome.addActionListener(e -> adicionarNome());
        btnSalvar.addActionListener(e -> salvarLista());
        btnCancelar.addActionListener(e -> cancelarListar());

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50)); // Adiciona espaçamento ao redor
        setTitle("Adicionar Lista");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(750, 400);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel listPanel = new JPanel(new GridLayout(1,1, 20, 10));
        listPanel.add(new JScrollPane(nomeList));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Dimension textFieldSize = new Dimension(200, 30);
        txtTitulo.setPreferredSize(textFieldSize);
        txtNome.setPreferredSize(textFieldSize);

        Dimension buttonSize = new Dimension(200, 40);
        btnAdicionarNome.setPreferredSize(buttonSize);

        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonPanel.add(new JLabel("Nome da Lista:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        buttonPanel.add(txtTitulo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        buttonPanel.add(new JLabel("Nome do Filme:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        buttonPanel.add(txtNome, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        buttonPanel.add(btnAdicionarNome, gbc);

        JPanel buttonsPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        btnSalvar.setPreferredSize(new Dimension(95, 40));
        btnCancelar.setPreferredSize(new Dimension(95, 40));
        buttonsPanel.add(btnSalvar);
        buttonsPanel.add(btnCancelar);

        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        buttonPanel.add(buttonsPanel, gbc);

        mainPanel.add(listPanel, BorderLayout.EAST);
        mainPanel.add(buttonPanel, BorderLayout.WEST);

        setContentPane(mainPanel);
    }

    private void adicionarNome() {
        String nome = txtNome.getText();
        if (!nome.isEmpty()) {
            listModel.addElement(nome);
            txtNome.setText("");
        }
    }

    private void salvarLista() {
        String titulo = txtTitulo.getText().trim();

        if (titulo.isEmpty() || listModel.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha os campos 'Nome da Lista' e adicione pelo menos um 'Nome do Filme'.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Lista lista = new Lista(titulo);
        for (int i = 0; i < listModel.size(); i++) {
            lista.adicionarNome(listModel.getElementAt(i));
        }

        listaController.salvarLista(usuarioCadastrado.getNomeUsuario(), lista);

        JOptionPane.showMessageDialog(this, "Lista salva com sucesso!");
        ListasWindow listasWindow = new ListasWindow(listaController, usuarioCadastrado);
        listasWindow.setVisible(true);
        this.dispose();
    }

    private void cancelarListar() {
        ListasWindow listasWindow = new ListasWindow(listaController, usuarioCadastrado);
        listasWindow.setVisible(true);
        this.dispose();
    }

}