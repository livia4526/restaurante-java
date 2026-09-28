/*
 * ARQUIVO: ItemPedido.java                   PACOTE: restaurante.model
 * RESPONSÁVEL:
 *
 * RESPONSABILIDADE:
 * Uma linha do pedido: QUAL item do cardápio e QUANTAS unidades.
 * Só existe dentro de um Pedido (é a "parte" da COMPOSIÇÃO).
 *
 * IMPLEMENTAÇÃO:
 * - Atributos privados: ItemCardapio item; int quantidade.
 * - Construtor ItemPedido(ItemCardapio item, int quantidade):
 *   item não nulo e quantidade > 0, senão IllegalArgumentException.
 * - void aumentarQuantidade(int qtd): qtd > 0.
 * - double calcularSubtotal(): item.calcularPreco() * quantidade.
 * - Getters: getItem(), getQuantidade().
 *
 * OBSERVAÇÕES:
 * - Taxa de serviço NÃO fica aqui (ela é do pedido inteiro).
 *
 * DEPENDE DE: ItemCardapio.
 */
package restaurante.model;

public class ItemPedido {
}