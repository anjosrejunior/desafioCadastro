package Class;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

import Exceptions.*;

public class Form {
    static final Scanner input = new Scanner(System.in);

    static private String nomeESobrenome;
    static private Pet.PetType petType;
    static private Pet.PetSex petSex;
    static private String numeroDaCasa;
    static private String cidade;
    static private String rua;
    static private String idade;
    static private String peso;
    static private String raca;

    public static void formAnswer() {
        File form = new File("C:\\Users\\Nato\\Estudos-DEV\\Sistema-Pet\\assets\\formulario.txt");

        try (BufferedReader bf = new BufferedReader(new FileReader(form))) {
            String line;
            while ((line = bf.readLine()) != null) {
                boolean validInput = false;
                while (!validInput) {
                    try {
                        switch (line) {
                            case "1 - Qual o nome e sobrenome do pet?" -> {
                                System.out.print(line + " ");
                                nomeESobrenome = input.nextLine();
                                if (nomeESobrenome.isBlank()) {
                                    nomeESobrenome = "NÃO INFORMADO";
                                } else if (FormNameException.regexNameError(nomeESobrenome)) {
                                    throw new FormNameException("Nome escrito errado, escreva novamente");
                                }
                                validInput = true;
                            }
                            case "2 - Qual o tipo do pet (Cachorro/Gato)?" -> {
                                System.out.print(line + " ");
                                String tipo = input.nextLine().toUpperCase();
                                try {
                                    petType = Pet.PetType.valueOf(tipo);
                                    validInput = true;
                                } catch (FormInvalidEnumException e) {
                                    System.out.println("\nErro: " + e.getMessage() + "\n");
                                }
                            }
                            case "3 - Qual o sexo do animal (Macho ou Fêmea)?" -> {
                                System.out.print(line + " ");
                                String sexoStr = input.nextLine().toUpperCase();
                                try {
                                    petSex = Pet.PetSex.valueOf(sexoStr);
                                    validInput = true;
                                } catch (FormInvalidEnumException e) {
                                    System.out.println("\nErro: " + e.getMessage() + "\n");
                                }
                            }
                            case "4 - Onde ele foi encontrado? Numero da Casa:" -> {
                                System.out.print(line + " ");
                                numeroDaCasa = input.nextLine();
                                if (numeroDaCasa.isBlank()) {
                                    numeroDaCasa = "NÃO INFORMADO";
                                } else if (FormAdressException.regexHouseNumberError(numeroDaCasa)) {
                                    throw new FormAdressException("Escreva apenas caracteres numéricos.");
                                }
                                validInput = true;
                            }
                            case "5 - Onde ele foi encontrado? Cidade:" -> {
                                System.out.print(line + " ");
                                cidade = input.nextLine();
                                if (cidade.isBlank()) {
                                    cidade = "NÃO INFORMADO";
                                } else if (FormAdressException.regexCityAndStreetError(cidade)) {
                                    throw new FormAdressException("Escreva a Cidade apenas utilizando letras maíusculas o minúsculas");
                                }
                                validInput = true;
                            }
                            case "6 - Onde ele foi encontrado? Rua:" -> {
                                System.out.print(line + " ");
                                rua = input.nextLine();
                                if (rua.isBlank()) {
                                    rua = "NÃO INFORMADO";
                                } else if (FormAdressException.regexCityAndStreetError(rua)) {
                                    throw new FormAdressException("Rua inválida, utilize apenas letras maíusculas e minúsculas");
                                }
                                validInput = true;
                            }
                            case "7 - Qual a idade aproximada do pet?" -> {
                                System.out.print(line + " ");
                                idade = input.nextLine();
                                if (idade.isBlank()) {
                                    idade = "NÃO INFORMADO";
                                    break;
                                } else if (Double.parseDouble(idade) <= 0.0 || Double.parseDouble(idade) > 20.0) {
                                    throw new FormAgeOrWeightException("Idade inválida, permitido apenas entre (0.1) Mêses ou (20.0) Anos.");
                                } else if (FormAgeOrWeightException.regexOnlyNumberError(idade)) {
                                    throw new FormAgeOrWeightException("Idade inválida, utilize apenas numeros!");
                                }
                                validInput = true;
                            }
                            case "8 - Qual o peso aproximado do pet?" -> {
                                System.out.print(line + " ");
                                peso = input.nextLine();
                                if (peso.isBlank()) {
                                    peso = "NÃO INFORMADO";
                                } else if (Double.parseDouble(peso) < 0.5 || Double.parseDouble(peso) > 60.0) {
                                    throw new FormAgeOrWeightException("Peso inválido, permitido apenas entre (0.5) ou (60.0) KG");
                                } else if (FormAgeOrWeightException.regexOnlyNumberError(peso)) {
                                    throw new FormAgeOrWeightException("Idade escrita errada, escreva novamente");
                                }
                                validInput = true;
                            }
                            case "9 - Qual a raça do pet?" -> {
                                System.out.print(line + " ");
                                raca = input.nextLine();
                                if (raca.isBlank()) {
                                    raca = "NÃO INFORMADO";
                                } else if (FormRaceException.regexRaceError(raca)) {
                                    throw new FormRaceException("Raça inválida, utilize apenas letras maiúsculas e minúculas!");
                                }
                                validInput = true;
                            }
                        }
                    } catch (FormRaceException e) {
                        System.out.println("\nErro: " + e.getMessage() + "\n");
                    } catch (FormAgeOrWeightException e) {
                        System.out.println("\nErro: " + e.getMessage() + "\n");
                    } catch (FormAdressException e) {
                        System.out.println("\nErro: " + e.getMessage() + "\n");
                    } catch (FormNameException e) {
                        System.out.println("\nErro: " + e.getMessage() + "\n");
                    } catch (IllegalArgumentException e) {
                        System.out.println("\nErro: Entrada inválida. Tente novamente.\n");
                    }
                }
            }

            Pet pet = new Pet(
                    nomeESobrenome,
                    petType,
                    petSex,
                    numeroDaCasa,
                    cidade,
                    rua,
                    idade,
                    peso,
                    raca
            );
            Pet.imprimirRepostaForm(pet, petType, petSex);
            System.out.println();
            DatabaseActions.savePetFile(pet);
        } catch (Exception e) {
            System.out.println("\nErro ao ler o arquivo: " + e.getMessage() + "\n");
        }
    }

