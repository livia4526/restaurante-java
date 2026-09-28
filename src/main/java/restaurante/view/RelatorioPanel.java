/*
 * ARQUIVO: RelatorioPanel.java               PACOTE: restaurante.view
 * RESPONSÁVEL:
 *
 * RESPONSABILIDADE:
 * Exibir o RELATÓRIO DO DIA.
 *
 * IMPLEMENTAÇÃO:
 * - Construtor RelatorioPanel(GerenciadorRestaurante g).
 * - Campo de data (JTextField dd/MM/yyyy, preenchido com hoje) + botão "Gerar".
 * - Ao gerar: new Relatorio(g.listarPedidosFechados()).gerarTexto(data)
 *   e mostrar em JTextArea (não editável, fonte Font.MONOSPACED) dentro
 *   de um JScrollPane.
 * - public void atualizar(): gera de novo para a data do campo.
 * - Data inválida (DateTimeParseException) -> JOptionPane.
 *
 * OBSERVAÇÕES:
 * - Nenhum cálculo aqui: tudo vem de Relatorio.
 *
 * DEPENDE DE: GerenciadorRestaurante, Relatorio.
 */
package restaurante.view;

import javax.swing.JPanel;

public class RelatorioPanel extends JPanel {
}