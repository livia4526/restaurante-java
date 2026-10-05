package restaurante.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import restaurante.exception.ItemNaoEncontradoException;
import restaurante.exception.MesaOcupadaException;
import restaurante.exception.MesaVaziaException;
import restaurante.model.ItemCardapio;
import restaurante.model.Mesa;
import restaurante.model.Pedido;

public class GerenciadorRestaurante {

    // O cardápio é uma lista (ArrayList) e as mesas ficam num
    // HashMap, onde a CHAVE é o número da mesa e o VALOR é o objeto Mesa.
    // Assim dá para achar a mesa 5 direto com mesas.get(5), sem percorrer tudo.
    private List<ItemCardapio> cardapio = new ArrayList<>();
    private Map<Integer, Mesa> mesas = new HashMap<>();

    // Pode ser null: nesse caso o gerenciador funciona só na memória (testes)
    private Persistencia persistencia;

    // Contadores para gerar o próximo código de item e número de pedido
    private int proximoCodigoItem = 1;
    private int proximoNumeroPedido = 1;


    // CONSTRUTORES


    // Sem arquivo: usado nos testes JUnit (não lê nem grava nada em disco)
    public GerenciadorRestaurante() {
        this.persistencia = null;
    }

    // Com arquivo: usado pelo Main. Já carrega o que estiver salvo.
    // "throws IOException" = quem chamar (o Main) é obrigado a tratar o erro.
    public GerenciadorRestaurante(Persistencia persistencia) throws IOException {
        if (persistencia == null) {
            throw new IllegalArgumentException("A persistência não pode ser nula.");
        }
        this.persistencia = persistencia;
        carregarDados();
    }

    // Lê o arquivo e coloca tudo de volta nas coleções
    private void carregarDados() throws IOException {
        // 1) Cardápio. Também montamos um mapa código -> item, porque a
        //    Persistencia precisa dele para remontar os itens de cada pedido.
        List<ItemCardapio> itensLidos = persistencia.carregarCardapio();
        Map<Integer, ItemCardapio> itensPorCodigo = new HashMap<>();

        for (ItemCardapio item : itensLidos) {
            cardapio.add(item);
            itensPorCodigo.put(item.getCodigo(), item);

            // O próximo código tem que ser maior que todos os já usados
            if (item.getCodigo() >= proximoCodigoItem) {
                proximoCodigoItem = item.getCodigo() + 1;
            }
        }

        // 2) Pedidos. Cada pedido volta para a mesa dele.
        List<Pedido> pedidosLidos = persistencia.carregarPedidos(itensPorCodigo);

        for (Pedido pedido : pedidosLidos) {
            Mesa mesa = obterOuCriarMesa(pedido.getNumeroMesa());
            mesa.adicionarPedido(pedido);

            if (pedido.getNumero() >= proximoNumeroPedido) {
                proximoNumeroPedido = pedido.getNumero() + 1;
            }
        }
    }


    // CARDÁPIO

    // Só informa qual será o próximo código (quem incrementa é o cadastrarItem)
    public int proximoCodigoItem() {
        return proximoCodigoItem;
    }

    public void cadastrarItem(ItemCardapio item) {
        if (item == null) {
            throw new IllegalArgumentException("O item não pode ser nulo.");
        }

        // Não pode existir dois itens com o mesmo código
        for (ItemCardapio existente : cardapio) {
            if (existente.getCodigo() == item.getCodigo()) {
                throw new IllegalArgumentException(
                        "Já existe um item com o código " + item.getCodigo() + ".");
            }
        }

        cardapio.add(item);

        if (item.getCodigo() >= proximoCodigoItem) {
            proximoCodigoItem = item.getCodigo() + 1;
        }

        salvar();
    }

    // Devolve uma CÓPIA da lista: quem receber não consegue
    // mexer no cardápio verdadeiro sem passar pelo gerenciador
    public List<ItemCardapio> listarCardapio() {
        return new ArrayList<>(cardapio);
    }

    // categoria: "Bebida", "Prato" ou "Sobremesa".
    // POLIMORFISMO: cada item responde getCategoria() do seu jeito.
    public List<ItemCardapio> listarPorCategoria(String categoria) {
        List<ItemCardapio> resultado = new ArrayList<>();

        for (ItemCardapio item : cardapio) {
            // equalsIgnoreCase compara o CONTEÚDO, ignorando maiúsculas/minúsculas
            if (item.getCategoria().equalsIgnoreCase(categoria)) {
                resultado.add(item);
            }
        }
        return resultado;
    }

    public ItemCardapio buscarItem(int codigo) throws ItemNaoEncontradoException {
        for (ItemCardapio item : cardapio) {
            if (item.getCodigo() == codigo) {
                return item;
            }
        }
        // Se o for terminou sem achar, o item não existe
        throw new ItemNaoEncontradoException(
                "Item com código " + codigo + " não encontrado no cardápio.");
    }

