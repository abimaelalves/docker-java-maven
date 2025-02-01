# 🚀 Tutorial: Subindo uma Aplicação Java com Maven no Docker

Este tutorial irá guiá-lo passo a passo na criação e execução de uma aplicação Java simples usando Maven e Docker. O objetivo é rodar uma aplicação "Hello World" dentro de um container Docker que permaneça em execução até ser explicitamente parado.

---

## 🛠️ **1. Estrutura do Projeto**

Crie um diretório para o projeto e organize os arquivos conforme abaixo:

```
hello-world-java/
│── Dockerfile
│── README.md
│── src/
│   └── main/
│       └── java/
│           └── com/
│               └── example/
│                   └── Main.java
│── pom.xml

mkdir -p hello-world-java/src/main/java/com/example && \
touch hello-world-java/{Dockerfile,README.md,pom.xml} hello-world-java/src/main/java/com/example/Main.java


```

---

## 📌 **2. Criando a Aplicação Java**

Crie a classe `Main.java` dentro do diretório `src/main/java/com/example/`:

```java
package com.example;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Hello, World!");
        while (true) {
            Thread.sleep(1000);
        }
    }
}
```

A aplicação imprimirá "Hello, World!" e ficará rodando indefinidamente até que o container seja parado.

---

## 📦 **3. Criando o Arquivo `pom.xml`**

Crie o arquivo `pom.xml` no diretório raiz para configurar o Maven:

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.example</groupId>
    <artifactId>hello-world</artifactId>
    <version>1.0-SNAPSHOT</version>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.8.1</version>
                <configuration>
                    <source>17</source>
                    <target>17</target>
                </configuration>
            </plugin>

            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-jar-plugin</artifactId>
                <version>3.2.0</version>
                <configuration>
                    <archive>
                        <manifest>
                            <mainClass>com.example.Main</mainClass>
                        </manifest>
                    </archive>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

Esse `pom.xml` garante que a aplicação será empacotada corretamente como um JAR executável.

---

## 🐳 **4. Criando o Dockerfile**

Crie o arquivo `Dockerfile` no diretório raiz:

```dockerfile
# Usa a imagem do Maven para compilar o projeto
FROM maven:3.9.5-eclipse-temurin-17 as builder
WORKDIR /app

# Copia os arquivos do projeto
COPY pom.xml .
COPY src ./src

# Compila a aplicação e empacota o JAR
RUN mvn clean package

# Usa uma imagem menor para rodar a aplicação
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copia o JAR gerado do estágio anterior
COPY --from=builder /app/target/*.jar app.jar

# Comando para rodar o programa e manter o container ativo
CMD ["java", "-jar", "app.jar"]
```

Esse Dockerfile faz a compilação do código e cria uma imagem leve contendo apenas o JRE necessário para rodar a aplicação.

---

## 🚀 **5. Construindo e Executando a Aplicação**

Agora, vamos compilar o código e subir o container.

### 🏗️ **Passo 1: Construir a Aplicação**
Execute o seguinte comando no terminal dentro do diretório do projeto:

```sh
cd hello-world-java && mvn clean package
```

Isso gerará o arquivo `app.jar` dentro do diretório `target/`.

### 🛠️ **Passo 2: Construir a Imagem Docker**

```sh
docker build -t hello-world-java .
```

### 📌 **Passo 3: Executar o Container**

```sh
docker run --name hello-java -d hello-world-java
```

O container será iniciado em modo **detached (-d)** e permanecerá rodando.

---

## 📡 **6. Verificando se a Aplicação Está Rodando**

Para visualizar os logs da aplicação:

```sh
docker logs -f hello-java
```

Saída esperada:
```
Hello, World!
```

---

## ❌ **7. Parando e Removendo o Container**

Caso queira parar a execução do container:

```sh
docker stop hello-java
```

Se quiser remover o container completamente:

```sh
docker rm hello-java
```

---

## 🛠️ **8. Debugando dentro do Container**

Se precisar acessar o container para verificar arquivos ou processos, use:

```sh
docker exec -it hello-java sh
```

---

## 🎉 **Conclusão**

Agora você tem um container rodando uma aplicação Java com Maven que imprime "Hello, World!" e permanece em execução até ser explicitamente parado. Esse tutorial garante que qualquer pessoa possa replicar esse processo facilmente. 🚀

Se tiver dúvidas ou melhorias, sinta-se à vontade para contribuir! 😃

