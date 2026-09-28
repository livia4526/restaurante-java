/*
 * ARQUIVO: MenuConsole.java                  PACOTE: restaurante.view
 * RESPONSÁVEL:
 *
 * RESPONSABILIDADE:
 * Interface de CONSOLE (texto) da Unidade 1. Mostra o menu, lê o que o
 * usuário digita e chama o GerenciadorRestaurante.
 * Na Unidade 2 será substituída pelas telas Swing (MenuView e painéis).
 *
 * IMPLEMENTAÇÃO:
 * - Atributos: GerenciadorRestaurante g; Scanner scanner = new Scanner(System.in).
 * - Construtor MenuConsole(GerenciadorRestaurante g).
 * - public void iniciar(): laço (while) mostrando o menu até o usuário sair:
 *     1 - Cadastrar item no cardápio
 *     2 - Listar cardápio (todos ou por categoria)
 *     3 - Abrir mesa
 *     4 - Adicionar item ao pedido da mesa
 *     5 - Remover item do pedido da mesa
 *     6 - Ver conta da mesa (itens, subtotal, taxa de 10%, total)
 *     7 - Fechar conta
 *     8 - Relatório do dia
 *     0 - Sair
 * - Um método privado para cada opção (cadastrarItem(), abrirMesa(), ...),
 *   para o iniciar() não virar um bloco gigante.
 * - Cadastro: perguntar o tipo (1-Bebida, 2-Prato, 3-Sobremesa) e depois só
 *   o campo específico do tipo (com gelo? / tamanho? / especial?).
 * - Relatório: System.out.println(new Relatorio(g.listarPedidosFechados())
 *   .gerarTexto(LocalDate.now()));
 * - try/catch em cada opção: ItemNaoEncontradoException, MesaOcupadaException,
 *   MesaVaziaException, IllegalArgumentException e NumberFormatException ->
 *   mostrar a mensagem e VOLTAR ao menu (o programa nunca pode fechar por erro).
 * - Ler números com Integer.parseInt(scanner.nextLine()) para evitar os
 *   problemas de misturar nextInt() com nextLine().
 *
 * OBSERVAÇÕES:
 * - Nenhum cálculo ou regra de negócio aqui: só ler, chamar o gerenciador
 *   e mostrar o resultado.
 *
 * DEPENDE DE: GerenciadorRestaurante, Relatorio, model/*, exception/*.
 */
package restaurante.view;

public class MenuConsole {
}
