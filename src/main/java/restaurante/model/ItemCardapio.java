/*
 * ARQUIVO: ItemCardapio.java                 PACOTE: restaurante.model
 * RESPONSÁVEL: Alice Santos
 *
 * RESPONSABILIDADE:
 * Classe ABSTRATA base de todo item do cardápio (HERANÇA + ABSTRAÇÃO).
 * Bebida, Prato e Sobremesa herdam desta classe.
 */
package restaurante.model;

import java.util.Locale;

// "abstract" = não dá para fazer "new ItemCardapio(...)".
// Ela serve só de modelo para as classes filhas (Bebida, Prato, Sobremesa).
public abstract class ItemCardapio {

    // ENCAPSULAMENTO: atributos privados, só acessíveis por getters
    private int codigo;
    private String nome;
    private double precoBase;

    // CONSTRUTOR: valida os dados antes de guardar.
    // Se algo estiver errado, lança IllegalArgumentException (exceção não verificada).
    public ItemCardapio(int codigo, String nome, double precoBase) {
        if (codigo <= 0) {
            throw new IllegalArgumentException("O código do item deve ser maior que zero.");
        }
        // Checa null ANTES de chamar trim(), senão daria NullPointerException
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do item não pode ser vazio.");
        }
        // O '|' é o separador usado no arquivo .txt, então não pode aparecer no nome
        if (nome.contains("|")) {
            throw new IllegalArgumentException("O nome do item não pode conter o caractere '|'.");
        }
        if (precoBase < 0) {
            throw new IllegalArgumentException("O preço base não pode ser negativo.");
        }

        this.codigo = codigo;
        this.nome = nome;
        this.precoBase = precoBase;
    }

    // GETTERS (não há setters: o item não é editado depois de criado)
    public int getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public double getPrecoBase() {
        return precoBase;
    }

    // MÉTODOS ABSTRATOS: sem corpo aqui. Cada filha é OBRIGADA a implementar.
    // É isso que permite o POLIMORFISMO: cada tipo calcula o preço do seu jeito.
    public abstract double calcularPreco();

    // Devolve "Bebida", "Prato" ou "Sobremesa"
    public abstract String getCategoria();

    // Exemplo de saída: [3] Pudim (Sobremesa) - R$ 11,00
    // %d = inteiro, %s = texto, %.2f = decimal com 2 casas.
    // O Locale pt-BR faz o decimal sair com vírgula (11,00) em qualquer computador.
    @Override
    public String toString() {
        return String.format(new Locale("pt", "BR"), "[%d] %s (%s) - R$ %.2f",
                codigo, nome, getCategoria(), calcularPreco());
    }
}