/*
 * ARQUIVO: Main.java                         PACOTE: restaurante
 * RESPONSÁVEL:
 *
 * RESPONSABILIDADE:
 * Ponto de entrada. Apenas "monta" o sistema e abre a janela.
 *
 * IMPLEMENTAÇÃO:
 * - Criar: Persistencia persistencia = new Persistencia("dados/dados.txt");
 * - Criar: GerenciadorRestaurante g = new GerenciadorRestaurante(persistencia);
 *   Esse construtor lança IOException: capturar, mostrar JOptionPane com o erro
 *   e encerrar (System.exit(1)).
 * - Abrir a interface na thread do Swing:
 *   SwingUtilities.invokeLater(() -> new MenuView(g).setVisible(true));
 *
 * OBSERVAÇÕES:
 * - Este arquivo deve ter poucas linhas. Nada de regra de negócio, leitura de
 *   arquivo ou criação de botões aqui.
 *
 * DEPENDE DE: Persistencia, GerenciadorRestaurante, MenuView.
 */
package restaurante;

public class Main {
    public static void main(String[] args) {
        // TODO
    }
}