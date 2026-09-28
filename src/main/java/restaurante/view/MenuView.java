/*
 * ARQUIVO: MenuView.java                     PACOTE: restaurante.view
 * RESPONSÁVEL:
 *
 * RESPONSABILIDADE:
 * Janela principal (JFrame) que organiza as telas em abas.
 *
 * IMPLEMENTAÇÃO:
 * - Construtor MenuView(GerenciadorRestaurante g).
 * - Criar CardapioPanel, MesasPanel e RelatorioPanel passando g.
 * - JTabbedPane com as abas "Cardápio", "Mesas" e "Relatório".
 * - addChangeListener: ao trocar de aba, chamar atualizar() do painel visível
 *   (assim um item recém-cadastrado aparece na tela de Mesas).
 * - setTitle("Sistema de Restaurante"), setSize(900, 600),
 *   setLocationRelativeTo(null), setDefaultCloseOperation(EXIT_ON_CLOSE).
 *
 * OBSERVAÇÕES:
 * - Nenhum formulário ou botão de funcionalidade aqui: tudo fica nos painéis.
 *
 * DEPENDE DE: CardapioPanel, MesasPanel, RelatorioPanel, GerenciadorRestaurante.
 */
package restaurante.view;

import javax.swing.JFrame;

public class MenuView extends JFrame {
}