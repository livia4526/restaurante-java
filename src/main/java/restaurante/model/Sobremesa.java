package restaurante.model;


public class Sobremesa extends ItemCardapio {


    private static final double ADICIONAL_ESPECIAL = 3.0;

    private boolean especial;

    public Sobremesa(int codigo, String nome, double precoBase, boolean especial) {
        super(codigo, nome, precoBase);
        this.especial = especial;
    }

    public boolean isEspecial() {
        return especial;
    }

    @Override
    public double calcularPreco() {
        return getPrecoBase() + (especial ? ADICIONAL_ESPECIAL : 0);
    }

    @Override
    public String getCategoria() {
        return "Sobremesa";
    }
}
