package littleboxd.controller;

public class CamposVaziosException extends LoginException {
    public CamposVaziosException() {
        super("Por favor, preencha todos os campos.");
    }
}