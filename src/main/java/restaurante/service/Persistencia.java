/*
 * ARQUIVO: Persistencia.java                 PACOTE: restaurante.service
 * RESPONSÁVEL: Alice Santos
 *
 * RESPONSABILIDADE:
 * Salvar e carregar cardápio e pedidos em um arquivo .txt (separador '|').
 *
 * FORMATO DO ARQUIVO (todos os ITEM primeiro, depois os PEDIDOs):
 *   ITEM|BEBIDA|1|Coca-Cola|6.0|true
 *   ITEM|PRATO|2|Hambúrguer Clássico|25.0|MEDIO
 *   ITEM|SOBREMESA|3|Pudim|8.0|false
 *   PEDIDO|1|5|Maria|FECHADO|2026-09-28T13:45:10
 *   ITEMPEDIDO|1|2|3          (numeroPedido|codigoItem|quantidade)
 *   PEDIDO|2|7|João|ABERTO|
 *
 * OBSERVAÇÕES:
 * - Nenhuma regra de negócio aqui: só ler e gravar.
 * - Linhas inválidas são ignoradas e avisadas em System.err (o programa não cai).
 */
package restaurante.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import restaurante.model.Bebida;
import restaurante.model.ItemCardapio;
import restaurante.model.ItemPedido;
import restaurante.model.Mesa;
import restaurante.model.Pedido;
import restaurante.model.Prato;
import restaurante.model.Sobremesa;
import restaurante.model.TamanhoPrato;

public class Persistencia {

    // Caminho do arquivo, por exemplo "dados/dados.txt"
    private String caminhoArquivo;

    public Persistencia(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }

    // ========================= SALVAR =========================

    // "throws IOException": se der erro ao gravar, quem chamou (o Gerenciador) trata
    public void salvar(List<ItemCardapio> cardapio, Collection<Mesa> mesas) throws IOException {
        File arquivo = new File(caminhoArquivo);

        // Cria a pasta "dados" se ela ainda não existir
        File pasta = arquivo.getParentFile();
        if (pasta != null) {
            Files.createDirectories(pasta.toPath());
        }

        // try-with-resources: o arquivo é fechado sozinho no final do bloco
        try (BufferedWriter escritor = new BufferedWriter(
                new FileWriter(arquivo, StandardCharsets.UTF_8))) {

            // 1) Primeiro TODOS os itens do cardápio
            for (ItemCardapio item : cardapio) {
                escritor.write(montarLinhaItem(item));
                escritor.newLine();
            }

            // 2) Depois os pedidos de todas as mesas, cada um seguido dos seus itens
            for (Mesa mesa : mesas) {
                for (Pedido pedido : mesa.getPedidos()) {
                    escritor.write(montarLinhaPedido(pedido));
                    escritor.newLine();

                    for (ItemPedido ip : pedido.getItens()) {
                        escritor.write("ITEMPEDIDO|" + pedido.getNumero() + "|"
                                + ip.getItem().getCodigo() + "|" + ip.getQuantidade());
                        escritor.newLine();
                    }
                }
            }
        }
    }

    // Monta a linha de um item. O instanceof descobre se é Bebida, Prato ou Sobremesa.
    private String montarLinhaItem(ItemCardapio item) {
        String base = item.getCodigo() + "|" + item.getNome() + "|"
                + Double.toString(item.getPrecoBase()); // Double.toString usa ponto: 6.0

        if (item instanceof Bebida) {
            Bebida b = (Bebida) item; // cast: trata o item como Bebida
            return "ITEM|BEBIDA|" + base + "|" + b.isComGelo();
        }
        if (item instanceof Prato) {
            Prato p = (Prato) item;
            return "ITEM|PRATO|" + base + "|" + p.getTamanho().name(); // name() = "MEDIO"
        }
        Sobremesa s = (Sobremesa) item;
        return "ITEM|SOBREMESA|" + base + "|" + s.isEspecial();
    }

    private String montarLinhaPedido(Pedido pedido) {
        String linha = "PEDIDO|" + pedido.getNumero() + "|" + pedido.getNumeroMesa()
                + "|" + pedido.getNomeCliente() + "|";

        if (pedido.isAberto()) {
            return linha + "ABERTO|"; // data fica vazia
        }
        return linha + "FECHADO|" + pedido.getDataHoraFechamento(); // toString() da data
    }

    // ========================= CARREGAR =========================

    public List<ItemCardapio> carregarCardapio() throws IOException {
        List<ItemCardapio> cardapio = new ArrayList<>();

        for (String linha : lerLinhas()) {
            // split com "\\|" porque o '|' é caractere especial; o -1 mantém campos vazios
            String[] partes = linha.split("\\|", -1);

            if (!partes[0].equals("ITEM")) {
                continue; // não é linha de item: outro método cuida dela
            }

            try {
                cardapio.add(criarItem(partes));
            } catch (RuntimeException e) {
                // Pega número mal escrito, tamanho inexistente, validação do ItemCardapio...
                System.err.println("Linha de item ignorada (" + e.getMessage() + "): " + linha);
            }
        }
        return cardapio;
    }

