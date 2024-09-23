import java.util.*;

public class Blackjack {
	static int[] somaJogador = {0, 0}, asJogador = {0, 0};
	static int umaVez, cartasCompradas = 0;
	static String jogarNovamente = "";
	static int[] possibilidadesDeParar = {14, 15, 15, 16, 16, 16, 16, 17, 17, 18, 18, 19};
	static int[] baralhoBase = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10};
	static List<Integer>[] baralhoDisponivelJogador = new ArrayList[2];
	static {
		baralhoDisponivelJogador[0] = new ArrayList<>(Arrays.asList(toIntegerArray(baralhoBase)));
		baralhoDisponivelJogador[1] = new ArrayList<>(Arrays.asList(toIntegerArray(baralhoBase)));
	}
	public static Integer[] toIntegerArray(int[] intArray) {
		return Arrays.stream(intArray).boxed().toArray(Integer[]::new);
	}
	static int jogando = 0;
	static String escolhaMenu = "";

	public static void resetarTudo() {
		baralhoDisponivelJogador[0] = new ArrayList<>(Arrays.asList(toIntegerArray(baralhoBase)));
		baralhoDisponivelJogador[1] = new ArrayList<>(Arrays.asList(toIntegerArray(baralhoBase)));
		somaJogador[0] = 0;
		somaJogador[1] = 0;
		jogarNovamente = "";
	}

	public static void cartaAs (int numero, int numeroAleatorio) {
		somaJogador[numero] += numeroAleatorio;
		cartasCompradas++;
		if (numeroAleatorio == 1) {
			asJogador[numero]++;
			somaJogador[numero] += 10;
		} if (somaJogador[numero] > 21 && asJogador[numero] >= 1) {
			somaJogador[numero] -= 10;
			asJogador[numero] -= 1;
		} int indice = 0;
		for (int carta = baralhoDisponivelJogador[numero].size(); carta >= 0 ; carta--) {
			if (carta == numeroAleatorio) {
				baralhoDisponivelJogador[numero].remove(indice);
				if (baralhoDisponivelJogador[numero].isEmpty()) {
					baralhoDisponivelJogador[numero] = new ArrayList<>(Arrays.asList(toIntegerArray(baralhoBase)));
					break;
				} break;
			} indice++;
		}
	}

	public static void hit (int numero) {
		if (somaJogador[numero] < 21) {
			Random rand = new Random();
			int numeroAleatorio = baralhoDisponivelJogador[numero].get(rand.nextInt(baralhoDisponivelJogador[numero].size()));
			cartaAs(numero, numeroAleatorio);
			if (numeroAleatorio == 1) {
				System.out.println("\nVocê comprou um às (1 ou 11)!\n");
			} else {
				System.out.println("\nVocê comprou um " + numeroAleatorio + "!\n");
			}
		} else if (somaJogador[numero] == 21) {
			System.out.println("\nVocê já está com 21!\n");
		} else if (somaJogador[numero] > 21) {
			System.out.println("\nVocê já estourou!\n");
		}
	}

	public static void acabou() {
		umaVez = 0;
		if ((somaJogador[0] == somaJogador[1]) || (somaJogador[0] > 21 && somaJogador[1] > 21)) {
			while (!Objects.equals(jogarNovamente, "jogar")) {
				if (umaVez == 0) {
					System.out.print("Vocês empataram!\n\nDigite 'jogar' para jogar novamente ou 'resetar' para resetar os baralhos!\n");
					umaVez++;
				}
				jogarNovamente = System.console().readLine();
				acabou2();
			}
		} else if ((somaJogador[0] > somaJogador[1] && somaJogador[0] <= 21) || (somaJogador[0] <= 21 && somaJogador[1] > 21)) {
			while (!Objects.equals(jogarNovamente, "jogar")) {
				if (umaVez == 0) {
					System.out.print("Jogador 1 ganhou!\n\nDigite 'jogar' para jogar novamente ou 'resetar' para resetar os baralhos!\n");
					umaVez++;
				}
				jogarNovamente = System.console().readLine();
				acabou2();
			}
		} else {
			while (!Objects.equals(jogarNovamente, "jogar")) {
				if (umaVez == 0) {
					System.out.print("Jogador 2 ganhou!\n\nDigite 'jogar' para jogar novamente ou 'resetar' para resetar os baralhos!\n");
					umaVez++;
				}
				jogarNovamente = System.console().readLine();
				acabou2();
			}
		}
	}

	public static void acabou2() {
		somaJogador[0] = 0;
		somaJogador[1] = 0;
		asJogador[0] = 0;
		asJogador[1] = 0;
		jogando = 0;
		cartasCompradas = 0;
		escolhaMenu = "";
		if (jogarNovamente.equals("resetar")) {
			System.out.println("Baralhos resetados!");
			resetarTudo();
		}
	}


	public static void main(String[] args) {
		resetarTudo();
		label:
		while (true) {
		System.out.print("\nPara jogar, digite 'AI' ou 'multijogador': ");
		escolhaMenu = (System.console().readLine()).toLowerCase();
			switch (escolhaMenu) {
				case "ai":
					jogando = 1;
					jogarNovamente = "";
					break;
				case "multijogador":
					jogando = 2;
					jogarNovamente = "";
					break;
				case "sair":
					break label;
			}
			while (jogando == 1) {
				System.out.println("\nVocê deseja dar 'hit' ou 'stand'?\nSua soma até o momento é " + somaJogador[0] + ".\n");
				String menu = System.console().readLine().toLowerCase();
				switch (menu) {
					case "hit":
						hit(0);
						break;
					case "stand":
						jogando = 0;
						Random rand = new Random();
						int pararEm = possibilidadesDeParar[rand.nextInt(possibilidadesDeParar.length)];
						if ((double) (baralhoDisponivelJogador[1].stream().mapToInt(Integer::intValue).sum())/(baralhoDisponivelJogador[1].size()) <= 4) {
							if (pararEm <= 17) {
								pararEm += ((baralhoDisponivelJogador[1].stream().mapToInt(Integer::intValue).sum())/(baralhoDisponivelJogador[1].size()))/2;
							}
						} else if ((double) (baralhoDisponivelJogador[1].stream().mapToInt(Integer::intValue).sum()) /(baralhoDisponivelJogador[1].size()) >= 6.5) {
							if (pararEm >= 16) {
								pararEm -= ((baralhoDisponivelJogador[1].stream().mapToInt(Integer::intValue).sum()) / (baralhoDisponivelJogador[1].size()))/2;
							}
						}
						if (cartasCompradas <= 2 && somaJogador[0] < 16) {
							pararEm = 1 + cartasCompradas*7;
						} while (somaJogador[1] < pararEm) {
						rand = new Random();
						int numeroAleatorio = baralhoDisponivelJogador[1].get(rand.nextInt(baralhoDisponivelJogador[1].size()));
						cartaAs(1, numeroAleatorio);
					}
						System.out.println("\n\nSua soma é de " + somaJogador[0] + " e a soma do oponente é " + somaJogador[1] + ".");
						acabou();
						break;
				}
			}
		while (jogando == 2) {
			if (umaVez == 0) {
				System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nJogador 1!");
				umaVez++;
			}
			System.out.println("\nVocê deseja dar 'hit' ou 'stand'?\nSua soma até o momento é " + somaJogador[0] + ".\n");
			String menu = System.console().readLine().toLowerCase();
			switch (menu) {
				case "hit":
					hit(0);
					break;
				case "stand":
					jogando = 3;
					umaVez = 0;
					while (jogando == 3) {
						if (umaVez == 0) {
							System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nJogador 2!");
							umaVez++;
						}
						System.out.println("\nVocê deseja dar 'hit' ou 'stand'?\nSua soma até o momento é " + somaJogador[1] + ".\n");
						menu = System.console().readLine().toLowerCase();
						switch (menu) {
							case "hit":
								hit(1);
								break;
							case "stand":
								System.out.println("\n\nA soma do primeiro jogador é de " + somaJogador[0] + " e a soma do segundo jogador é de " + somaJogador[1] + ".");
								acabou();
								break;
						}
					} break;
			}
			}
		}
	}
}