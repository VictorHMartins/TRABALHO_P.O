import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

interface Transacao {
    void efetuarPagamento(double valor);
}

abstract class Gasto implements Transacao {
    protected String descricao;
    protected double valor;
    protected Date dataVencimento;
    protected String tipo;
    protected boolean quitado;

    public Gasto(String descricao, double valor, Date dataVencimento, String tipo) {
        this.descricao = descricao;
        this.valor = valor;
        this.dataVencimento = dataVencimento;
        this.tipo = tipo;
        this.quitado = false;
    }

    public boolean isQuitado() {
        return quitado;
    }

    @Override
    public void efetuarPagamento(double valor) {
        this.quitado = true;
        System.out.println("Gasto quitado com o valor de: R$" + valor);
    }

    public abstract void mostrarDetalhes();

    @Override
    public String toString() {
        return descricao + ";" + valor + ";" + SistemaGerenciadorGastos.FORMATO_DATA.format(dataVencimento) + ";" + tipo + ";" + quitado;
    }
}

class JogosGasto extends Gasto {
    public JogosGasto(String descricao, double valor, Date dataVencimento) {
        super(descricao, valor, dataVencimento, "Jogos");
    }

    @Override
    public void mostrarDetalhes() {
        System.out.println("Jogos: " + descricao + ", Valor: R$" + valor + ", Data de Vencimento: " + dataVencimento);
    }
}

class ComidaGasto extends Gasto {
    public ComidaGasto(String descricao, double valor, Date dataVencimento) {
        super(descricao, valor, dataVencimento, "Comida");
    }

    @Override
    public void mostrarDetalhes() {
        System.out.println("Comida: " + descricao + ", Valor: R$" + valor + ", Data de Vencimento: " + dataVencimento);
    }
}

class RoupasGasto extends Gasto {
    public RoupasGasto(String descricao, double valor, Date dataVencimento) {
        super(descricao, valor, dataVencimento, "Roupas");
    }

    @Override
    public void mostrarDetalhes() {
        System.out.println("Roupas: " + descricao + ", Valor: R$" + valor + ", Data de Vencimento: " + dataVencimento);
    }
}

class UberGasto extends Gasto {
    public UberGasto(String descricao, double valor, Date dataVencimento) {
        super(descricao, valor, dataVencimento, "Uber");
    }

    @Override
    public void mostrarDetalhes() {
        System.out.println("Uber: " + descricao + ", Valor: R$" + valor + ", Data de Vencimento: " + dataVencimento);
    }
}

class CinemaGasto extends Gasto {
    public CinemaGasto(String descricao, double valor, Date dataVencimento) {
        super(descricao, valor, dataVencimento, "Cinema");
    }

    @Override
    public void mostrarDetalhes() {
        System.out.println("Cinema: " + descricao + ", Valor: R$" + valor + ", Data de Vencimento: " + dataVencimento);
    }
}

class UsuarioSistema {
    private String login;
    private String senhaCriptografada;

    public UsuarioSistema(String login, String senha) {
        this.login = login;
        this.senhaCriptografada = criptografarSenha(senha);
    }

    private String criptografarSenha(String senha) {
        return new StringBuilder(senha).reverse().toString();
    }

    public String getLogin() {
        return login;
    }

    public String getSenhaCriptografada() {
        return senhaCriptografada;
    }

    public static void salvarUsuario(UsuarioSistema usuario, String caminhoArquivo) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo, true))) {
            writer.write(usuario.getLogin() + ";" + usuario.getSenhaCriptografada());
            writer.newLine();
        }
    }

    public static ArrayList<UsuarioSistema> listarUsuarios(String caminhoArquivo) throws IOException {
        ArrayList<UsuarioSistema> usuarios = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length == 2) {
                    usuarios.add(new UsuarioSistema(dados[0], dados[1]));
                }
            }
        }
        return usuarios;
    }
}

class TipoGasto {
    private String nome;

