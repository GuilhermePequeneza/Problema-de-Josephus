import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.StringTokenizer;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Implementa o ambiente grafico para o Jogo da Vida
 */
public class UIGraf implements MouseListener, ActionListener, MouseMotionListener {
    
    JosephusRegras jvr; // Para acessar as regras do jogo da vida
    
    // Utilizado para animação (classe interna)
    Simulacao _simular;
    
    //Janela principal.
    JFrame _jframe = null; 

    // Componentes para o ambiente
    Container _pAmbiente = null; // container do Ambiente (celulas)
    JLabel [][]_labels = null;  // matriz de labels 

    Color _corCelulas = new Color(255,255,255);
    Color _corFundo = new Color(0,0,0);
    Color _corBorda = null;

    // Componentes para os botoes
    Container _pBotoes = null;   // container dos botoes 
    JButton _jbProx = null;
    JButton _jbLimpar = null;
    JButton _jbSair = null;
    JButton _jbIniciar = null;
    JButton _jbFinalizar = null;
    JButton _jbSalvar = null;
    JButton _jbLer = null; 

    /**
     * @return Returns the _corBorda.
     */
    protected Color getCorBorda() {
        return _corBorda;
    }

    /**
     * @param borda The _corBorda to set.
     */
    protected void setCorBorda(Color borda) {
        _corBorda = borda;
    }

    /**
     * @return Returns the _corCelulas.
     */
    protected Color getCorCelulas() {
        return _corCelulas;
    }

    /**
     * @param celulas The _corCelulas to set.
     */
    protected void setCorCelulas(Color celulas) {
        _corCelulas = celulas;
    }

    /**
     * @return Returns the _corFundo.
     */
    protected Color getCorFundo() {
        return _corFundo;
    }

    /**
     * @param fundo The _corFundo to set.
     */
    protected void setCorFundo(Color fundo) {
        _corFundo = fundo;
    }

    /**
     * Construtor - inicia o ambiente (matriz) para o jogo da vida
     * @param linhas linhas para o ambiente
     * @param colunas colunas para o ambiente
     */
    UIGraf (int linhas, int colunas) {

        // Ativa construtor pai e Cria o ambiente (matriz)
        //super(linhas, colunas);
        jvr = new JosephusRegras(linhas, colunas);

        _jframe = new JFrame("Josephus");
        _jframe.setSize(500, 500);
        _jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // painel de ambiente
        _pAmbiente = new JPanel(); // cria painel
        _pAmbiente.setLayout(new GridLayout(linhas, colunas)); // define layout
        _labels = new JLabel [linhas][colunas]; // cria array de jpanels
        setCorCelulas(Color.BLUE);
        setCorFundo(Color.BLACK);
        setCorBorda(Color.GRAY);

        // painel de botoes
        _pBotoes = new JPanel();
        // Define layout dos botoes
        _pBotoes.setLayout (new FlowLayout ());        
    }

    /**
     * Adiciona os componentes na janela
     * @param frame janela principal (JFrame)
     */
    public void adicionaComponentes() {

        // Pega o container da janela principal
        Container pane = _jframe.getContentPane();

        // Insere painel de ambiente e as celulas(jlabels)
        inserePainelAmbiente(pane);

        // Insere painel de botoes e os botoes 
        inserePainelBotoes(pane);        
    }

    /**
     * Trata evento mouse pressionado
     * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
     */
    public void mousePressed(MouseEvent e) {
        Component c = e.getComponent();

        // Se for pressionado um JButton
        if (c instanceof JButton) {
            JButton jb = (JButton)c;
            if(jb.getText().equals("Proxima geracao")){                
                desenhaAmbiente((int)jvr.simularJosephus()); 
            }            
            else if(jb.getText().equals("Iniciar")){
                iniciarAnimacao();
            }
            else if(jb.getText().equals("Sair")){
                System.exit(0);
            }
        }
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    /* (non-Javadoc)
     * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
     */
    public void mouseDragged(MouseEvent e) {
    }

    /* (non-Javadoc)
     * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
     */
    public void mouseMoved(MouseEvent e) {
    }

    /**
     * Trata eventos de opcoes de menu
     * @param e comando correspondente a opcao
     */
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        if (comando.equals("Sair")){
            System.exit(0);        
        } else if (comando.equals("Sobre")){
            sobre();
        } else if (comando.equals("Motif")){
            try {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
                SwingUtilities.updateComponentTreeUI(_jframe);
                _jframe.pack();
            } catch (Exception ex){

            }
        } else if (comando.equals("Windows")){
            try {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                SwingUtilities.updateComponentTreeUI(_jframe);
                _jframe.pack();
            } catch (Exception ex){

            }
        } else if (comando.equals("Metal")){
            try {
                UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
                SwingUtilities.updateComponentTreeUI(_jframe);
                _jframe.pack();
            } catch (Exception ex){

            }       
        }
    }

