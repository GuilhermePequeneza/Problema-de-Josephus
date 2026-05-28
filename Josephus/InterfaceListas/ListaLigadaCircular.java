/**
 * ListaLigadaCircular. Ed para Lista Ligada Circular
 *
 * @author Guilherme Pequeneza
 * @version 1.1 2023/05/15
 */
package InterfaceListas;
public class ListaLigadaCircular<T> implements IListaLigada<T> {
    No inicio, fim;
    int qtdNos;

    public ListaLigadaCircular() {
        setInicio(null);
        setFim(null);
        setQtdNos(0);
    }

    public void setInicio(No inicio) { this.inicio = inicio; }
    public No  getInicio()           { return this.inicio; }
    public void setFim(No fim)       { this.fim = fim; }
    public No  getFim()              { return this.fim; }
    public int  getQtdNos()          { return this.qtdNos; }

    private void setQtdNos(int qtdNos) { this.qtdNos = qtdNos; }

    public boolean estaVazia() {
        return getQtdNos() == 0 && getInicio() == null && getFim() == null;
    }

    public void inserirInicio(Object elem) {
        No novo = new No(elem);
        if (estaVazia()) {
            setInicio(novo);
            setFim(novo);
            novo.setProximo(novo); // aponta para si mesmo
        } else {
            novo.setProximo(inicio);
            setInicio(novo);
            fim.setProximo(inicio); // fim aponta para novo inicio
        }
        setQtdNos(getQtdNos() + 1);
    }

    public void inserirFim(Object elem) {
        No novo = new No(elem);
        if (estaVazia()) {
            setInicio(novo);
            setFim(novo);
            novo.setProximo(novo); // aponta para si mesmo
        } else {
            fim.setProximo(novo);
            setFim(novo);
            novo.setProximo(inicio); // novo fim aponta para inicio
        }
        setQtdNos(getQtdNos() + 1);
    }

    public Object removerInicio() {
        if (estaVazia()) return null;
        No aux = getInicio();
        if (getInicio() == getFim()) {
            setInicio(null);
            setFim(null);
        } else {
            setInicio(aux.getProximo());
            fim.setProximo(inicio); // mantém circularidade
        }
        setQtdNos(getQtdNos() - 1);
        return aux.getConteudo();
    }

    public Object removerFim() {
        if (estaVazia()) return null;
        No aux;
        if (getInicio() == getFim()) {
            aux = getInicio();
            setInicio(null);
            setFim(null);
        } else {
            No ant = getInicio();
            while (ant.getProximo() != fim) {
                ant = ant.getProximo();
            }
            aux = fim;
            ant.setProximo(inicio); // mantém circularidade
            setFim(ant);
        }
        setQtdNos(getQtdNos() - 1);
        return aux.getConteudo();
    }

    /**
     * removerMeio — remove o nó no índice n (base 0, circular).
     * O índice é aplicado com módulo para suportar valores maiores que qtdNos.
     *
     * @param n índice do nó a remover
     * @return conteúdo do nó removido
     */
    public Object removerMeio(int n) {
        if (estaVazia()) return null;

        // Normaliza o índice para evitar out-of-bounds em lista circular
        n = n % getQtdNos();

        No achou;
        if (getInicio() == getFim()) {
            // Único elemento
            achou = getInicio();
            setInicio(null);
            setFim(null);
        } else if (n == 0) {
            // Remove o início
            achou = getInicio();
            setInicio(achou.getProximo());
            fim.setProximo(inicio);
        } else {
            No ant = getInicio();
            for (int i = 1; i < n; i++) {
                ant = ant.getProximo();
            }
            achou = ant.getProximo();
            ant.setProximo(achou.getProximo());
            // Se removeu o fim, atualiza ponteiro fim
            if (achou == getFim()) {
                setFim(ant);
            }
        }

        setQtdNos(getQtdNos() - 1);
        return achou.getConteudo();
    }

    public String toString() {
        if (estaVazia()) return "[]";
        No temp = getInicio();
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < getQtdNos(); i++) {
            sb.append(temp.getConteudo());
            if (i < getQtdNos() - 1) sb.append(",");
            temp = temp.getProximo();
        }
        sb.append("]");
        return sb.toString();
    }
}
