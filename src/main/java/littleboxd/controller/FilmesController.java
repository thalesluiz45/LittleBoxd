package littleboxd.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import littleboxd.model.Filme;

import java.io.FileWriter;
import java.io.Reader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FilmesController {

    private final Gson gson = new Gson();

    private String getFilePath(String usuario) {
        return "filmes_" + usuario + ".json";
    }

    public List<Filme> carregarFilmes(String usuario) {
        List<Filme> filmes = new ArrayList<>();
        String filePath = getFilePath(usuario);

        try (Reader reader = new FileReader(filePath)) {
            Type listType = new TypeToken<ArrayList<Filme>>(){}.getType();
            filmes = gson.fromJson(reader, listType);

            boolean precisaSalvar = false;
            for (Filme filme : filmes) {
                if (filme.getId() == null || filme.getId().isEmpty()) {
                    filme.setId(UUID.randomUUID().toString());
                    precisaSalvar = true;
                }
            }

            if (precisaSalvar) {
                salvarFilmes(usuario, filmes);
            }

        } catch (IOException e) {
            System.out.println("Não foi possível carregar os filmes para o usuário " + usuario + ": " + e.getMessage());
        }

        return filmes;
    }

    public void adicionarFilme(String usuario, Filme filme) {
        List<Filme> filmes = carregarFilmes(usuario);
        filmes.add(filme);

        salvarFilmes(usuario, filmes);
    }

    private void salvarFilmes(String usuario, List<Filme> filmes) {
        String filePath = getFilePath(usuario);

        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(filmes, writer);
        } catch (IOException e) {
            System.out.println("Não foi possível salvar os filmes para o usuário " + usuario + ": " + e.getMessage());
        }
    }

    public void removerFilme(String usuario, String tituloFilme) {
        List<Filme> filmes = carregarFilmes(usuario);

        filmes.removeIf(filme -> filme.getTitulo().equals(tituloFilme));

        salvarFilmes(usuario, filmes);
    }

    public void atualizarFilme(String usuario, Filme filmeAtualizado) {
        List<Filme> filmes = carregarFilmes(usuario);

        for (int i = 0; i < filmes.size(); i++) {
            if (filmes.get(i).getId().equals(filmeAtualizado.getId())) {
                filmes.set(i, filmeAtualizado);
                break;
            }
        }

        salvarFilmes(usuario, filmes);
    }

}
