```markdown
# 🍔 Sistema de Restaurante / Lanchonete

Sistema desktop de gerenciamento de restaurante/lanchonete desenvolvido em **Java** com interface gráfica **Swing**.

Projeto avaliativo da disciplina de Laboratório de Programação — Ciência da Computação, Universidade Tiradentes (UNIT).

---

## 👥 Integrantes e Responsabilidades

| # | Nome | Módulo(s) |
|---|------|-----------|
| 1 | Alice Santos | `Main.java` · `model/ItemCardapio.java` · `model/Bebida.java` |
| 2 | Larissa Castro | `model/Prato.java` · `model/Sobremesa.java` · `model/Mesa.java` |
| 3 | Lívia Rodrigues | `model/Pedido.java` · `model/ItemPedido.java` · `service/GerenciadorRestaurante.java` |
| 4 | .... | `service/Relatorio.java` · `service/Persistencia.java` · `view/MenuView.java` |

---

## 📌 Descrição do Projeto

O **Sistema de Restaurante** é uma aplicação desktop que permite ao usuário gerenciar o funcionamento de um restaurante ou lanchonete de forma simples e visual.

Com ele, é possível cadastrar itens no cardápio (bebidas, pratos e sobremesas), abrir mesas, registrar pedidos com vários itens, calcular o total com taxa de serviço e gerar relatórios do dia.

Os dados são persistidos em um arquivo `.txt` local, sem dependência de banco de dados externo.

---

## 🚀 Funcionalidades

- ✅ Cadastro de itens no cardápio (bebida, prato, sobremesa)
- ✅ Listagem de itens do cardápio por categoria
- ✅ Abertura de mesa com número e nome do cliente
- ✅ Adição de itens ao pedido de uma mesa
- ✅ Remoção de itens do pedido
- ✅ Cálculo de subtotal (soma dos itens)
- ✅ Cálculo de taxa de serviço (10% sobre o subtotal)
- ✅ Cálculo do total final
- ✅ Fechamento de conta da mesa
- ✅ Relatório do dia (itens mais vendidos e total arrecadado)
- ✅ Persistência automática em arquivo `.txt` local

---

## 🗂️ Estrutura do Projeto

```
restaurante-java/
├── src/
│   ├── main/
│   │   └── Main.java                    # Ponto de entrada
│   ├── model/
│   │   ├── ItemCardapio.java            # Classe abstrata base
│   │   ├── Bebida.java                  # Herda de ItemCardapio
│   │   ├── Prato.java                   # Herda de ItemCardapio
│   │   ├── Sobremesa.java               # Herda de ItemCardapio
│   │   ├── ItemPedido.java              # Item + quantidade
│   │   ├── Pedido.java                  # Contém vários ItemPedido (composição)
│   │   └── Mesa.java                    # Mesa com um Pedido
│   ├── service/
│   │   ├── GerenciadorRestaurante.java  # Lógica central
│   │   ├── Relatorio.java               # Relatórios do dia
│   │   └── Persistencia.java            # Salvar/carregar em .txt
│   ├── view/
│   │   └── MenuView.java                # Interface com o usuário
│   └── exception/
│       ├── ItemNaoEncontradoException.java
│       ├── MesaOcupadaException.java
│       └── MesaVaziaException.java
├── dados/
│   └── dados.txt                        # Arquivo de persistência
└── README.md
```

---

## 🧠 Conceitos Aplicados

| Conceito | Onde é usado |
|----------|--------------|
| **Herança** | `Bebida`, `Prato` e `Sobremesa` herdam de `ItemCardapio` |
| **Polimorfismo** | Cada subclasse implementa `calcularPreco()` de forma diferente |
| **Abstração** | `ItemCardapio` é uma classe abstrata |
| **Composição** | `Pedido` contém uma lista de `ItemPedido` |
| **Encapsulamento** | Atributos privados com getters e setters |
| **Exceções customizadas** | `ItemNaoEncontradoException`, `MesaOcupadaException`, `MesaVaziaException` |
| **Coleções** | Uso de `ArrayList` e `HashMap` |
| **Streams** | Filtros e ordenação em relatórios |
| **Persistência em arquivo** | `FileWriter` e `BufferedReader` com separador `\|` |
| **Testes com JUnit** | Cobertura das regras de negócio |
| **Interface gráfica** | Swing |

---

## 🍽️ Regras de Negócio

1. Um **item do cardápio** tem nome, preço base e categoria
2. **Bebida**: preço base + R$ 2,00 se for "com gelo"
3. **Prato**: preço base + adicional por tamanho (Pequeno = 0, Médio = +5, Grande = +10)
4. **Sobremesa**: preço base + R$ 3,00 se for "especial"
5. Um **pedido** pode ter vários itens, cada um com sua quantidade
6. **Subtotal** = soma de (preço do item × quantidade)
7. **Taxa de serviço** = 10% do subtotal
8. **Total** = subtotal + taxa de serviço
9. Uma **mesa** só pode ter um pedido aberto por vez
10. Ao **fechar a conta**, o pedido é arquivado e a mesa fica livre para um novo cliente

---

## ▶️ Como Executar

### Pré-requisitos
- Java JDK 17 ou superior

### Compilar e executar

```bash
# Compilar
javac -d bin src/**/*.java

# Executar
java -cp bin main.Main
```

---

## 🧪 Como Rodar os Testes

```bash
# Com Maven
mvn test

# Ou com JUnit direto
java -cp bin:lib/junit.jar org.junit.runner.JUnitCore tests.GerenciadorTest
```

---

## 📊 Exemplo de Relatório do Dia

```
=====================================
  RELATÓRIO DO DIA - 14/09/2026
=====================================

Total arrecadado: R$ 1.234,50
Taxa de serviço arrecadada: R$ 112,23

Itens mais vendidos:
  1. Coca-Cola             - 15 unidades
  2. Hambúrguer Clássico   - 12 unidades
  3. Pudim                 -  8 unidades

Mesas atendidas: 6
=====================================
```

---

## 📌 Observações

- O arquivo `dados/dados.txt` é criado automaticamente na primeira execução
- Os testes não dependem de arquivo em disco — usam objetos criados diretamente em memória
- O projeto não utiliza bibliotecas externas além do JUnit, tornando a execução simples em qualquer máquina com Java instalado
- A interface pode ser desenvolvida em Swing (gráfica) ou console, conforme preferência do grupo
```

---
