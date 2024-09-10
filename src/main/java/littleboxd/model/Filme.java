package littleboxd.model;

public class Filme {
    private String id;
    private String titulo;
    private String comentario;
    private int nota;

    public Filme(String titulo, String comentario, int nota) {
        this.titulo = titulo;
        this.comentario = comentario;
        this.nota = nota;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
