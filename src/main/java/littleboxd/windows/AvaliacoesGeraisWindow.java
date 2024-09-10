package littleboxd.windows;

import littleboxd.controller.FilmesController;
import littleboxd.controller.UsuarioController;
import littleboxd.model.Filme;
import littleboxd.model.Usuario;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AvaliacoesGeraisWindow extends JFrame {

    private JTable tabelaAvaliacoes;
    private JButton btnVoltar, btnVerAvaliacao;
    private Usuario usuario;
    private UsuarioController usuarioController;

    class DefaultTableModelNaoEditavel extends javax.swing.table.DefaultTableModel {
        public DefaultTableModelNaoEditavel(Object[][] data, Object[] columnNames) {
            super(data, columnNames);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }

    public AvaliacoesGeraisWindow(Usuario usuario) {
        this.usuario = usuario;
        this.usuarioController = new UsuarioController();
        initComponents();
        atualizarTabela();
    }

    private void initComponents() {
        tabelaAvaliacoes = new JTable(new DefaultTableModelNaoEditavel(
                new Object[][]{},
                new String[]{
                        "Filme", "Comentário", "Nota" // Removido "Usuário"
                }
        ));
        JScrollPane jScrollPane1 = new JScrollPane(tabelaAvaliacoes);
        tabelaAvaliacoes.getTableHeader().setReorderingAllowed(false);

        btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(evt -> dispose());

        btnVerAvaliacao = new JButton("Ver Avaliação");
        btnVerAvaliacao.addActionListener(evt -> verAvaliacao());

        ImageIcon icon = new ImageIcon(getClass().getResource("/icon/box.png"));
        setIconImage(icon.getImage());

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(jScrollPane1, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        buttonPanel.add(btnVerAvaliacao);
        buttonPanel.add(btnVoltar);

        mainPanel.add(tablePanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        setTitle("Avaliações Gerais");
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(450, 400);
        setLocationRelativeTo(null);
    }

    private void verAvaliacao() {
        int selectedRow = tabelaAvaliacoes.getSelectedRow();
        if (selectedRow != -1) {
            Object tituloObj = tabelaAvaliacoes.getValueAt(selectedRow, 0);
            Object comentarioObj = tabelaAvaliacoes.getValueAt(selectedRow, 1);
            Object notaObj = tabelaAvaliacoes.getValueAt(selectedRow, 2);

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

        Map<String, Usuario> usuariosMap = usuarioController.getUsuarios();
        List<String> usuarios = new ArrayList<>(usuariosMap.keySet());

        List<Filme> todosFilmes = new ArrayList<>();
        for (String nomeUsuario : usuarios) {
            List<Filme> filmesDoUsuario = controller.carregarFilmes(nomeUsuario);
            for (Filme filme : filmesDoUsuario) {
                todosFilmes.add(filme);
            }
        }

        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tabelaAvaliacoes.getModel();
        model.setRowCount(0);

        for (Filme filme : todosFilmes) {
            model.addRow(new Object[]{filme.getTitulo(), filme.getComentario(), filme.getNota()});
        }
    }
}
