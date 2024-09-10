package littleboxd.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import littleboxd.model.Usuario;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class UsuarioController {

    private static final String ARQUIVO_USUARIOS = "usuarios.json";
    private Map<String, Usuario> usuarios;

    public UsuarioController() {
        carregarUsuarios();
    }

    private void carregarUsuarios() {
        try (FileReader reader = new FileReader(ARQUIVO_USUARIOS)) {
            Type type = new TypeToken<Map<String, Usuario>>() {}.getType();
            usuarios = new Gson().fromJson(reader, type);

            if (usuarios == null) {
                usuarios = new HashMap<>();
            }
        } catch (IOException e) {
            usuarios = new HashMap<>();
        }
    }

    public boolean registrarUsuario(Usuario usuario) {
        if (usuarios.containsKey(usuario.getNomeUsuario())) {
            return false;
        }

        usuarios.put(usuario.getNomeUsuario(), usuario);
        salvarUsuarios();
        return true;
    }

    public boolean verificarCredenciais(String username, String password) {
        Usuario usuario = usuarios.get(username);
        return usuario != null && usuario.getSenha().equals(password);
    }


    public Usuario obterUsuario(String nomeUsuario) {
        return usuarios.get(nomeUsuario);
    }

    public Map<String, Usuario> getUsuarios() {
        return usuarios;
    }

    public void salvarUsuarios() {
        try (FileWriter writer = new FileWriter(ARQUIVO_USUARIOS)) {
            new Gson().toJson(usuarios, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