    public static void formAnswerUpdatePetFile(String petTypeStr, String petSexStr){
        File form = new File("C:\\Users\\Nato\\Estudos-DEV\\Sistema-Pet\\assets\\formularioUpdate.txt");

        try (BufferedReader bf = new BufferedReader(new FileReader(form))) {
            String line;
            while ((line = bf.readLine()) != null) {
                boolean validInput = false;
                while (!validInput) {
                    try {
                        switch (line) {
                            case "1 - Qual o nome e sobrenome do pet?" -> {
                                System.out.print(line + " ");
                                nomeESobrenome = input.nextLine();
                                if (nomeESobrenome.isBlank()) {
                                    nomeESobrenome = "NÃO INFORMADO";
                                } else if (FormNameException.regexNameError(nomeESobrenome)) {
                                    throw new FormNameException("Nome escrito errado, escreva novamente");
                                }
                                validInput = true;
                            }
                            case "2 - Onde ele foi encontrado? Numero da Casa:" -> {
                                System.out.print(line + " ");
                                numeroDaCasa = input.nextLine();
                                if (numeroDaCasa.isBlank()) {
                                    numeroDaCasa = "NÃO INFORMADO";
                                } else if (FormAdressException.regexHouseNumberError(numeroDaCasa)) {
                                    throw new FormAdressException("Escreva apenas caracteres numéricos.");
                                }
                                validInput = true;
                            }
                            case "3 - Onde ele foi encontrado? Cidade:" -> {
                                System.out.print(line + " ");
                                cidade = input.nextLine();
                                if (cidade.isBlank()) {
                                    cidade = "NÃO INFORMADO";
                                } else if (FormAdressException.regexCityAndStreetError(cidade)) {
                                    throw new FormAdressException("Escreva a Cidade apenas utilizando letras maíusculas o minúsculas");
                                }
                                validInput = true;
                            }
                            case "4 - Onde ele foi encontrado? Rua:" -> {
                                System.out.print(line + " ");
                                rua = input.nextLine();
                                if (rua.isBlank()) {
                                    rua = "NÃO INFORMADO";
                                } else if (FormAdressException.regexCityAndStreetError(rua)) {
                                    throw new FormAdressException("Rua inválida, utilize apenas letras maíusculas e minúsculas");
                                }
                                validInput = true;
                            }
                            case "5 - Qual a idade aproximada do pet?" -> {
                                System.out.print(line + " ");
                                idade = input.nextLine();
                                if (idade.isBlank()) {
                                    idade = "NÃO INFORMADO";
                                    break;
                                } else if (Double.parseDouble(idade) <= 0.0 || Double.parseDouble(idade) > 20.0) {
                                    throw new FormAgeOrWeightException("Idade inválida, permitido apenas entre (0.1) Mêses ou (20.0) Anos.");
                                } else if (FormAgeOrWeightException.regexOnlyNumberError(idade)) {
                                    throw new FormAgeOrWeightException("Idade inválida, utilize apenas numeros!");
                                }
                                validInput = true;
                            }
                            case "6 - Qual o peso aproximado do pet?" -> {
                                System.out.print(line + " ");
                                peso = input.nextLine();
                                if (peso.isBlank()) {
                                    peso = "NÃO INFORMADO";
                                } else if (Double.parseDouble(peso) < 0.5 || Double.parseDouble(peso) > 60.0) {
                                    throw new FormAgeOrWeightException("Peso inválido, permitido apenas entre (0.5) ou (60.0) KG");
                                } else if (FormAgeOrWeightException.regexOnlyNumberError(peso)) {
                                    throw new FormAgeOrWeightException("Idade escrita errada, escreva novamente");
                                }
                                validInput = true;
                            }
                            case "7 - Qual a raça do pet?" -> {
                                System.out.print(line + " ");
                                raca = input.nextLine();
                                if (raca.isBlank()) {
                                    raca = "NÃO INFORMADO";
                                } else if (FormRaceException.regexRaceError(raca)) {
                                    throw new FormRaceException("Raça inválida, utilize apenas letras maiúsculas e minúculas!");
                                }
                                validInput = true;
                            }
                        }
                    } catch (FormRaceException e) {
                        System.out.println("\nErro: " + e.getMessage() + "\n");
                    } catch (FormAgeOrWeightException e) {
                        System.out.println("\nErro: " + e.getMessage() + "\n");
                    } catch (FormAdressException e) {
                        System.out.println("\nErro: " + e.getMessage() + "\n");
                    } catch (FormNameException e) {
                        System.out.println("\nErro: " + e.getMessage() + "\n");
                    } catch (IllegalArgumentException e) {
                        System.out.println("\nErro: Entrada inválida. Tente novamente.\n");
                    }
                }
            }

            try {
                petType = Pet.PetType.valueOf(petTypeStr.toUpperCase());
                petSex = Pet.PetSex.valueOf(petSexStr.toUpperCase());
            } catch (FormInvalidEnumException e) {
                System.out.println("\nErro: " + e.getMessage() + "\n");
            }

            Pet pet = new Pet(
                    nomeESobrenome,
                    petType,
                    petSex,
                    numeroDaCasa,
                    cidade,
                    rua,
                    idade,
                    peso,
                    raca
            );
            Pet.imprimirRepostaForm(pet, petType, petSex);
            System.out.println();
            DatabaseActions.savePetFile(pet);
        } catch (Exception e) {
            System.out.println("\nErro ao ler o arquivo: " + e.getMessage() + "\n");
        }
    }
}