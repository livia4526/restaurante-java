/*
 * ARQUIVO: MesaOcupadaException.java         PACOTE: restaurante.exception
 * RESPONSÁVEL:
 *
 * RESPONSABILIDADE:
 * Sinalizar a tentativa de abrir uma mesa que já tem pedido aberto.
 *
 * IMPLEMENTAÇÃO:
 * - Construtor com String mensagem -> super(mensagem).
 *
 * OBSERVAÇÕES:
 * - Lançada apenas em GerenciadorRestaurante.abrirMesa().
 */
package restaurante.exception;

public class MesaOcupadaException extends Exception {
}