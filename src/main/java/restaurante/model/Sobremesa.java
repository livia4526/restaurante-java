/*
 * ARQUIVO: Sobremesa.java                    PACOTE: restaurante.model
 * RESPONSÁVEL:
 *
 * RESPONSABILIDADE:
 * Item do cardápio do tipo sobremesa. Herda de ItemCardapio.
 *
 * IMPLEMENTAÇÃO:
 * - Constante: private static final double ADICIONAL_ESPECIAL = 3.0;
 * - Atributo: boolean especial (+ getter isEspecial()).
 * - Construtor Sobremesa(int codigo, String nome, double precoBase, boolean especial).
 * - calcularPreco(): precoBase + (especial ? ADICIONAL_ESPECIAL : 0).
 * - getCategoria(): return "Sobremesa";
 *
 * DEPENDE DE: ItemCardapio.
 */
package restaurante.model;

public class Sobremesa extends ItemCardapio {
}