    // Transforma as partes de uma linha ITEM no objeto certo
    private ItemCardapio criarItem(String[] partes) {
        if (partes.length != 6) {
            throw new IllegalArgumentException("quantidade de campos inválida");
        }

        String tipo = partes[1];
        int codigo = Integer.parseInt(partes[2]);
        String nome = partes[3];
        double precoBase = Double.parseDouble(partes[4]);

        if (tipo.equals("BEBIDA")) {
            return new Bebida(codigo, nome, precoBase, Boolean.parseBoolean(partes[5]));
        }
        if (tipo.equals("PRATO")) {
            return new Prato(codigo, nome, precoBase, TamanhoPrato.valueOf(partes[5]));
        }
        if (tipo.equals("SOBREMESA")) {
            return new Sobremesa(codigo, nome, precoBase, Boolean.parseBoolean(partes[5]));
        }
        throw new IllegalArgumentException("tipo de item desconhecido: " + tipo);
    }

    // Recebe um mapa código -> item para conseguir "remontar" os itens de cada pedido
    public List<Pedido> carregarPedidos(Map<Integer, ItemCardapio> itensPorCodigo)
            throws IOException {
        List<Pedido> pedidos = new ArrayList<>();

        // Mapas auxiliares, indexados pelo número do pedido
        Map<Integer, Pedido> pedidosPorNumero = new HashMap<>();
        Map<Integer, LocalDateTime> datasFechamento = new HashMap<>();

        for (String linha : lerLinhas()) {
            String[] partes = linha.split("\\|", -1);

            try {
                if (partes[0].equals("PEDIDO")) {
                    lerLinhaPedido(partes, pedidos, pedidosPorNumero, datasFechamento);
                } else if (partes[0].equals("ITEMPEDIDO")) {
                    lerLinhaItemPedido(partes, pedidosPorNumero, itensPorCodigo);
                }
            } catch (RuntimeException e) {
                System.err.println("Linha de pedido ignorada (" + e.getMessage() + "): " + linha);
            }
        }

        // Só DEPOIS de adicionar todos os itens é que fechamos os pedidos
        // (um pedido fechado não aceita mais itens)
        for (Pedido pedido : pedidos) {
            LocalDateTime data = datasFechamento.get(pedido.getNumero());
            if (data != null) {
                pedido.fechar(data);
            }
        }
        return pedidos;
    }

    private void lerLinhaPedido(String[] partes, List<Pedido> pedidos,
                                Map<Integer, Pedido> pedidosPorNumero,
                                Map<Integer, LocalDateTime> datasFechamento) {
        if (partes.length != 6) {
            throw new IllegalArgumentException("quantidade de campos inválida");
        }

        int numero = Integer.parseInt(partes[1]);
        int numeroMesa = Integer.parseInt(partes[2]);
        String nomeCliente = partes[3];

        Pedido pedido = new Pedido(numero, numeroMesa, nomeCliente);

        // Guarda a data para fechar depois (se o pedido estiver FECHADO)
        if (partes[4].equals("FECHADO")) {
            datasFechamento.put(numero, LocalDateTime.parse(partes[5]));
        }

        pedidos.add(pedido);
        pedidosPorNumero.put(numero, pedido);
    }

    private void lerLinhaItemPedido(String[] partes, Map<Integer, Pedido> pedidosPorNumero,
                                    Map<Integer, ItemCardapio> itensPorCodigo) {
        if (partes.length != 4) {
            throw new IllegalArgumentException("quantidade de campos inválida");
        }

        int numeroPedido = Integer.parseInt(partes[1]);
        int codigoItem = Integer.parseInt(partes[2]);
        int quantidade = Integer.parseInt(partes[3]);

        Pedido pedido = pedidosPorNumero.get(numeroPedido);
        ItemCardapio item = itensPorCodigo.get(codigoItem);

        if (pedido == null) {
            throw new IllegalArgumentException("pedido " + numeroPedido + " não encontrado");
        }
        if (item == null) {
            throw new IllegalArgumentException("item " + codigoItem + " não encontrado");
        }
        pedido.adicionarItem(item, quantidade);
    }

    // ========================= AUXILIAR =========================

    // Lê o arquivo inteiro e devolve a lista de linhas (sem linhas em branco).
    // Se o arquivo ainda não existe (primeira execução), devolve lista vazia.
    private List<String> lerLinhas() throws IOException {
        List<String> linhas = new ArrayList<>();
        File arquivo = new File(caminhoArquivo);

        if (!arquivo.exists()) {
            return linhas;
        }

        try (BufferedReader leitor = new BufferedReader(
                new FileReader(arquivo, StandardCharsets.UTF_8))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    linhas.add(linha);
                }
            }
        }
        return linhas;
    }
}