    private void desenhaAmbiente(int remover){
        int indiceLinear = remover - 1;
        int i = indiceLinear / 40;
        int j = indiceLinear % 40;        
        _labels[i][j].setBackground(new Color(0,100,0));
        _labels[i][j].setForeground(new Color(0,0,100));
    }

    private class Simulacao extends Thread {
        public boolean continuar = true;
        Object removido;
        public void run() {
            try {
                while (continuar) {
                    removido= jvr.simularJosephus();
                    if(removido == null){
                        finalizarAnimacao();
                    }
                    else{
                        desenhaAmbiente((int)removido);                    
                        sleep(300);        
                    }                    
                }    
            } catch (InterruptedException e) {
                System.out.println("ERRO INESPERADO");
                System.exit(0);
            }    
        }
    }

    public void iniciarAnimacao() {
        _simular = new Simulacao();
        _simular.start();
        _jbIniciar.setEnabled(false);        
    }

    public void finalizarAnimacao() {
        _simular.continuar = false;
        _jbIniciar.setEnabled(true);        
        _simular = null;
    }

    /**
     * Mostra a interface com os componentes
     */
    public void mostraAmbienteGUI() {
        JFrame.setDefaultLookAndFeelDecorated(true); // Estilo Java (default)

        //Define a janela e adiciona os componentes.
        adicionaComponentes();

        //Mostra a janela.
        _jframe.pack();
        _jframe.setVisible(true);
    }

    private void inserePainelAmbiente(Container pane){

        // Cria e define os jlabels para o ambiente
        for (int i = 0; i < _labels.length; i++) {
            for (int j = 0; j < _labels[i].length; j++) {
                // Cria o jlabel de define os atributos
                _labels[i][j] =  new JLabel (""+ i +","+ j);
                _labels[i][j].setPreferredSize(new Dimension (15, 15));
                _labels[i][j].setMaximumSize(new Dimension (15, 15));
                _labels[i][j].setMinimumSize(new Dimension (15, 15));
                _labels[i][j].setToolTipText("(" + (39-i) + "," + (39-j) + ")");
                _labels[i][j].setOpaque(true);
                _labels[i][j].setForeground(getCorFundo());
                _labels[i][j].setBorder(BorderFactory.createLineBorder(getCorBorda()));
                _labels[i][j].setBackground(getCorFundo());
                _labels[i][j].addMouseListener(this);
                _labels[i][j].addMouseMotionListener(this);

                // Adiciona o jlabel para o painel de ambiente
                _pAmbiente.add(_labels[i][j], 0);
            }
        }
        // Adiciona o painel de jlabels na posicao norte da janela principal 
        pane.add ("North", _pAmbiente);
    }

    private void inserePainelBotoes(Container pane){
        // Cria os botoes
        _jbProx = new JButton ("Proxima geracao");
        _jbProx.setToolTipText("Mostra proxima geracao");
        _jbProx.addMouseListener(this);       

        _jbIniciar = new JButton ("Iniciar");
        _jbIniciar.setToolTipText("Inicia Simulacao");
        _jbIniciar.addMouseListener(this);     

        _jbSair = new JButton ("Sair");
        _jbSair.setToolTipText("Termina programa");
        _jbSair.addMouseListener(this);

        // adiciona os botoes
        _pBotoes.add (_jbProx);        
        _pBotoes.add (_jbIniciar);        
        _pBotoes.add (_jbSair);

        //adiciona o painel de botoes na posi��o sul da janela principal
        pane.add ("South", _pBotoes);
    }

    private void sobre(){
        String texto = "Jogo da Vida\nVersão 2.0\n\n"
            + "(c) Copyright 2026. Todos os direitos reservados.\n\n"
            + "LED - Laboratório de Estruturas Dinâmicas\n"
            + "Prof. Dr. Julio Arakaki\nCiência da Computação - PUCSP";

        JOptionPane.showMessageDialog(null, texto, "Sobre Jogo da Vida", JOptionPane.INFORMATION_MESSAGE);
    }
}

