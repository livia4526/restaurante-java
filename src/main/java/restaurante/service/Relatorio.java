/*
 * ARQUIVO: Relatorio.java                    PACOTE: restaurante.service
 * RESPONSÁVEL: Alice Santos
 *
 * RESPONSABILIDADE:
 * Calcular e formatar o RELATÓRIO DO DIA a partir dos pedidos fechados.
 *
 * OBSERVAÇÕES:
 * - A data vem por parâmetro (não usamos LocalDate.now() aqui), assim dá para testar.
 * - Não lê arquivo nem acessa o Gerenciador: só recebe a lista de pedidos.
 */
package restaurante.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import restaurante.model.ItemPedido;
import restaurante.model.Pedido;

public class Relatorio {

    // Quantos itens aparecem no ranking do texto do relatório
    private static final int LIMITE_RANKING = 3;

    private List<Pedido> pedidosFechados;

    public Relatorio(List<Pedido> pedidosFechados) {
        // Guarda uma cópia. Se vier null, usa lista vazia (evita NullPointerException)
        if (pedidosFechados == null) {
            this.pedidosFechados = new ArrayList<>();
        } else {
            this.pedidosFechados = new ArrayList<>(pedidosFechados);
        }
    }

    // Filtra só os pedidos que foram fechados no dia informado
    private List<Pedido> pedidosDoDia(LocalDate dia) {
        List<Pedido> doDia = new ArrayList<>();

        for (Pedido pedido : pedidosFechados) {
            // toLocalDate() tira a hora e deixa só a data, para comparar com o dia
            if (pedido.getDataHoraFechamento() != null
                    && pedido.getDataHoraFechamento().toLocalDate().equals(dia)) {
                doDia.add(pedido);
            }
        }
        return doDia;
    }

    // Soma do total (subtotal + taxa) de cada pedido do dia
    public double totalArrecadado(LocalDate dia) {
        double soma = 0;
        for (Pedido pedido : pedidosDoDia(dia)) {
            soma += pedido.calcularTotal();
        }
        return soma;
    }

    // Soma só da taxa de serviço (10%) de cada pedido do dia
    public double totalTaxaServico(LocalDate dia) {
        double soma = 0;
        for (Pedido pedido : pedidosDoDia(dia)) {
            soma += pedido.calcularTaxaServico();
        }
        return soma;
    }

    // Cada pedido fechado conta como uma mesa atendida
    public long mesasAtendidas(LocalDate dia) {
        return pedidosDoDia(dia).size();
    }

    // Retorna os itens mais vendidos: pares (nome do item, quantidade total),
    // do que mais vendeu para o que menos vendeu, limitado a "limite" itens.
    public List<Map.Entry<String, Integer>> itensMaisVendidos(LocalDate dia, int limite) {
        // 1) Soma a quantidade de cada item (chave = nome, valor = total vendido)
        Map<String, Integer> totais = new HashMap<>();

        for (Pedido pedido : pedidosDoDia(dia)) {
            for (ItemPedido ip : pedido.getItens()) {
                String nome = ip.getItem().getNome();

                if (totais.containsKey(nome)) {
                    totais.put(nome, totais.get(nome) + ip.getQuantidade());
                } else {
                    totais.put(nome, ip.getQuantidade());
                }
            }
        }

        // 2) Passa o mapa para uma lista de pares (nome, quantidade)
        List<Map.Entry<String, Integer>> ranking = new ArrayList<>();
        for (String nome : totais.keySet()) {
            ranking.add(Map.entry(nome, totais.get(nome)));
        }

        // 3) Ordena do maior para o menor (método da "bolha").
        //    Se a quantidade empatar, desempata pelo nome em ordem alfabética.
        for (int i = 0; i < ranking.size() - 1; i++) {
            for (int j = 0; j < ranking.size() - 1 - i; j++) {
                Map.Entry<String, Integer> atual = ranking.get(j);
                Map.Entry<String, Integer> proximo = ranking.get(j + 1);

                boolean trocar = proximo.getValue() > atual.getValue()
                        || (proximo.getValue().equals(atual.getValue())
                            && proximo.getKey().compareTo(atual.getKey()) < 0);

                if (trocar) {
                    ranking.set(j, proximo);
                    ranking.set(j + 1, atual);
                }
            }
        }

        // 4) Mantém só os "limite" primeiros
        List<Map.Entry<String, Integer>> resultado = new ArrayList<>();
        for (int i = 0; i < ranking.size() && i < limite; i++) {
            resultado.add(ranking.get(i));
        }
        return resultado;
    }

    // Monta o texto completo do relatório (formato do README)
    public String gerarTexto(LocalDate dia) {
        String linha = "=====================================";
        String dataTexto = dia.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        // StringBuilder: monta um texto grande aos poucos, sem criar várias Strings
        StringBuilder texto = new StringBuilder();
        texto.append(linha).append("\n");
        texto.append("  RELATÓRIO DO DIA - ").append(dataTexto).append("\n");
        texto.append(linha).append("\n\n");

        texto.append("Total arrecadado: ").append(formatarMoeda(totalArrecadado(dia))).append("\n");
        texto.append("Taxa de serviço arrecadada: ")
                .append(formatarMoeda(totalTaxaServico(dia))).append("\n\n");

        texto.append("Itens mais vendidos:\n");
        List<Map.Entry<String, Integer>> ranking = itensMaisVendidos(dia, LIMITE_RANKING);

        if (ranking.isEmpty()) {
            texto.append("  Nenhuma venda registrada.\n");
        }
        for (int i = 0; i < ranking.size(); i++) {
            Map.Entry<String, Integer> entrada = ranking.get(i);
            int quantidade = entrada.getValue();
            String unidade = quantidade == 1 ? "unidade" : "unidades";

            // %-22s = texto alinhado à esquerda em 22 espaços; %2d = número em 2 espaços
            texto.append(String.format("  %d. %-22s - %2d %s\n",
                    i + 1, entrada.getKey(), quantidade, unidade));
        }

        texto.append("\nMesas atendidas: ").append(mesasAtendidas(dia)).append("\n");
        texto.append(linha);
        return texto.toString();
    }

    // Formata como R$ 1.234,50 (ponto nos milhares e vírgula nos centavos)
    private String formatarMoeda(double valor) {
        return String.format(new Locale("pt", "BR"), "R$ %,.2f", valor);
    }
}