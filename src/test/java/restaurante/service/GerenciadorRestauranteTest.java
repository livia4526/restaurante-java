/*
 * ARQUIVO: GerenciadorRestauranteTest.java   PACOTE: restaurante.service (em src/test)
 * RESPONSÁVEL: Lívia
 *
 * O QUE TESTA (usando new GerenciadorRestaurante() — SEM arquivo):
 * - Cadastro: códigos em sequência e código repetido recusado.
 * - buscarItem existente; inexistente -> ItemNaoEncontradoException.
 * - listarPorCategoria("Bebida") retorna só bebidas.
 * - abrirMesa e abrir de novo -> MesaOcupadaException.
 * - adicionarItem / fecharConta em mesa não aberta -> MesaVaziaException.
 * - adicionarItem ou removerItem com código inválido -> ItemNaoEncontradoException.
 * - Fluxo completo: abrir, adicionar, fecharConta -> total correto, mesa livre,
 *   pedido presente em listarPedidosFechados().
 * - Após fechar, abrir a mesma mesa para outro cliente funciona.
 * - listarMesas() em ordem de número.
 *
 * CARDÁPIO USADO (criado pelo método criarGerenciadorComCardapio()):
 *   código 1 - Coca-Cola com gelo  = R$ 8,00
 *   código 2 - Hambúrguer médio    = R$ 30,00
 *   código 3 - Pudim especial      = R$ 11,00
 *
 * DEPENDE DE: GerenciadorRestaurante e todo o model/exception.
 */
package restaurante.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.Test;

import restaurante.exception.ItemNaoEncontradoException;
import restaurante.exception.MesaOcupadaException;
import restaurante.exception.MesaVaziaException;
import restaurante.model.Bebida;
import restaurante.model.ItemCardapio;
import restaurante.model.Mesa;
import restaurante.model.Pedido;
import restaurante.model.Prato;
import restaurante.model.Sobremesa;
import restaurante.model.TamanhoPrato;

class GerenciadorRestauranteTest {

    private static final double DELTA = 0.001;

    // Método auxiliar: cada teste chama este método e recebe um gerenciador
    // NOVO, com o mesmo cardápio. Assim um teste não interfere no outro.
    // O construtor SEM Persistencia não lê nem grava nenhum arquivo.
    private GerenciadorRestaurante criarGerenciadorComCardapio() {
        GerenciadorRestaurante g = new GerenciadorRestaurante();
        g.cadastrarItem(new Bebida(g.proximoCodigoItem(), "Coca-Cola", 6.0, true));
        g.cadastrarItem(new Prato(g.proximoCodigoItem(), "Hambúrguer", 25.0, TamanhoPrato.MEDIO));
        g.cadastrarItem(new Sobremesa(g.proximoCodigoItem(), "Pudim", 8.0, true));
        return g;
    }

    // ---------------- CARDÁPIO ----------------

    @Test
    void cadastrarGeraCodigosEmSequencia() {
        GerenciadorRestaurante g = criarGerenciadorComCardapio();

        assertEquals(3, g.listarCardapio().size());
        assertEquals(4, g.proximoCodigoItem()); // depois dos códigos 1, 2 e 3
    }

