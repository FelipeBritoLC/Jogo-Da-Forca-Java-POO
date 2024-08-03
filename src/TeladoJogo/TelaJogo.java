package TeladoJogo;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import LogicaDoJogo.JogoDaForca;

public class TelaJogo {
    private JogoDaForca jogo;
    private JFrame frame;
    private JLabel lblPalavra, lblDica, lblAcertos, lblPenalidade, lblImagem;
    private JTextField txtLetra;
    private JTextArea txtTentativasErradas;
    private JButton btnIniciar;
    private String categoriaSelecionada;

    public TelaJogo() {
        try {
            jogo = new JogoDaForca();
        } catch (Exception e) {
            e.printStackTrace();
        }
        frame = new JFrame("Jogo da Forca");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);
        frame.setLayout(new GridLayout(12, 1));

        btnIniciar = new JButton("Iniciar Jogo");
        btnIniciar.setBackground(new Color(0, 100, 0)); 
        btnIniciar.setForeground(Color.WHITE);
        btnIniciar.setBorder(new LineBorder(Color.WHITE, 2));
        btnIniciar.setFocusPainted(false);
        btnIniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (categoriaSelecionada != null) {
                        jogo.iniciar(categoriaSelecionada);
                        atualizarTela();
                        btnIniciar.setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Selecione uma categoria antes de iniciar o jogo.", "Erro",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Erro ao iniciar o jogo: " + ex.getMessage(), "Erro",
                            JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });
        btnIniciar.setVisible(false); 

        JPanel pnlCategorias = new JPanel();
        pnlCategorias.add(new JLabel("Escolha uma categoria:"));
        
        Dimension buttonSize = new Dimension(110, 50); 

        JButton btnCategoria1 = new JButton("Divas pop");
        btnCategoria1.setPreferredSize(buttonSize);
        btnCategoria1.setBackground(new Color(75, 0, 130)); 
        btnCategoria1.setForeground(Color.WHITE);
        btnCategoria1.setBorder(new LineBorder(Color.WHITE, 2));
        btnCategoria1.setFocusPainted(false); 
        btnCategoria1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                categoriaSelecionada = "divas_pop";
                JOptionPane.showMessageDialog(frame, "Categoria divas pop selecionada.", "Categoria",
                        JOptionPane.INFORMATION_MESSAGE);
                btnIniciar.setVisible(true); 
            }
        });

        JButton btnCategoria2 = new JButton("Filmes");
        btnCategoria2.setPreferredSize(buttonSize);
        btnCategoria2.setBackground(new Color(0, 0, 139)); 
        btnCategoria2.setForeground(Color.WHITE);
        btnCategoria2.setBorder(new LineBorder(Color.WHITE, 2));
        btnCategoria2.setFocusPainted(false); 
        btnCategoria2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                categoriaSelecionada = "Filmes";
                JOptionPane.showMessageDialog(frame, "Categoria filmes selecionada.", "Categoria",
                        JOptionPane.INFORMATION_MESSAGE);
                btnIniciar.setVisible(true); 
            }
        });

        JButton btnCategoria3 = new JButton("Esportes");
        btnCategoria3.setPreferredSize(buttonSize);
        btnCategoria3.setBackground(new Color(139, 0, 0)); 
        btnCategoria3.setForeground(Color.WHITE);
        btnCategoria3.setBorder(new LineBorder(Color.WHITE, 2));
        btnCategoria3.setFocusPainted(false); 
        btnCategoria3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                categoriaSelecionada = "esportes";
                JOptionPane.showMessageDialog(frame, "Categoria esportes selecionada.", "Categoria",
                        JOptionPane.INFORMATION_MESSAGE);
                btnIniciar.setVisible(true); 
            }
        });

        pnlCategorias.add(btnCategoria1);
        pnlCategorias.add(btnCategoria2);
        pnlCategorias.add(btnCategoria3);

        lblPalavra = new JLabel("Palavra: ");
        lblDica = new JLabel("Dica: ");
        txtLetra = new JTextField(1);
        
        JButton btnAdivinhar = new JButton("Adivinhar Letra");
        btnAdivinhar.setPreferredSize(buttonSize);
        btnAdivinhar.setBackground(new Color(0, 100, 0)); 
        btnAdivinhar.setForeground(Color.WHITE);
        btnAdivinhar.setBorder(new LineBorder(Color.WHITE, 2));
        btnAdivinhar.setFocusPainted(false); 
        btnAdivinhar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String letra = txtLetra.getText();
                    jogo.getOcorrencias(letra);
                    atualizarTela();
                    txtLetra.setText(""); 
                    if (jogo.terminou()) {
                        encerrarJogo();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });

        lblAcertos = new JLabel("Acertos: ");
        lblPenalidade = new JLabel("Penalidade: ");
        lblImagem = new JLabel();
        txtTentativasErradas = new JTextArea();
        txtTentativasErradas.setEditable(false);

        frame.add(pnlCategorias);
        frame.add(btnIniciar); 
        frame.add(lblPalavra);
        frame.add(lblDica);
        frame.add(txtLetra);
        frame.add(btnAdivinhar);
        frame.add(lblAcertos);
        frame.add(lblPenalidade);
        frame.add(lblImagem);
        frame.add(new JLabel("Tentativas Erradas: ")); 
        frame.add(new JScrollPane(txtTentativasErradas));

        frame.setVisible(true);
    }

    private void atualizarTela() {
        lblPalavra.setText(
                "Palavra: " + jogo.getPalavraAdivinhada() + " (" + jogo.getPalavraAdivinhada().length() + " letras)");
        lblDica.setText("Dica: " + jogo.getDica());
        lblAcertos.setText("Acertos: " + jogo.getAcertos());
        lblPenalidade.setText("Penalidade: " + jogo.getNumeroPenalidade() + " - " + jogo.getNomePenalidade());

        ImageIcon icon = new ImageIcon("imagens/" + jogo.getNumeroPenalidade() + ".png");
        Image image = icon.getImage();
        int width = lblImagem.getWidth();
        int height = lblImagem.getHeight();
        int imgWidth = image.getWidth(null);
        int imgHeight = image.getHeight(null);

        double aspectRatio = (double) imgWidth / imgHeight;
        if (width / aspectRatio <= height) {
            height = (int) (width / aspectRatio);
        } else {
            width = (int) (height * aspectRatio);
        }
        Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        lblImagem.setIcon(new ImageIcon(scaledImage));

        txtTentativasErradas.setText("");
        txtTentativasErradas.append(
                String.join(" ", jogo.getTentativasErradas().stream().map(String::toUpperCase).toArray(String[]::new))
        );
    }

    private void encerrarJogo() {
        String mensagemFim = "Fim do jogo: " + jogo.getResultado();
        if (!jogo.getResultado().equals("voce venceu")) {
            mensagemFim += "\nA palavra era: " + jogo.getPalavraSorteada();
        }
        JOptionPane.showMessageDialog(frame, mensagemFim, "Fim do Jogo", JOptionPane.INFORMATION_MESSAGE);
        int opcao = JOptionPane.showOptionDialog(frame,
                "Voce " + (jogo.getResultado().equals("voce venceu") ? "venceu!" : "foi enforcado.")
                        + "\nDeseja iniciar um novo jogo?",
                "Fim do Jogo",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new String[] { "Novo Jogo", "Encerrar" },
                "Novo Jogo");
        if (opcao == JOptionPane.YES_OPTION) {
            try {
                if (categoriaSelecionada != null) {
                    jogo.iniciar(categoriaSelecionada);
                    atualizarTela();
                } else {
                    JOptionPane.showMessageDialog(frame, "Selecione uma categoria antes de iniciar o jogo.", "Erro",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Erro ao iniciar o jogo: " + ex.getMessage(), "Erro",
                        JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        } else {
            System.exit(0);
        }
    }

}
