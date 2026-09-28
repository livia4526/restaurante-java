/*
 * ARQUIVO: ItemCardapio.java                 PACOTE: restaurante.model
 * RESPONSÁVEL:
 *
 * RESPONSABILIDADE:
 * Classe ABSTRATA base de todo item do cardápio (HERANÇA + ABSTRAÇÃO).
 * Bebida, Prato e Sobremesa herdam desta classe.
 *
 * IMPLEMENTAÇÃO:
 * - Atributos privados: int codigo, String nome, double precoBase.
 * - Construtor ItemCardapio(int codigo, String nome, double precoBase), validando:
 *     codigo > 0; nome não nulo, não vazio e SEM o caractere '|'
 *     (ele é o separador do arquivo); precoBase >= 0.
 *   Se inválido: throw new IllegalArgumentException("mensagem clara").
 * - Getters: getCodigo(), getNome(), getPrecoBase(). Sem setters (item não é editado).
 * - public abstract double calcularPreco();   // POLIMORFISMO: cada filha calcula
 * - public abstract String getCategoria();    // "Bebida", "Prato" ou "Sobremesa"
 * - toString(): ex. "[3] Pudim (Sobremesa) - R$ 11,00" usando calcularPreco().
 *
 * OBSERVAÇÕES:
 * - Não colocar regras de pedido, mesa ou arquivo aqui.
 * - As assinaturas de calcularPreco() e getCategoria() são usadas pelo grupo
 *   inteiro. Depois de publicadas, não mudar sem avisar.
 *
 * DEPENDE DE: nada.
 */
package restaurante.model;

public abstract class ItemCardapio {
}