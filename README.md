# 🍔 Sistema de Restaurante 2.0

Sistema desktop de gerenciamento de restaurante/lanchonete desenvolvido em **Java** com interface gráfica **JavaFX**.

Projeto avaliativo da disciplina de Projeto de Programação em Java — Ciência da Computação, Universidade Tiradentes (UNIT).

---

## 👥 Integrantes e Responsabilidades

| # | Nome | Módulo(s) |
|---|------|-----------|
| 1 | Alice Santos | `Main.java` · `service` (`Persistencia`, `Relatorio`) |
| 2 | Larissa Castro | `model` (`Prato`, `TamanhoPrato`, `Sobremesa`, `ItemPedido`, `Pedido`, `Mesa`) |
| 3 | Lívia Rodrigues | `service` (`GerenciadorRestaurante`) · `test` |
| 4 | Pedro Afonso | `exception` · `view` |

---

## 📌 Descrição do Projeto

O **Sistema de Restaurante** é uma aplicação desktop que permite gerenciar o funcionamento de um restaurante ou lanchonete de forma simples e visual.

Com ele, é possível montar o cardápio (bebidas, pratos e sobremesas com adicionais), abrir mesas, registrar pedidos com vários itens, calcular a conta com taxa de serviço, fechar ou cancelar mesas e gerar o relatório de qualquer dia.

Os dados são salvos automaticamente em um arquivo `.txt` local, sem dependência de banco de dados.

---

## 🚀 Funcionalidades

**Cardápio**
- ✅ Cadastro de itens: bebida (com ou sem gelo), prato (pequeno, médio ou grande) e sobremesa (com adicionais)
- ✅ Listagem do cardápio, com filtro por categoria
- ✅ Remoção de itens do cardápio (o item é desativado, sem afetar pedidos já registrados)

**Atendimento**
- ✅ Abertura de mesa com número e nome do cliente
- ✅ Visualização das mesas livres e ocupadas
- ✅ Adição de itens ao pedido, com quantidade
- ✅ Remoção de itens do pedido, inclusive de só parte da quantidade
- ✅ Cálculo de subtotal, taxa de serviço (10%) e total
- ✅ Fechamento de conta (bloqueado para pedidos sem itens)
- ✅ Cancelamento de mesa aberta por engano

**Relatório**
- ✅ Relatório de qualquer dia, escolhido em um calendário: total arrecadado, taxa de serviço, itens mais vendidos e mesas atendidas

**Geral**
- ✅ Interface gráfica em JavaFX, organizada em abas
- ✅ Persistência automática em arquivo `.txt` local
- ✅ Mensagens de erro claras, sem fechar o programa

---

## 🖥️ Interface

A janela principal é dividida em três abas:

| Aba | O que dá para fazer |
|-----|---------------------|
| **Cardápio** | Cadastrar itens (o formulário muda conforme o tipo escolhido), listar com filtro por categoria e remover itens do cardápio |
| **Mesas** | Abrir mesa, ver mesas livres e ocupadas, adicionar e remover itens, acompanhar subtotal, taxa e total, fechar a conta ou cancelar a mesa |
| **Relatório** | Escolher o dia no calendário e gerar o relatório |

---

## 🗂️ Estrutura do Projeto

```
restaurante-java/
├── pom.xml
├── README.md
├── LICENSE
├── .gitignore
└── src/
    ├── main/
    │   └── java/
    │       └── restaurante/
    │           ├── Main.java
    │           ├── model/
    │           │   ├── ItemCardapio.java
    │           │   ├── Bebida.java
    │           │   ├── Prato.java
    │           │   ├── TamanhoPrato.java
    │           │   ├── Sobremesa.java
    │           │   ├── Adicional.java
    │           │   ├── ItemPedido.java
    │           │   ├── Pedido.java
    │           │   └── Mesa.java
    │           ├── service/
    │           │   ├── GerenciadorRestaurante.java
    │           │   ├── Relatorio.java
    │           │   └── Persistencia.java
    │           ├── view/
    │           │   ├── RestauranteApp.java
    │           │   ├── CardapioView.java
    │           │   ├── MesasView.java
    │           │   ├── RelatorioView.java
    │           │   └── Alertas.java
    │           └── exception/
    │               ├── ItemNaoEncontradoException.java
    │               ├── MesaOcupadaException.java
    │               ├── MesaVaziaException.java
    │               └── PedidoVazioException.java
    └── test/
        └── java/
            └── restaurante/
                ├── model/
                │   ├── ItemCardapioTest.java
                │   ├── PedidoTest.java
                │   └── MesaTest.java
                └── service/
                    ├── GerenciadorRestauranteTest.java
                    ├── RelatorioTest.java
                    └── PersistenciaTest.java
```

| Pasta | Responsabilidade |
|-------|------------------|
| `model` | As entidades do restaurante e os cálculos de preço e da conta |
| `service` | Regras de negócio (`GerenciadorRestaurante`), relatório e gravação/leitura do arquivo |
| `view` | Interface gráfica JavaFX: janela principal, uma classe por aba e as caixas de mensagem |
| `exception` | Exceções personalizadas lançadas pelas regras de negócio |
| `test` | Testes automatizados com JUnit |

---

## 🧠 Conceitos Aplicados