    public TipoGasto(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public static void salvarTipoGasto(TipoGasto tipo, String caminhoArquivo) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo, true))) {
            writer.write(tipo.getNome());
            writer.newLine();
        }
    }

    public static ArrayList<TipoGasto> listarTipos(String caminhoArquivo) throws IOException {
        ArrayList<TipoGasto> tipos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                tipos.add(new TipoGasto(linha));
            }
        }
        return tipos;
    }

    public static void atualizarTipoGasto(ArrayList<TipoGasto> tipos, String nomeAntigo, String nomeNovo, String caminhoArquivo) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            for (TipoGasto tipo : tipos) {
                if (tipo.getNome().equals(nomeAntigo)) {
                    writer.write(nomeNovo);
                } else {
                    writer.write(tipo.getNome());
                }
                writer.newLine();
            }
        }
    }

    public static void excluirTipoGasto(ArrayList<TipoGasto> tipos, String nomeParaExcluir, String caminhoArquivo) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            for (TipoGasto tipo : tipos) {
                if (!tipo.getNome().equals(nomeParaExcluir)) {
                    writer.write(tipo.getNome());
                    writer.newLine();
                }
            }
        }
    }
}

public class SistemaGerenciadorGastos {
    private static final String ARQUIVO_GASTOS = "gastos.txt";
    private static final String ARQUIVO_USUARIOS = "usuarios.txt";
    private static final String ARQUIVO_TIPOS = "tipos_gasto.txt";
    private static final String ARQUIVO_PAGAMENTOS = "pagamentos.txt"; 
    public static final SimpleDateFormat FORMATO_DATA = new SimpleDateFormat("dd/MM/yyyy");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean rodando = true;

        while (rodando) {
            System.out.println("Menu Principal:");
            System.out.println("1. Registrar Gasto");
            System.out.println("2. Registrar Pagamento");
            System.out.println("3. Exibir Gastos Pendentes");
            System.out.println("4. Exibir Gastos Quitados");
            System.out.println("5. Gerenciar Tipos de Gastos");
            System.out.println("6. Gerenciar Usuários");
            try {
                System.out.println("7. Editar ou Excluir Gasto");
                System.out.println("8. Sair");
                int opcao = scanner.nextInt();
                scanner.nextLine(); 
            
                switch (opcao) {
                    case 1:
                        registrarGasto(scanner);
                        break;
                    case 2:
                        registrarPagamento(scanner);
                        break;
                    case 3:
                        exibirGastos(false);
                        break;
                    case 4:
                        exibirGastos(true);
                        break;
                    case 5:
                        gerenciarTiposGasto(scanner);
                        break;
                    case 6:
                        gerenciarUsuarios(scanner);
                        break;
                    case 7:
                        editarOuExcluirGasto(scanner);
                        break;
                    case 8:
                        rodando = false;
                        System.out.println("Saindo do sistema...");
                        break;
                    default:
                        System.out.println("Opção inválida, tente novamente.");
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Ocorreu um erro de entrada/saída.");
            }
        }
        scanner.close();
    }

