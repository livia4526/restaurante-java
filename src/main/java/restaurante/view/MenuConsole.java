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

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import restaurante.exception.ItemNaoEncontradoException;
import restaurante.exception.MesaOcupadaException;
import restaurante.exception.MesaVaziaException;
import restaurante.model.Bebida;
import restaurante.model.ItemCardapio;
import restaurante.model.ItemPedido;
import restaurante.model.Pedido;
import restaurante.model.Prato;
import restaurante.model.Sobremesa;
import restaurante.model.TamanhoPrato;
import restaurante.service.GerenciadorRestaurante;
import restaurante.service.Relatorio;

public class MenuConsole {
    private final GerenciadorRestaurante g;
    private final Scanner scanner = new Scanner(System.in);

    public MenuConsole(GerenciadorRestaurante g) {
        if (g == null) {
            throw new IllegalArgumentException("O gerenciador não pode ser nulo.");
        }
        this.g = g;
    }

    public void iniciar() {
        boolean executando = true;
        while (executando) {
            mostrarMenu();
            if (!scanner.hasNextLine()) {
                break;
            }

            try {
                int opcao = Integer.parseInt(scanner.nextLine().trim());
                switch (opcao) {
                    case 1 -> cadastrarItem();
                    case 2 -> listarCardapio();
                    case 3 -> abrirMesa();
                    case 4 -> adicionarItem();
                    case 5 -> removerItem();
                    case 6 -> verConta();
                    case 7 -> fecharConta();
                    case 8 -> mostrarRelatorio();
                    case 0 -> executando = false;
                    default -> System.out.println("Opção inválida. Escolha uma opção do menu.");
                }
            } catch (NumberFormatException e) {
                mostrarErro(e);
            }
        }
        System.out.println("Sistema encerrado.");
    }

    private void mostrarMenu() {
        System.out.println("\n=== RESTAURANTE ===");
        System.out.println("1 - Cadastrar item no cardápio");
        System.out.println("2 - Listar cardápio (todos ou por categoria)");
        System.out.println("3 - Abrir mesa");
        System.out.println("4 - Adicionar item ao pedido da mesa");
        System.out.println("5 - Remover item do pedido da mesa");
        System.out.println("6 - Ver conta da mesa");
        System.out.println("7 - Fechar conta");
        System.out.println("8 - Relatório do dia");
        System.out.println("0 - Sair");
        System.out.print("Opção: ");
    }

    private void cadastrarItem() {
        try {
            System.out.println("Tipo: 1-Bebida, 2-Prato, 3-Sobremesa");
            int tipo = lerInteiro("Escolha: ");
            int codigo = g.proximoCodigoItem();
            String nome = lerTexto("Nome: ");
            double preco = lerDouble("Preço base: R$ ");

            ItemCardapio item;
            switch (tipo) {
                case 1 -> item = new Bebida(codigo, nome, preco,
                        lerSimNao("É com gelo? (s/n): "));
                case 2 -> {
                    System.out.println("Tamanho: 1-Pequeno, 2-Médio, 3-Grande");
                    int tamanho = lerInteiro("Escolha: ");
                    TamanhoPrato[] tamanhos = TamanhoPrato.values();
                    if (tamanho < 1 || tamanho > tamanhos.length) {
                        throw new IllegalArgumentException("Tamanho inválido.");
                    }
                    item = new Prato(codigo, nome, preco, tamanhos[tamanho - 1]);
                }
                case 3 -> item = new Sobremesa(codigo, nome, preco,
                        lerSimNao("É especial? (s/n): "));
                default -> throw new IllegalArgumentException("Tipo de item inválido.");
            }

            g.cadastrarItem(item);
            System.out.println("Item cadastrado: " + item);
        } catch (IllegalArgumentException e) {
            mostrarErro(e);
        }
    }

    private void listarCardapio() {
        try {
            System.out.println("Listar: 1-Todos, 2-Bebidas, 3-Pratos, 4-Sobremesas");
            int filtro = lerInteiro("Escolha: ");
            List<ItemCardapio> itens;
            if (filtro == 1) {
                itens = g.listarCardapio();
            } else if (filtro >= 2 && filtro <= 4) {
                String[] categorias = {"Bebida", "Prato", "Sobremesa"};
                itens = g.listarPorCategoria(categorias[filtro - 2]);
            } else {
                throw new IllegalArgumentException("Opção de listagem inválida.");
            }

            if (itens.isEmpty()) {
                System.out.println("Nenhum item encontrado.");
            } else {
                itens.forEach(System.out::println);
            }
        } catch (IllegalArgumentException e) {
            mostrarErro(e);
        }
    }

