package restaurante.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pedido {
    public static final double TAXA_SERVICO = 0.10;

    private int numero;
    private int numeroMesa;
    private String nomeCliente;

    private List<ItemPedido> itens = new ArrayList<>();

    private boolean aberto = true;

    private LocalDateTime dataHoraFechamento;

    public Pedido(int numero, int numeroMesa, String nomeCliente) {
        if (nomeCliente == null || nomeCliente.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do cliente não pode ser vazio.");
        }
        if (nomeCliente.contains("|")) {
            throw new IllegalArgumentException("O nome do cliente não pode conter o caractere '|'.");
        }
        this.numero = numero;
        this.numeroMesa = numeroMesa;
        this.nomeCliente = nomeCliente;
    }

    public void adicionarItem(ItemCardapio item, int quantidade) {
        if (!aberto) {
            throw new IllegalStateException("O pedido já está fechado.");
        }
        if (item == null) {
            throw new IllegalArgumentException("O item não pode ser nulo.");
        }

        for (ItemPedido ip : itens) {
            if (ip.getItem().getCodigo() == item.getCodigo()) {
                ip.aumentarQuantidade(quantidade);
                return;
            }
        }

        itens.add(new ItemPedido(item, quantidade));
    }

    public boolean removerItem(int codigoItem) {
        for (int i = 0; i < itens.size(); i++) {
            if (itens.get(i).getItem().getCodigo() == codigoItem) {
                itens.remove(i);
                return true;
            }
        }
        return false;
    }

    public double calcularSubtotal() {
        double soma = 0;
        for (ItemPedido ip : itens) {
            soma += ip.calcularSubtotal();
        }
        return soma;
    }

    public double calcularTaxaServico() {
        return calcularSubtotal() * TAXA_SERVICO;
    }

    public double calcularTotal() {
        return calcularSubtotal() + calcularTaxaServico();
    }

    public void fechar(LocalDateTime momento) {
        if (!aberto) {
            throw new IllegalStateException("O pedido já está fechado.");
        }
        if (momento == null) {
            throw new IllegalArgumentException("A data de fechamento não pode ser nula.");
        }
        aberto = false;
        dataHoraFechamento = momento;
    }

    public List<ItemPedido> getItens() {
        return Collections.unmodifiableList(itens);
    }

    public int getNumero() {
        return numero;
    }

    public int getNumeroMesa() {
        return numeroMesa;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public boolean isAberto() {
        return aberto;
    }

    public LocalDateTime getDataHoraFechamento() {
        return dataHoraFechamento;
    }
}
