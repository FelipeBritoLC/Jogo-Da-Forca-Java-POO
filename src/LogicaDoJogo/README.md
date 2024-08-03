# Explicação dos métodos da classe JogoDaForca

## 💫 Classe JogoDa Forca:
```java
public class JogoDaForca {
    private List<String> palavras;
    private List<String> dicas;
    private String palavraSorteada;
    private String dicaSorteada;
    private Set<String> letrasAdivinhadas;
    private int penalidade;
    private StringBuilder palavraAdivinhada;
    private List<String> tentativasErradas;
```
### - Declara as variáveis que vamos utilizar de forma privada.
- a variável letrasAdivinhadas é uma coleção de strings, utilizando o set para garantir que cada letra seja armazenada apenas uma vez.
- variável palavraAdivinhada é um StringBuilder. O StringBuilder é usado para construir e modificar a string de maneira eficiente.

## 💫 Método JogoDaForca:
```java
public JogoDaForca() {
        palavras = new ArrayList<>();
        dicas = new ArrayList<>();
        letrasAdivinhadas = new HashSet<>();
        tentativasErradas = new ArrayList<>(); 
    }
```
### Inicializa as coleções que serão usadas no jogo:
- palavras como um novo ArrayList para armazenar as palavras do jogo.
- dicas como um novo ArrayList para armazenar as dicas associadas às palavras.
- letrasAdivinhadas como um novo HashSet para rastrear as letras que já foram adivinhadas pelo jogador, garantindo que não haja duplicatas.
- tentativasErradas como um novo ArrayList para armazenar as tentativas incorretas feitas pelo jogador.
  
## 💫 Método carregarPalavras:
```java
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
```
### 1. Limpeza das listas:
- palavras.clear(); e dicas.clear(); limpam as listas palavras e dicas para garantir que estejam vazias antes de carregar novos dados.
### 2. Abertura do Arquivo:
- Tenta abrir um arquivo localizado no diretório "arquivos", cujo nome é composto pela categoria fornecida e a extensão ".txt".
### 3. Leitura do Arquivo:
- Utiliza um BufferedReader para ler o arquivo linha por linha.
- Para cada linha, divide a linha usando o caractere ; como delimitador.
- Se a linha contém exatamente dois componentes após a divisão:
  1. O primeiro componente (antes do ;) é tratado como uma palavra e adicionado à lista palavras.
  2. O segundo componente (após o ;) é tratado como uma dica e adicionado à lista dicas.
- Se a linha não estiver no formato correto (não contém exatamente dois componentes), imprime uma mensagem de erro e ignora a linha.
### 4. Tratamento de Exceções:
- Captura e trata FileNotFoundException para o caso de o arquivo não existir, lançando uma nova exceção com a mensagem "Arquivo de palavras inexistente".
- Captura e trata IOException para outros erros de leitura de arquivo, lançando uma nova exceção com a mensagem "Erro ao ler o arquivo de palavras".

## 💫 Método iniciar: 
```java
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
```
- Carrega as palavras e dicas para a categoria fornecida.
- Verifica se há palavras carregadas; caso contrário, lança uma exceção.
- Seleciona aleatoriamente uma palavra e sua dica da lista carregada.
- Inicializa as variáveis e estruturas de dados necessárias para o estado inicial do jogo.

## 💫 Método iniciar:
```java
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
```
### Sobrecarga do método iniciar:
- esse método é uma sobrecarga do método iniciar, utilizado quando não é passada nenhuma categoria na chamada do método, de modo que a categoria padrão é selecionada automaticamente.
- esse método é utilizado na aplicação console. 

## 💫 Método getPalavraSorteada:
```java
public String getPalavraSorteada() {
        return palavraSorteada;
    }
```
- Retorna a palavra sorteada

## 💫 Método getDica:
```java
public String getDica() {
        if (dicaSorteada == null) {
            return "";
        }
        return dicaSorteada;
    }
```
- Retorna a dica referente a palavra sorteada 
## 💫 Método getTamanho:
```java
public int getTamanho() {
        if (palavraSorteada == null) {
            return 0;
        }
        return palavraSorteada.length();
    }

```
- Retorna o tamanho referente a palavra sorteada

## 💫 Método getOcorrencias:
```java
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
```
- Verifica se uma palavra foi sorteada (palavraSorteada == null). Se não houver palavra sorteada, lança uma exceção com a mensagem "Nenhuma palavra foi sorteada ainda".
- Converte a letra adivinhada para minúscula (letra = letra.toLowerCase()).
- Verifica se a letra é inválida (vazia, tem mais de um caractere ou já foi adivinhada anteriormente). Se qualquer uma dessas condições for verdadeira, lança uma exceção com a mensagem "Letra inválida ou já adivinhada".
- Adiciona a letra ao conjunto letrasAdivinhadas.
- Cria uma lista ocorrencias para armazenar as posições (1-based) das ocorrências da letra na palavra sorteada.
- Itera sobre cada caractere da palavra sorteada. Se a letra no índice atual corresponder à letra adivinhada, adiciona o índice (mais 1) à lista ocorrencias e atualiza palavraAdivinhada para mostrar a letra adivinhada na posição correta.
- Se a lista ocorrencias estiver vazia (a letra adivinhada não está na palavra sorteada):
  1. Incrementa a penalidade (penalidade++).
  2. Adiciona a letra à lista de tentativas erradas (tentativasErradas).
- Retorna a lista de ocorrências da letra adivinhada.
## 💫 Método terminou:
```java
public boolean terminou() {
        return penalidade == 6 || (palavraAdivinhada != null && palavraAdivinhada.indexOf("_") == -1);
    }
```
- verifica se o jogo terminou

## 💫 Método getPalavraAdivinhada:
```java
public String getPalavraAdivinhada() {
        if (palavraAdivinhada == null) {
            return "";
        }
        return palavraAdivinhada.toString();
    }
```
- Verifica se palavraAdivinhada é null. Se for, retorna uma string vazia.
- Caso contrário, converte palavraAdivinhada, que representa a sequência de caracteres armazenada no StringBuilder, em String e a retorna.
## 💫 Método getAcertos:
```java
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
```
- retorna o número de acertos do usuário utilizando um loop para percorrer a palavra sorteada, e a cada letra do conjunto letrasAdivinhadas que estiver presente na palavra, incrementa um acerto.
    
## 💫 Método getNumeroPenalidade e getNomePenalidade:
```java
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
```
- retorna o número de penalidades e o nome da penalidade referente a essa quantidade.
## 💫 Método getTentativasErradas:
```java
public List<String> getTentativasErradas() {
        return tentativasErradas;
    }
```
- retorna uma lista contendo as letras que foram tentativas erradas do usuário.
## 💫 Método getResultado:
```java
public String getResultado() {
        if (penalidade == 6) {
            return "você foi enforcado";
        } else if (palavraAdivinhada != null && palavraAdivinhada.indexOf("_") == -1) {
            return "voce venceu";
        } else {
            return "jogo em andamento";
        }
    }
```
- verifica as condições de andamento do jogo e retorna o seu resultado.

    

    

