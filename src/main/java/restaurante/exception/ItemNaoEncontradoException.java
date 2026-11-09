package restaurante.exception;

public class ItemNaoEncontradoException extends Exception {
    public ItemNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
