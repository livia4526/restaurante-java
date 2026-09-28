/*
 * ARQUIVO: Persistencia.java                 PACOTE: restaurante.service
 * RESPONSÁVEL:
 *
 * RESPONSABILIDADE:
 * Salvar e carregar cardápio e pedidos em um arquivo .txt (separador '|').
 *
 * FORMATO DO ARQUIVO (gravar TODOS os ITEM primeiro, depois os PEDIDOs):
 *   ITEM|BEBIDA|1|Coca-Cola|6.0|true
 *   ITEM|PRATO|2|Hambúrguer Clássico|25.0|MEDIO
 *   ITEM|SOBREMESA|3|Pudim|8.0|false
 *   PEDIDO|1|5|Maria|FECHADO|2026-09-28T13:45:10
 *   ITEMPEDIDO|1|2|3          (numeroPedido|codigoItem|quantidade)
 *   PEDIDO|2|7|João|ABERTO|
 *
 * IMPLEMENTAÇÃO:
 * - Construtor Persistencia(String caminhoArquivo).
 * - void salvar(List<ItemCardapio> cardapio, Collection<Mesa> mesas) throws IOException:
 *   criar a pasta se não existir (Files.createDirectories); escrever em UTF-8
 *   (new FileWriter(arquivo, StandardCharsets.UTF_8) + BufferedWriter);
 *   usar instanceof para descobrir o tipo do item.
 * - List<ItemCardapio> carregarCardapio() throws IOException:
 *   arquivo inexistente -> lista vazia (primeira execução).
 * - List<Pedido> carregarPedidos(Map<Integer, ItemCardapio> itensPorCodigo) throws IOException:
 *   para cada PEDIDO, criar o Pedido, adicionar seus ITEMPEDIDO e, SÓ DEPOIS,
 *   se for FECHADO, chamar pedido.fechar(data).
 * - Números: gravar com Double.toString e ler com Double.parseDouble (ponto
 *   decimal, nunca vírgula). Datas: LocalDateTime.toString()/parse().
 * - Usar split("\\|", -1) (o '|' precisa de escape e o -1 mantém campos vazios).
 *
 * OBSERVAÇÕES:
 * - Nada de regra de negócio nem Swing aqui.
 * - Linhas inválidas: ignorar e registrar em System.err, sem derrubar o programa.
 *
 * DEPENDE DE: model/*.
 */
package restaurante.service;

public class Persistencia {
}