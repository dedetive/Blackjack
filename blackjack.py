import random
baralho_base = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10]
as_jogador = [0, 0]
soma_jogador = [0, 0]
baralho_disponivel_jogador = [0, 0]
uma_vez = 0
baralho_disponivel_jogador[0] = baralho_disponivel_jogador[1] = baralho_base[:]
jogar_novamente = ""
possibilidades_de_parar = [14, 15, 15, 16, 16, 16, 16, 17, 17, 18, 18, 19]
cartas_compradas = 0

def resetar_tudo():
    global soma_jogador, jogar_novamente, baralho_disponivel_jogador, baralho_base
    baralho_disponivel_jogador[0] = baralho_disponivel_jogador[1] = baralho_base[:]
    soma_jogador[0] = soma_jogador[1] = 0
    jogar_novamente = ""

def acabou():
    global soma_jogador, jogar_novamente
    uma_vez = 0
    if soma_jogador[0] == soma_jogador[1] or (soma_jogador[0] > 21 and soma_jogador[1] > 21):
        while jogar_novamente != "jogar":
            if uma_vez == 0:
                jogar_novamente = str(input("Vocês empataram!\n\nDigite \'jogar\' para jogar novamente ou \'resetar\' para resetar os baralhos!\n"))
                uma_vez += 1
            acabou2()
    elif (soma_jogador[0] > soma_jogador[1] and soma_jogador[0] <= 21) or (soma_jogador[0] <= 21 and soma_jogador[1] > 21):
        while jogar_novamente != "jogar":
            if uma_vez == 0:
                jogar_novamente = str(input("Jogador 1 ganhou!\n\nDigite \'jogar\' para jogar novamente ou \'resetar\' para resetar os baralhos!\n"))
                uma_vez += 1
            acabou2()
    elif (soma_jogador[0] < soma_jogador[1] and soma_jogador[1] <= 21) or (soma_jogador[0] > 21 and soma_jogador[1] <= 21):
        while jogar_novamente != "jogar":
            if uma_vez == 0:
                jogar_novamente = str(input("Jogador 2 ganhou!\n\nDigite \'jogar\' para jogar novamente ou \'resetar\' para resetar os baralhos!\n"))
                uma_vez += 1
            acabou2()

def hit(numero):
    global menu, soma_jogador, baralho_disponivel_jogador, baralho_base, carta, numero_aleatorio, indice
    if menu == "hit" and soma_jogador[numero] < 21:
        numero_aleatorio = random.choice(baralho_disponivel_jogador[numero])
        carta_as(numero)
        if numero_aleatorio == 1:
            print(f"\nVocê comprou um ás (1 ou 11)!\n")
        else:
            print(f"\nVocê comprou um {numero_aleatorio}!\n")
    if menu == "hit" and soma_jogador[numero] == 21:
        print("\nVocê já está com 21!\n")
    elif menu == "hit" and soma_jogador[numero] > 21:
        print("\nVocê já estourou!\n")

def acabou2():
    global soma_jogador, as_jogador, escolha_menu, jogando, cartas_compradas
    soma_jogador[0] = as_jogador[0] = as_jogador[1] = soma_jogador[1] = jogando = cartas_compradas = 0
    escolha_menu = ""
    if jogar_novamente == "resetar":
        print("Baralhos resetados!")
        resetar_tudo()

def carta_as(numero):
    global soma_jogador, numero_aleatorio, cartas_compradas, as_jogador, indice
    soma_jogador[numero] += numero_aleatorio
    cartas_compradas += 1
    if numero_aleatorio == 1:
        as_jogador[numero] += 1
        soma_jogador[numero] += 10
    if soma_jogador[numero] > 21 and as_jogador[numero] >= 1:
        soma_jogador[numero] -= 10
        as_jogador[numero] -= 1
    indice = 0
    for carta in baralho_disponivel_jogador[numero]:
        if carta == numero_aleatorio:
            print(f"Deletou {carta} de baralho {baralho_disponivel_jogador[numero]}")
            del baralho_disponivel_jogador[numero][indice]
            if baralho_disponivel_jogador[numero] == []:
                baralho_disponivel_jogador[numero] = baralho_base[:]
                break
            break
        indice += 1

jogando = 0

resetar_tudo()

while True:
    escolha_menu = str(input("\nPara jogar, digite \'AI\' ou \'multijogador\': ")).lower()
    if escolha_menu == "ai":
        jogando = 1
        jogar_novamente = ""
    elif escolha_menu == "multijogador":
        jogando = 2
        jogar_novamente = ""
    while jogando == 1:
        menu = str(input(f"\nVocê deseja dar \'hit\' ou \'stand\'?\nSua soma até o momento é {soma_jogador[0]}.\n"))
        hit(0)
        if menu == "stand":
            jogando = 0
            parar_em = random.choice(possibilidades_de_parar)
            if sum(baralho_disponivel_jogador[1])/len(baralho_disponivel_jogador[1]) <= 4:
                if parar_em <= 17:
                    parar_em += int((sum(baralho_disponivel_jogador[1])/len(baralho_disponivel_jogador[1])))/2
            elif sum(baralho_disponivel_jogador[1])/len(baralho_disponivel_jogador[1]) >= 6.5:
                if parar_em >= 16:
                    parar_em -= int((sum(baralho_disponivel_jogador[1])/len(baralho_disponivel_jogador[1])))/2
            if cartas_compradas <= 2 and soma_jogador[0] < 16:
                parar_em = 1 + cartas_compradas * 7
            while soma_jogador[1] < parar_em:
                numero_aleatorio = random.choice(baralho_disponivel_jogador[1])
                carta_as(1)
            print(f"\n\nSua soma é de {soma_jogador[0]} e a soma do oponente é {soma_jogador[1]}.")
            acabou()
    
    
    
    
    while jogando == 2:
        if uma_vez == 0:
            print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nJogador 1!")
            uma_vez += 1
        menu = str(input(f"Você deseja dar \'hit\' ou \'stand\'?\nSua soma até o momento é {soma_jogador[0]}.\n"))
        hit(0)
        if menu == "stand":
            jogando = 3
            uma_vez = 0
            while jogando == 3:
                if uma_vez == 0:
                    print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nJogador 2!")
                    uma_vez += 1
                menu = str(input(f"Você deseja dar \'hit\' ou \'stand\'?\nSua soma até o momento é {soma_jogador[1]}.\n"))
                hit(1)
                if menu == "stand":
                    print(f"\n\nA soma do primeiro jogador é de {soma_jogador[0]} e a soma do segundo jogador é de {soma_jogador[1]}.")
                    acabou()