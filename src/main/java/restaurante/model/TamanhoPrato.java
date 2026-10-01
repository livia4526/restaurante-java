package restaurante.model;

public enum TamanhoPrato {

    PEQUENO(0.0, "Pequeno"),
    MEDIO(5.0, "Médio"),
    GRANDE(10.0, "Grande");

    private final double adicional;
    private final String descricao;


    TamanhoPrato(double adicional, String descricao) {
        this.adicional = adicional;
        this.descricao = descricao;
    }

    public double getAdicional() {
        return adicional;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
