/*
 * ARQUIVO: ItemCardapioTest.java             PACOTE: restaurante.model (em src/test)
 * RESPONSÁVEL: Lívia
 *
 * O QUE TESTA:
 * - Bebida sem gelo = preço base; com gelo = base + 2.
 * - Prato PEQUENO/MEDIO/GRANDE = base + 0 / 5 / 10.
 * - Sobremesa normal = base; especial = base + 3.
 * - getCategoria() de cada subclasse.
 * - Polimorfismo: lista de ItemCardapio com os 3 tipos e a soma de calcularPreco().
 * - Validações do construtor (código, nome vazio, nome com '|', preço negativo),
 *   testadas com try/catch + fail().
 *
 * COMO RODAR: setinha verde ao lado de "class ItemCardapioTest" no IntelliJ,
 * ou "mvn test" no terminal.
 *
 * DEPENDE DE: Bebida, Prato, Sobremesa, TamanhoPrato.
 */
package restaurante.model;

// "import static" deixa usar assertEquals(...) direto, sem escrever Assertions.assertEquals(...)
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class ItemCardapioTest {

    // Margem de erro para comparar double (às vezes a conta dá 7.9999999 em vez de 8.0)
    private static final double DELTA = 0.001;

    // ---------------- BEBIDA ----------------

    // @Test avisa o JUnit que este método é um teste
    @Test
    void bebidaSemGeloCustaOPrecoBase() {
        Bebida agua = new Bebida(1, "Água", 4.0, false);

        // assertEquals(ESPERADO, OBTIDO, margem): se forem diferentes, o teste falha
        assertEquals(4.0, agua.calcularPreco(), DELTA);
    }

    @Test
    void bebidaComGeloSomaDoisReais() {
        Bebida coca = new Bebida(2, "Coca-Cola", 6.0, true);
        assertEquals(8.0, coca.calcularPreco(), DELTA);
    }

    // ---------------- PRATO ----------------

    @Test
    void pratoSomaOAdicionalDeCadaTamanho() {
        Prato pequeno = new Prato(3, "Hambúrguer", 25.0, TamanhoPrato.PEQUENO);
        Prato medio = new Prato(4, "Hambúrguer", 25.0, TamanhoPrato.MEDIO);
        Prato grande = new Prato(5, "Hambúrguer", 25.0, TamanhoPrato.GRANDE);

        assertEquals(25.0, pequeno.calcularPreco(), DELTA); // + 0
        assertEquals(30.0, medio.calcularPreco(), DELTA);   // + 5
        assertEquals(35.0, grande.calcularPreco(), DELTA);  // + 10
    }

    // ---------------- SOBREMESA ----------------

    @Test
    void sobremesaNormalCustaOPrecoBase() {
        Sobremesa pudim = new Sobremesa(6, "Pudim", 8.0, false);
        assertEquals(8.0, pudim.calcularPreco(), DELTA);
    }

    @Test
    void sobremesaEspecialSomaTresReais() {
        Sobremesa pudim = new Sobremesa(7, "Pudim", 8.0, true);
        assertEquals(11.0, pudim.calcularPreco(), DELTA);
    }

    // ---------------- CATEGORIA E POLIMORFISMO ----------------

    @Test
    void cadaTipoInformaSuaCategoria() {
        assertEquals("Bebida", new Bebida(1, "Suco", 5.0, false).getCategoria());
        assertEquals("Prato", new Prato(2, "Lasanha", 30.0, TamanhoPrato.MEDIO).getCategoria());
        assertEquals("Sobremesa", new Sobremesa(3, "Sorvete", 7.0, false).getCategoria());
    }

    // A lista é de ItemCardapio, mas cada objeto calcula o preço do SEU jeito.
    // Isso é o POLIMORFISMO funcionando.
    @Test
    void polimorfismoCadaItemCalculaDoSeuJeito() {
        List<ItemCardapio> itens = new ArrayList<>();
        itens.add(new Bebida(1, "Coca-Cola", 6.0, true));                  // 8.00
        itens.add(new Prato(2, "Hambúrguer", 25.0, TamanhoPrato.GRANDE));  // 35.00
        itens.add(new Sobremesa(3, "Pudim", 8.0, true));                   // 11.00

        double soma = 0;
        for (ItemCardapio item : itens) {
            soma += item.calcularPreco();
        }

        assertEquals(54.0, soma, DELTA);
    }

    // ---------------- VALIDAÇÕES (exceções) ----------------
    // Padrão usado: se a exceção NÃO for lançada, a linha do fail() é
    // executada e o teste falha. Se for lançada, o catch "segura" e o teste passa.

    @Test
    void precoNegativoNaoEAceito() {
        try {
            new Bebida(1, "Coca-Cola", -1.0, false);
            fail("Deveria ter lançado IllegalArgumentException para preço negativo");
        } catch (IllegalArgumentException e) {
            // era o esperado
        }
    }

    @Test
    void nomeVazioNaoEAceito() {
        try {
            new Sobremesa(1, "   ", 8.0, false);
            fail("Deveria ter lançado IllegalArgumentException para nome vazio");
        } catch (IllegalArgumentException e) {
            // era o esperado
        }
    }

    @Test
    void nomeComBarraVerticalNaoEAceito() {
        // O '|' é o separador do arquivo dados.txt, então não pode estar no nome
        try {
            new Prato(1, "Arroz|Feijão", 20.0, TamanhoPrato.PEQUENO);
            fail("Deveria ter lançado IllegalArgumentException para nome com '|'");
        } catch (IllegalArgumentException e) {
            // era o esperado
        }
    }

    @Test
    void codigoZeroNaoEAceito() {
        try {
            new Bebida(0, "Suco", 5.0, false);
            fail("Deveria ter lançado IllegalArgumentException para código 0");
        } catch (IllegalArgumentException e) {
            // era o esperado
        }
    }
}