/*
 * ARQUIVO: CardapioPanel.java                PACOTE: restaurante.view
 * RESPONSÁVEL:
 *
 * RESPONSABILIDADE:
 * Tela de CADASTRO e LISTAGEM do cardápio.
 *
 * IMPLEMENTAÇÃO:
 * - Construtor CardapioPanel(GerenciadorRestaurante g).
 * - Formulário: JComboBox tipo (Bebida/Prato/Sobremesa), JTextField nome,
 *   JTextField preço, e o campo específico, que muda com o tipo:
 *   JCheckBox "Com gelo" / JComboBox<TamanhoPrato> / JCheckBox "Especial".
 * - Botão "Cadastrar": ler os campos, criar o objeto certo com
 *   g.proximoCodigoItem() e chamar g.cadastrarItem(item).
 * - JTable com código, nome, categoria e preço final (calcularPreco()),
 *   mais um JComboBox de filtro ("Todos"/categorias) usando g.listarPorCategoria().
 * - public void atualizar(): recarrega a tabela.
 * - Erros (NumberFormatException, IllegalArgumentException):
 *   JOptionPane.showMessageDialog com mensagem amigável.
 *
 * OBSERVAÇÕES:
 * - Não calcular preço nem gerar código aqui: usar o model/gerenciador.
 *
 * DEPENDE DE: GerenciadorRestaurante, Bebida, Prato, Sobremesa, TamanhoPrato.
 */
package restaurante.view;

import javax.swing.JPanel;

public class CardapioPanel extends JPanel {
}