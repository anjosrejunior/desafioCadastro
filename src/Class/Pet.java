package Class;

public class Pet {
    private String nomeESobrenome;
    private final Pet.PetType petType;
    private final Pet.PetSex sex;
    private String numeroDaCasa;
    private String cidade;
    private String rua;
    private String idade;
    private String peso;
    private String raca;

    public Pet(String nomeESobrenome, Pet.PetType petType, Pet.PetSex sex, String numeroDaCasa, String cidade, String rua, String idade, String peso, String raca) {
        this.nomeESobrenome = nomeESobrenome;
        this.petType = petType;
        this.sex = sex;
        this.numeroDaCasa = numeroDaCasa;
        this.cidade = cidade;
        this.rua = rua;
        this.idade = idade;
        this.peso = peso;
        this.raca = raca;
    }

    public enum PetType {
        NaoInformado("NÃO INFORMADO"),
        CACHORRO("Cachorro"),
        GATO("Gato");

        private String Type;

        PetType(String Type) {
            this.Type = Type;
        }

        public String getType() {
            return Type;
        }
    }

    public enum PetSex {
        NAOINFORMADO("Nao informado"),
        MACHO("Macho"),
        FEMEA("Femea");

        private String sex;

        PetSex(String sex) {
            this.sex = sex;
        }

        public String getSexStr(){
            return sex;
        }
    }

    public String getNomeESobrenome() {
        return nomeESobrenome;
    }

    public Pet.PetType getTipoPet() {
        return petType;
    }

    public Pet.PetSex getSex() {
        return sex;
    }

    public String getNumeroDaCasa() {
        return numeroDaCasa;
    }

    public String getCidade() {
        return cidade;
    }

    public String getRua() {
        return rua;
    }

    public String getIdade() {
        return idade;
    }

    public String getPeso() {
        return peso;
    }

    public String getRaca() {
        return raca;
    }

    public static void imprimirRepostaForm(Pet pet, Pet.PetType petType, Pet.PetSex sexPet){
        System.out.println("\n--- Informações do Pet ---");
        System.out.println("Nome e Sobrenome do Pet: " + pet.getNomeESobrenome());
        System.out.println("Tipo do Pet: " + petType.getType());
        System.out.println("Sexo animal: " + sexPet.getSexStr());
        System.out.println("Idade do Pet: " + pet.getIdade());
        System.out.println("Peso do Pet: " + pet.getPeso());
        System.out.println("Raça do Pet: " + pet.getRaca());
        System.out.println("--- Endereço ---");
        System.out.println("Número da Casa: " + pet.getNumeroDaCasa());
        System.out.println("Cidade: " + pet.getCidade());
        System.out.println("Rua: " + pet.getRua());
    }
}
