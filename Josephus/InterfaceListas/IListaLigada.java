
/**
 * Escreva a descrição da interface IListaLigada aqui.
 * 
 * @author Felipe Estima
 * @author Guilherme Pequeneza
 * @author Igor Dias
 * @author Thierry Nadjarian 
 * 
 * @version 26/05/26
 */
package InterfaceListas;
public interface IListaLigada<T>
{
    //COLOCAR TODOS OS METODOS COM AS ASSINATURAS CERTAS AQUI
    void setInicio(No inicio);
    void setFim(No fim);
    No getInicio();
    No getFim();
    int getQtdNos();
    boolean estaVazia();
    void inserirInicio(Object elem);
    void inserirFim(Object elem);
    Object removerInicio();
    Object removerFim();
    Object removerMeio(int n);
    String toString();
    
}
