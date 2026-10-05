import java.util.Scanner;

public class SistemaEscolaEAD {
    private Scanner scanner;
    private ListaDeAlunos listaAlunos;

    // Array bidimensional obrigatorio: 2 turmas x 3 cursos
    private Curso[][] matrizCursos;

    public SistemaEscolaEAD() {
        scanner = new Scanner(System.in);
        listaAlunos = new ListaDeAlunos(20);
        matrizCursos = new Curso[2][3];

        cadastrarCursosIniciais();
    }

    private void cadastrarCursosIniciais() {
        matrizCursos[0][0] = new Curso(101, "Java Basico", 40);
        matrizCursos[0][1] = new Curso(102, "Banco de Dados", 50);
        matrizCursos[0][2] = new Curso(103, "Desenvolvimento Web", 60);

        matrizCursos[1][0] = new Curso(201, "Programacao Orientada a Objetos", 45);
        matrizCursos[1][1] = new Curso(202, "Engenharia de Software", 40);
        matrizCursos[1][2] = new Curso(203, "Algoritmos", 50);
    }

    public void iniciar() {
        int opcao;

        do {
            exibirMenu();
            opcao = lerInteiro("Escolha uma opcao: ");

            switch (opcao) {
                case 1:
                    listaAlunos.exibirLista();
                    break;
                case 2:
                    adicionarAluno();
                    break;
                case 3:
                    lancarNotas();
                    break;
                case 4:
                    verificarNotas();
                    break;
                case 5:
                    verificarFinanceiro();
                    break;
                case 6:
                    exibirCursos();
                    break;
                case 7:
                    exibirResumo();
                    break;
                case 0:
                    System.out.println("Sistema encerrado.");
                    break;
                default:
                    System.out.println("Opcao invalida.");
            }
        } while (opcao != 0);
    }

    private void exibirMenu() {
        System.out.println();
        System.out.println("========================================");
        System.out.println("       ESCOLA EAD - MENU PRINCIPAL");
        System.out.println("========================================");
        System.out.println("1 - Visualizar lista de alunos");
        System.out.println("2 - Adicionar aluno");
        System.out.println("3 - Lancar notas do aluno");
        System.out.println("4 - Verificar notas do aluno");
        System.out.println("5 - Verificar financeiro do aluno");
        System.out.println("6 - Visualizar cursos e matriculas");
        System.out.println("7 - Exibir resumo do sistema");
        System.out.println("0 - Sair");
        System.out.println("========================================");
    }

    private void adicionarAluno() {
        System.out.println("\n--- CADASTRO DE ALUNO ---");

        int codigo = lerInteiro("Codigo: ");

        if (listaAlunos.buscarPorCodigo(codigo) != null) {
            System.out.println("Ja existe aluno com esse codigo.");
            return;
        }

        String nome = lerTexto("Nome: ");
        String dataNascimento = lerTexto("Data de nascimento: ");
        String email = lerTexto("E-mail: ");
        String senha = lerTexto("Senha: ");

        System.out.println("1 - Aluno comum");
        System.out.println("2 - Aluno bolsista");
        int tipo = lerInteiro("Tipo: ");

        Aluno aluno;

        if (tipo == 2) {
            String bolsa = lerTexto("Tipo de bolsa: ");
            aluno = new AlunoBolsista(codigo, nome, dataNascimento, email, senha, bolsa);
        } else {
            aluno = new Aluno(codigo, nome, dataNascimento, email, senha);
        }

        exibirCursos();

        int codigoCurso = lerInteiro("Codigo do curso para matricula: ");
        Curso curso = buscarCurso(codigoCurso);

        if (curso == null) {
            System.out.println("Curso nao encontrado. Aluno sera cadastrado sem curso.");
        } else {
            aluno.setCursoMatriculado(curso);
        }

        // Tres notas iniciais, todas zeradas ate o lancamento.
        aluno.lancarNotas(0, 0, 0);

        // Tres mensalidades para demonstracao do controle financeiro.
        aluno.adicionarMensalidades(new double[] {199.90, 199.90, 199.90});

        if (listaAlunos.adicionarAluno(aluno)) {
            System.out.println("Aluno cadastrado com sucesso.");
        } else {
            System.out.println("Nao foi possivel cadastrar o aluno.");
        }
    }

