import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

import Exception.ArquivoNaoEncontradoException;
import Exception.DuplicidadeException;
import Exception.FormatoInvalidoException;

public class SistemaEscola {
    private Scanner scanner = new Scanner(System.in);
    private ArrayList<Aluno> alunosCadastrados = new ArrayList<>();
    private ArrayList<Turma> turmasCadastradas = new ArrayList<>();

    public void executar() throws Exception {
        int opcao;

        do {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Cadastrar Aluno");
            System.out.println("2. Definir a idade de um aluno");
            System.out.println("3. Cadastrar Turma");
            System.out.println("4. Inserir Aluno na Turma");
            System.out.println("5. Remover Aluno da Turma");
            System.out.println("6. Listar Alunos da Turma");
            System.out.println("7. Remover todos alunos de uma turma");
            System.out.println("8. Cadastrar turmas a partir de um arquivo");
            System.out.println("9. Cadastrar alunos a partir de um arquivo");
            System.out.println("10. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();  

            switch (opcao) {
                case 1:
                    cadastrarAluno();
                    break;
                case 2:
                    definirIdadeAluno();
                    break;
                case 3:
                    cadastrarTurma();
                    break;
                case 4:
                    inserirAlunoNaTurma();
                    break;
                case 5:
                    removerAlunoDaTurma();
                    break;
                case 6:
                    listarAlunosDaTurma();
                    break;
                case 7:
                    removerTodosAlunosDaTurma();
                    break;
                case 8:
                    cadastrarTurmasPorArquivo();
                    break;
                case 9:
                    cadastrarAlunosPorArquivo();
                    break;
                case 10:
                    System.out.println("Saindo do sistema...");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        } while (opcao != 10);
    }

    public <T> T buscarComando(String textoComando, List<T> lista, Function<T, String> keyExtractor) {
        System.out.print(textoComando);
        String input = scanner.nextLine();
        
        for (T item : lista) {
            if (keyExtractor.apply(item).equals(input)) {
                return item;
            }
        }
        return null; 
    }

    public Aluno buscarAlunoCmd(String textoComando) {
        return buscarComando(textoComando, alunosCadastrados, Aluno::getCpf);
    }

    public Turma buscarTurmaCmd(String textoComando) {
        return buscarComando(textoComando, turmasCadastradas, Turma::getCodigo);
    }

    public Turma buscarTurmaPorCodigo(String codigo) {
        for (Turma turma : turmasCadastradas) {
            if (turma.getCodigo().equals(codigo)) {
                return turma;
            }
        }
        return null;
    }

    public void cadastrarAluno() {
        System.out.print("Digite o nome do aluno: ");
        String nome = scanner.nextLine();
        System.out.print("Digite o CPF do aluno: ");
        String cpf = scanner.nextLine();

        for (Aluno aluno : alunosCadastrados) {
            if (aluno.getCpf().equals(cpf)) {
                System.out.println("Erro: Aluno com CPF " + cpf + " já cadastrado.");
                return;
            }
        }

        Aluno aluno = new Aluno(nome, cpf);
        alunosCadastrados.add(aluno);
        System.out.println("Aluno cadastrado com sucesso!");
    }

    public void definirIdadeAluno() {
        try {
            Aluno aluno = buscarAlunoCmd("Digite o CPF do aluno a ser editado: ");
            if (aluno == null) {
                throw new RuntimeException("Erro: Aluno não encontrado.");
            }
    
            System.out.print("Digite a idade do aluno " + aluno.getNome() + ": ");
            int idade = scanner.nextInt();
            scanner.nextLine();
    
            if (idade < 0) {
                throw new IllegalArgumentException("Erro: A idade não pode ser negativa.");
            }
    
            aluno.setIdade(idade);
            System.out.println(aluno.toString());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
    

    public void cadastrarTurma() {
        System.out.print("Digite o nome da turma: ");
        String nome = scanner.nextLine();
        System.out.print("Digite o código da turma: ");
        String codigo = scanner.nextLine();

        for (Turma turma : turmasCadastradas) {
            if (turma.getCodigo().equals(codigo)) {
                System.out.println("Erro: Turma com código " + codigo + " já cadastrada.");
                return;
            }
        }

        Turma turma = new Turma(nome, codigo);
        turmasCadastradas.add(turma);
        System.out.println("Turma cadastrada com sucesso!");
    }

    public void inserirAlunoNaTurma() {
        Aluno aluno = buscarAlunoCmd("Digite o CPF do aluno a ser inserido: ");
        if (aluno != null) {
            Turma turma = buscarTurmaCmd("Digite o código da turma que deseja adicionar o aluno: ");
            turma.adicionarAluno(aluno);
        } else {
            System.out.println("Aluno não encontrado. Certifique-se de que o aluno foi cadastrado.");
        }
    }

    public void removerAlunoDaTurma() {
        Aluno aluno = buscarAlunoCmd("Digite o CPF do aluno a ser removido: ");
        if (aluno != null) {
            Turma turma = buscarTurmaCmd("Digite o código da turma que deseja remover o aluno: ");
            turma.removerAluno(aluno);
        } else {
            System.out.println("Aluno não encontrado.");
        }
    }

    public void listarAlunosDaTurma() {
        Turma turma = buscarTurmaCmd("Digite o código da turma que deseja listar os alunos: ");
        turma.listarAlunos();
    }

    public void removerTodosAlunosDaTurma() {
        System.out.println("Você tem certeza que deseja remover todos alunos da turma? \n1. Sim\n2. Não");
        int confirmacaoAcao = scanner.nextInt();
        scanner.nextLine();
        if(confirmacaoAcao == 1){
            Turma turma = buscarTurmaCmd("Digite o código da turma que deseja remover todos alunos: ");
            turma.removerTodos();
        }
    }

    public void cadastrarTurmasPorArquivo() {
        try {
            System.out.print("Digite o nome do arquivo que deseja ler os dados para adição de turmas: ");
            String nomeArquivo = scanner.nextLine();
            String diretorioProjeto = System.getProperty("user.dir");
    
            File arquivo = new File(diretorioProjeto, nomeArquivo);
            System.out.println("Caminho absoluto do arquivo: " + arquivo.getAbsolutePath());
    
            if (!arquivo.exists()) {
                throw new ArquivoNaoEncontradoException("Erro: Arquivo não encontrado!");
            }
    
            Scanner scannerArquivo = new Scanner(arquivo);
    
            if (scannerArquivo.hasNextLine()) {
                String cabecalho = scannerArquivo.nextLine();
                System.out.println("Cabeçalho: " + cabecalho);
            }
    
            while (scannerArquivo.hasNextLine()) {
                String linha = scannerArquivo.nextLine();
                String[] dados = linha.split(",");
                if (dados.length < 2) {
                    throw new FormatoInvalidoException("Erro: Formato de arquivo inválido para turmas.");
                }
    
                if (buscarTurmaPorCodigo(dados[1]) != null) {
                    throw new DuplicidadeException("Erro: Código de turma duplicado.");
                }
    
                Turma turma = new Turma(dados[0], dados[1]);
                turmasCadastradas.add(turma);
                System.out.println("Adicionando a turma: " + dados[0] + "/" + dados[1]);
            }
    
            scannerArquivo.close();
        } catch (ArquivoNaoEncontradoException | FormatoInvalidoException | DuplicidadeException e) {
            System.out.println(e.getMessage());
        } catch (FileNotFoundException e) {
            System.out.println("Erro ao acessar o arquivo: " + e.getMessage());
        }
    }
    


    public void cadastrarAlunosPorArquivo() throws FileNotFoundException {
        System.out.print("Digite o nome do arquivo que deseja ler os dados para adição de alunos: ");
        String nomeArquivo = scanner.nextLine();
        String diretorioProjeto = System.getProperty("user.dir");

        File arquivo = new File(diretorioProjeto, nomeArquivo);
        Scanner scanner = new Scanner(arquivo);

        if (scanner.hasNextLine()) {
            String cabecalho = scanner.nextLine();
            System.out.println("Cabeçalho: " + cabecalho);
        } 
        while (scanner.hasNextLine()) {
            String linha = scanner.nextLine();
            String[] dados = linha.split(",");
            Turma turmaAluno = buscarTurmaPorCodigo(dados[5]);
            if(turmaAluno != null){
                turmaAluno.adicionarAluno(new Aluno(dados[0], dados[1]));
            }
            System.out.println("Adicionando a turma: " + dados[5] + " o Aluno:" + dados[0] + "/" + dados[1]);
        }
        
        scanner.close();
    }
}
