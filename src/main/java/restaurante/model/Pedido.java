/*
 * ARQUIVO: Pedido.java                       PACOTE: restaurante.model
 * RESPONSÁVEL:
 *
 * RESPONSABILIDADE:
 * Pedido de um cliente em uma mesa. É o "todo" da COMPOSIÇÃO: o Pedido
 * cria, guarda e remove seus ItemPedido. Faz os cálculos da conta.
 *
 * IMPLEMENTAÇÃO:
 * - Constante: public static final double TAXA_SERVICO = 0.10;
 * - Atributos: int numero; int numeroMesa; String nomeCliente (sem '|');
 *   List<ItemPedido> itens = new ArrayList<>(); boolean aberto = true;
 *   LocalDateTime dataHoraFechamento (null enquanto aberto).
 * - Construtor Pedido(int numero, int numeroMesa, String nomeCliente).
 * - void adicionarItem(ItemCardapio item, int quantidade):
 *     se o pedido estiver fechado -> IllegalStateException;
 *     se o item (mesmo código) já existe -> aumentarQuantidade;
 *     senão -> itens.add(new ItemPedido(item, quantidade)).
 * - boolean removerItem(int codigoItem): remove a linha inteira; retorna false
 *   se não achou (quem lança ItemNaoEncontradoException é o Gerenciador).
 * - double calcularSubtotal()     -> soma dos subtotais (use stream).
 * - double calcularTaxaServico()  -> calcularSubtotal() * TAXA_SERVICO.
 * - double calcularTotal()        -> subtotal + taxa.
 * - void fechar(LocalDateTime momento): aberto = false; guarda a data.
 *   Fechar duas vezes -> IllegalStateException.
 * - List<ItemPedido> getItens(): return Collections.unmodifiableList(itens);
 * - Getters dos demais atributos + isAberto().
 *
 * OBSERVAÇÕES:
 * - fechar() recebe a data por parâmetro para os testes e a Persistencia
 *   poderem informar datas específicas.
 * - Não buscar itens no cardápio nem mexer em Mesa aqui.
 *
 * DEPENDE DE: ItemPedido, ItemCardapio.
 */
package restaurante.model;

public class Pedido {
}