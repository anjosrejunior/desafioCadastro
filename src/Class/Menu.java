package Class;

import Exceptions.MenuException;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu{
    static Scanner input = new Scanner(System.in);

    public static void activateMenu(){
        int menuAnswer = 0;
        boolean isCorrect = false;
        do {
            try {
                System.out.println("---- Menu Inicial ----");
                System.out.println("1. Cadastrar um novo pet");
                System.out.println("2. Alterar os dados do pet cadastrado");
                System.out.println("3. Deletar um pet cadastrado");
                System.out.println("4. Listar todos os pets cadastrados");
                System.out.println("5. Listar pets por algum critério (idade, nome, raça)");
                System.out.println("6. Sair");
                System.out.print("\nResposta: ");
                menuAnswer = input.nextInt();
                System.out.println();
                isCorrect = (menuAnswer > 0 && menuAnswer <= 6);

                if (menuAnswer < 1 || menuAnswer > 6) {
                    throw new MenuException("Digite um número entre 1 e 6");
                }
            } catch (InputMismatchException e){
                System.out.println("\nCaractere inválido. Digite um número.\n");
                input.nextLine();
            } catch (MenuException e) {
                System.out.println("\nErro: " + e.getMessage() + "\n"); // Captura a sua exceção personalizada
                input.nextLine();
            }
        } while (!isCorrect);

        switch (menuAnswer) {
            case 1 -> {
                System.out.println("---- Cadastro de PETS ----");
                DatabaseActions.createPetFile();
            }
            case 2 -> {
                System.out.println("---- Atualização de Cadastro ----");
                System.out.println("Procure pela Cadastro que Deseja Atualizar...");
                DatabaseActions.updatePetFile();
            }
            case 3 -> {
                System.out.println("---- Exclusão de Cadastro ----");
                System.out.println("Procure pela Cadastro que Deseja Deletar...");
                DatabaseActions.menuDeletePetFile();
            }
            case 4 -> {
                System.out.println("---- Lista de PETS Cadastrados ----");
                DatabaseActions.listAllPets();
            }
            case 5 -> {
                System.out.println("---- Busca Segmentada ----");
                DatabaseActions.sortSearch();
            }
            case 6 -> {
                System.out.println("Desligando aplicação...");
            }
        }
        input.close();
    }
}