    // MESAS E PEDIDOS

    public Pedido abrirMesa(int numeroMesa, String nomeCliente) throws MesaOcupadaException {
        Mesa mesa = obterOuCriarMesa(numeroMesa);

        // Regra: uma mesa só pode ter UM pedido aberto por vez
        if (mesa.isOcupada()) {
            throw new MesaOcupadaException("A mesa " + numeroMesa + " já está ocupada.");
        }

        // O construtor do Pedido valida o nome do cliente
        Pedido pedido = new Pedido(proximoNumeroPedido, numeroMesa, nomeCliente);
        mesa.adicionarPedido(pedido);
        proximoNumeroPedido++;

        salvar();
        return pedido;
    }

    public void adicionarItem(int numeroMesa, int codigoItem, int quantidade)
            throws MesaVaziaException, ItemNaoEncontradoException {
        Mesa mesa = obterMesaOcupada(numeroMesa);   // pode lançar MesaVaziaException
        ItemCardapio item = buscarItem(codigoItem); // pode lançar ItemNaoEncontradoException

        // Quem soma a quantidade (se o item já estiver no pedido) é o Pedido
        mesa.getPedidoAberto().adicionarItem(item, quantidade);
        salvar();
    }

    public void removerItem(int numeroMesa, int codigoItem)
            throws MesaVaziaException, ItemNaoEncontradoException {
        Mesa mesa = obterMesaOcupada(numeroMesa);

        // O Pedido devolve false quando o item não está nele
        boolean removido = mesa.getPedidoAberto().removerItem(codigoItem);
        if (!removido) {
            throw new ItemNaoEncontradoException(
                    "O item de código " + codigoItem + " não está no pedido da mesa "
                            + numeroMesa + ".");
        }
        salvar();
    }

    public Pedido consultarPedidoAberto(int numeroMesa) throws MesaVaziaException {
        Mesa mesa = obterMesaOcupada(numeroMesa);
        return mesa.getPedidoAberto();
    }

    // Devolve as mesas em ordem de número (1, 2, 3...).
    // O HashMap não guarda ordem, então ordenamos os números antes.
    public List<Mesa> listarMesas() {
        List<Integer> numeros = new ArrayList<>(mesas.keySet());
        Collections.sort(numeros);

        List<Mesa> resultado = new ArrayList<>();
        for (int numero : numeros) {
            resultado.add(mesas.get(numero));
        }
        return resultado;
    }

    public Pedido fecharConta(int numeroMesa) throws MesaVaziaException {
        Mesa mesa = obterMesaOcupada(numeroMesa);
        Pedido pedido = mesa.getPedidoAberto();

        // Ao fechar, o pedido deixa de estar aberto e a mesa fica livre sozinha
        pedido.fechar(LocalDateTime.now());

        salvar();
        return pedido;
    }

    // Junta os pedidos FECHADOS de todas as mesas (usado pelo Relatorio)
    public List<Pedido> listarPedidosFechados() {
        List<Pedido> fechados = new ArrayList<>();

        for (Mesa mesa : mesas.values()) {
            for (Pedido pedido : mesa.getPedidos()) {
                if (!pedido.isAberto()) {
                    fechados.add(pedido);
                }
            }
        }
        return fechados;
    }

    // MÉTODOS AUXILIARES (private: só esta classe usa)

    // Busca a mesa e garante que ela tem um pedido aberto.
    // Usado em adicionar, remover, consultar e fechar, para não repetir código.
    private Mesa obterMesaOcupada(int numeroMesa) throws MesaVaziaException {
        Mesa mesa = mesas.get(numeroMesa);

        if (mesa == null || !mesa.isOcupada()) {
            throw new MesaVaziaException("A mesa " + numeroMesa + " não está aberta.");
        }
        return mesa;
    }

    // Devolve a mesa se ela já existir; senão cria, guarda no mapa e devolve
    private Mesa obterOuCriarMesa(int numeroMesa) {
        Mesa mesa = mesas.get(numeroMesa);

        if (mesa == null) {
            mesa = new Mesa(numeroMesa); // o construtor valida se o número é > 0
            mesas.put(numeroMesa, mesa);
        }
        return mesa;
    }

    // Grava tudo no arquivo depois de cada alteração.
    // Se não houver persistência (testes), não faz nada.
    private void salvar() {
        if (persistencia == null) {
            return;
        }
        try {
            persistencia.salvar(cardapio, mesas.values());
        } catch (IOException e) {
            // Transformamos em RuntimeException (não verificada) para não
            // obrigar TODOS os métodos acima a declararem "throws IOException"
            throw new RuntimeException("Erro ao salvar os dados no arquivo: "
                    + e.getMessage(), e);
        }
    }
}