    @Test
    void naoPodeCadastrarDoisItensComOMesmoCodigo() {
        GerenciadorRestaurante g = criarGerenciadorComCardapio();

        try {
            g.cadastrarItem(new Bebida(1, "Guaraná", 5.0, false)); // código 1 já existe
            fail("Deveria ter lançado IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // era o esperado
        }
    }

    // "throws Exception": buscarItem pode lançar uma exceção VERIFICADA
    // (ItemNaoEncontradoException). O Java obriga a tratar ou repassar;
    // num teste, a gente só repassa. Se ela acontecer, o teste falha.
    @Test
    void buscarItemQueExiste() throws Exception {
        GerenciadorRestaurante g = criarGerenciadorComCardapio();

        ItemCardapio item = g.buscarItem(2);

        assertEquals("Hambúrguer", item.getNome());
    }

    @Test
    void buscarItemQueNaoExisteLancaExcecao() {
        GerenciadorRestaurante g = criarGerenciadorComCardapio();

        try {
            g.buscarItem(99);
            fail("Deveria ter lançado ItemNaoEncontradoException");
        } catch (ItemNaoEncontradoException e) {
            // era o esperado
        }
    }

    @Test
    void listarPorCategoriaTrazSoAquelaCategoria() {
        GerenciadorRestaurante g = criarGerenciadorComCardapio();

        List<ItemCardapio> bebidas = g.listarPorCategoria("Bebida");

        assertEquals(1, bebidas.size());
        assertEquals("Coca-Cola", bebidas.get(0).getNome());
    }

    // ---------------- EXCEÇÕES DE MESA E ITEM ----------------

    @Test
    void abrirMesaJaOcupadaLancaExcecao() throws Exception {
        GerenciadorRestaurante g = criarGerenciadorComCardapio();
        g.abrirMesa(5, "Maria");

        try {
            g.abrirMesa(5, "João");
            fail("Deveria ter lançado MesaOcupadaException");
        } catch (MesaOcupadaException e) {
            // era o esperado
        }
    }

    @Test
    void adicionarItemEmMesaNaoAbertaLancaExcecao() throws Exception {
        GerenciadorRestaurante g = criarGerenciadorComCardapio();

        try {
            g.adicionarItem(5, 1, 1); // ninguém abriu a mesa 5
            fail("Deveria ter lançado MesaVaziaException");
        } catch (MesaVaziaException e) {
            // era o esperado
        }
    }

    @Test
    void adicionarItemQueNaoExisteLancaExcecao() throws Exception {
        GerenciadorRestaurante g = criarGerenciadorComCardapio();
        g.abrirMesa(5, "Maria");

        try {
            g.adicionarItem(5, 99, 1); // não existe item 99 no cardápio
            fail("Deveria ter lançado ItemNaoEncontradoException");
        } catch (ItemNaoEncontradoException e) {
            // era o esperado
        }
    }

    @Test
    void removerItemQueNaoEstaNoPedidoLancaExcecao() throws Exception {
        GerenciadorRestaurante g = criarGerenciadorComCardapio();
        g.abrirMesa(5, "Maria");
        g.adicionarItem(5, 1, 1);

        try {
            g.removerItem(5, 3); // o pudim existe no cardápio, mas não foi pedido
            fail("Deveria ter lançado ItemNaoEncontradoException");
        } catch (ItemNaoEncontradoException e) {
            // era o esperado
        }
    }

    @Test
    void fecharContaDeMesaNaoAbertaLancaExcecao() {
        GerenciadorRestaurante g = criarGerenciadorComCardapio();

        try {
            g.fecharConta(5);
            fail("Deveria ter lançado MesaVaziaException");
        } catch (MesaVaziaException e) {
            // era o esperado
        }
    }

    // ---------------- FLUXO COMPLETO ----------------

    // Simula um atendimento do começo ao fim
    @Test
    void fluxoCompletoDeAtendimento() throws Exception {
        GerenciadorRestaurante g = criarGerenciadorComCardapio();

        g.abrirMesa(5, "Maria");
        g.adicionarItem(5, 1, 2); // 2 Coca-Cola  = 16
        g.adicionarItem(5, 2, 1); // 1 Hambúrguer = 30

        Pedido aberto = g.consultarPedidoAberto(5);
        assertEquals(46.0, aberto.calcularSubtotal(), DELTA);
        assertEquals(50.6, aberto.calcularTotal(), DELTA);   // 46 + 10%

        Pedido fechado = g.fecharConta(5);

        assertFalse(fechado.isAberto());
        assertEquals(50.6, fechado.calcularTotal(), DELTA);
        assertFalse(g.listarMesas().get(0).isOcupada());     // mesa 5 ficou livre
        assertEquals(1, g.listarPedidosFechados().size());   // e o pedido foi para o histórico
    }

    @Test
    void depoisDeFecharAMesaPodeSerAbertaParaOutroCliente() throws Exception {
        GerenciadorRestaurante g = criarGerenciadorComCardapio();
        g.abrirMesa(5, "Maria");
        g.adicionarItem(5, 1, 1);
        g.fecharConta(5);

        Pedido novo = g.abrirMesa(5, "João");

        assertEquals("João", novo.getNomeCliente());
        assertEquals(2, novo.getNumero());                              // segundo pedido do dia
        assertEquals(2, g.listarMesas().get(0).getPedidos().size());    // Maria + João
    }

    @Test
    void listarMesasEmOrdemDeNumero() throws Exception {
        GerenciadorRestaurante g = criarGerenciadorComCardapio();
        g.abrirMesa(7, "Ana");
        g.abrirMesa(2, "Bruno");
        g.abrirMesa(5, "Carla");

        List<Mesa> mesas = g.listarMesas();

        assertEquals(2, mesas.get(0).getNumero());
        assertEquals(5, mesas.get(1).getNumero());
        assertEquals(7, mesas.get(2).getNumero());
    }
}