    private void abrirMesa() {
        try {
            int numero = lerInteiro("Número da mesa: ");
            String cliente = lerTexto("Nome do cliente: ");
            Pedido pedido = g.abrirMesa(numero, cliente);
            System.out.println("Mesa " + numero + " aberta para " + cliente
                    + " (pedido " + pedido.getNumero() + ").");
        } catch (MesaOcupadaException | IllegalArgumentException e) {
            mostrarErro(e);
        }
    }

    private void adicionarItem() {
        try {
            int mesa = lerInteiro("Número da mesa: ");
            int codigo = lerInteiro("Código do item: ");
            int quantidade = lerInteiro("Quantidade: ");
            g.adicionarItem(mesa, codigo, quantidade);
            System.out.println("Item adicionado ao pedido.");
        } catch (MesaVaziaException | ItemNaoEncontradoException
                 | IllegalArgumentException e) {
            mostrarErro(e);
        }
    }

    private void removerItem() {
        try {
            int mesa = lerInteiro("Número da mesa: ");
            int codigo = lerInteiro("Código do item: ");
            g.removerItem(mesa, codigo);
            System.out.println("Item removido do pedido.");
        } catch (MesaVaziaException | ItemNaoEncontradoException
                 | IllegalArgumentException e) {
            mostrarErro(e);
        }
    }

    private void verConta() {
        try {
            int numero = lerInteiro("Número da mesa: ");
            Pedido pedido = g.consultarPedidoAberto(numero);
            mostrarPedido(pedido);
        } catch (MesaVaziaException | IllegalArgumentException e) {
            mostrarErro(e);
        }
    }

    private void fecharConta() {
        try {
            int numero = lerInteiro("Número da mesa: ");
            Pedido pedido = g.fecharConta(numero);
            System.out.println("Conta fechada:");
            mostrarPedido(pedido);
        } catch (MesaVaziaException | IllegalArgumentException e) {
            mostrarErro(e);
        }
    }

    private void mostrarRelatorio() {
        try {
            System.out.println(new Relatorio(g.listarPedidosFechados())
                    .gerarTexto(LocalDate.now()));
        } catch (IllegalArgumentException e) {
            mostrarErro(e);
        }
    }

    private void mostrarPedido(Pedido pedido) {
        System.out.println("Pedido " + pedido.getNumero() + " - Mesa " + pedido.getNumeroMesa()
                + " - Cliente: " + pedido.getNomeCliente());
        for (ItemPedido item : pedido.getItens()) {
            System.out.printf(new Locale("pt", "BR"), "%s x%d - R$ %.2f%n",
                    item.getItem().getNome(), item.getQuantidade(), item.calcularSubtotal());
        }
        System.out.printf(new Locale("pt", "BR"), "Subtotal: R$ %.2f%n", pedido.calcularSubtotal());
        System.out.printf(new Locale("pt", "BR"), "Taxa de serviço (10%%): R$ %.2f%n",
                pedido.calcularTaxaServico());
        System.out.printf(new Locale("pt", "BR"), "Total: R$ %.2f%n", pedido.calcularTotal());
    }

    private int lerInteiro(String mensagem) {
        System.out.print(mensagem);
        return Integer.parseInt(scanner.nextLine().trim());
    }

    private double lerDouble(String mensagem) {
        System.out.print(mensagem);
        return Double.parseDouble(scanner.nextLine().trim().replace(',', '.'));
    }

    private String lerTexto(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine().trim();
    }

    private boolean lerSimNao(String mensagem) {
        String resposta = lerTexto(mensagem);
        if (resposta.equalsIgnoreCase("s")) {
            return true;
        }
        if (resposta.equalsIgnoreCase("n")) {
            return false;
        }
        throw new IllegalArgumentException("Responda com 's' ou 'n'.");
    }

    private void mostrarErro(Exception e) {
        System.out.println("Erro: " + e.getMessage());
    }
}
