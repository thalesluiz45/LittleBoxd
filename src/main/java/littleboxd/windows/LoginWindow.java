package littleboxd.windows;

import littleboxd.controller.CamposVaziosException;
import littleboxd.controller.LoginException;
import littleboxd.controller.UsuarioController;
import littleboxd.controller.UsuarioNaoEncontradoException;
import littleboxd.model.Usuario;

import javax.swing.*;
import java.awt.*;

public class LoginWindow extends JFrame {
    private JPanel panel;
    private JLabel lblTitle;
    private JLabel lblUsername;
    private JLabel lblPassword;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnCreateAccount;
    private UsuarioController usuarioController;


    public LoginWindow() {
        usuarioController = new UsuarioController();
        initComponents();
    }

    private void initComponents() {
        panel = new JPanel();
        lblTitle = new JLabel("LITTLEBOXD");
        lblUsername = new JLabel("Usuário:");
        lblPassword = new JLabel("Senha:");
        txtUsername = new JTextField();
        txtPassword = new JPasswordField();
        btnLogin = new JButton("Login");
        btnCreateAccount = new JButton("Registrar");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Login");
        setResizable(false);
        getContentPane().setLayout(null);

        ImageIcon icon = new ImageIcon(getClass().getResource("/icon/box.png"));
        setIconImage(icon.getImage());

        lblTitle.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 16));
        lblTitle.setBounds(100, 10, 100, 30);
        getContentPane().add(lblTitle);

        lblUsername.setBounds(40, 60, 70, 25);
        getContentPane().add(lblUsername);

        txtUsername.setBounds(100, 60, 160, 25);
        getContentPane().add(txtUsername);

        lblPassword.setBounds(40, 90, 70, 25);
        getContentPane().add(lblPassword);

        txtPassword.setBounds(100, 90, 160, 25);
        getContentPane().add(txtPassword);

        btnLogin.setBounds(50, 130, 100, 25);
        getContentPane().add(btnLogin);

        btnCreateAccount.setBounds(160, 130, 100, 25);
        getContentPane().add(btnCreateAccount);

        setSize(new Dimension(300, 220));
        setLocationRelativeTo(null);


        btnLogin.addActionListener(e -> {
            try {
                login();
            } catch (LoginException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro de Login", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCreateAccount.addActionListener(e -> registrar());

    }

    private void login() throws LoginException {
        String nomeUsuario = txtUsername.getText();
        String senha = new String(txtPassword.getPassword());

        if (nomeUsuario.isEmpty() || senha.isEmpty()) {
            throw new CamposVaziosException();
        }

        if (usuarioController.verificarCredenciais(nomeUsuario, senha)) {
            JOptionPane.showMessageDialog(this, "Login bem-sucedido!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            Usuario usuarioAutenticado = usuarioController.obterUsuario(nomeUsuario);

            MenuWindow menuWindow = new MenuWindow(usuarioAutenticado);
            menuWindow.setVisible(true);
            this.dispose();
        } else {
            throw new UsuarioNaoEncontradoException();
        }

    }

    private void registrar() {
        RegistroWindow cadastroWindow = new RegistroWindow(this);
        cadastroWindow.setVisible(true);
        this.dispose();
    }
}
