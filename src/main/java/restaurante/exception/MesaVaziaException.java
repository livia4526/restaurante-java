/*
 * ARQUIVO: MesaVaziaException.java           PACOTE: restaurante.exception
 * RESPONSÁVEL:
 *
 * RESPONSABILIDADE:
 * Sinalizar uma operação numa mesa SEM pedido aberto (inexistente ou livre):
 * adicionar/remover item, consultar ou fechar a conta.
 *
 * IMPLEMENTAÇÃO:
 * - Construtor com String mensagem -> super(mensagem).
 *
 * OBSERVAÇÕES:
 * - Lançada pelo método privado obterMesaOcupada() do GerenciadorRestaurante.
 */
package restaurante.exception;

public class MesaVaziaException extends Exception {
}