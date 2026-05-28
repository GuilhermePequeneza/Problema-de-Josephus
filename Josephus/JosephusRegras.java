import InterfaceListas.*;

/**
 * JosephusRegras — encapsula a lógica do Problema de Josephus.
 * Utiliza uma ListaLigadaCircular para simular as eliminações.
 *
 * @author (seu nome)
 * @version 1.1
 */
public class JosephusRegras {
    private IListaLigada l;
    private int passo;
    private int posicaoAtual;
    private int tamanhoAtual;

    /**
     * @param qtd   quantidade de pessoas no círculo (1..N)
     * @param passo valor K — de quanto em quanto conta
     */
    public JosephusRegras(int qtd, int passo) {
        this.l = new ListaLigadaCircular();
        for (int i = 1; i <= qtd; i++) {
            l.inserirFim(i);
        }
        this.passo = passo;
        this.posicaoAtual = 0;
        this.tamanhoAtual = l.getQtdNos();
    }

    public int getTamanho() {
        return tamanhoAtual;
    }

    /**
     * Executa um passo da simulação: elimina a pessoa na posição
     * (posicaoAtual + passo - 1) e avança o ponteiro para o próximo.
     *
     * @return o número da pessoa eliminada, ou null se só restar 1
     */
    public Object simularJosephus() {
        if (tamanhoAtual <= 1) return null;

        int indiceAlvo = (posicaoAtual + passo - 1) % tamanhoAtual;
        Object removido = l.removerMeio(indiceAlvo);

        tamanhoAtual = l.getQtdNos();

        // Após a remoção, o índice "indiceAlvo" agora aponta para o próximo
        // da fila (o nó seguinte ocupou essa posição). Se o índice removido
        // era o último da lista (circular), volta ao início.
        posicaoAtual = (tamanhoAtual > 0) ? indiceAlvo % tamanhoAtual : 0;

        return removido;
    }

    /**
     * Retorna o sobrevivente final (deve ser chamado quando getTamanho() == 1).
     */
    public Object getSobrevivente() {
        if (tamanhoAtual == 1) {
            return l.removerInicio();
        }
        return null;
    }
}
