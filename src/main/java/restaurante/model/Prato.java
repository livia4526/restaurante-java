/*
 * ARQUIVO: Prato.java                        PACOTE: restaurante.model
 * RESPONSÁVEL:
 *
 * RESPONSABILIDADE:
 * Item do cardápio do tipo prato, com tamanho. Herda de ItemCardapio.
 *
 * IMPLEMENTAÇÃO:
 * - Atributo: TamanhoPrato tamanho (+ getter). Não aceitar null
 *   (IllegalArgumentException).
 * - Construtor Prato(int codigo, String nome, double precoBase, TamanhoPrato tamanho).
 * - calcularPreco(): precoBase + tamanho.getAdicional().
 * - getCategoria(): return "Prato";
 * - Opcional: toString() com " - Médio" etc.
 *
 * OBSERVAÇÕES:
 * - NÃO escrever 0, 5 ou 10 aqui: os valores ficam só no enum TamanhoPrato.
 *
 * DEPENDE DE: ItemCardapio, TamanhoPrato.
 */
package restaurante.model;

public class Prato extends ItemCardapio {
}