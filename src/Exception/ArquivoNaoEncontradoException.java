package Exception;

public class ArquivoNaoEncontradoException extends RuntimeException {
    public ArquivoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
