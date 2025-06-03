package Class;

import Exceptions.DatabaseSortSearchException;
import Exceptions.FormInvalidEnumException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Scanner;

public class DatabaseActions {

    private static final Scanner input = new Scanner(System.in);

    public static void createPetFile(){
        Form.formAnswer();
    }

    public static void savePetFile(Pet pet) {
        LocalDateTime nowTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy'T'HHmm");
        String nowTimeStr = nowTime.format(formatter);
        String fileNamePattern = nowTimeStr + "-" + pet.getNomeESobrenome().toUpperCase();

        try (BufferedWriter bfw = new BufferedWriter(new FileWriter("C:\\Users\\Nato\\Estudos-DEV\\Sistema-Pet\\petsCadastrados\\" + fileNamePattern + ".txt"))){
            bfw.write(pet.getNomeESobrenome());
            bfw.newLine();
            bfw.write(pet.getTipoPet().getType());
            bfw.newLine();
            bfw.write(pet.getSex().getSexStr());
            bfw.newLine();
            bfw.write(pet.getRua() + ", " + pet.getNumeroDaCasa() + ", " + pet.getCidade());
            bfw.newLine();
            bfw.write(pet.getIdade() + " anos");
            bfw.newLine();
            bfw.write(pet.getPeso() + "KG");
            bfw.newLine();
            bfw.write(pet.getRaca());
        } catch (Exception e) {
            System.out.println("\nErro: " + e.getMessage() + "\n");
        }
        System.out.println("Ficha de inscrição do Pet cadastrada com Sucesso!");
    }

    public static File[] createFile() {
        String PET_DIR = "petsCadastrados\\";
        File[] arquivos;
        try {
            File dir = new File(PET_DIR);
            if (!dir.exists() || !dir.isDirectory()) {
                throw new FileNotFoundException("Diretório de pets não encontrado.");
            }
            arquivos = dir.listFiles();
            if (arquivos == null || arquivos.length == 0) {
                throw new FileNotFoundException("Nenhum arquivo pet encontrado.");
            }
            return arquivos;
        } catch (FileNotFoundException e) {
            System.out.println("\nErro: " + e.getMessage() + "\n");
        }
        return null;
    }

    public static void listAllPets(){
        for (File arquivo : Objects.requireNonNull(createFile())) {
            if (arquivo.isFile()) {
                try (BufferedReader bfr = new BufferedReader(new FileReader(arquivo))){
                    String line;
                    while ((line = bfr.readLine()) != null){
                        System.out.println(line);
                    }
                    System.out.println();
                } catch (IOException e) {
                    System.out.println("Erro ao ler o arquivo: " + arquivo.getName());
                }
            }
        }
    }

    public static boolean containsIgnoreCase(String str, String substring) {
        if (str == null || substring == null) {
            return false;
        }
        return str.toLowerCase().contains(substring.toLowerCase());
    }

