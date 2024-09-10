package littleboxd.windows;

import littleboxd.controller.ListaController;
import littleboxd.model.Lista;
import littleboxd.model.Usuario;

import javax.swing.*;
import java.awt.*;

public class ListasWindow extends JFrame {

    private JList<String> listaView;
    private DefaultListModel<String> listModel;
    private ListaController listaController;
    private Usuario usuarioCadastrado;
    private JButton btnVerLista, btnEditar, btnRemover, btnVoltar;

    public ListasWindow(ListaController listaController, Usuario usuarioCadastrado) {
        this.listaController = listaController;
        this.usuarioCadastrado = usuarioCadastrado;
        initComponents();
        carregarListas();
    }

    private void initComponents() {
        listModel = new DefaultListModel<>();
        listaView = new JList<>(listModel);
        JButton btnAdicionar = new JButton("Adicionar Lista");
        btnVerLista = new JButton("Ver Lista");
        btnEditar = new JButton("Editar Lista");
        btnRemover = new JButton("Remover Lista");
        btnVoltar = new JButton("Voltar");

        ImageIcon icon = new ImageIcon(getClass().getResource("/icon/box.png"));
        setIconImage(icon.getImage());

        btnAdicionar.addActionListener(e -> openAddListWindow());
        btnVerLista.addActionListener(e -> verDescricaoLista());
        btnEditar.addActionListener(e -> editarLista());
        btnRemover.addActionListener(e -> removerLista());
        btnVoltar.addActionListener(e -> dispose());

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        setTitle("Minhas Listas");
        setResizable(false);
        setSize(300, 400);

        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.add(new JScrollPane(listaView), BorderLayout.CENTER);
        listPanel.add(btnAdicionar, BorderLayout.SOUTH);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 0)); // GridLayout
        buttonPanel.add(btnVerLista);
        buttonPanel.add(btnEditar);
        buttonPanel.add(btnRemover);
        buttonPanel.add(btnVoltar);

        mainPanel.add(listPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void carregarListas() {
        listModel.clear();
        var listas = listaController.carregarListas(usuarioCadastrado.getNomeUsuario());
        for (var lista : listas) {
            listModel.addElement(lista.getTitulo());
        }
    }

    private void openAddListWindow() {
        AdicionarListaWindow adicionarListaWindow = new AdicionarListaWindow(listaController, usuarioCadastrado);
        adicionarListaWindow.setVisible(true);
        this.dispose();
    }

    private void verDescricaoLista() {
        String tituloSelecionado = listaView.getSelectedValue();
        if (tituloSelecionado != null) {
            Lista lista = encontrarListaPorTitulo(tituloSelecionado);
            if (lista != null) {
                VerFilmesWindow descricaoWindow = new VerFilmesWindow(lista);
                descricaoWindow.setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma lista para ver.");
        }
    }

    private void editarLista() {
        String tituloSelecionado = listaView.getSelectedValue();
        if (tituloSelecionado != null) {
            Lista lista = encontrarListaPorTitulo(tituloSelecionado);
            if (lista != null) {
                EditarListaWindow editarListaWindow = new EditarListaWindow(lista, listaController, usuarioCadastrado);
                editarListaWindow.setVisible(true);
                this.dispose();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma lista para editar.");
        }
    }


    private void removerLista() {
        String tituloSelecionado = listaView.getSelectedValue();
        if (tituloSelecionado != null) {
            int confirmacao = JOptionPane.showConfirmDialog(this,
                    "Tem certeza de que deseja remover a lista '" + tituloSelecionado + "'?",
                    "Confirmar Remoção",
                    JOptionPane.YES_NO_OPTION);

            if (confirmacao == JOptionPane.YES_OPTION) {
                listaController.removerLista(usuarioCadastrado.getNomeUsuario(), tituloSelecionado);
                carregarListas();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma lista para remover.");
        }
    }

    private Lista encontrarListaPorTitulo(String titulo) {
        var listas = listaController.carregarListas(usuarioCadastrado.getNomeUsuario());
        for (var lista : listas) {
            if (lista.getTitulo().equals(titulo)) {
                return lista;
            }
        }
        return null;
    }
}
