package littleboxd.windows;

import littleboxd.controller.FilmesController;
import littleboxd.model.Filme;
import littleboxd.model.Usuario;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FilmesWindow extends JFrame {

    private JTable tabelaFilmes;
    private JButton btnAdicionarFilme, btnEditarFilme, btnRemoverFilme, btnVoltar, btnAvaliacoesGerais, btnVerFilme;
    private Usuario usuario;

    public FilmesWindow(Usuario usuario) {
        this.usuario = usuario;
        initComponents();
        atualizarTabela();
    }

    class DefaultTableModelNaoEditavel extends javax.swing.table.DefaultTableModel {
        public DefaultTableModelNaoEditavel(Object[][] data, Object[] columnNames) {
            super(data, columnNames);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }

    private void initComponents() {
        tabelaFilmes = new JTable(new DefaultTableModelNaoEditavel(
                new Object[][]{},
                new String[]{
                        "Filme", "Comentário", "Nota"
                }
        ));

        ImageIcon icon = new ImageIcon(getClass().getResource("/icon/box.png"));
        setIconImage(icon.getImage());

        JScrollPane jScrollPane1 = new JScrollPane(tabelaFilmes);
        tabelaFilmes.getTableHeader().setReorderingAllowed(false);

        tabelaFilmes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaFilmes.setCellSelectionEnabled(false);
        tabelaFilmes.setRowSelectionAllowed(true);

        btnAdicionarFilme = new JButton("Adicionar Filme");
        btnEditarFilme = new JButton("Editar Filme");
        btnRemoverFilme = new JButton("Remover Filme");
        btnVoltar = new JButton("Voltar");
        btnAvaliacoesGerais = new JButton("Avaliações Gerais");
        btnVerFilme = new JButton("Ver Filme");

        btnVoltar.addActionListener(evt -> dispose());
        btnAdicionarFilme.addActionListener(evt -> adicionarFilme());
        btnEditarFilme.addActionListener(evt -> editarFilme());
        btnRemoverFilme.addActionListener(evt -> removerFilme());
        btnAvaliacoesGerais.addActionListener(evt -> abrirAvaliacoesGerais());
        btnVerFilme.addActionListener(evt  -> verFilme());

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(jScrollPane1, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 0));
        buttonPanel.add(btnAdicionarFilme);
        buttonPanel.add(btnEditarFilme);
        buttonPanel.add(btnRemoverFilme);
        buttonPanel.add(btnVerFilme);
        buttonPanel.add(btnAvaliacoesGerais);
        buttonPanel.add(btnVoltar);

        mainPanel.add(tablePanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        setTitle("Meus filmes");
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(450, 400);
        setLocationRelativeTo(null);
    }

    private void adicionarFilme() {
        AdicionarFilmeWindow adicionarFilmeWindow = new AdicionarFilmeWindow(this, usuario.getNomeUsuario());
        adicionarFilmeWindow.setVisible(true);
        this.setVisible(false);
    }

    private void editarFilme() {
        int selectedRow = tabelaFilmes.getSelectedRow();
        if (selectedRow != -1) {
            String tituloFilme = (String) tabelaFilmes.getValueAt(selectedRow, 0);
            FilmesController controller = new FilmesController();
            List<Filme> filmes = controller.carregarFilmes(usuario.getNomeUsuario());
            Filme filmeOriginal = filmes.stream().filter(filme -> filme.getTitulo().equals(tituloFilme)).findFirst().orElse(null);

            if (filmeOriginal != null) {
                EditarFilmeWindow editarFilmeWindow = new EditarFilmeWindow(filmeOriginal, controller, usuario, this);
                editarFilmeWindow.setVisible(true);
                this.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "Filme não encontrado.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um filme para editar.");
        }
    }

    private void removerFilme() {
        int selectedRow = tabelaFilmes.getSelectedRow();
        if (selectedRow != -1) {
            String tituloFilme = (String) tabelaFilmes.getValueAt(selectedRow, 0);
            int confirmacao = JOptionPane.showConfirmDialog(this,
                    "Tem certeza de que deseja remover o filme '" + tituloFilme + "'?",
                    "Confirmar Remoção",
                    JOptionPane.YES_NO_OPTION);

            if (confirmacao == JOptionPane.YES_OPTION) {
                FilmesController controller = new FilmesController();
                controller.removerFilme(usuario.getNomeUsuario(), tituloFilme);
                atualizarTabela();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um filme para remover.");
        }
    }

    private void abrirAvaliacoesGerais() {
        AvaliacoesGeraisWindow avaliacoesGeraisWindow = new AvaliacoesGeraisWindow(usuario);
        avaliacoesGeraisWindow.setVisible(true);
    }

    private void verFilme(){
        int selectedRow = tabelaFilmes.getSelectedRow();
        if (selectedRow != -1) {
            Object tituloObj = tabelaFilmes.getValueAt(selectedRow, 0);
            Object comentarioObj = tabelaFilmes.getValueAt(selectedRow, 1);
            Object notaObj = tabelaFilmes.getValueAt(selectedRow, 2);

            String tituloFilme = tituloObj != null ? tituloObj.toString() : "Desconhecido";
            String comentario = comentarioObj != null ? comentarioObj.toString() : "Nenhum comentário";
            String nota = notaObj != null ? notaObj.toString() : "Nota não disponível";

            new DetalhesFilmeWindow(tituloFilme, comentario, nota);
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma avaliação para ver os detalhes.");
        }
    }

    public void atualizarTabela() {
        FilmesController controller = new FilmesController();
        List<Filme> filmes = controller.carregarFilmes(usuario.getNomeUsuario());

        DefaultTableModelNaoEditavel model = (DefaultTableModelNaoEditavel) tabelaFilmes.getModel();
        model.setRowCount(0);

        for (Filme filme : filmes) {
            model.addRow(new Object[]{filme.getTitulo(), filme.getComentario(), filme.getNota()});
        }
    }
}
