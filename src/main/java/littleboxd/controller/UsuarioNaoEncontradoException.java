package littleboxd.controller;

public class UsuarioNaoEncontradoException extends LoginException {
    public UsuarioNaoEncontradoException() {
        super("Usuário não encontrado.");
    }
}
