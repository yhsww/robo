# Fruit Bot

Projeto desenvolvido em Java com JavaFX, no qual diferentes robôs interagem com um tabuleiro que contém elementos como frutas, obstáculos, rochas e bombas.

O projeto permite explorar diferentes modos de jogo e estratégias de movimentação dos robôs.

## Tecnologias utilizadas
- **Java** 
- **JavaFX**
- **Apache Maven**
- **CSS**

## Pré-requisitos

Antes de executar o projeto, instale:
- JDK 21.
- Apache Maven.

Verifique se Java e Maven estão disponíveis no terminal:

```bash
java -version
mvn -version
git --version
```

## Como obter o projeto

Clone o repositório:

```bash
git clone https://github.com/yhsww/robo.git
cd robo
```

## Como executar

Na raiz do projeto, execute:

```bash
mvn clean javafx:run
```

O Maven fará a compilação do código, resolverá as dependências declaradas no `pom.xml` e iniciará a aplicação pela classe principal `jyk.jogo.Jogo`.
Para compilar sem iniciar a interface gráfica:

```bash
mvn clean compile
```

## Organização do código

### Interface gráfica

- `Jogo.java`: classe principal utilizada para iniciar a aplicação.
- `TelaMenu.java`: implementação da tela de menu.
- `TelaJogo.java`: implementação da interface do jogo.
- `Imagens.java`: suporte relacionado às imagens utilizadas na aplicação.

### Regras do jogo

- `Tabuleiro.java`: representa a estrutura do tabuleiro.
- `Posicao.java`: representa posições utilizadas no jogo.
- `celulas/`: contém as classes dos elementos presentes no tabuleiro, como frutas, bombas, obstáculos e rochas.
- `robo/`: contém as classes relacionadas aos robôs, cores, movimentos e tratamento de movimentos inválidos.
- `modos/`: contém as implementações dos diferentes modos de jogo.

## Modos de jogo 

O código contém classes correspondentes aos seguintes modos:

- **Modo automático** — `ModoAutomatico`.
- **Dois robôs** — `ModoDoisRobosBurros`.
- **Um robô simples** — `ModoUmRobo`.
- **Um robô simples e um inteligente** — `ModoUmBurroUmInteligente`.
- **Um robô simples e um inteligente com obstáculos** — `ModoUmBurroUmInteligenteObstaculos`.

## Dependências

As dependências e configurações de compilação estão declaradas no arquivo `pom.xml`.
Atualmente, o projeto utiliza:

- JavaFX Controls 21.0.5.
- Maven Compiler Plugin 3.13.0.
- JavaFX Maven Plugin 0.0.8.
