/*
 * ARQUIVO: MesaTest.java                     PACOTE: restaurante.model (em src/test)
 * RESPONSÁVEL: Lívia
 *
 * O QUE TESTAR:
 * - Mesa nova: livre, getPedidoAberto() == null.
 * - Após adicionarPedido(pedido aberto): ocupada.
 * - Após pedido.fechar(...): livre de novo, mas o pedido continua em getPedidos().
 * - Dois pedidos (um fechado, um novo aberto): getPedidos().size() == 2.
 * - Adicionar segundo pedido aberto com a mesa ocupada -> IllegalStateException.
 *
 * DEPENDE DE: Mesa, Pedido.
 */
package restaurante.model;

class MesaTest {
}