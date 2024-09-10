package littleboxd.windows;

import littleboxd.controller.UsuarioController;
import littleboxd.model.Usuario;

import javax.swing.*;
import java.awt.*;

public class RegistroWindow extends JFrame {

    private JLabel lblUsername;
    private JLabel lblPassword;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnRegister;
    private JButton btnCancel;
    private LoginWindow loginWindow;

    private UsuarioController usuarioController;

    public RegistroWindow(LoginWindow loginWindow) {
        this.loginWindow = loginWindow;
        usuarioController = new UsuarioController();
        initComponents();
    }

    private void initComponents() {
        lblUsername = new JLabel("Usuário:");
        lblPassword = new JLabel("Senha:");
        txtUsername = new JTextField();
        txtPassword = new JPasswordField();
        btnRegister = new JButton("Cadastrar");
        btnCancel = new JButton("Cancelar");
        btnRegister.addActionListener(e -> registrar());
        btnCancel.addActionListener(e -> cancelar());

        ImageIcon icon = new ImageIcon(getClass().getResource("/icon/box.png"));
        setIconImage(icon.getImage());

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastrar Usuário");
        setResizable(false);
        getContentPane().setLayout(null);

        lblUsername.setBounds(40, 30, 70, 25);
        getContentPane().add(lblUsername);

        txtUsername.setBounds(100, 30, 160, 25);
        getContentPane().add(txtUsername);

        lblPassword.setBounds(40, 60, 70, 25);
        getContentPane().add(lblPassword);

        txtPassword.setBounds(100, 60, 160, 25);
        getContentPane().add(txtPassword);

        btnRegister.setBounds(50, 110, 100, 25);
        getContentPane().add(btnRegister);

        btnCancel.setBounds(160, 110, 100, 25);
        getContentPane().add(btnCancel);

        setSize(new Dimension(300, 200));
        setLocationRelativeTo(null);

    }

    private void registrar() {
        String nomeUsuario = txtUsername.getText();
        String senha = new String(txtPassword.getPassword());

        if (nomeUsuario.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, insira tanto o nome de usuário quanto a senha para registrar.", "Erro de Registro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Usuario novoUsuario = new Usuario(nomeUsuario, senha);
        boolean registrado = usuarioController.registrarUsuario(novoUsuario);

        if (registrado) {
            JOptionPane.showMessageDialog(this, "Usuário registrado com sucesso!");
            LoginWindow loginWindow = new LoginWindow();
            loginWindow.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao registrar usuário. Tente outro nome de usuário.", "Erro de Registro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cancelar() {
        LoginWindow loginWindow = new LoginWindow();
        loginWindow.setVisible(true);
        this.dispose();
    }

}
