/*
 * ARQUIVO: PedidoTest.java                   PACOTE: restaurante.model (em src/test)
 * RESPONSÁVEL: Lívia
 *
 * O QUE TESTAR:
 * - Pedido novo: aberto, sem itens, subtotal 0.
 * - Adicionar 2x item A e 1x item B -> subtotal correto.
 * - Adicionar o MESMO item duas vezes soma a quantidade (1 linha só).
 * - Taxa = 10% do subtotal; total = subtotal + taxa.
 * - removerItem existente -> true e o subtotal cai; inexistente -> false.
 * - fechar(data): isAberto() false e a data guardada.
 * - Adicionar item depois de fechado -> IllegalStateException.
 * - getItens() não pode ser modificada (UnsupportedOperationException).
 *
 * DEPENDE DE: Pedido, ItemPedido, Bebida/Prato (itens de exemplo).
 */
package restaurante.model;

class PedidoTest {
}