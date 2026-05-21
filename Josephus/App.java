import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 * Escreva uma descrição da classe App aqui.
 * 
 * @author (seu nome) 
 * @version (um número da versão ou uma data)
 */
public class App
{
    public static void main(String args[])
    {
        //vamo usar a porra da entrada do swing
        int Linhas = 5,Colunas = 5;
            
        
        //------------------------------------------ MEU FRAME 
        JFrame J = new JFrame(); // meu Frame de Janela Principal    
        J.setTitle("Janela do Simpson Gamer");
        J.setSize(1000, 700); //TAMNHO SIZE
        J.setLocationRelativeTo(null); //SETO NO CENTRO DA TELA
        J.setResizable(false); // NAO PODE AUMENTAR TAMANHO
        J.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //FECHA AO CLICAR X, porque o padrao só esconde
        J.getContentPane().setBackground(Color.black); //BOTEI COR NO FUNDO, mas n adianta pq meu fundo vai ser o do painel abaixo
        //---------------------------------------------------


        //-------------------------------- PAINEL PRINCIPAL
        JPanel painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.setBackground(Color.BLACK);

        //-------------------------------- CONTAINER CENTRAL
        // Isso evita o grid gigante
        JPanel painelCentro = new JPanel(new FlowLayout(
                FlowLayout.CENTER,
                0,
                120
        ));

        painelCentro.setBackground(Color.BLACK);

        //-------------------------------- PAINEL DO JOGO
        JPanel painelJogo = new JPanel();

        painelJogo.setLayout(new GridLayout(
                Linhas,
                Colunas,
                15, // espaço horizontal
                15  // espaço vertical
        ));

        painelJogo.setBackground(Color.BLACK);

        for(int i = 1; i <= Linhas * Colunas; i++)
        {
            JLabel numero = new JLabel("" + i);

            // centraliza texto
            numero.setHorizontalAlignment(SwingConstants.CENTER);

            // tamanho fixo
            numero.setPreferredSize(new Dimension(50,50));

            // deixa visível
            numero.setOpaque(true);
            numero.setBackground(Color.DARK_GRAY);
            numero.setForeground(Color.WHITE);

            // fonte menor
            numero.setFont(new Font("Arial", Font.BOLD, 20));

            painelJogo.add(numero);
        }

        painelCentro.add(painelJogo);
        painelPrincipal.add(painelCentro, BorderLayout.CENTER);

        //------------------------- final 
        J.add(painelPrincipal);
        J.setVisible(true);
    }
}