import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * JosephusApp — Interface gráfica para o Problema de Josephus.
 *
 * 
 * Estrutura de layout:
 *   - Barra superior: Iniciar | Pausar | ✕
 *   - Centro: círculo de pessoas + label de status
 *   - Barra inferior: 3 campos de entrada
 *
 * @author Felipe Estima
 * @author Guilherme Pequeneza
 * @author Igor Dias
 * @author Thierry Nadjarian 
 * 
 * @version 26/05/26
 */
public class JosephusApp extends JFrame {

    // ─── Simulação ────────────────────────────────────────────────────
    private JosephusRegras regras;
    private int totalPessoas;
    private int passoK;
    private int velocidadeMs;

    // ─── Labels do círculo (índice = número da pessoa - 1) ────────────
    private final List<JLabel> slots = new ArrayList<>();
    private final Set<Integer> eliminados = new HashSet<>();

    // ─── Componentes UI ───────────────────────────────────────────────
    private JButton btnIniciar, btnPausar;
    private JTextField fieldQtd, fieldK, fieldVel;
    private JLabel lblStatus;
    private JLabel lblInfo;
    private JPanel painelCirculo;

    // ─── Worker ───────────────────────────────────────────────────────
    private SwingWorker<Void, Object[]> worker;

    // ─────────────────────────────────────────────────────────────────
    /**
     * Construtor: monta todos os painéis e exibe a janela.
     */
    public JosephusApp() {
        construirJanela();
        construirBarraSuperior();
        construirAreaCentral();
        construirBarraInferior();
        setVisible(true);
    }

