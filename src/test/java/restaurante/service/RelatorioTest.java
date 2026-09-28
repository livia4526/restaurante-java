/*
 * ARQUIVO: RelatorioTest.java                PACOTE: restaurante.service (em src/test)
 * RESPONSÁVEL: Lívia
 *
 * O QUE TESTAR (criar Pedidos à mão e fechar com datas fixas, sem arquivo):
 * - totalArrecadado e totalTaxaServico do dia batem com a soma dos pedidos.
 * - Pedido fechado em OUTRO dia não entra no relatório.
 * - itensMaisVendidos: ordem decrescente de quantidade e respeita o limite.
 * - mesasAtendidas = número de pedidos fechados no dia.
 * - Dia sem vendas: totais 0 e lista vazia (sem exceção).
 * - gerarTexto contém "Total arrecadado" e o nome do item mais vendido.
 *
 * DEPENDE DE: Relatorio, Pedido, itens do model.
 */
package restaurante.service;

class RelatorioTest {
}