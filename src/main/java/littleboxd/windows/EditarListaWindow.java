package littleboxd.windows;

import littleboxd.controller.ListaController;
import littleboxd.model.Lista;
import littleboxd.model.Usuario;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class EditarListaWindow extends JFrame {

    private JTextField txtTitulo;
    private JTextField txtNome;
    private DefaultListModel<String> listModel;
    private JList<String> nomeList;
    private ListaController listaController;
    private Usuario usuarioCadastrado;
    private Lista listaOriginal;

    public EditarListaWindow(Lista listaOriginal, ListaController listaController, Usuario usuarioCadastrado) {
        this.listaOriginal = listaOriginal;
        this.listaController = listaController;
        this.usuarioCadastrado = usuarioCadastrado;
        initComponents();
    }

    private void initComponents() {
        setTitle("Editar Lista");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(650, 400);
        setLocationRelativeTo(null);
        setResizable(false);

        ImageIcon icon = new ImageIcon(getClass().getResource("/icon/box.png"));
        setIconImage(icon.getImage());

        txtTitulo = new JTextField(20);
        txtNome = new JTextField(20);
        JButton btnAdicionarNome = new JButton("Adicionar Filme");
        JButton btnRemoverNome = new JButton("Remover Filme");
        JButton btnSalvar = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");
        listModel = new DefaultListModel<>();
        nomeList = new JList<>(listModel);

        txtTitulo.setText(listaOriginal.getTitulo());
        for (String nome : listaOriginal.getNomes()) {
            listModel.addElement(nome);
        }

        btnAdicionarNome.addActionListener(e -> adicionarNome());
        btnRemoverNome.addActionListener(e -> removerNome());
        btnSalvar.addActionListener(e -> salvarLista());
        btnCancelar.addActionListener(e -> cancelarEdicao());

        JPanel listPanel = new JPanel(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(nomeList);
        listPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Dimension textFieldSize = new Dimension(200, 30);
        Dimension buttonSize = new Dimension(200, 40);
        txtTitulo.setPreferredSize(textFieldSize);
        txtNome.setPreferredSize(textFieldSize);
        btnAdicionarNome.setPreferredSize(buttonSize);
        btnRemoverNome.setPreferredSize(buttonSize);

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

        gbc.gridy = 3;
        buttonPanel.add(btnRemoverNome, gbc);

        JPanel buttonsPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        btnSalvar.setPreferredSize(new Dimension(95, 40));
        btnCancelar.setPreferredSize(new Dimension(95, 40));
        buttonsPanel.add(btnSalvar);
        buttonsPanel.add(btnCancelar);

        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        buttonPanel.add(buttonsPanel, gbc);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.add(buttonPanel, BorderLayout.WEST);
        mainPanel.add(listPanel, BorderLayout.CENTER);

        setContentPane(mainPanel);
    }


    private void adicionarNome() {
        String nome = txtNome.getText();
        if (!nome.isEmpty()) {
            listModel.addElement(nome);
            txtNome.setText("");
        }
    }

    private void removerNome() {
        int selectedIndex = nomeList.getSelectedIndex();
        if (selectedIndex != -1) {
            listModel.remove(selectedIndex);
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um filme para remover.");
        }
    }

    private void salvarLista() {
        String titulo = txtTitulo.getText().trim();

        if (titulo.isEmpty() || listModel.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha os campos 'Nome da Lista' e adicione pelo menos um 'Nome do Filme'.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Lista listaAtualizada = new Lista(titulo);
        for (int i = 0; i < listModel.size(); i++) {
            listaAtualizada.adicionarNome(listModel.getElementAt(i));
        }

        listaController.removerLista(usuarioCadastrado.getNomeUsuario(), listaOriginal.getTitulo());
        listaController.salvarLista(usuarioCadastrado.getNomeUsuario(), listaAtualizada);

        JOptionPane.showMessageDialog(this, "Lista atualizada com sucesso!");

        ListasWindow listasWindow = new ListasWindow(listaController, usuarioCadastrado);
        listasWindow.setVisible(true);
        this.dispose();
    }

    private void cancelarEdicao() {
        ListasWindow listasWindow = new ListasWindow(listaController, usuarioCadastrado);
        listasWindow.setVisible(true);
        this.dispose();
    }
}
