/*
 * ARQUIVO: TamanhoPrato.java                 PACOTE: restaurante.model
 * RESPONSÁVEL:
 *
 * RESPONSABILIDADE:
 * Enum com os tamanhos de prato e o adicional de preço de cada um.
 *
 * IMPLEMENTAÇÃO:
 * - Constantes: PEQUENO(0.0), MEDIO(5.0), GRANDE(10.0).
 * - Atributo private final double adicional + construtor do enum.
 * - public double getAdicional().
 * - toString() amigável: "Pequeno", "Médio", "Grande" (para o JComboBox).
 *
 * OBSERVAÇÕES:
 * - A Persistencia grava name() ("MEDIO") e lê com TamanhoPrato.valueOf(...).
 *   Por isso toString() pode ter acento, mas os NOMES das constantes não mudam.
 *
 * DEPENDE DE: nada.
 */
package restaurante.model;

public enum TamanhoPrato {
}