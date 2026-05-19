
/**
 * Escreva uma descrição da classe TesteRemoverMeio aqui.
 * 
 * @author (seu nome) 
 * @version (um número da versão ou uma data)
 */
import InterfaceListas.*;
public class TesteRemoverMeio
{
    public static void main(){
        
        IListaLigada l = new ListaLigadaCircular();
        
        l.inserirFim(2);
        l.inserirFim(3);
        l.inserirFim(4);
        l.inserirFim(5);
        l.inserirFim(6);
        l.inserirFim(7);
        
        l.inserirFim(8);
        l.inserirFim(9);
        l.inserirFim(10);
        l.inserirInicio(1);
        
        int indiceAlvo;

        int passo = 1;

        int posicaoAtual = 1;

        int tamanhoAtual = l.getQtdNos();

        System.out.println("Lista: " + l);

        while(tamanhoAtual != 1){

            System.out.println(l.getQtdNos());            

            indiceAlvo = posicaoAtual + passo - 1;            

            l.removerMeio(indiceAlvo);

            System.out.println("Lista: " + l);

            posicaoAtual = indiceAlvo % tamanhoAtual;  

            if(posicaoAtual == 0)

                posicaoAtual = 1;

            tamanhoAtual = l.getQtdNos();            

        }
 
    }
}