    private void lancarNotas() {
        int codigo = lerInteiro("Codigo do aluno: ");
        Aluno aluno = listaAlunos.buscarPorCodigo(codigo);

        if (aluno == null) {
            System.out.println("Aluno nao encontrado.");
            return;
        }

        double n1 = lerNota("Nota 1: ");
        double n2 = lerNota("Nota 2: ");
        double n3 = lerNota("Nota 3: ");

        aluno.lancarNotas(n1, n2, n3);
        System.out.println("Notas lancadas com sucesso.");
    }

    private void verificarNotas() {
        int codigo = lerInteiro("Codigo do aluno: ");
        Aluno aluno = listaAlunos.buscarPorCodigo(codigo);

        if (aluno == null) {
            System.out.println("Aluno nao encontrado.");
            return;
        }

        System.out.println("\nAluno: " + aluno.getNome());
        aluno.exibirNotas();
    }

    private void verificarFinanceiro() {
        int codigo = lerInteiro("Codigo do aluno: ");
        Aluno aluno = listaAlunos.buscarPorCodigo(codigo);

        if (aluno == null) {
            System.out.println("Aluno nao encontrado.");
            return;
        }

        aluno.exibirMensalidades();

        int parcela = lerInteiro("Digite o numero da parcela para pagar (0 para voltar): ");

        if (parcela != 0) {
            aluno.pagarMensalidade(parcela - 1);
            aluno.exibirMensalidades();
        }
    }

    private void exibirCursos() {
        System.out.println("\n--- CURSOS DISPONIVEIS ---");

        for (int i = 0; i < matrizCursos.length; i++) {
            System.out.println("Turma/semestre " + (i + 1) + ":");

            for (int j = 0; j < matrizCursos[i].length; j++) {
                if (matrizCursos[i][j] != null) {
                    matrizCursos[i][j].exibeDados();
                }
            }
        }

        System.out.println("\n--- ALUNOS MATRICULADOS ---");
        for (int i = 0; i < matrizCursos.length; i++) {
            for (int j = 0; j < matrizCursos[i].length; j++) {
                Curso curso = matrizCursos[i][j];

                if (curso != null) {
                    System.out.println("\nCurso: " + curso.getNome());

                    boolean encontrou = false;

                    for (int k = 0; k < listaAlunos.getTotalAlunos(); k++) {
                        Aluno aluno = listaAlunos.getAlunos()[k];

                        if (aluno.getCursoMatriculado() == curso) {
                            System.out.println("- " + aluno.getNome() +
                                               " (codigo " + aluno.getCodigo() + ")");
                            encontrou = true;
                        }
                    }

                    if (!encontrou) {
                        System.out.println("- Nenhum aluno matriculado.");
                    }
                }
            }
        }
    }

    private Curso buscarCurso(int codigo) {
        for (int i = 0; i < matrizCursos.length; i++) {
            for (int j = 0; j < matrizCursos[i].length; j++) {
                if (matrizCursos[i][j] != null &&
                    matrizCursos[i][j].getCodigo() == codigo) {
                    return matrizCursos[i][j];
                }
            }
        }
        return null;
    }

    private void exibirResumo() {
        System.out.println("\n--- RESUMO DO SISTEMA ---");
        System.out.println("Total de alunos: " + listaAlunos.getTotalAlunos());
        System.out.println("Matriz de cursos: " +
                           matrizCursos.length + " x " + matrizCursos[0].length);
        System.out.println("Array de alunos: Aluno[]");
        System.out.println("Array de notas: double[3]");
        System.out.println("Array de lancamento: boolean[3]");
        System.out.println("Array de mensalidades: Mensalidade[]");
        System.out.println("Heranca: AlunoBolsista extends Aluno");
        System.out.println("Polimorfismo: exibeDados() sobrescrito");
    }

    private int lerInteiro(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                int valor = Integer.parseInt(scanner.nextLine());
                return valor;
            } catch (NumberFormatException e) {
                System.out.println("Digite um numero inteiro valido.");
            }
        }
    }

    private double lerNota(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                double valor = Double.parseDouble(scanner.nextLine().replace(',', '.'));

                if (valor < 0 || valor > 10) {
                    System.out.println("A nota deve estar entre 0 e 10.");
                } else {
                    return valor;
                }
            } catch (NumberFormatException e) {
                System.out.println("Digite uma nota valida.");
            }
        }
    }

    private String lerTexto(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine();
    }

    public static void main(String[] args) {
        SistemaEscolaEAD sistema = new SistemaEscolaEAD();
        sistema.iniciar();
    }
}