    private static void registrarGasto(Scanner scanner) {
        try {
            System.out.println("Informe a descrição do gasto:");
            String descricao = scanner.nextLine();
            System.out.println("Informe o valor:");
            double valor = scanner.nextDouble();
            System.out.println("Informe a data de vencimento (dd/MM/yyyy):");
            scanner.nextLine();  
            String dataVencimentoStr = scanner.nextLine();
            Date dataVencimento = FORMATO_DATA.parse(dataVencimentoStr);
            System.out.println("Informe o tipo (Jogos, Comida, Roupas, Uber, Cinema):");
            String tipo = scanner.nextLine();

            Gasto gasto = criarGastoPorTipo(descricao, valor, dataVencimento, tipo);
            if (gasto != null) {
                salvarGasto(gasto);
                System.out.println("Gasto registrado com sucesso.");
            } else {
                System.out.println("Tipo de gasto inválido.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao registrar gasto: " + e.getMessage());
        }
    }

    private static Gasto criarGastoPorTipo(String descricao, double valor, Date dataVencimento, String tipo) {
        switch (tipo.toLowerCase()) {
            case "Jogos":
                return new JogosGasto(descricao, valor, dataVencimento);
            case "Comida":
                return new ComidaGasto(descricao, valor, dataVencimento);
            case "Roupas":
                return new RoupasGasto(descricao, valor, dataVencimento);
            case "Uber":
                return new UberGasto(descricao, valor, dataVencimento);
            case "Cinema":
                return new CinemaGasto(descricao, valor, dataVencimento);
            default:
                return null;
        }
    }

    private static void salvarGasto(Gasto gasto) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_GASTOS, true))) {
            writer.write(gasto.toString());
            writer.newLine();
        }
    }

    private static void registrarPagamento(Scanner scanner) {
        try {
            System.out.println("Informe a descrição do gasto que deseja pagar:");
            String descricao = scanner.nextLine();
            ArrayList<Gasto> gastosPendentes = obterGastos(false);

            for (Gasto gasto : gastosPendentes) {
                if (gasto.descricao.equals(descricao)) {
                    System.out.println("Informe o valor pago:");
                    double valor = scanner.nextDouble();
                    gasto.efetuarPagamento(valor);
                    atualizarGastos(gastosPendentes);
                    salvarPagamento(descricao, valor);
                    return;
                }
            }

            System.out.println("Gasto não encontrado.");
        } catch (Exception e) {
            System.out.println("Erro ao registrar pagamento: " + e.getMessage());
        }
    }

    private static void salvarPagamento(String descricao, double valor) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_PAGAMENTOS, true))) {
            writer.write(descricao + ";" + valor);
            writer.newLine();
        }
    }

    private static void exibirGastos(boolean quitados) throws IOException {
        ArrayList<Gasto> gastos = obterGastos(quitados);
        if (gastos.isEmpty()) {
            System.out.println("Nenhum gasto para exibir.");
        } else {
            for (Gasto gasto : gastos) {
                gasto.mostrarDetalhes();
            }
        }
    }

    private static ArrayList<Gasto> obterGastos(boolean quitados) throws IOException {
        ArrayList<Gasto> listaGastos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_GASTOS))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length == 5) {
                    String descricao = dados[0];
                    double valor = Double.parseDouble(dados[1]);
                    Date dataVencimento = FORMATO_DATA.parse(dados[2]);
                    String tipo = dados[3];
                    boolean quitado = Boolean.parseBoolean(dados[4]);

                    if (quitado == quitados) {
                        Gasto gasto = criarGastoPorTipo(descricao, valor, dataVencimento, tipo);
                        if (gasto != null) {
                            listaGastos.add(gasto);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao obter lista de gastos: " + e.getMessage());
        }
        return listaGastos;
    }

    private static void atualizarGastos(ArrayList<Gasto> gastos) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_GASTOS))) {
            for (Gasto gasto : gastos) {
                writer.write(gasto.toString());
                writer.newLine();
            }
        }
    }

    private static void gerenciarTiposGasto(Scanner scanner) {
        System.out.println("Menu de Tipos de Gastos:");
        System.out.println("1. Adicionar Tipo");
        System.out.println("2. Exibir Tipos");
        System.out.println("3. Atualizar Tipo");
        System.out.println("4. Excluir Tipo");
        int opcao = scanner.nextInt();
        scanner.nextLine();  

        switch (opcao) {
            case 1:
                adicionarTipo(scanner);
                break;
            case 2:
                exibirTipos();
                break;
            case 3:
                atualizarTipo(scanner);
                break;
            case 4:
                excluirTipo(scanner);
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }

    private static void adicionarTipo(Scanner scanner) {
        System.out.println("Informe o nome do novo tipo:");
        String nomeTipo = scanner.nextLine();
        TipoGasto tipo = new TipoGasto(nomeTipo);
        try {
            TipoGasto.salvarTipoGasto(tipo, ARQUIVO_TIPOS);
            System.out.println("Tipo adicionado com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao adicionar tipo: " + e.getMessage());
        }
    }

    private static void exibirTipos() {
        try {
            ArrayList<TipoGasto> tipos = TipoGasto.listarTipos(ARQUIVO_TIPOS);
            for (TipoGasto tipo : tipos) {
                System.out.println(tipo.getNome());
            }
        } catch (IOException e) {
            System.out.println("Erro ao exibir tipos: " + e.getMessage());
        }
    }

    private static void atualizarTipo(Scanner scanner) {
        try {
            ArrayList<TipoGasto> tipos = TipoGasto.listarTipos(ARQUIVO_TIPOS);
            System.out.println("Informe o nome do tipo que deseja atualizar:");
            String nomeAntigo = scanner.nextLine();
            System.out.println("Informe o novo nome:");
            String nomeNovo = scanner.nextLine();
            TipoGasto.atualizarTipoGasto(tipos, nomeAntigo, nomeNovo, ARQUIVO_TIPOS);
            System.out.println("Tipo atualizado com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao atualizar tipo: " + e.getMessage());
        }
    }

    private static void excluirTipo(Scanner scanner) {
        try {
            ArrayList<TipoGasto> tipos = TipoGasto.listarTipos(ARQUIVO_TIPOS);
            System.out.println("Informe o nome do tipo que deseja excluir:");
            String nomeParaExcluir = scanner.nextLine();
            TipoGasto.excluirTipoGasto(tipos, nomeParaExcluir, ARQUIVO_TIPOS);
            System.out.println("Tipo excluído com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao excluir tipo: " + e.getMessage());
        }
    }

    private static void gerenciarUsuarios(Scanner scanner) {
        System.out.println("Menu de Usuários:");
        System.out.println("1. Adicionar Usuário");
        System.out.println("2. Exibir Usuários");
        int opcao = scanner.nextInt();
        scanner.nextLine(); 

        switch (opcao) {
            case 1:
                adicionarUsuario(scanner);
                break;
            case 2:
                exibirUsuarios();
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }

    private static void adicionarUsuario(Scanner scanner) {
        System.out.println("Informe o login do usuário:");
        String login = scanner.nextLine();
        System.out.println("Informe a senha do usuário:");
        String senha = scanner.nextLine();
        UsuarioSistema usuario = new UsuarioSistema(login, senha);
        try {
            UsuarioSistema.salvarUsuario(usuario, ARQUIVO_USUARIOS);
            System.out.println("Usuário adicionado com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao adicionar usuário: " + e.getMessage());
        }
    }

    private static void exibirUsuarios() {
        try {
            ArrayList<UsuarioSistema> usuarios = UsuarioSistema.listarUsuarios(ARQUIVO_USUARIOS);
            for (UsuarioSistema usuario : usuarios) {
                System.out.println("Login: " + usuario.getLogin());
            }
        } catch (IOException e) {
            System.out.println("Erro ao exibir usuários: " + e.getMessage());
        }
    }

    private static void editarOuExcluirGasto(Scanner scanner) {
        try {
            ArrayList<Gasto> gastos = obterGastos(false);
            System.out.println("Informe a descrição do gasto que deseja editar ou excluir:");
            String descricao = scanner.nextLine();

            for (Gasto gasto : gastos) {
                if (gasto.descricao.equals(descricao)) {
                    System.out.println("1. Editar Gasto");
                    System.out.println("2. Excluir Gasto");
                    int opcao = scanner.nextInt();
                    scanner.nextLine();  

                    if (opcao == 1) {
                        System.out.println("Informe a nova descrição:");
                        gasto.descricao = scanner.nextLine();
                        System.out.println("Informe o novo valor:");
                        gasto.valor = scanner.nextDouble();
                        System.out.println("Informe a nova data de vencimento (dd/MM/yyyy):");
                        scanner.nextLine();  
                        String dataVencimentoStr = scanner.nextLine();
                        gasto.dataVencimento = FORMATO_DATA.parse(dataVencimentoStr);
                        atualizarGastos(gastos);
                        System.out.println("Gasto editado com sucesso.");
                    } else if (opcao == 2) {
                        gastos.remove(gasto);
                        atualizarGastos(gastos);
                        System.out.println("Gasto excluído com sucesso.");
                    }
                    return;
                }
            }
            System.out.println("Gasto não encontrado.");
        } catch (Exception e) {
            System.out.println("Erro ao editar ou excluir gasto: " + e.getMessage());
        }
    }
}
