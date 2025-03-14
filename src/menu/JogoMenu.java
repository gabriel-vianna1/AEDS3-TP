package menu;

import crud.CRUD;
import java.util.Scanner;
import produtos.Jogo;

public class JogoMenu {
    private static int opcao;
    private static final Scanner entrada = new Scanner(System.in);

    public static void exibirMenu() {
        
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

    private static void procurarJogo() {
        System.out.println("Informe a posição do jogo que você deseja encontrar: ");
        int idProcurado = entrada.nextInt();
        Jogo jogo = CRUD.read(idProcurado);
        
      if(jogo == null){
            System.out.println("Não foi possível encontrar esse jogo. Verifique se ele não foi removido ou se essa posição existe");
        }
    
        else {
        System.out.println("Jogo encontrado: " + jogo.toString());
         
     }
    }

    private static void atualizaJogo() {

        System.out.println("Atualizando jogo...");
    }

    private static void excluirJogo() {

        System.out.println("Informe a posição do jogo que você deseja excluir: ");
        
        int idDeletar = entrada.nextInt();
        entrada.nextLine();

        Jogo jogo = CRUD.read(idDeletar);

        boolean deletado = CRUD.delete(idDeletar);

            if(deletado){
                System.out.println("O jogo  " + jogo.getTitle() + " foi excluído com sucesso! ");
            }
        else {
            System.out.println("Não foi possível excluír o jogo. Verifique se a posição é existente ou se ele já não foi removido. ");
        }
    }

    private static void criarJogo() {

        System.out.println("Criando jogo...");
    }


}

