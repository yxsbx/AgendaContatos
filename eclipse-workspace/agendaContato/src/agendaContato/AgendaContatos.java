package agendaContato;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class AgendaContatos {

    private static final String FILE_NAME = "contatos.txt";

    public static void main(String[] args) {
        criarArquivoSeNaoExistir();
        List<Contato> contatos = lerContatosDoArquivo();
        exibirMenu(contatos);

        Scanner scanner = new Scanner(System.in);
        int opcao = 0;

        do {
            System.out.print("Escolha uma opção: ");
            if (scanner.hasNextInt()) {
                opcao = scanner.nextInt();
                scanner.nextLine();
                if (opcao < 1 || opcao > 4) {
                    System.out.println("Por favor, digite um número entre 1 e 4.");
                }
            } else {
                System.out.println("Por favor, digite apenas números.");
                scanner.nextLine();
            }

            switch (opcao) {
                case 1:
                    adicionarContato(scanner, contatos);
                    break;
                case 2:
                    removerContato(scanner, contatos);
                    break;
                case 3:
                    editarContato(scanner, contatos);
                    break;
                case 4:
                    salvarContatosNoArquivo(contatos);
                    System.out.println("Saindo da agenda.");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }

            exibirMenu(contatos);
        } while (opcao != 4);

        scanner.close();
    }

    private static void salvarContatosNoArquivo(List<Contato> contatos) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Contato contato : contatos) {
                StringBuilder linha = new StringBuilder()
                        .append(contato.getId())
                        .append(" | ")
                        .append(contato.getNome())
                        .append(" ")
                        .append(contato.getSobreNome());

                // Adicionar telefone, se disponível
                if (contato.getTelefone() != null) {
                    linha.append(" | ")
                            .append(contato.getTelefone().getDdd())
                            .append(" ")
                            .append(contato.getTelefone().getNumero());
                }

                bw.write(linha.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar contatos no arquivo: " + e.getMessage());
        }
    }

    private static List<Contato> lerContatosDoArquivo() {
        List<Contato> contatos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split("\\|");

                if (partes.length >= 3) {
                    Long id = Long.parseLong(partes[0].trim());
                    String nomeCompleto = partes[1].trim();
                    String[] partesNome = nomeCompleto.split(" ");
                    String nome = partesNome[0].trim();
                    String sobreNome = partesNome.length > 1 ? partesNome[1].trim() : "";

                    Contato contato = new Contato(id, nome, sobreNome);

                    if (partes.length > 2) {
                        String[] partesTelefone = partes[2].trim().split(" ");
                        String ddd = partesTelefone[0].trim();
                        Long numero = Long.parseLong(partesTelefone[1].trim());
                        Telefone telefone = new Telefone(ddd, numero);
                        contato.setTelefone(telefone);
                    }

                    contatos.add(contato);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Erro ao ler contatos do arquivo: " + e.getMessage());
        }
        return contatos;
    }

    private static void criarArquivoSeNaoExistir() {
        File arquivo = new File(FILE_NAME);
        if (!arquivo.exists()) {
            try {
                arquivo.createNewFile();
            } catch (IOException e) {
                System.out.println("Erro ao criar o arquivo: " + e.getMessage());
            }
        }
    }

    private static void exibirMenu(List<Contato> contatos) {
        System.out.println("##################");
        System.out.println("##### AGENDA #####");
        System.out.println(">>>> Contatos <<<<");
        System.out.println("Id | Nome");
        exibirContatos(contatos);
        System.out.println(">>>> Menu <<<<");
        System.out.println("1 - Adicionar Contato");
        System.out.println("2 - Remover Contato");
        System.out.println("3 - Editar Contato");
        System.out.println("4 - Sair");
    }

    private static void exibirContatos(List<Contato> contatos) {
        for (Contato contato : contatos) {
            System.out.println(contato.getId() + " | " + contato.getNome() + " " + contato.getSobreNome());
        }
    }

    private static void adicionarContato(Scanner scanner, List<Contato> contatos) {
        System.out.print("Digite o nome do contato: ");
        String nome = scanner.nextLine();
        System.out.print("Digite o sobrenome do contato: ");
        String sobreNome = scanner.nextLine();

        System.out.print("Digite o DDD do telefone (3 dígitos): ");
        String ddd = scanner.nextLine();
        while (ddd.length() != 3 || !ddd.matches("\\d+")) {
            System.out.println("DDD deve conter 3 dígitos. Tente novamente.");
            System.out.print("Digite o DDD do telefone (3 dígitos): ");
            ddd = scanner.nextLine();
        }

        System.out.print("Digite o número do telefone (9 dígitos): ");
        String numero = scanner.nextLine();
        while (numero.length() != 9 || !numero.matches("\\d+")) {
            System.out.println("Número deve conter 9 dígitos numéricos. Tente novamente.");
            System.out.print("Digite o número do telefone (9 dígitos): ");
            numero = scanner.nextLine();
        }

        if (telefoneJaExistente(contatos, ddd, Long.parseLong(numero))) {
            System.out.println("Este número de telefone já está cadastrado. Não é possível adicionar o contato.");
            return;
        }

        Long novoId = contatos.isEmpty() ? 1 : contatos.get(contatos.size() - 1).getId() + 1;
        Contato novoContato = new Contato(novoId, nome, sobreNome);
        Telefone novoTelefone = new Telefone(ddd, Long.parseLong(numero));

        novoContato.setTelefone(novoTelefone);

        contatos.add(novoContato);
        System.out.println("Contato adicionado com sucesso!");
    }

    private static void removerContato(Scanner scanner, List<Contato> contatos) {
        System.out.print("Digite o ID do contato que deseja remover: ");
        Long idRemover = scanner.nextLong();
        scanner.nextLine();

        for (int i = 0; i < contatos.size(); i++) {
            if (Objects.requireNonNull(contatos.get(i)).getId().equals(idRemover)) {
                contatos.remove(i);
                System.out.println("Contato removido com sucesso!");
                reorganizarIds(contatos);
                return;
            }
        }
        System.out.println("Contato não encontrado com o ID fornecido.");
    }

    private static void reorganizarIds(List<Contato> contatos) {
        for (int i = 0; i < contatos.size(); i++) {
            Contato contato = contatos.get(i);
            if (contato != null) {
                contato.setId((long) (i + 1));
            }
        }
    }

    private static void editarContato(Scanner scanner, List<Contato> contatos) {
        System.out.print("Digite o ID do contato que deseja editar: ");
        Long idEditar = scanner.nextLong();
        scanner.nextLine();

        for (Contato contato : contatos) {
            if (Objects.requireNonNull(contato).getId().equals(idEditar)) {
                System.out.print("Digite o novo nome do contato: ");
                String novoNome = scanner.nextLine();
                System.out.print("Digite o novo sobrenome do contato: ");
                String novoSobreNome = scanner.nextLine();

                String novoDdd;
                String novoNumero;

                if (contato.getTelefone() != null) {
                    System.out.println("DDD atual: " + contato.getTelefone().getDdd());
                    System.out.print("Digite o novo DDD do telefone (3 dígitos): ");
                    novoDdd = scanner.nextLine();
                    while (novoDdd.length() != 3 || !novoDdd.matches("\\d+")) {
                        System.out.println("DDD deve conter 3 dígitos. Tente novamente.");
                        System.out.print("Digite o novo DDD do telefone (3 dígitos): ");
                        novoDdd = scanner.nextLine();
                    }

                    System.out.println("Número atual: " + contato.getTelefone().getNumero());
                    System.out.print("Digite o novo número do telefone (9 dígitos): ");
                    novoNumero = scanner.nextLine();
                    while (novoNumero.length() != 9 || !novoNumero.matches("\\d+")) {
                        System.out.println("Número deve conter 9 dígitos numéricos. Tente novamente.");
                        System.out.print("Digite o novo número do telefone (9 dígitos): ");
                        novoNumero = scanner.nextLine();
                    }

                    if (novoDdd.equals(contato.getTelefone().getDdd()) && novoNumero.equals(String.valueOf(contato.getTelefone().getNumero()))) {
                        System.out.println("Não houve alterações no número de telefone.");
                        return;
                    } else {
                        contato.getTelefone().setDdd(novoDdd);
                        contato.getTelefone().setNumero(Long.parseLong(novoNumero));
                        System.out.println("Número de telefone alterado com sucesso!");
                    }
                } else {
                    adicionarTelefone(scanner, contato);
                }

                contato.setNome(novoNome);
                contato.setSobreNome(novoSobreNome);

                System.out.println("Contato editado com sucesso!");
                return;
            }
        }
        System.out.println("Contato não encontrado com o ID fornecido.");
    }

    private static void adicionarTelefone(Scanner scanner, Contato contato) {
        System.out.print("Digite o DDD do telefone (3 dígitos): ");
        String ddd = scanner.nextLine();
        while (ddd.length() != 3 || !ddd.matches("\\d+")) {
            System.out.println("DDD deve conter 3 dígitos. Tente novamente.");
            System.out.print("Digite o DDD do telefone (3 dígitos): ");
            ddd = scanner.nextLine();
        }

        System.out.print("Digite o número do telefone (9 dígitos): ");
        String numero = scanner.nextLine();
        while (numero.length() != 9 || !numero.matches("\\d+")) {
            System.out.println("Número deve conter 9 dígitos numéricos. Tente novamente.");
            System.out.print("Digite o número do telefone (9 dígitos): ");
            numero = scanner.nextLine();
        }

        Telefone novoTelefone = new Telefone(ddd, Long.parseLong(numero));
        contato.setTelefone(novoTelefone);

        System.out.println("Telefone adicionado com sucesso!");
    }

    private static boolean telefoneJaExistente(List<Contato> contatos, String ddd, Long numero) {
        for (Contato contato : contatos) {
            if (contato.getTelefone() != null &&
                    contato.getTelefone().getDdd().equals(ddd) &&
                    contato.getTelefone().getNumero().equals(numero)) {
                return true;
            }
        }
        return false;
    }
}