/*
 * ARQUIVO: GerenciadorRestauranteTest.java   PACOTE: restaurante.service (em src/test)
 * RESPONSÁVEL: Lívia
 *
 * O QUE TESTAR (usar new GerenciadorRestaurante() — SEM arquivo):
 * - @BeforeEach: gerenciador novo com 3 itens cadastrados.
 * - buscarItem existente; inexistente -> assertThrows(ItemNaoEncontradoException).
 * - listarPorCategoria("Bebida") retorna só bebidas.
 * - abrirMesa e abrir de novo -> MesaOcupadaException.
 * - adicionarItem em mesa não aberta -> MesaVaziaException.
 * - adicionarItem com código inválido -> ItemNaoEncontradoException.
 * - Fluxo completo: abrir, adicionar, fecharConta -> total correto, mesa livre,
 *   pedido presente em listarPedidosFechados().
 * - Após fechar, abrir a mesma mesa para outro cliente funciona.
 *
 * DEPENDE DE: GerenciadorRestaurante e todo o model/exception.
 */
package restaurante.service;

class GerenciadorRestauranteTest {
}