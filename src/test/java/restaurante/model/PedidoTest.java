/*
 * ARQUIVO: PedidoTest.java                   PACOTE: restaurante.model (em src/test)
 * RESPONSÁVEL: Lívia
 *
 * O QUE TESTA:
 * - Pedido novo: aberto, sem itens, subtotal 0.
 * - Adicionar 2x item A e 1x item B -> subtotal correto.
 * - Adicionar o MESMO item duas vezes soma a quantidade (1 linha só).
 * - Taxa = 10% do subtotal; total = subtotal + taxa.
 * - removerItem existente -> true e o subtotal cai; inexistente -> false.
 * - fechar(data): isAberto() false e a data guardada.
 * - Não pode adicionar item nem fechar de novo depois de fechado.
 * - getItens() não pode ser modificada de fora.
 * - Quantidade zero e nome de cliente vazio são recusados.
 *
 * ITENS USADOS EM TODOS OS TESTES:
 *   Coca-Cola com gelo    = 6 + 2  = R$ 8,00
 *   Hambúrguer médio      = 25 + 5 = R$ 30,00
 *
 * DEPENDE DE: Pedido, ItemPedido, Bebida, Prato.
 */
package restaurante.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class PedidoTest {

    private static final double DELTA = 0.001;

    // Itens de exemplo, reaproveitados em todos os testes
    private final Bebida coca = new Bebida(1, "Coca-Cola", 6.0, true);                 // 8.00
    private final Prato hamburguer = new Prato(2, "Hambúrguer", 25.0, TamanhoPrato.MEDIO); // 30.00

    @Test
    void pedidoNovoComecaAbertoEVazio() {
        Pedido pedido = new Pedido(1, 5, "Maria");

        assertTrue(pedido.isAberto());                      // assertTrue: a condição tem que ser true
        assertEquals(0, pedido.getItens().size());
        assertEquals(0.0, pedido.calcularSubtotal(), DELTA);
        assertNull(pedido.getDataHoraFechamento());         // assertNull: tem que ser null
    }

    @Test
    void subtotalSomaPrecoVezesQuantidade() {
        Pedido pedido = new Pedido(1, 5, "Maria");
        pedido.adicionarItem(coca, 2);        // 2 x 8  = 16
        pedido.adicionarItem(hamburguer, 1);  // 1 x 30 = 30

        assertEquals(46.0, pedido.calcularSubtotal(), DELTA);
    }

    @Test
    void mesmoItemDuasVezesSomaAQuantidade() {
        Pedido pedido = new Pedido(1, 5, "Maria");
        pedido.adicionarItem(coca, 2);
        pedido.adicionarItem(coca, 1);

        // Continua sendo UMA linha no pedido, agora com quantidade 3
        assertEquals(1, pedido.getItens().size());
        assertEquals(3, pedido.getItens().get(0).getQuantidade());
    }

    @Test
    void taxaEDezPorCentoETotalSomaATaxa() {
        Pedido pedido = new Pedido(1, 5, "Maria");
        pedido.adicionarItem(coca, 2);
        pedido.adicionarItem(hamburguer, 1);   // subtotal = 46

        assertEquals(4.6, pedido.calcularTaxaServico(), DELTA);  // 10% de 46
        assertEquals(50.6, pedido.calcularTotal(), DELTA);       // 46 + 4.6
    }

    @Test
    void removerItemQueExisteDiminuiOSubtotal() {
        Pedido pedido = new Pedido(1, 5, "Maria");
        pedido.adicionarItem(coca, 2);
        pedido.adicionarItem(hamburguer, 1);

        boolean removido = pedido.removerItem(1); // código da Coca

        assertTrue(removido);
        assertEquals(1, pedido.getItens().size());
        assertEquals(30.0, pedido.calcularSubtotal(), DELTA); // sobrou só o hambúrguer
    }

    @Test
    void removerItemQueNaoEstaNoPedidoRetornaFalse() {
        Pedido pedido = new Pedido(1, 5, "Maria");
        pedido.adicionarItem(coca, 1);

        assertFalse(pedido.removerItem(99));   // assertFalse: a condição tem que ser false
        assertEquals(1, pedido.getItens().size());
    }

    @Test
    void fecharGuardaADataEDeixaDeEstarAberto() {
        Pedido pedido = new Pedido(1, 5, "Maria");
        LocalDateTime momento = LocalDateTime.of(2026, 10, 5, 20, 30); // 05/10/2026 às 20:30

        pedido.fechar(momento);

        assertFalse(pedido.isAberto());
        assertEquals(momento, pedido.getDataHoraFechamento());
    }

    @Test
    void naoPodeAdicionarItemDepoisDeFechado() {
        Pedido pedido = new Pedido(1, 5, "Maria");
        pedido.fechar(LocalDateTime.of(2026, 10, 5, 20, 30));

        try {
            pedido.adicionarItem(coca, 1);
            fail("Deveria ter lançado IllegalStateException");
        } catch (IllegalStateException e) {
            // era o esperado
        }
    }

    @Test
    void naoPodeFecharDuasVezes() {
        Pedido pedido = new Pedido(1, 5, "Maria");
        pedido.fechar(LocalDateTime.of(2026, 10, 5, 20, 30));

        try {
            pedido.fechar(LocalDateTime.of(2026, 10, 5, 21, 0));
            fail("Deveria ter lançado IllegalStateException");
        } catch (IllegalStateException e) {
            // era o esperado
        }
    }

    // ENCAPSULAMENTO: quem recebe a lista de itens não pode mexer nela "por fora".
    // Itens só entram e saem pelos métodos do próprio Pedido.
    @Test
    void listaDeItensNaoPodeSerAlteradaDeFora() {
        Pedido pedido = new Pedido(1, 5, "Maria");

        try {
            pedido.getItens().add(new ItemPedido(coca, 1));
            fail("Deveria ter lançado UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            // era o esperado
        }
    }

    @Test
    void quantidadeZeroNaoEAceita() {
        Pedido pedido = new Pedido(1, 5, "Maria");

        try {
            pedido.adicionarItem(coca, 0);
            fail("Deveria ter lançado IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // era o esperado
        }
    }

    @Test
    void nomeDoClienteVazioNaoEAceito() {
        try {
            new Pedido(1, 5, "");
            fail("Deveria ter lançado IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // era o esperado
        }
    }
}