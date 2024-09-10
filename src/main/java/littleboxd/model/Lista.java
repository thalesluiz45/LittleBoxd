package littleboxd.model;

import java.util.ArrayList;
import java.util.List;

public class Lista {
    private String titulo;
    private List<String> nomes;

    public Lista(String titulo) {
        this.titulo = titulo;
        this.nomes = new ArrayList<>();
    }

    public String getTitulo() {
        return titulo;
    }

    public List<String> getNomes() {
        return nomes;
    }

    public void adicionarNome(String nome) {
        this.nomes.add(nome);
    }

}
