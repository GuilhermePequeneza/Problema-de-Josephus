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
        
        JFrame J = new JFrame(); // meu Frame de Janela Principal
        JButton B = new JButton("Botao do negocio"); // Um botao
        JLabel La = new JLabel(); // Uma Label, Texto/Iamgem
        
        
        //------------------------------------------ MEU FRAME 
        
        J.setTitle("Janela do Simpson Gamer");
        J.setSize(420, 420); //TAMNHO SIZE
        J.setLocationRelativeTo(null); //SETO NO CENTRO
        
        J.setResizable(false); // NAO PODE AUMENTAR TAMANHO
        J.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //FECHA AO CLICAR X

         // J.setIconImage(Im.getImage()); MANO EU SETEI A FOTO COMO ICONE DA JANELA QUE FODA
        J.getContentPane().setBackground(Color.black); //BOTEI COR NO FUNDO
        
        
        //-------------------------------------- MEU PAINEL
        
        JPanel painel = new JPanel();
        JPanel painel2 = new JPanel();
        JPanel painel3 = new JPanel();
        
        painel.setBackground(Color.WHITE); //setando cor do fundo
        painel.setBounds(0,0,250,250); // x,y,horizontal,vertical
        painel.setLayout(new GridBagLayout()); // PESQUISAR COMO FUNCIONA O GRIDLAYOUT MAS ELE CENTRALIZA  A LABEL E ACHO QUE SERVE PARA AS IMAGENS IGUAL
        // ImageIcon Im = new ImageIcon();  to sem imagem no momento
        
        //------------------------------------------LABEL - TEXTO DENTRO DO PAINEL
        La.setText("UMA JANELA DE TESTES");
        // La.setHorinzontalTextPosition(JLabel.CENTER); //DEFINO A LABEL NO CENTRO, MAS USANDO A JLabel?
        La.setFont(new Font("Arial", Font.BOLD, 10)); // 32 é o tamanho do texto
        // La.setIcon(Im);
        
        


        
        //Pra rodar tem que chegar no setVisible
        painel.add(La);
        
            
        J.add(painel);
        J.add(painel2);
        J.add(painel3);
        J.setVisible(true);
    }
}