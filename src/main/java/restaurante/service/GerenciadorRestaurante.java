/*
 * ARQUIVO: GerenciadorRestaurante.java       PACOTE: restaurante.service
 * RESPONSÁVEL:
 *
 * RESPONSABILIDADE:
 * Lógica central. A interface SÓ conversa com esta classe. Aplica as regras
 * de negócio, lança as exceções customizadas e salva após cada alteração.
 *
 * IMPLEMENTAÇÃO:
 * - Atributos: List<ItemCardapio> cardapio (ArrayList);
 *   Map<Integer, Mesa> mesas (HashMap); Persistencia persistencia (pode ser null);
 *   int proximoCodigoItem = 1; int proximoNumeroPedido = 1.
 * - Construtor vazio: sem arquivo (usado nos testes).
 * - Construtor (Persistencia p) throws IOException: carrega o cardápio, depois
 *   os pedidos (distribuindo cada um na Mesa de numeroMesa), e ajusta os
 *   contadores para (maior valor lido + 1).
 * - Métodos (PRIMEIRO publicar só as assinaturas, com
 *   throw new UnsupportedOperationException(), para as telas começarem):
 *     int proximoCodigoItem()
 *     void cadastrarItem(ItemCardapio item)   [código repetido -> IllegalArgumentException]
 *     List<ItemCardapio> listarCardapio()
 *     List<ItemCardapio> listarPorCategoria(String categoria)   [stream + filter]
 *     ItemCardapio buscarItem(int codigo) throws ItemNaoEncontradoException
 *     Pedido abrirMesa(int numeroMesa, String nomeCliente) throws MesaOcupadaException
 *         [cria a Mesa se não existir]
 *     void adicionarItem(int numeroMesa, int codigoItem, int quantidade)
 *         throws MesaVaziaException, ItemNaoEncontradoException
 *     void removerItem(int numeroMesa, int codigoItem)
 *         throws MesaVaziaException, ItemNaoEncontradoException
 *     Pedido consultarPedidoAberto(int numeroMesa) throws MesaVaziaException
 *     List<Mesa> listarMesas()   [ordenadas por número]
 *     Pedido fecharConta(int numeroMesa) throws MesaVaziaException
 *         [pedido.fechar(LocalDateTime.now()); retorna o pedido fechado]
 *     List<Pedido> listarPedidosFechados()   [flatMap nos pedidos de todas as mesas]
 * - Método privado obterMesaOcupada(num): mesa inexistente ou livre ->
 *   MesaVaziaException. Reutilizar em adicionar/remover/consultar/fechar.
 * - Método privado salvar(): se persistencia != null, chama
 *   persistencia.salvar(cardapio, mesas.values()); IOException ->
 *   throw new UncheckedIOException(e). Chamar após cadastrar, abrir,
 *   adicionar, remover e fechar.
 *
 * OBSERVAÇÕES:
 * - Nada de Swing, System.out ou formatação de relatório aqui.
 * - Não retornar as listas internas diretamente: devolver cópias ou listas
 *   somente leitura.
 *
 * DEPENDE DE: model/*, exception/*, Persistencia.
 */
package restaurante.service;

public class GerenciadorRestaurante {
}