package menu;

import crud.CRUD;
import crud.ImportadorCSV;
import produtos.Jogo;
import java.util.Scanner;

public class JogoMenu {
    private int opcao;
    private final Scanner entrada = new Scanner(System.in);

    public void exibirMenu() {
        do {

            System.out.println("\nEscolha a operação: ");
            System.out.println("1 - Procurar");
            System.out.println("2 - Atualizar");
            System.out.println("3 - Excluir");
            System.out.println("4 - Criar");
            System.out.println("0 - Encerrar o programa");
            System.out.print("Opção: ");

            opcao = entrada.nextInt();
            entrada.nextLine(); 

            switch (opcao) {

                case 1 -> procurarJogo();
                case 2 -> atualizaJogo();
                case 3 -> excluirJogo();
                case 4 -> criarJogo();

                case 0 -> {
                    System.out.println("Encerrando");
                    break;
                }

                default -> System.out.println("Opção inválida");

            }
        } while (opcao != 0);
    }

    private void procurarJogo() {
        System.out.println("Informe a posição do jogo que você deseja encontrar: ");
        int idProcurado = entrada.nextInt();
        Jogo jogo = CRUD.read();
        System.out.println("Procurando jogo...");
    }

    private void atualizaJogo() {

        System.out.println("Atualizando jogo...");
    }

    private void excluirJogo() {

        System.out.println("Excluindo jogo...");
    }

    private void criarJogo() {

        System.out.println("Criando jogo...");
    }


}

