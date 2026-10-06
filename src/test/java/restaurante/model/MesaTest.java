/*
 * ARQUIVO: MesaTest.java                     PACOTE: restaurante.model (em src/test)
 * RESPONSÁVEL: Lívia
 *
 * O QUE TESTA:
 * - Mesa nova: livre, getPedidoAberto() == null.
 * - Após adicionarPedido(pedido aberto): ocupada.
 * - Após pedido.fechar(...): livre de novo, mas o pedido continua em getPedidos().
 * - Dois pedidos (um fechado, um novo aberto): getPedidos().size() == 2.
 * - Adicionar segundo pedido aberto com a mesa ocupada -> IllegalStateException.
 * - Número de mesa inválido e proteção da lista de pedidos.
 *
 * DEPENDE DE: Mesa, Pedido.
 */
package restaurante.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class MesaTest {

    @Test
    void mesaNovaComecaLivre() {
        Mesa mesa = new Mesa(5);

        assertEquals(5, mesa.getNumero());
        assertFalse(mesa.isOcupada());
        assertNull(mesa.getPedidoAberto());
        assertEquals(0, mesa.getPedidos().size());
    }

    @Test
    void comPedidoAbertoAMesaFicaOcupada() {
        Mesa mesa = new Mesa(5);
        Pedido pedido = new Pedido(1, 5, "Maria");

        mesa.adicionarPedido(pedido);

        assertTrue(mesa.isOcupada());
        assertEquals(pedido, mesa.getPedidoAberto());
    }

    // Regra do grupo: a mesa guarda todos os pedidos, mas fica livre
    // quando nenhum deles está aberto
    @Test
    void fecharOPedidoLiberaAMesaMasGuardaOHistorico() {
        Mesa mesa = new Mesa(5);
        Pedido pedido = new Pedido(1, 5, "Maria");
        mesa.adicionarPedido(pedido);

        pedido.fechar(LocalDateTime.of(2026, 10, 5, 20, 30));

        assertFalse(mesa.isOcupada());
        assertNull(mesa.getPedidoAberto());
        assertEquals(1, mesa.getPedidos().size()); // o pedido fechado continua na mesa
    }

    @Test
    void mesaGuardaVariosPedidosAoLongoDoDia() {
        Mesa mesa = new Mesa(5);

        Pedido primeiro = new Pedido(1, 5, "Maria");
        mesa.adicionarPedido(primeiro);
        primeiro.fechar(LocalDateTime.of(2026, 10, 5, 12, 0));

        Pedido segundo = new Pedido(2, 5, "João");
        mesa.adicionarPedido(segundo);

        assertEquals(2, mesa.getPedidos().size());
        assertEquals(segundo, mesa.getPedidoAberto()); // o aberto é o do João
    }

    @Test
    void naoAceitaDoisPedidosAbertosAoMesmoTempo() {
        Mesa mesa = new Mesa(5);
        mesa.adicionarPedido(new Pedido(1, 5, "Maria"));

        try {
            mesa.adicionarPedido(new Pedido(2, 5, "João"));
            fail("Deveria ter lançado IllegalStateException");
        } catch (IllegalStateException e) {
            // era o esperado
        }
    }

    @Test
    void numeroDaMesaPrecisaSerMaiorQueZero() {
        try {
            new Mesa(0);
            fail("Deveria ter lançado IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // era o esperado
        }
    }

    // getPedidos() devolve uma CÓPIA: mexer nela não muda a mesa de verdade
    @Test
    void alterarAListaRecebidaNaoAlteraAMesa() {
        Mesa mesa = new Mesa(5);
        mesa.adicionarPedido(new Pedido(1, 5, "Maria"));

        mesa.getPedidos().clear(); // apaga só a cópia

        assertEquals(1, mesa.getPedidos().size());
    }
}