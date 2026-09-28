/*
 * ARQUIVO: Bebida.java                       PACOTE: restaurante.model
 * RESPONSÁVEL:
 *
 * RESPONSABILIDADE:
 * Item do cardápio do tipo bebida. Herda de ItemCardapio.
 *
 * IMPLEMENTAÇÃO:
 * - Constante: private static final double ADICIONAL_GELO = 2.0;
 * - Atributo: boolean comGelo (+ getter isComGelo()).
 * - Construtor Bebida(int codigo, String nome, double precoBase, boolean comGelo)
 *   chamando super(codigo, nome, precoBase).
 * - calcularPreco(): precoBase + (comGelo ? ADICIONAL_GELO : 0).
 * - getCategoria(): return "Bebida";
 * - Opcional: sobrescrever toString() acrescentando " (com gelo)".
 *
 * OBSERVAÇÕES:
 * - "Coca-Cola com gelo" e "Coca-Cola sem gelo" são DOIS itens cadastrados.
 *
 * DEPENDE DE: ItemCardapio.
 */
package restaurante.model;

public class Bebida extends ItemCardapio {
}