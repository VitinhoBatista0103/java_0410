public class Aluno {
    private int codigo;
    private String nome;
    private String dataNascimento;
    private String email;
    private String senha;
    private Curso cursoMatriculado;

    private double[] notas = new double[3];
    private boolean[] lancada = new boolean[3];

    private Mensalidade[] mensalidades;
    private int numParcelas;

    public Aluno(int codigo, String nome, String dataNascimento, String email, String senha) {
        this.codigo = codigo;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.email = email;
        this.senha = senha;
    }

    public int getCodigo() { return codigo; }
    public void setCodigo(int codigo) { this.codigo = codigo; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(String dataNascimento) { this.dataNascimento = dataNascimento; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public Curso getCursoMatriculado() { return cursoMatriculado; }
    public void setCursoMatriculado(Curso cursoMatriculado) {
        this.cursoMatriculado = cursoMatriculado;
    }

    public void exibeDados() {
        System.out.println("Codigo: " + codigo);
        System.out.println("Nome: " + nome);
        System.out.println("Data de nascimento: " + dataNascimento);
        System.out.println("E-mail: " + email);
        System.out.println("Curso: " +
            (cursoMatriculado != null ? cursoMatriculado.getNome() : "Nao matriculado"));
    }

    public void lancarNotas(double nota1, double nota2, double nota3) {
        notas[0] = validarNota(nota1);
        notas[1] = validarNota(nota2);
        notas[2] = validarNota(nota3);

        for (int i = 0; i < 3; i++) {
            lancada[i] = true;
        }
    }

    private double validarNota(double nota) {
        if (nota < 0) return 0;
        if (nota > 10) return 10;
        return nota;
    }

    public double calcularMedia() {
        return (notas[0] + notas[1] + notas[2]) / 3.0;
    }

    public void exibirNotas() {
        System.out.printf("Nota 1: %.2f%n", notas[0]);
        System.out.printf("Nota 2: %.2f%n", notas[1]);
        System.out.printf("Nota 3: %.2f%n", notas[2]);

        boolean todasLancadas = lancada[0] && lancada[1] && lancada[2];
        if (todasLancadas) {
            System.out.printf("Media: %.2f%n", calcularMedia());
        } else {
            System.out.println("As tres notas ainda nao foram lancadas.");
        }
    }

    public void adicionarMensalidades(double[] valores) {
        numParcelas = valores.length;
        mensalidades = new Mensalidade[numParcelas];

        for (int i = 0; i < numParcelas; i++) {
            mensalidades[i] = new Mensalidade(valores[i]);
        }
    }

    public void exibirMensalidades() {
        if (mensalidades == null) {
            System.out.println("Nenhuma mensalidade cadastrada.");
            return;
        }

        System.out.println("--- FINANCEIRO ---");
        for (int i = 0; i < numParcelas; i++) {
            String status = mensalidades[i].isPago() ? "PAGO" : "PENDENTE";
            System.out.printf("Parcela %d - R$ %.2f - %s%n",
                i + 1, mensalidades[i].getValor(), status);
        }
    }

    public void pagarMensalidade(int indice) {
        if (mensalidades == null) {
            System.out.println("Nenhuma mensalidade cadastrada.");
            return;
        }

        if (indice < 0 || indice >= numParcelas) {
            System.out.println("Numero de parcela invalido.");
            return;
        }

        mensalidades[indice].darBaixa();
        System.out.println("Parcela " + (indice + 1) + " paga com sucesso.");
    }
}
