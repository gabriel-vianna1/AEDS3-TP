import crud.ImportadorCSV;
import java.util.*;
import menu.JogoMenu;
import produtos.Jogo;

public class Main {
    public static void main(String[] args) {
        
    List<Jogo> listaJogos = ImportadorCSV.CriarLista();
    
    try{
        ImportadorCSV.criaByteArq(listaJogos);
    }catch(Exception e){    
        e.printStackTrace();
    }

    JogoMenu.exibirMenu();
    
    }
}
