/*
 * ARQUIVO: ItemNaoEncontradoException.java   PACOTE: restaurante.exception
 * RESPONSÁVEL:
 *
 * RESPONSABILIDADE:
 * Sinalizar que um código de item não existe no cardápio ou no pedido.
 *
 * IMPLEMENTAÇÃO:
 * - Construtor que recebe String mensagem e chama super(mensagem).
 *
 * OBSERVAÇÕES:
 * - Exceção VERIFICADA (extends Exception): obriga o try/catch na interface.
 * - Lançada pelo GerenciadorRestaurante em buscarItem, adicionarItem e removerItem.
 */
package restaurante.exception;

public class ItemNaoEncontradoException extends Exception {
    public ItemNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
