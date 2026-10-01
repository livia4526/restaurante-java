/*
 * ARQUIVO: Main.java                         PACOTE: restaurante
 * RESPONSÁVEL: Alice Santos
 *
 * RESPONSABILIDADE:
 * Ponto de entrada. Apenas "monta" o sistema e inicia a interface.
 * Nada de regra de negócio, leitura de arquivo ou menus aqui.
 *
 * DEPENDE DE: Persistencia, GerenciadorRestaurante, MenuConsole.
 */
package restaurante;

import java.io.IOException;

import restaurante.service.GerenciadorRestaurante;
import restaurante.service.Persistencia;
import restaurante.view.MenuConsole;

public class Main {
    public static void main(String[] args) {
        // Objeto que sabe salvar/ler os dados no arquivo dados/dados.txt
        Persistencia persistencia = new Persistencia("dados/dados.txt");

        try {
            // O construtor do gerenciador lê o arquivo, e por isso pode lançar
            // IOException (exceção VERIFICADA: o compilador obriga o try/catch)
            GerenciadorRestaurante g = new GerenciadorRestaurante(persistencia);

            // Inicia a interface de console (o menu)
            new MenuConsole(g).iniciar();
        } catch (IOException e) {
            // Se não conseguiu carregar os dados: mostra o erro e encerra o programa
            System.out.println("Erro ao carregar os dados: " + e.getMessage());
            System.exit(1); // 1 = terminou com erro (0 seria sucesso)
        }
    }
}