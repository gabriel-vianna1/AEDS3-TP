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

    public static Jogo lerJogo(){

        Jogo jogo = new Jogo();
        jogo.setId(0); // Inicializa o valor do ID como 0 por precaução, pois não será usado no create.
        int quantGeneros;
    

       System.out.println("Digite o título do jogo: ");
       jogo.setTitle(entrada.nextLine());

       System.out.println("Digite a data de lançamento do jogo: Exmp -> Feb 02, 2005");
       jogo.setReleaseDate(produtos.Jogo.FormatDate(entrada.nextLine()));
       
       System.out.println("Avalie o jogo com uma nota de 1.0 a 5.0: ");
       jogo.setRating(entrada.nextFloat());

       System.out.println("Digite quantas avaliações esse jogo possui: ");
       System.out.println("Obs: Caso esse número for maior que 1000, será representado como -> Exmp: 1500 = 1.5K");
       jogo.setReviews((entrada.nextLine()));

       entrada.nextLine(); // Limpa o buffer.

       System.out.println("Digite a quantidade de genêros em que esse jogo se encaixa: ");
        quantGeneros = entrada.nextInt();
        entrada.nextLine(); // Limpa novamente.
        
        String[] generos = new String[quantGeneros]; // Array contendo os gêneros
    
        System.out.println("Informe esses genêros: ");
            for(int i = 0; i < quantGeneros; i++){
                generos[i] = entrada.nextLine();
            } 
       jogo.setGenres(generos);

       System.out.println("Digite a quantidade de horas totais jogadas em média por jogador: ");
       jogo.setPlays(entrada.nextLine());

       System.out.println("Digite o país onde esse jogo foi desenvolvido: Obs-> Canadá = CAN, Brasil = BRA, etc. ");
       jogo.setCountry(entrada.nextLine());
       
        
       return jogo; // Retorna com os novos valores
    }

    private static void procurarJogo() {

        System.out.println("Informe a posição do jogo que você deseja encontrar: ");
        int idProcurado = entrada.nextInt();

    if(idProcurado > 0){ // Verifica o se o ID é válido
        Jogo jogo = CRUD.read(idProcurado);
        
      if(jogo == null){
            System.out.println("Não foi possível encontrar esse jogo. Verifique se ele não foi removido ou se essa posição existe");
        }
    
        else {
        System.out.println("Jogo encontrado: " + jogo.toString());
         
     }
    }
  }

private static void atualizaJogo() {

    boolean atualizado;

        System.out.println("Informe a posição do jogo que você deseja atualizar");
        int idAtualizar = entrada.nextInt();
        entrada.nextLine(); // Limpa o buffer

    if(idAtualizar > 0){ // Verifica se o ID é valido.
        Jogo jogo = lerJogo(); // Chama o método lerJogo, que solicita os novos valores ao usuário.
        jogo.setId(idAtualizar);
        atualizado = CRUD.update(jogo); // Chamamos o método update para atualizar e substituir os valores antigos pelos valores inseridos

            if(atualizado){
                System.out.println("Atualizado com sucesso! ");
            }
 
        else{
        System.out.println("Falha ao atualizar");

     }
 }
    else{
        System.out.println("Favor inserir um valor válido! ");
    }      
}

    private static void excluirJogo() {

        System.out.println("Informe a posição do jogo que você deseja excluir: "); // Pega a posição do ID
        
        int idDeletar = entrada.nextInt();
        entrada.nextLine();
    
    if(idDeletar > 0){

        Jogo jogo = CRUD.read(idDeletar); // Lê o ID que vai ser deletado

        boolean deletado = CRUD.delete(idDeletar); // Chama o método delete

            if(deletado){
                System.out.println("O jogo  " + jogo.getTitle() + " foi excluído com sucesso! ");
            }
        else {
            System.out.println("Não foi possível excluír o jogo. Verifique se a posição é existente ou se ele já não foi removido. ");
        }
      }
    }

    private static void criarJogo() {

        Jogo jogo = lerJogo();
        CRUD.create(jogo); // Chama o método create

    }


}

