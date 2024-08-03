package LogicaDoJogo;

import java.io.*;
import java.util.*;

public class JogoDaForca {
    private List<String> palavras;
    private List<String> dicas;
    private String palavraSorteada;
    private String dicaSorteada;
    private Set<String> letrasAdivinhadas;
    private int penalidade;
    private StringBuilder palavraAdivinhada;
    private List<String> tentativasErradas; 

    public JogoDaForca() {
        palavras = new ArrayList<>();
        dicas = new ArrayList<>();
        letrasAdivinhadas = new HashSet<>();
        tentativasErradas = new ArrayList<>(); 
    }

    private void carregarPalavras(String categoria) throws Exception {
        palavras.clear();
        dicas.clear();
        try (BufferedReader br = new BufferedReader(new FileReader("arquivos/" + categoria + ".txt"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] parts = linha.split(";");
                if (parts.length == 2) {
                    palavras.add(parts[0].trim());
                    dicas.add(parts[1].trim());
                } else {
                    System.err.println("Linha ignorada (formato incorreto): " + linha);
                }
            }
        } catch (FileNotFoundException e) {
            throw new Exception("Arquivo de palavras inexistente");
        } catch (IOException e) {
            throw new Exception("Erro ao ler o arquivo de palavras");
        }
    }

    public void iniciar(String categoria) throws Exception {
        carregarPalavras(categoria);
        if (palavras.isEmpty()) {
            throw new Exception("Nenhuma palavra carregada para a categoria " + categoria);
        }
        Random random = new Random();
        int indice = random.nextInt(palavras.size());
        palavraSorteada = palavras.get(indice).toLowerCase();
        dicaSorteada = dicas.get(indice);
        letrasAdivinhadas.clear();
        penalidade = 0;
        palavraAdivinhada = new StringBuilder("_".repeat(palavraSorteada.length()));
        tentativasErradas.clear(); 
        System.out.println("Palavra sorteada: " + palavraSorteada); 
    }
    
    public void iniciar() throws Exception {
        String categoria = "categoriapadrao";
        carregarPalavras(categoria);
        if (palavras.isEmpty()) {
            throw new Exception("Nenhuma palavra carregada para a categoria " + categoria);
        }
        Random random = new Random();
        int indice = random.nextInt(palavras.size());
        palavraSorteada = palavras.get(indice).toLowerCase();
        dicaSorteada = dicas.get(indice);
        letrasAdivinhadas.clear();
        penalidade = 0;
        palavraAdivinhada = new StringBuilder("_".repeat(palavraSorteada.length()));
        tentativasErradas.clear(); 
        System.out.println("Palavra sorteada: " + palavraSorteada); 
    }

    public String getPalavraSorteada() {
        return palavraSorteada;
    }

    public String getDica() {
        if (dicaSorteada == null) {
            return "";
        }
        return dicaSorteada;
    }

    public int getTamanho() {
        if (palavraSorteada == null) {
            return 0;
        }
        return palavraSorteada.length();
    }

    public ArrayList<Integer> getOcorrencias(String letra) throws Exception {
        if (palavraSorteada == null) {
            throw new Exception("Nenhuma palavra foi sorteada ainda");
        }
        letra = letra.toLowerCase();
        if (letra.isEmpty() || letra.length() > 1 || letrasAdivinhadas.contains(letra)) {
            throw new Exception("Letra inválida ou já adivinhada");
        }

        letrasAdivinhadas.add(letra);
        ArrayList<Integer> ocorrencias = new ArrayList<>();
        for (int i = 0; i < palavraSorteada.length(); i++) {
            if (palavraSorteada.charAt(i) == letra.charAt(0)) {
                ocorrencias.add(i + 1);
                palavraAdivinhada.setCharAt(i, letra.charAt(0));
            }
        }

        if (ocorrencias.isEmpty()) {
            penalidade++;
            tentativasErradas.add(letra); 
        }

        return ocorrencias;
    }

    public boolean terminou() {
        return penalidade == 6 || (palavraAdivinhada != null && palavraAdivinhada.indexOf("_") == -1);
    }

    public String getPalavraAdivinhada() {
        if (palavraAdivinhada == null) {
            return "";
        }
        return palavraAdivinhada.toString();
    }

    public int getAcertos() {
        if (palavraSorteada == null) {
            return 0;
        }
        List<Character> caracteresPalavra = new ArrayList<>();
        for (char c : palavraSorteada.toCharArray()) {
            caracteresPalavra.add(c);
        }
        int acertos = 0;
        for (String letraStr : letrasAdivinhadas) {
            Character letra = letraStr.charAt(0);
            while (caracteresPalavra.contains(letra)) {
                acertos++;
                caracteresPalavra.remove(letra);
            }
        }
        
        return acertos;
    }

    public int getNumeroPenalidade() {
        return penalidade;
    }

    public String getNomePenalidade() {
        String[] penalidades = {
                "sem penalidades", "uma perna", "segunda perna",
                "um braço", "segundo braço", "tronco", "cabeça"
        };
        return penalidades[penalidade];
    }

    public String getResultado() {
        if (penalidade == 6) {
            return "você foi enforcado";
        } else if (palavraAdivinhada != null && palavraAdivinhada.indexOf("_") == -1) {
            return "voce venceu";
        } else {
            return "jogo em andamento";
        }
    }

    public List<String> getTentativasErradas() {
        return tentativasErradas;
    }

}
