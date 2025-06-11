package main;

import dao.IdiomaDAO;
import dao.TextoDAO;
import dao.TraducaoDAO;
import dao.UsuarioDAO;
import model.Idioma;
import model.Texto;
import model.Traducao;
import model.Usuario;
import util.ConexaoDB;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static TextoDAO textoDAO;
    private static TraducaoDAO traducaoDAO;
    private static UsuarioDAO usuarioDAO;
    private static IdiomaDAO idiomaDAO;

    public static void main(String[] args) {
        Connection conexao = ConexaoDB.recuperaConexao();
        textoDAO = new TextoDAO(conexao);
        traducaoDAO = new TraducaoDAO(conexao);
        usuarioDAO = new UsuarioDAO(conexao);
        idiomaDAO = new IdiomaDAO(conexao);

        Scanner scanner = new Scanner(System.in);
        scanner.useDelimiter("\r?\n");

        int usuarioLogadoId = 2; // Simula o usuário "Maria Costa" logado
        System.out.println("Bem-vinda, Maria Costa!");

        while (true) {
            System.out.println("\n===== SISTEMA DE TRADUÇÃO COLABORATIVA =====");
            System.out.println("1. Listar todos os textos");
            System.out.println("2. Adicionar um novo texto para tradução");
            System.out.println("3. Ver traduções de um texto");
            System.out.println("4. Submeter uma nova tradução");
            System.out.println("5. Votar em uma tradução");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    listarTodosTextos();
                    break;
                case 2:
                    adicionarNovoTexto(scanner);
                    break;
                case 3:
                    verTraducoes(scanner);
                    break;
                case 4:
                    submeterTraducao(scanner, usuarioLogadoId);
                    break;
                case 5:
                    votar(scanner, usuarioLogadoId);
                    break;
                case 0:
                    System.out.println("Saindo do sistema...");
                    scanner.close();
                    try {
                        if (conexao != null && !conexao.isClosed()) {
                            conexao.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private static void listarTodosTextos() {
        System.out.println("\n--- Todos os Textos no Sistema ---");
        try {
            List<Texto> textos = textoDAO.listarTodos();
            if (textos.isEmpty()) {
                System.out.println("Nenhum texto encontrado.");
            } else {
                textos.forEach(System.out::println);
            }
        } catch (SQLException e) {
            System.out.println("ERRO: Não foi possível buscar os textos. " + e.getMessage());
        }
    }

    private static void adicionarNovoTexto(Scanner scanner) {
        System.out.println("\n--- Adicionar Novo Texto ---");
        try {
            List<Idioma> idiomas = idiomaDAO.listarTodos();
            System.out.println("Idiomas disponíveis:");
            idiomas.forEach(idioma -> System.out.println(idioma.getId() + " - " + idioma.getNome()));

            System.out.print("Digite o conteúdo do novo texto: ");
            String conteudo = scanner.next();
            System.out.print("Digite o ID do idioma de ORIGEM: ");
            int idOrigem = scanner.nextInt();
            System.out.print("Digite o ID do idioma de DESTINO: ");
            int idDestino = scanner.nextInt();

            Idioma idiomaOrigem = idiomas.stream().filter(i -> i.getId() == idOrigem).findFirst().orElse(null);
            Idioma idiomaDestino = idiomas.stream().filter(i -> i.getId() == idDestino).findFirst().orElse(null);

            if (idiomaOrigem == null || idiomaDestino == null) {
                System.out.println("ERRO: ID de idioma inválido.");
                return;
            }

            Texto novoTexto = new Texto(conteudo, idiomaOrigem, idiomaDestino);
            textoDAO.salvar(novoTexto);

        } catch (SQLException e) {
            System.out.println("ERRO: Não foi possível adicionar o novo texto. " + e.getMessage());
        }
    }

    private static void verTraducoes(Scanner scanner) {
        System.out.println("\n--- Ver Traduções de um Texto ---");
        try {
            List<Texto> textosComTraducoes = textoDAO.listarTextosComTraducoes();
            if (textosComTraducoes.isEmpty()) {
                System.out.println("Nenhum texto foi traduzido ainda. Submeta uma tradução primeiro (opção 4).");
                return;
            }
            System.out.println("Textos que já possuem traduções:");
            textosComTraducoes.forEach(System.out::println);

            System.out.print("\nDigite o ID do texto para ver as suas traduções: ");
            int textoId = scanner.nextInt();

            boolean idValido = textosComTraducoes.stream().anyMatch(t -> t.getId() == textoId);
            if (!idValido) {
                System.out.println("ERRO: O ID digitado não corresponde a um texto com traduções.");
                return;
            }

            Texto textoSelecionado = textoDAO.buscarPorId(textoId);
            System.out.println("\n--- Traduções para o Texto ID " + textoId + " ---");
            System.out.println("Texto Original: '" + textoSelecionado.getConteudo() + "'");
            List<Traducao> traducoes = traducaoDAO.buscarPorTexto(textoSelecionado);
            traducoes.forEach(System.out::println);

        } catch (SQLException e) {
            System.out.println("ERRO: Não foi possível buscar as traduções. " + e.getMessage());
        }
    }

    private static void submeterTraducao(Scanner scanner, int usuarioId) {
        System.out.println("\n--- Submeter Nova Tradução ---");
        listarTodosTextos();
        System.out.print("\nDigite o ID do texto que você quer traduzir: ");
        int textoId = scanner.nextInt();

        System.out.print("Digite sua sugestão de tradução: ");
        String conteudoTraducao = scanner.next();

        try {
            Texto texto = textoDAO.buscarPorId(textoId);
            Usuario autor = usuarioDAO.buscarPorId(usuarioId);

            if (texto != null && autor != null) {
                Traducao novaTraducao = new Traducao(conteudoTraducao, autor, texto);
                traducaoDAO.salvar(novaTraducao);
            } else {
                System.out.println("ERRO: Texto ou Usuário não encontrado.");
            }
        } catch (SQLException e) {
            System.out.println("ERRO: Não foi possível salvar a tradução. " + e.getMessage());
        }
    }

    private static void votar(Scanner scanner, int usuarioId) {
        System.out.println("\n--- Votar em uma Tradução ---");
        try {
            List<Texto> textosComTraducoes = textoDAO.listarTextosComTraducoes();
            if (textosComTraducoes.isEmpty()) {
                System.out.println("Nenhuma tradução disponível para votação no momento.");
                return;
            }
            System.out.println("Primeiro, escolha um texto para ver as traduções disponíveis para votar:");
            textosComTraducoes.forEach(System.out::println);
            System.out.print("\nDigite o ID do texto: ");
            int textoId = scanner.nextInt();

            Texto textoSelecionado = textoDAO.buscarPorId(textoId);
            if(textoSelecionado == null) {
                System.out.println("ERRO: Texto não encontrado.");
                return;
            }

            List<Traducao> traducoes = traducaoDAO.buscarPorTexto(textoSelecionado);
            if (traducoes.isEmpty()) {
                System.out.println("Este texto não tem traduções para votar.");
                return;
            }
            System.out.println("\nTraduções do texto " + textoId + ":");
            traducoes.forEach(System.out::println);

            System.out.print("\nAgora, digite o ID da tradução em que deseja votar: ");
            int traducaoId = scanner.nextInt();

            boolean idValido = traducoes.stream().anyMatch(t -> t.getId() == traducaoId);
            if (idValido) {
                traducaoDAO.adicionarVoto(usuarioId, traducaoId);
            } else {
                System.out.println("ERRO: ID de tradução inválido para este texto.");
            }

        } catch (SQLException e) {
            System.out.println("ERRO: Ocorreu um problema no processo de votação. " + e.getMessage());
        }
    }
}