    // ─── 1. Janela principal ──────────────────────────────────────────
    /**
     * Configura propriedades gerais da janela: tamanho, título, layout e cor de fundo.
     */
    private void construirJanela() {
        setTitle("Problema de Josephus");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(5, 5));
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(Color.DARK_GRAY);
    }

    // ─── 2. Barra superior ───────────────────────────────────────────
    /**
     * Constrói a barra superior contendo os botões Iniciar, Parar e o botão de fechar.
     */
    private void construirBarraSuperior() {
        JPanel topo = new JPanel(new BorderLayout());
        topo.setBackground(Color.DARK_GRAY);
        topo.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JPanel esquerda = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        esquerda.setBackground(Color.DARK_GRAY);

        btnIniciar = criarBotaoVerde("Iniciar");
        btnPausar  = criarBotaoVerde("Parar");
        btnIniciar.setEnabled(false);
        btnPausar.setEnabled(false);

        esquerda.add(btnIniciar);
        esquerda.add(btnPausar);

        JButton btnFechar = new JButton("✕");
        btnFechar.setForeground(Color.RED);
        btnFechar.setFont(new Font("Monospaced", Font.BOLD, 18));
        btnFechar.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        btnFechar.setContentAreaFilled(false);
        btnFechar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnFechar.addActionListener(e -> System.exit(0));

        topo.add(esquerda, BorderLayout.WEST);
        topo.add(btnFechar, BorderLayout.EAST);
        add(topo, BorderLayout.NORTH);

        btnIniciar.addActionListener(e -> iniciarSimulacao());
        btnPausar.addActionListener(e -> cancelarSimulacao());
    }

    // ─── 3. Área central ─────────────────────────────────────────────
    /**
     * Constrói a área central com o círculo de pessoas e os labels de status.
     */
    private void construirAreaCentral() {
        JPanel centro = new JPanel(new BorderLayout());
        centro.setBackground(Color.BLACK);
        centro.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        lblStatus = new JLabel("JOSEPHUS", JLabel.CENTER);
        lblStatus.setFont(new Font("Monospaced", Font.BOLD, 60));
        lblStatus.setForeground(Color.WHITE);
        lblStatus.setPreferredSize(new Dimension(0, 90));

        lblInfo = new JLabel(" ", JLabel.CENTER);
        lblInfo.setFont(new Font("Monospaced", Font.PLAIN, 15));
        lblInfo.setForeground(new Color(0, 200, 100));
        lblInfo.setPreferredSize(new Dimension(0, 26));

        painelCirculo = new JPanel(null); // layout absoluto para posicionar em círculo
        painelCirculo.setBackground(Color.BLACK);

        JPanel painelInfo = new JPanel(new BorderLayout());
        painelInfo.setBackground(Color.BLACK);
        painelInfo.add(lblStatus, BorderLayout.NORTH);
        painelInfo.add(lblInfo,   BorderLayout.SOUTH);

        centro.add(painelInfo,    BorderLayout.NORTH);
        centro.add(painelCirculo, BorderLayout.CENTER);

        add(centro, BorderLayout.CENTER);
    }

    // ─── 4. Barra inferior ───────────────────────────────────────────
    /**
     * Constrói o painel lateral direito que exibe a sequência de eliminados
     * e o sobrevivente ao término da simulação.
     */
    private void construirBarraInferior() {
        JPanel inferior = new JPanel(new GridLayout(1, 3, 8, 0));
        inferior.setBackground(Color.DARK_GRAY);
        inferior.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        fieldQtd = new JTextField();
        fieldK   = new JTextField();
        fieldVel = new JTextField();

        inferior.add(criarCampoRotulado("Quantidade de Pessoas (N)", fieldQtd));
        inferior.add(criarCampoRotulado("Número K de Passos",        fieldK));
        inferior.add(criarCampoRotulado("Velocidade da Simulação (ms)", fieldVel));

        add(inferior, BorderLayout.SOUTH);

        DocumentListener dl = new DocumentListener() {
            public void insertUpdate(DocumentEvent e)  { verificarCampos(); }
            public void removeUpdate(DocumentEvent e)  { verificarCampos(); }
            public void changedUpdate(DocumentEvent e) { verificarCampos(); }
        };
        fieldQtd.getDocument().addDocumentListener(dl);
        fieldK.getDocument().addDocumentListener(dl);
        fieldVel.getDocument().addDocumentListener(dl);
    }

    // ─── 5. Habilitar botão quando todos os campos estão preenchidos ──
    /**
     * Constrói a barra inferior com os três campos de configuração da simulação.
     * Ativa o botão Iniciar somente quando todos os campos estiverem preenchidos.
     */
    private void verificarCampos() {
        boolean ok = !fieldQtd.getText().trim().isEmpty()
                && !fieldK.getText().trim().isEmpty()
                && !fieldVel.getText().trim().isEmpty();
        btnIniciar.setEnabled(ok);
        btnPausar.setEnabled(ok);
    }

    // ─── 6. Desenha círculo de N pessoas ─────────────────────────────
    /**
     * Habilita o botão Iniciar somente quando os três campos de entrada
     * estiverem preenchidos com algum conteúdo.
     */
    private void desenharCirculo(int n) {
        painelCirculo.removeAll();
        slots.clear();
        eliminados.clear();

        int largura = painelCirculo.getWidth();
        int altura  = painelCirculo.getHeight();
        if (largura == 0) largura = 980;
        if (altura  == 0) altura  = 450;

        int tamanhoCell = Math.min(52, Math.max(22, (int)(Math.min(largura, altura) * 0.36 * Math.PI / n)));
        int raioX = (int)(largura * 0.42) - tamanhoCell;
        int raioY = (int)(altura  * 0.42) - tamanhoCell;
        int cx = largura / 2;
        int cy = altura  / 2;

        for (int i = 0; i < n; i++) {
            double angulo = 2 * Math.PI * i / n - Math.PI / 2;
            int x = (int)(cx + raioX * Math.cos(angulo)) - tamanhoCell / 2;
            int y = (int)(cy + raioY * Math.sin(angulo)) - tamanhoCell / 2;

            JLabel lbl = new JLabel(String.valueOf(i + 1), JLabel.CENTER);
            lbl.setBounds(x, y, tamanhoCell, tamanhoCell);
            lbl.setOpaque(true);
            lbl.setBackground(Color.DARK_GRAY);
            lbl.setForeground(Color.WHITE);
            lbl.setFont(new Font("Monospaced", Font.BOLD, Math.max(9, tamanhoCell / 3)));
            lbl.setBorder(BorderFactory.createLineBorder(new Color(0, 150, 0), 2));
            painelCirculo.add(lbl);
            slots.add(lbl);
        }

        painelCirculo.revalidate();
        painelCirculo.repaint();
    }

    // ─── 7. Inicia a simulação via SwingWorker ────────────────────────
    /**
     * Lê os parâmetros dos campos, valida-os, inicializa a lógica de Josephus
     * e executa a simulação em uma thread de fundo (SwingWorker).
     * Cada eliminação é publicada para a EDT atualizar a UI.
     */
    private void iniciarSimulacao() {
        if (worker != null && !worker.isDone()) return;

        try {
            totalPessoas = Integer.parseInt(fieldQtd.getText().trim());
            passoK       = Integer.parseInt(fieldK.getText().trim());
            velocidadeMs = Integer.parseInt(fieldVel.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "N, K e Velocidade devem ser números inteiros válidos!",
                    "Entrada Inválida", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (totalPessoas < 2) {
            JOptionPane.showMessageDialog(this,
                    "N deve ser maior ou igual a 2!", "Entrada Inválida", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (passoK < 2) {
            JOptionPane.showMessageDialog(this,
                    "K deve ser maior que 1!", "Entrada Inválida", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (velocidadeMs < 1) velocidadeMs = 100;

        bloquearCampos(false);
        lblStatus.setFont(new Font("Monospaced", Font.BOLD, 50));
        lblStatus.setText("SIMULANDO...");
        lblInfo.setText(" ");

        regras = new JosephusRegras(totalPessoas, passoK);

        SwingUtilities.invokeLater(() -> desenharCirculo(totalPessoas));

        worker = new SwingWorker<Void, Object[]>() {

            @Override
            protected Void doInBackground() throws Exception {
                Thread.sleep(500);

                while (regras.getTamanho() > 1 && !isCancelled()) {
                    Object removido = regras.simularJosephus();
                    if (removido != null) {
                        publish(new Object[]{removido, regras.getTamanho(), false});
                    }
                    Thread.sleep(velocidadeMs);
                }

                if (!isCancelled()) {
                    publish(new Object[]{null, 1, true});
                }
                return null;
            }

            @Override
            protected void process(List<Object[]> chunks) {
                for (Object[] dados : chunks) {
                    boolean concluido = (boolean) dados[2];

                    if (concluido) {
                        int sobrevivente = -1;
                        for (int i = 0; i < slots.size(); i++) {
                            if (!eliminados.contains(i + 1)) {
                                sobrevivente = i + 1;
                                break;
                            }
                        }

                        lblStatus.setFont(new Font("Monospaced", Font.BOLD, 40));
                        lblStatus.setText("SOBREVIVENTE: " + sobrevivente + " ✓");
                        lblInfo.setText("Simulação concluída  |  N=" + totalPessoas + "  K=" + passoK);

                        if (sobrevivente > 0) {
                            JLabel lbl = slots.get(sobrevivente - 1);
                            lbl.setBackground(new Color(160, 120, 0));
                            lbl.setForeground(Color.YELLOW);
                            lbl.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
                        }
                    } else {
                        int valorEliminado = (int) dados[0];
                        int restantes      = (int) dados[1];

                        eliminados.add(valorEliminado);

                        JLabel lbl = slots.get(valorEliminado - 1);
                        lbl.setBackground(new Color(100, 0, 0));
                        lbl.setForeground(new Color(70, 70, 70));
                        lbl.setBorder(BorderFactory.createLineBorder(new Color(60, 0, 0), 1));

                        lblStatus.setFont(new Font("Monospaced", Font.BOLD, 50));
                        lblStatus.setText("ELIMINADO: " + valorEliminado);
                        lblInfo.setText("Restantes: " + restantes + "  |  N=" + totalPessoas + "  K=" + passoK);
                    }
                }
            }

            @Override
            protected void done() {
                bloquearCampos(true);
                if (isCancelled()) {
                    lblStatus.setFont(new Font("Monospaced", Font.BOLD, 55));
                    lblStatus.setText("CANCELADO");
                    lblInfo.setText(" ");
                }
            }
        };

        worker.execute();
    }

    // ─── 8. Cancela simulação ─────────────────────────────────────────

    /**
     * Cancela a Simulacao
     */
    private void cancelarSimulacao() {
        if (worker != null && !worker.isDone()) {
            worker.cancel(true);
        }
    }

    // ─── 9. Bloqueia/libera campos durante a simulação ────────────────

    /**
     * Enquanto a simulacao ocorre bloqueia a edicao de informacoes
     *
     * @param editavel
     */
    private void bloquearCampos(boolean editavel) {
        fieldQtd.setEditable(editavel);
        fieldK.setEditable(editavel);
        fieldVel.setEditable(editavel);
        Color cor = editavel ? Color.WHITE : new Color(200, 200, 200);
        fieldQtd.setBackground(cor);
        fieldK.setBackground(cor);
        fieldVel.setBackground(cor);
    }

    // ─── Auxiliares ───────────────────────────────────────────────────
    private JPanel criarCampoRotulado(String textoRotulo, JTextField campo) {
        JPanel painel = new JPanel(new BorderLayout(0, 3));
        painel.setBackground(Color.DARK_GRAY);

        JLabel rotulo = new JLabel(textoRotulo);
        rotulo.setFont(new Font("Monospaced", Font.PLAIN, 11));
        rotulo.setForeground(Color.WHITE);

        campo.setFont(new Font("Monospaced", Font.PLAIN, 13));
        campo.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        campo.setBackground(Color.GRAY);

        painel.add(rotulo, BorderLayout.NORTH);
        painel.add(campo,  BorderLayout.CENTER);
        return painel;
    }

    private JButton criarBotaoVerde(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Monospaced", Font.PLAIN, 13));
        btn.setForeground(Color.BLACK);
        btn.setBorder(BorderFactory.createLineBorder(new Color(0, 150, 0), 2));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(160, 40));
        btn.setBackground(Color.GREEN);
        return btn;
    }

    // ─── Ponto de entrada ─────────────────────────────────────────────
    public static void main(String[] args) {
        SwingUtilities.invokeLater(JosephusApp::new);
    }
}
