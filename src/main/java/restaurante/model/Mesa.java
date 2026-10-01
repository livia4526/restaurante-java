package restaurante.model;

import java.util.ArrayList;
import java.util.List;

public class Mesa {

    private int numero;

    private List<Pedido> pedidos = new ArrayList<>();

    public Mesa(int numero) {
        if (numero <= 0) {
            throw new IllegalArgumentException("O número da mesa deve ser maior que zero.");
        }
        this.numero = numero;
    }

    public int getNumero() {
        return numero;
    }

    public Pedido getPedidoAberto() {
        for (Pedido p : pedidos) {
            if (p.isAberto()) {
                return p;
            }
        }
        return null;
    }
    public boolean isOcupada() {
        return getPedidoAberto() != null;
    }

    public void adicionarPedido(Pedido p) {
        if (p == null) {
            throw new IllegalArgumentException("O pedido não pode ser nulo.");
        }
        if (p.isAberto() && isOcupada()) {
            throw new IllegalStateException("A mesa " + numero + " já possui um pedido aberto.");
        }
        pedidos.add(p);
    }

    public List<Pedido> getPedidos() {
        return new ArrayList<>(pedidos);
    }
}
