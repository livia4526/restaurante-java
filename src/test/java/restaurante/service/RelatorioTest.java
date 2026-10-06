/*
 * ARQUIVO: RelatorioTest.java                PACOTE: restaurante.service (em src/test)
 * RESPONSÁVEL: Lívia
 *
 * O QUE TESTA (Pedidos criados à mão e fechados com datas fixas, sem arquivo):
 * - totalArrecadado e totalTaxaServico do dia batem com a soma dos pedidos.
 * - Pedido fechado em OUTRO dia e pedido ainda aberto não entram no relatório.
 * - itensMaisVendidos: ordem decrescente de quantidade, desempate pelo nome
 *   e respeita o limite.
 * - mesasAtendidas = número de pedidos fechados no dia.
 * - Dia sem vendas: totais 0 e lista vazia (sem exceção).
 * - gerarTexto contém as informações principais.
 *
 * PEDIDOS USADOS (criados pelo método criarPedidos()):
 *   Pedido 1 - 05/10 - 2 Coca (16) + 1 Hambúrguer (30) = 46  -> total 50,60
 *   Pedido 2 - 05/10 - 1 Coca (8)  + 1 Pudim (11)      = 19  -> total 20,90
 *   Pedido 3 - 04/10 - 5 Hambúrguer                    (OUTRO dia)
 *   Pedido 4 - ainda aberto                            (não pode contar)
 *
 * DEPENDE DE: Relatorio, Pedido, itens do model.
 */
package restaurante.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import restaurante.model.Bebida;
import restaurante.model.Pedido;
import restaurante.model.Prato;
import restaurante.model.Sobremesa;
import restaurante.model.TamanhoPrato;

class RelatorioTest {

    private static final double DELTA = 0.001;

    // O dia que vamos analisar e o dia anterior
    private static final LocalDate DIA = LocalDate.of(2026, 10, 5);
    private static final LocalDate DIA_ANTERIOR = LocalDate.of(2026, 10, 4);

    private final Bebida coca = new Bebida(1, "Coca-Cola", 6.0, true);                     // 8.00
    private final Prato hamburguer = new Prato(2, "Hambúrguer", 25.0, TamanhoPrato.MEDIO); // 30.00
    private final Sobremesa pudim = new Sobremesa(3, "Pudim", 8.0, true);                  // 11.00

    // Monta a lista de pedidos descrita no comentário do topo
    private List<Pedido> criarPedidos() {
        List<Pedido> pedidos = new ArrayList<>();

        Pedido p1 = new Pedido(1, 5, "Maria");
        p1.adicionarItem(coca, 2);
        p1.adicionarItem(hamburguer, 1);
        p1.fechar(DIA.atTime(12, 0));          // atTime: 05/10/2026 às 12:00
        pedidos.add(p1);

        Pedido p2 = new Pedido(2, 3, "João");
        p2.adicionarItem(coca, 1);
        p2.adicionarItem(pudim, 1);
        p2.fechar(DIA.atTime(20, 30));
        pedidos.add(p2);

        Pedido p3 = new Pedido(3, 5, "Ana");
        p3.adicionarItem(hamburguer, 5);
        p3.fechar(DIA_ANTERIOR.atTime(19, 0)); // outro dia
        pedidos.add(p3);

        Pedido p4 = new Pedido(4, 7, "Bruno");
        p4.adicionarItem(pudim, 10);           // ainda aberto (sem data)
        pedidos.add(p4);

        return pedidos;
    }

    @Test
    void totalArrecadadoSomaOsPedidosDoDia() {
        Relatorio relatorio = new Relatorio(criarPedidos());

        // 50.60 + 20.90 = 71.50 (o pedido do dia anterior e o aberto ficam de fora)
        assertEquals(71.5, relatorio.totalArrecadado(DIA), DELTA);
    }

    @Test
    void taxaDeServicoSomaAsTaxasDoDia() {
        Relatorio relatorio = new Relatorio(criarPedidos());

        // 4.60 + 1.90 = 6.50
        assertEquals(6.5, relatorio.totalTaxaServico(DIA), DELTA);
    }

    @Test
    void cadaPedidoFechadoContaComoUmaMesaAtendida() {
        Relatorio relatorio = new Relatorio(criarPedidos());

        assertEquals(2, relatorio.mesasAtendidas(DIA));
        assertEquals(1, relatorio.mesasAtendidas(DIA_ANTERIOR));
    }

    @Test
    void pedidoDeOutroDiaEntraSoNoDiaDele() {
        Relatorio relatorio = new Relatorio(criarPedidos());

        // 5 Hambúrgueres = 150 + 10% = 165
        assertEquals(165.0, relatorio.totalArrecadado(DIA_ANTERIOR), DELTA);
    }

    // Map.Entry é um "par": getKey() = nome do item, getValue() = quantidade vendida
    @Test
    void maisVendidosEmOrdemDecrescente() {
        Relatorio relatorio = new Relatorio(criarPedidos());

        List<Map.Entry<String, Integer>> ranking = relatorio.itensMaisVendidos(DIA, 3);

        // Coca: 2 + 1 = 3 | Hambúrguer: 1 | Pudim: 1
        assertEquals(3, ranking.size());
        assertEquals("Coca-Cola", ranking.get(0).getKey());
        assertEquals(3, ranking.get(0).getValue());
        // Empate (1 e 1): desempata pela ordem alfabética, Hambúrguer antes de Pudim
        assertEquals("Hambúrguer", ranking.get(1).getKey());
        assertEquals("Pudim", ranking.get(2).getKey());
    }

    @Test
    void maisVendidosRespeitaOLimite() {
        Relatorio relatorio = new Relatorio(criarPedidos());

        List<Map.Entry<String, Integer>> ranking = relatorio.itensMaisVendidos(DIA, 1);

        assertEquals(1, ranking.size());
        assertEquals("Coca-Cola", ranking.get(0).getKey());
    }

    @Test
    void diaSemVendasDaZeroSemErro() {
        Relatorio relatorio = new Relatorio(criarPedidos());
        LocalDate diaSemVendas = LocalDate.of(2026, 1, 1);

        assertEquals(0.0, relatorio.totalArrecadado(diaSemVendas), DELTA);
        assertEquals(0.0, relatorio.totalTaxaServico(diaSemVendas), DELTA);
        assertEquals(0, relatorio.mesasAtendidas(diaSemVendas));
        assertEquals(0, relatorio.itensMaisVendidos(diaSemVendas, 3).size());
    }

    @Test
    void textoDoRelatorioTemAsInformacoesPrincipais() {
        Relatorio relatorio = new Relatorio(criarPedidos());

        String texto = relatorio.gerarTexto(DIA);

        // contains: confere se um pedaço de texto aparece dentro do outro (Aula 04)
        assertTrue(texto.contains("05/10/2026"));
        assertTrue(texto.contains("Total arrecadado: R$ 71,50"));
        assertTrue(texto.contains("Coca-Cola"));
        assertTrue(texto.contains("Mesas atendidas: 2"));
    }
}