| Conceito | Onde é usado |
|----------|--------------|
| **Herança** | `Bebida`, `Prato` e `Sobremesa` herdam de `ItemCardapio`; as exceções herdam de `Exception`; `RestauranteApp` herda de `Application` |
| **Polimorfismo** | Cada subclasse implementa `calcularPreco()` de forma diferente |
| **Abstração** | `ItemCardapio` é uma classe abstrata |
| **Composição** | `Pedido` contém uma lista de `ItemPedido`; `Mesa` contém uma lista de `Pedido` |
| **Encapsulamento** | Atributos privados com getters; listas devolvidas como cópia ou somente leitura |
| **Enum** | `TamanhoPrato` (tamanhos do prato) e `Adicional` (adicionais da sobremesa) |
| **Exceções customizadas** | `ItemNaoEncontradoException`, `MesaOcupadaException`, `MesaVaziaException`, `PedidoVazioException` |
| **Coleções** | `ArrayList` (cardápio, itens, pedidos, adicionais) e `HashMap` (mesas por número) |
| **Persistência em arquivo** | `BufferedWriter` e `BufferedReader` com separador `\|` |
| **Testes com JUnit** | Regras de negócio, relatório e persistência |
| **Interface gráfica** | JavaFX (`Application`, `Stage`, `TabPane`, `TableView`, `DatePicker`, `Alert`) |

---

## 🍽️ Regras de Negócio

**Cardápio**
1. Um **item do cardápio** tem código, nome, preço base e categoria
2. **Bebida**: preço base + R$ 2,00 se for "com gelo"
3. **Prato**: preço base + adicional por tamanho (Pequeno = 0, Médio = +5, Grande = +10)
4. **Sobremesa**: preço base + soma dos adicionais escolhidos, de uma lista fixa:

   | Adicional | Preço |
   |-----------|-------|
   | Calda de chocolate | R$ 3,00 |
   | Chantilly | R$ 2,00 |
   | Morango | R$ 2,50 |
   | Granulado | R$ 1,50 |

5. Um item **removido do cardápio** é desativado: deixa de aparecer na listagem e não pode mais ser pedido, mas continua nos pedidos já registrados e no relatório

**Pedido e conta**
6. Um **pedido** pode ter vários itens, cada um com sua quantidade; pedir de novo um item que já está no pedido soma a quantidade
7. Ao **remover um item do pedido**, é possível tirar só parte da quantidade; se a quantidade chegar a zero, o item sai do pedido
8. **Subtotal** = soma de (preço do item × quantidade)
9. **Taxa de serviço** = 10% do subtotal
10. **Total** = subtotal + taxa de serviço

**Mesas**
11. Uma **mesa** só pode ter um pedido aberto por vez
12. **Não é possível fechar a conta** de um pedido sem itens
13. Ao **fechar a conta**, o pedido é arquivado com data e hora e a mesa fica livre para um novo cliente
14. Uma mesa aberta por engano pode ser **cancelada**: o pedido é descartado e não entra no relatório

**Relatório**
15. O relatório considera apenas as contas **fechadas no dia escolhido**
16. **Mesas atendidas** = número de contas fechadas no dia (uma mesma mesa pode ser contada mais de uma vez se atendeu clientes diferentes)

---

## ▶️ Como Executar

### Pré-requisitos
- Java JDK 17 ou superior
- Maven (ou uma IDE com suporte a Maven, como IntelliJ IDEA)

### Executar
```bash
mvn javafx:run
```

Ou, no IntelliJ, rode a classe `restaurante.Main`.

---

## 🧪 Como Rodar os Testes

```bash
mvn test
```

Ou, no IntelliJ, clique com o botão direito em `src/test/java` → **Run 'All Tests'**.

---

## 📊 Exemplo de Relatório do Dia

```
=====================================
  RELATÓRIO DO DIA - 14/10/2026
=====================================

Total arrecadado: R$ 1.234,50
Taxa de serviço arrecadada: R$ 112,23

Itens mais vendidos:
  1. Coca-Cola              - 15 unidades
  2. Hambúrguer Clássico    - 12 unidades
  3. Pudim                  -  8 unidades

Mesas atendidas: 6
=====================================
```

---

## 🔄 Versões

| Versão | Novidades |
|--------|-----------|
| **1.0** | Regras de negócio, persistência em arquivo e testes, com interface de console |
| **2.0** | Interface gráfica em JavaFX, remoção de itens do cardápio, adicionais da sobremesa, remoção parcial de itens do pedido, cancelamento de mesa, bloqueio de conta vazia e relatório de qualquer dia |

---

## 💡 Melhorias Futuras

- Edição de nome e preço de itens do cardápio, guardando o preço da hora da venda em cada pedido
- Taxa de serviço opcional no fechamento da conta
- Divisão da conta entre várias pessoas
- Exportação da conta e do relatório em arquivo `.txt`

---

## 📌 Observações

- O arquivo `dados/dados.txt` é criado automaticamente na primeira execução e não é enviado ao GitHub
- Ao atualizar da versão 1.0 para a 2.0, apague a pasta `dados/`, pois o formato do arquivo mudou
- Os testes não usam o arquivo de dados: as regras são testadas com objetos em memória e a persistência com uma pasta temporária
- Bibliotecas externas: apenas JavaFX e JUnit, baixadas automaticamente pelo Maven
