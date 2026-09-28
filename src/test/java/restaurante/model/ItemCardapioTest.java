/*
 * ARQUIVO: ItemCardapioTest.java             PACOTE: restaurante.model (em src/test)
 * RESPONSÁVEL: Lívia
 *
 * O QUE TESTAR (JUnit 5, @Test, assertEquals(esperado, obtido, 0.001)):
 * - Bebida sem gelo = preço base; com gelo = base + 2.
 * - Prato PEQUENO/MEDIO/GRANDE = base + 0 / 5 / 10.
 * - Sobremesa normal = base; especial = base + 3.
 * - getCategoria() de cada subclasse.
 * - Polimorfismo: List<ItemCardapio> com os 3 tipos e a soma de calcularPreco().
 * - assertThrows(IllegalArgumentException.class, ...) para preço negativo,
 *   nome vazio e nome com '|'.
 *
 * DEPENDE DE: Bebida, Prato, Sobremesa, TamanhoPrato.
 */
package restaurante.model;

class ItemCardapioTest {
}