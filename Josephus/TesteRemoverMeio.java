import InterfaceListas.*;

/**
 * TesteRemoverMeio — testa as operações de remoção da ListaLigadaCircular.
 *
 * @author Felipe Estima
 * @author Guilherme Pequeneza
 * @author Igor Dias
 * @author Thierry Nadjarian 
 * 
 * @version 26/05/26
 */
public class TesteRemoverMeio {
    public static void main(String[] args) {
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

        System.out.println("Lista inicial:      " + l); // [1,2,3,4,5,6,7,8,9,10]

        l.removerMeio(11); // índice 11 % 10 = 1 → remove o 2
        System.out.println("Após removerMeio(11): " + l); // [1,3,4,5,6,7,8,9,10]

        l.removerMeio(3);  // índice 3 % 9 = 3 → remove o 5
        System.out.println("Após removerMeio(3):  " + l); // [1,3,4,6,7,8,9,10]

        l.removerMeio(3);  // índice 3 % 8 = 3 → remove o 6
        System.out.println("Após removerMeio(3):  " + l); // [1,3,4,7,8,9,10]
    }
}
