Disciplina: Programação Orientada a Objetos

Professora: Talita Vieira Ribeiro

Data de Entrega: 11 de Junho de 2025

1. Introdução

Este projeto consiste na criação de um Sistema de Tradução Colaborativa, uma aplicação prática desenvolvida para consolidar os conhecimentos adquiridos na disciplina de Programação Orientada a Objetos. O sistema, inspirado em plataformas de crowdsourcing como o Duolingo Incubator, permite que utilizadores submetam, visualizem e votem em traduções de textos, promovendo a colaboração.

A aplicação foi desenvolvida em Java, utilizando o padrão de arquitetura DAO (Data Access Object) para a persistência de dados e o JDBC para a comunicação com uma base de dados relacional MySQL.

1. Análise de Conformidade com os Requisitos

A seguir, uma análise detalhada que comprova o cumprimento de todos os requisitos técnicos exigidos para o projeto.

1. Pilares da Programação Orientada a Objetos

Abstração: As entidades do sistema (Utilizador, Texto, Tradução, Idioma) foram modeladas como classes no pacote model, abstraindo os seus atributos e comportamentos essenciais para o domínio do problema.

Encapsulamento: Todos os atributos das classes de modelo foram declarados como private ou protected, com o acesso controlado através de métodos públicos (getters), garantindo a integridade e a segurança dos dados dos objetos.

Herança: Foi implementada uma hierarquia de classes com a classe Usuario sendo abstrata e as classes concretas Colaborador e Moderador a herdarem dela. Isto promove a reutilização de código e a especialização de tipos.

Polimorfismo: O polimorfismo de sobrescrita foi aplicado de duas formas principais:

O método abstrato getTipoUsuario() na classe Usuario é obrigatoriamente sobrescrito pelas suas subclasses.

Os métodos da interface BaseDAO (como salvar e buscarPorId) são sobrescritos nas classes concretas como TextoDAO e TraducaoDAO.

1. Classes Abstratas e Interfaces

Classe Abstrata: A classe Usuario foi definida como abstract, contendo tanto métodos concretos (getters) como um método abstrato (getTipoUsuario()), cumprindo o requisito.

Interface: A interface BaseDAO foi criada para definir um contrato para as operações de acesso a dados. Ela é implementada por múltiplas classes (TextoDAO, TraducaoDAO, UsuarioDAO e IdiomaDAO), demonstrando o uso correto de interfaces para desacoplar o código.

1. Relacionamentos entre Classes

Diversidade de Cardinalidades: O modelo de classes implementa os seguintes relacionamentos:

1. para N (Um para Muitos): Um Texto pode possuir várias Traducoes. Um Usuario pode ser o autor de várias Traducoes.

N para N (Muitos para Muitos): O relacionamento entre Usuario e Traducao através de votos é modelado pela tabela associativa Voto na base de dados.

Agregação: A relação entre Texto e Traducao é um exemplo de agregação, onde um texto é um "todo" que agrupa uma "parte" (as suas traduções).

2\.4. Collections

O projeto utiliza extensivamente o framework de Collections do Java. Especificamente, List e ArrayList são usados em toda a camada DAO para retornar conjuntos de dados do banco, como em textoDAO.listarTodos().

2\.5. Persistência de Dados

JDBC e Padrão DAO: A comunicação com a base de dados MySQL é feita exclusivamente via JDBC. Toda a lógica de acesso a dados foi isolada na camada DAO, seguindo o padrão de projeto exigido. A classe ConexaoDB centraliza a obtenção da ligação.

Operações CRUD: As operações básicas de persistência (Create, Read, Update, Delete) foram definidas na interface BaseDAO e implementadas conforme a necessidade do sistema.

Create: salvar() (ex: TraducaoDAO, TextoDAO).

Read: buscarPorId() e listarTodos() (ex: TextoDAO, UsuarioDAO).

Update / Delete: Os métodos foram declarados na interface para cumprir o requisito, mas não foram implementados pois não são essenciais para a lógica principal do sistema, evitando código desnecessário.
