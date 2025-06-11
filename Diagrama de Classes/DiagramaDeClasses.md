Descrição da Estrutura do Projeto

O projeto está organizado em três camadas principais: Modelos, Acesso a Dados (DAO) e Classes de Suporte. A interação é gerida pela classe Main.

1. Entidades Principais (Modelos):

Usuario: É a classe principal para os utilizadores, definida como abstrata (<<Abstract>>), o que significa que não pode ser instanciada diretamente. Contém atributos comuns como id e nome.

Colaborador e Moderador: São classes que herdam de Usuario, representando os tipos específicos de utilizadores.

Texto: Representa a frase a ser traduzida, com um id e o seu conteudo.

Traducao: É a sugestão de tradução para um Texto, contendo o seu próprio conteudo e um contador de votos.

Idioma: Modela um idioma, com id e nome.

Voto: É uma classe associativa que representa o relacionamento de "muitos-para-muitos" entre Usuario e Traducao.

1. Camada de Acesso a Dados (DAO):

BaseDAO: É uma Interface (<<Interface>>) que define o contrato com os métodos essenciais (salvar, buscarPorId, listarTodos) que todas as classes DAO devem implementar.

TextoDAO, TraducaoDAO, UsuarioDAO, IdiomaDAO: São as classes concretas que implementam a interface BaseDAO. Cada uma é responsável pela comunicação com o banco de dados para a sua respetiva entidade.

1. Classes de Suporte:

ConexaoDB: Uma classe de utilidade (<<Utility>>) que tem a única responsabilidade de criar e fornecer a ligação à base de dados MySQL.

Main: É o ponto de entrada da aplicação. Ela utiliza as classes DAO para orquestrar as operações do sistema, como listar textos e registar votos.

1. Principais Relacionamentos:

Herança (---|>): Colaborador e Moderador são tipos de Usuario.

Implementação (...|>): As classes DAO implementam o contrato definido pela BaseDAO.

Associação (---):

Um Texto pode ter muitas (\*) Traducoes.

Uma Traducao é sempre criada por um (1) Usuario.

Agregação (o---):

Um Texto tem um (1) Idioma de origem e um (1) Idioma de destino.

Dependência (...>):

A classe Main utiliza as classes DAO e a ConexaoDB para funcionar.
