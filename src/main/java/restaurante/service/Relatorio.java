/*
 * ARQUIVO: Relatorio.java                    PACOTE: restaurante.service
 * RESPONSÁVEL:
 *
 * RESPONSABILIDADE:
 * Calcular e formatar o RELATÓRIO DO DIA a partir dos pedidos fechados.
 *
 * IMPLEMENTAÇÃO (usar STREAMS):
 * - Construtor Relatorio(List<Pedido> pedidosFechados).
 * - Método privado pedidosDoDia(LocalDate dia): filtra pelos pedidos com
 *   dataHoraFechamento.toLocalDate().equals(dia).
 * - double totalArrecadado(LocalDate dia)   -> soma de calcularTotal().
 * - double totalTaxaServico(LocalDate dia)  -> soma de calcularTaxaServico().
 * - long mesasAtendidas(LocalDate dia)      -> quantidade de pedidos fechados no dia.
 * - List<Map.Entry<String, Integer>> itensMaisVendidos(LocalDate dia, int limite):
 *   flatMap nos itens -> Collectors.groupingBy(nome do item,
 *   summingInt(quantidade)) -> ordenar decrescente -> limit(limite).
 * - String gerarTexto(LocalDate dia): monta o texto no formato do README
 *   (valores com NumberFormat.getCurrencyInstance(new Locale("pt", "BR"))).
 *
 * OBSERVAÇÕES:
 * - NÃO chamar LocalDate.now() nos cálculos: a data vem por parâmetro
 *   (senão não dá para testar).
 * - Não ler arquivo nem acessar o Gerenciador aqui.
 *
 * DEPENDE DE: Pedido, ItemPedido.
 */
package restaurante.service;

public class Relatorio {
}