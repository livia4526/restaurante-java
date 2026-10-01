package restaurante.model;

public class Prato extends ItemCardapio {

    private TamanhoPrato tamanho;

    public Prato(int codigo, String nome, double precoBase, TamanhoPrato tamanho) {
        super(codigo, nome, precoBase);

        if (tamanho == null) {
            throw new IllegalArgumentException("O tamanho do prato não pode ser nulo.");
        }

        this.tamanho = tamanho;
    }

    public TamanhoPrato getTamanho() {
        return tamanho;
    }

    @Override
    public double calcularPreco() {
        return getPrecoBase() + tamanho.getAdicional();
    }

    @Override
    public String getCategoria() {
        return "Prato";
    }

    @Override
    public String toString() {
        return super.toString() + " - " + tamanho;
    }
}
