package restaurante.model;

public class ItemPedido {
    private ItemCardapio item;
    private int quantidade;

    public ItemPedido(ItemCardapio item, int quantidade) {
        if (item == null) {
            throw new IllegalArgumentException("O item não pode ser nulo.");
        }
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
        }
        this.item = item;
        this.quantidade = quantidade;
    }

    public void aumentarQuantidade(int qtd) {
        if (qtd <= 0) {
            throw new IllegalArgumentException("A quantidade a aumentar deve ser maior que zero.");
        }
        quantidade += qtd;
    }

    public double calcularSubtotal() {
        return item.calcularPreco() * quantidade;
    }

    public ItemCardapio getItem() {
        return item;
    }

    public int getQuantidade() {
        return quantidade;
    }
}
