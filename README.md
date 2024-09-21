# TRABALHO_P.O

O sistema possui várias classes que representam os componentes principais, como gastos, tipos de gastos, transações e usuários. Cada gasto pode pertencer a diferentes categorias (ex.: Jogos, Comida, Roupas) e pode ser registrado ou marcado como quitado. Usuários podem ser gerenciados no sistema e os dados são persistidos em arquivos de texto.

Classes Principais
1. Classe Gasto (Abstrata)
Representa um gasto genérico que implementa a interface Transacao. Cada gasto possui uma descrição, valor, data de vencimento, tipo e um estado que indica se foi quitado ou não.

Atributos:
descricao: Descrição do gasto.
valor: Valor do gasto.
dataVencimento: Data de vencimento do gasto.
tipo: Tipo do gasto (ex.: Jogos, Comida, etc.).
quitado: Indica se o gasto foi quitado ou não.
Métodos:
efetuarPagamento(double valor): Marca o gasto como quitado e imprime uma mensagem.
mostrarDetalhes(): Método abstrato para exibir os detalhes do gasto.
toString(): Representa o gasto como uma string no formato utilizado para persistência.
2. Subclasses de Gasto
Estas subclasses representam tipos específicos de gastos:

JogosGasto: Gasto relacionado a jogos.
ComidaGasto: Gasto relacionado a comida.
RoupasGasto: Gasto relacionado a roupas.
UberGasto: Gasto relacionado a corridas de Uber.
CinemaGasto: Gasto relacionado a cinema.
Cada uma delas implementa o método mostrarDetalhes() para exibir as informações específicas do gasto.

3. Classe UsuarioSistema
Representa um usuário no sistema, contendo login e senha criptografada.

Atributos:
login: Login do usuário.
senhaCriptografada: Senha criptografada usando inversão de string.
Métodos:
salvarUsuario(UsuarioSistema usuario, String caminhoArquivo): Salva o usuário em um arquivo.
listarUsuarios(String caminhoArquivo): Lista todos os usuários do arquivo.
criptografarSenha(String senha): Criptografa a senha invertendo-a.
4. Classe TipoGasto
Representa os diferentes tipos de gastos permitidos no sistema.

Atributos:
nome: Nome do tipo de gasto.
Métodos:
salvarTipoGasto(TipoGasto tipo, String caminhoArquivo): Salva um novo tipo de gasto.
listarTipos(String caminhoArquivo): Lista todos os tipos de gastos.
atualizarTipoGasto(ArrayList<TipoGasto> tipos, String nomeAntigo, String nomeNovo, String caminhoArquivo): Atualiza o nome de um tipo de gasto.
excluirTipoGasto(ArrayList<TipoGasto> tipos, String nomeParaExcluir, String caminhoArquivo): Exclui um tipo de gasto.
5. Classe SistemaGerenciadorGastos
Classe principal que contém o menu do sistema e os métodos para interação com o usuário.

Atributos:
ARQUIVO_GASTOS: Caminho para o arquivo de gastos.
ARQUIVO_USUARIOS: Caminho para o arquivo de usuários.
ARQUIVO_TIPOS: Caminho para o arquivo de tipos de gastos.
ARQUIVO_PAGAMENTOS: Caminho para o arquivo de pagamentos.
FORMATO_DATA: Formato padrão de data para exibir e parsear as datas (dd/MM/yyyy).
Métodos:
main(String[] args): Método principal que contém o menu de interação com o usuário.
registrarGasto(Scanner scanner): Registra um novo gasto no sistema.
registrarPagamento(Scanner scanner): Marca um gasto como quitado e registra o pagamento.
exibirGastos(boolean quitados): Exibe os gastos quitados ou pendentes.
gerenciarTiposGasto(Scanner scanner): Menu para gerenciamento de tipos de gastos.
gerenciarUsuarios(Scanner scanner): Menu para gerenciamento de usuários.
editarOuExcluirGasto(Scanner scanner): Permite editar ou excluir um gasto existente.
Arquitetura de Arquivos
O sistema persiste os dados em arquivos de texto para manter um registro dos gastos, usuários e tipos de gastos:

gastos.txt: Contém os registros de todos os gastos, com o formato descricao;valor;dataVencimento;tipo;quitado.
usuarios.txt: Contém o login e a senha criptografada de cada usuário, com o formato login;senhaCriptografada.
tipos_gasto.txt: Contém os tipos de gastos disponíveis.
pagamentos.txt: Contém os registros de pagamentos efetuados, com o formato descricaoGasto;valor.
