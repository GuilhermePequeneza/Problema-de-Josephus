package InterfaceListas;


/**
 * Escreva uma descrição da classe testeListaLigada aqui.
 * 
 * @author (seu nome) 
 * @version (um número da versão ou uma data)
 */
public class testeListaLigada
{
    public static void main(String args[])
    {
        IListaLigada l;
        l = new ListaLigadaCircular();
        
        l.inserirFim(2);
        l.inserirFim(3);
        l.inserirFim(4);
        l.inserirFim(5);
        l.inserirFim(6);
        l.inserirFim(7);
        
        System.out.println("" + l);
    }
}