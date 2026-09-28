/*
 * ARQUIVO: MesasPanel.java                   PACOTE: restaurante.view
 * RESPONSÁVEL:
 *
 * RESPONSABILIDADE:
 * Tela de ATENDIMENTO: abrir mesa, montar o pedido, ver a conta e fechá-la.
 *
 * IMPLEMENTAÇÃO:
 * - Construtor MesasPanel(GerenciadorRestaurante g).
 * - "Abrir mesa": campos número + cliente -> g.abrirMesa(...).
 * - Lista das mesas ocupadas (g.listarMesas() filtrando isOcupada()).
 * - Com uma mesa selecionada:
 *     JComboBox com o cardápio + JSpinner quantidade -> g.adicionarItem(...);
 *     botão "Remover item" (linha selecionada) -> g.removerItem(...);
 *     JTable do pedido (item, qtd, preço unit., subtotal);
 *     JLabels Subtotal / Taxa (10%) / Total, com pedido.calcularXxx().
 * - "Fechar conta": confirmar, chamar g.fecharConta(...) e mostrar o resumo
 *   (cliente, total) em JOptionPane; depois atualizar().
 * - public void atualizar(): recarrega o cardápio do combo, as mesas e o pedido.
 * - try/catch de MesaOcupadaException, MesaVaziaException,
 *   ItemNaoEncontradoException e NumberFormatException -> JOptionPane.
 *
 * OBSERVAÇÕES:
 * - Nenhum cálculo de valor aqui: só exibir o que o Pedido calcula.
 *
 * DEPENDE DE: GerenciadorRestaurante, Mesa, Pedido, ItemPedido, exception/*.
 */
package restaurante.view;

import javax.swing.JPanel;

public class MesasPanel extends JPanel {
}