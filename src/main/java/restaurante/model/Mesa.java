/*
 * ARQUIVO: Mesa.java                         PACOTE: restaurante.model
 * RESPONSÁVEL:
 *
 * RESPONSABILIDADE:
 * Uma mesa do restaurante e TODOS os pedidos que passaram por ela no dia
 * ("Mesa com vários pedidos"). No máximo UM pedido aberto por vez.
 *
 * IMPLEMENTAÇÃO:
 * - Atributos: int numero (> 0); List<Pedido> pedidos = new ArrayList<>();
 * - boolean isOcupada(): existe pedido com isAberto() == true.
 * - Pedido getPedidoAberto(): o pedido aberto ou null (use stream + filter).
 * - void adicionarPedido(Pedido p): se p estiver aberto e a mesa já estiver
 *   ocupada -> IllegalStateException (proteção extra); senão adiciona.
 * - List<Pedido> getPedidos(): lista somente leitura.
 * - getNumero().
 *
 * OBSERVAÇÕES:
 * - A mesa fica livre sozinha quando o pedido aberto é fechado. Não crie
 *   atributo "ocupada": ele ficaria dessincronizado.
 * - As exceções de negócio (MesaOcupada/MesaVazia) são lançadas pelo
 *   GerenciadorRestaurante, não aqui.
 *
 * DEPENDE DE: Pedido.
 */
package restaurante.model;

public class Mesa {
}