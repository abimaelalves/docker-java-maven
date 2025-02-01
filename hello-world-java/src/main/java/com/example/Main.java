package com.example;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws IOException {
        System.out.println("===================================");
        System.out.println("🚀 Application Started Successfully!");
        System.out.println("===================================");

        // Configurando o Logger para exibir logs INFO ou superiores
        logger.setLevel(Level.INFO);

        // Criando o servidor HTTP na porta 8080
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", new HelloHandler());
        server.setExecutor(null);
        server.start();

        logger.info("🌍 HTTP Server started at http://localhost:8080");
    }

    // Manipulador para responder requisições HTTP
    static class HelloHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = "Hello, World!";
            exchange.sendResponseHeaders(200, response.length());

            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();

            // Logando a requisição recebida
            Logger.getLogger(Main.class.getName()).info(
                    "📥 Received request: " + exchange.getRequestMethod() +
                    " from " + exchange.getRemoteAddress());
        }
    }
}