    public static String[] sortSearch(){
        byte tick;
        byte tickAux = 0;
        String auxStr;
        boolean isValidInput = false;
        String[] parameters = new String[0];
        String[] archives = null;

        boolean isValidtick = false;
        while(!isValidtick) {
            try {
                System.out.print("Quantos parâmetros de busca você deseja utilizar? 1 ou 2: ");
                tickAux = input.nextByte();
                input.nextLine();

                if (tickAux < 1 || tickAux > 2) {
                    throw new DatabaseSortSearchException("Escolha no mínimo 1 e no máximo 2 parâmetros de busca!");
                }
                parameters = new String[tickAux+1];
                isValidtick = true;

                while(!isValidInput){
                    System.out.println("\nPrimeiramente diga o tipo do animal, GATO OU CACHORRO?");
                    System.out.print("Resposta: ");
                    auxStr = input.nextLine();

                    try {
                        Pet.PetType petType;
                        petType = Pet.PetType.valueOf(auxStr.toUpperCase());
                        isValidInput = true;
                    } catch (FormInvalidEnumException e) {
                        System.out.println("\nErro: " + e.getMessage() + "\n");
                    }
                    parameters[0] = auxStr;
                }

                tick = 1;
                String[] options = {"1 - Nome ou sobrenome",
                        "2 - Sexo",
                        "3 - Idade",
                        "4 - Peso",
                        "5 - Raça",
                        "4 - Endereço"
                };
                while (tick <= tickAux) {
                    System.out.println("\nEscoha um parâmetro de Busca\n");
                    for (String option : options) {
                        System.out.println(option);
                    }
                    System.out.print("\nResposta: ");
                    auxStr = input.nextLine();

                    System.out.println("\nVocê selecionou: " + options[Byte.parseByte(auxStr)-1]);
                    System.out.print("Digite: ");
                    auxStr = input.nextLine();
                    parameters[tick] = auxStr;

                    options[tick] = options[tick] + " (Parâmetro Utilizado)";
                    tick++;
                }
                System.out.println("\nParâmetros Utilizados:");
                for (String parameter : parameters) {
                    System.out.println(parameter);
                }
            } catch (DatabaseSortSearchException e) {
                System.out.println("\nErro: " + e.getMessage() + "\n");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            String nome = null;
            String especie = null;
            String sexo = null;
            String endereco = null;
            String idade = null;
            String peso = null;
            String raca = null;
            tick = 0;

            System.out.println("\nLista de Resultados:\n");

            int numberOfTheList = 0;
            for (File arquivo : Objects.requireNonNull(createFile())) {
                if (arquivo.isFile()) {
                    try (BufferedReader bfr = new BufferedReader(new FileReader(arquivo))) {
                        String line;
                        while ((line = bfr.readLine()) != null) {
                            switch (tick) {
                                case 0 -> nome = line;
                                case 1 -> especie = line;
                                case 2 -> sexo = line;
                                case 3 -> endereco = line;
                                case 4 -> idade = line;
                                case 5 -> peso = line;
                                case 6 -> raca = line;
                            }
                            tick++;
                        }
                        tick = 0;
                        String concat = nome + " - " + especie + " - " + sexo + " - " + endereco + " - " + idade + " - " + peso + " - " + raca;

                        for (int i = 1; i < tickAux + 1; i++) {
                            if (containsIgnoreCase(concat, parameters[0]) && containsIgnoreCase(concat, parameters[i])) {
                                numberOfTheList++;
                                System.out.println(numberOfTheList + ". " + concat);
                                break;
                            }
                        }
                    } catch (IOException e) {
                        System.out.println("Erro ao ler o arquivo: " + arquivo.getName());
                    }
                }
            }

            archives = new String[numberOfTheList];
            for (File arquivo : Objects.requireNonNull(createFile())) {
                if (arquivo.isFile()) {
                    try (BufferedReader bfr = new BufferedReader(new FileReader(arquivo))) {
                        String line;
                        while ((line = bfr.readLine()) != null) {
                            switch (tick) {
                                case 0 -> nome = line;
                                case 1 -> especie = line;
                                case 2 -> sexo = line;
                                case 3 -> endereco = line;
                                case 4 -> idade = line;
                                case 5 -> peso = line;
                                case 6 -> raca = line;
                            }
                            tick++;
                        }
                        tick = 0;
                        String concat = nome + " - " + especie + " - " + sexo + " - " + endereco + " - " + idade + " - " + peso + " - " + raca;

                        int tickArchive = 0;
                        for (int i = 1; i < tickAux + 1; i++) {
                            if (containsIgnoreCase(concat, parameters[0]) && containsIgnoreCase(concat, parameters[i])) {
                                archives[tickArchive] = arquivo.getName();
                                tickArchive++;
                                break;
                            }
                        }
                    } catch (IOException e) {
                        System.out.println("Erro ao ler o arquivo: " + arquivo.getName());
                    }
                }
            }

        }
        return archives;
    }

    public static void deletePetFile(String fileName){
        Path caminho = Paths.get("petsCadastrados\\" + fileName);
        try {
            Files.delete(caminho);
            System.out.println("Arquivo excluído com sucesso.");
        } catch (IOException e) {
            System.out.println("Falha ao excluir o arquivo: " + e.getMessage());
        }
    }

    public static void menuDeletePetFile(){
        File[] arquivos = createFile();
        if (arquivos == null) {
            System.out.println("Erro ao criar lista de arquivos.");
            return;
        }
        String[] nameFiles = sortSearch();

        System.out.println("\nDigite o número da ficha que deseja atualizar.");
        System.out.print("Reposta: ");
        int fileIndex = input.nextInt();
        input.nextLine();
        System.out.println();

        String namePetFile = null;

        for (File arquivo : arquivos) {
            try (BufferedReader bfr = new BufferedReader(new FileReader(arquivo))) {
                if (arquivo.getName().equals(nameFiles[fileIndex-1])) {
                    namePetFile = arquivo.getName();
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                System.out.println("Erro ao ler o arquivo: " + arquivo.getName());
            }
        }

        System.out.println("Tem certeza que você deseja deletar o cadastro: " + namePetFile + "?");
        System.out.print("Digite 'SIM' para confirmar e 'NAO' para desistir da exclusão: ");
        String answer = input.nextLine();
        if(answer.equals("SIM")) {
            deletePetFile(namePetFile);
        }
    }

    public static void updatePetFile() {
        File[] arquivos = createFile();
        if (arquivos == null) {
            System.out.println("Não há arquivos para atualizar.");
            return;
        }
        String[] nameFiles = sortSearch();

        System.out.println("\nDigite o número da ficha que deseja atualizar.");
        System.out.print("Reposta: ");
        int fileIndex = input.nextInt();
        System.out.println();

        String namePetFile = null;
        String petType = null;
        String petSex = null;
        String line;
        int lineIndex = 1;
        for (File arquivo : arquivos) {
            try (BufferedReader bfr = new BufferedReader(new FileReader(arquivo))) {
                if (arquivo.getName().equals(nameFiles[fileIndex-1])) {
                    namePetFile = arquivo.getName();
                    while ((line = bfr.readLine()) != null){
                        switch (lineIndex){
                            case 2 -> petType = line;
                            case 3 -> petSex = line;
                        }
                        lineIndex++;
                    }
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                System.out.println("Erro ao ler o arquivo: " + arquivo.getName());
            }
        }
        Form.formAnswerUpdatePetFile(petType,petSex);
        deletePetFile(namePetFile);
    }
}