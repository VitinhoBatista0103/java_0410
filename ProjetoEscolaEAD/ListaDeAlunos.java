public class ListaDeAlunos {
    private Aluno[] alunos;
    private int totalAlunos;

    public ListaDeAlunos(int capacidade) {
        alunos = new Aluno[capacidade];
        totalAlunos = 0;
    }

    public boolean adicionarAluno(Aluno a) {
        if (a == null || totalAlunos >= alunos.length) {
            return false;
        }

        if (buscarPorCodigo(a.getCodigo()) != null) {
            return false;
        }

        alunos[totalAlunos] = a;
        totalAlunos++;
        return true;
    }

    public void exibirLista() {
        if (totalAlunos == 0) {
            System.out.println("Nenhum aluno cadastrado.");
            return;
        }

        for (int i = 0; i < totalAlunos; i++) {
            System.out.println("------------------------------");
            alunos[i].exibeDados();
        }
        System.out.println("------------------------------");
    }

    public Aluno buscarPorCodigo(int codigo) {
        for (int i = 0; i < totalAlunos; i++) {
            if (alunos[i].getCodigo() == codigo) {
                return alunos[i];
            }
        }
        return null;
    }

    public int getTotalAlunos() {
        return totalAlunos;
    }

    public Aluno[] getAlunos() {
        return alunos;
    }
}
