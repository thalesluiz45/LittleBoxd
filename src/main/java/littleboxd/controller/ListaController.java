package littleboxd.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import littleboxd.model.Lista;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ListaController {

    public List<Lista> carregarListas(String usuario) {
        try (FileReader reader = new FileReader(getCaminhoArquivo(usuario))) {
            Type type = new TypeToken<List<Lista>>() {}.getType();
            List<Lista> listas = new Gson().fromJson(reader, type);

            if (listas == null) {
                listas = new ArrayList<>();
            }

            return listas;
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public void salvarLista(String usuario, Lista lista) {
        List<Lista> listas = carregarListas(usuario);
        listas.add(lista);
        salvarListas(listas, usuario);
    }

    public void removerLista(String usuario, String titulo) {
        List<Lista> listas = carregarListas(usuario);
        listas.removeIf(lista -> lista.getTitulo().equals(titulo));
        salvarListas(listas, usuario);
    }

    private void salvarListas(List<Lista> listas, String usuario) {
        try (FileWriter writer = new FileWriter(getCaminhoArquivo(usuario))) {
            new Gson().toJson(listas, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getCaminhoArquivo(String usuario) {
        return "listas_" + usuario + ".json";
    }
}
