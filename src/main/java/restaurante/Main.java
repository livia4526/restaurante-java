/*
 * ARQUIVO: Main.java                         PACOTE: restaurante
 * RESPONSÁVEL: Integrante 1 (Alice)
 *
 * RESPONSABILIDADE:
 * Ponto de entrada. Apenas "monta" o sistema e inicia a interface.
 *
 * IMPLEMENTAÇÃO:
 * - Criar: Persistencia persistencia = new Persistencia("dados/dados.txt");
 * - Criar: GerenciadorRestaurante g = new GerenciadorRestaurante(persistencia);
 *   Esse construtor lança IOException: capturar, mostrar a mensagem com
 *   System.out.println e encerrar (System.exit(1)).
 * - Iniciar a interface de console: new MenuConsole(g).iniciar();
 *
 * OBSERVAÇÕES:
 * - Na Unidade 2, a última linha passa a abrir o MenuView (Swing).
 * - Nada de regra de negócio, leitura de arquivo ou menus aqui.
 *
 * DEPENDE DE: Persistencia, GerenciadorRestaurante, MenuConsole.
 */
package restaurante;

public class Main {
    public static void main(String[] args) {
        // TODO
    }
}