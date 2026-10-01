/*
 * ARQUIVO: Bebida.java                       PACOTE: restaurante.model
 * RESPONSÁVEL: Alice Santos
 *
 * RESPONSABILIDADE:
 * Item do cardápio do tipo bebida. Herda de ItemCardapio.
 *
 * OBSERVAÇÃO:
 * "Coca-Cola com gelo" e "Coca-Cola sem gelo" são DOIS itens cadastrados.
 */
package restaurante.model;

// HERANÇA: Bebida "é um" ItemCardapio, então já ganha código, nome e precoBase
public class Bebida extends ItemCardapio {

    // Constante: valor fixo (static final). Por convenção, nome em MAIÚSCULAS
    private static final double ADICIONAL_GELO = 2.0;

    // Atributo próprio da bebida (os outros vêm da classe mãe)
    private boolean comGelo;

    public Bebida(int codigo, String nome, double precoBase, boolean comGelo) {
        // super(...) chama o construtor da classe mãe (que faz as validações)
        super(codigo, nome, precoBase);
        this.comGelo = comGelo;
    }

    public boolean isComGelo() {
        return comGelo;
    }

    // SOBRESCRITA (@Override): implementa o método abstrato da classe mãe.
    // Operador ternário: se comGelo for true soma o adicional, senão soma 0.
    @Override
    public double calcularPreco() {
        return getPrecoBase() + (comGelo ? ADICIONAL_GELO : 0);
    }

    @Override
    public String getCategoria() {
        return "Bebida";
    }

    // Reaproveita o toString() da mãe (super.toString()) e acrescenta " (com gelo)"
    @Override
    public String toString() {
        if (comGelo) {
            return super.toString() + " (com gelo)";
        }
        return super.toString();
    }
}