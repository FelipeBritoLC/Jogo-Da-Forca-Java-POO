package AplicacaoConsole; 

import java.util.ArrayList;
import java.util.Scanner;
import LogicaDoJogo.JogoDaForca;

public class AplicacaoConsole {
	private JogoDaForca jogo;
	private Scanner teclado;
	private String letraDigitada;		//letra lida do teclado
	private ArrayList<Integer> ocorrencias; // posicoes adivinhadas

	public AplicacaoConsole() {
		try {
			jogo = new JogoDaForca();
			jogo.iniciar();

			System.out.println("tamanho da palavra = " + jogo.getTamanho());
			System.out.println("dica: " + jogo.getDica());
			teclado = new Scanner(System.in);

			//loop de leituras
			do {
				System.out.println("\ndigite uma letra: " );
				letraDigitada = teclado.nextLine();
				try {
					ocorrencias = jogo.getOcorrencias(letraDigitada);
					if (ocorrencias.size() > 0) 
						System.out.println("voce acertou a letra =" + letraDigitada);
					else 
						System.out.println("voce errou a letra = " + letraDigitada);

					System.out.println("------------RESUMO-------------------");
					System.out.println("total de acertos = " + jogo.getAcertos());
					System.out.println("penalidade = " + jogo.getNumeroPenalidade() + "-" + jogo.getNomePenalidade());
					System.out.println("letras adivinhadas = " + jogo.getPalavraAdivinhada());
					System.out.println("-------------------------------------");
				} 
				catch (Exception e) {
					System.out.println(e.getMessage());
				}
			} while (!jogo.terminou());

			teclado.close();
			System.out.println("\n");
			System.out.println("resultado final = " + jogo.getResultado());
			System.out.println("letras adivinhadas = " + jogo.getPalavraAdivinhada());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		AplicacaoConsole app = new AplicacaoConsole();
	}
}
