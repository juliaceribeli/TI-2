package app;

import static spark.Spark.*;
import service.LivroService;

public class Aplicacao {
    
    private static LivroService livroService = new LivroService();
    
    public static void main(String[] args) {
        // Configure a porta
        port(8080);
        
        // Configure arquivos estáticos
        staticFiles.location("/public");
        
        // Rotas da aplicação
        post("/livro/insert", (request, response) -> livroService.insert(request, response));
        get("/livro/:id", (request, response) -> livroService.get(request, response));
        get("/livro/list/:orderby", (request, response) -> livroService.getAll(request, response));
        get("/livro/update/:id", (request, response) -> livroService.getToUpdate(request, response));
        post("/livro/update/:id", (request, response) -> livroService.update(request, response));
        get("/livro/delete/:id", (request, response) -> livroService.delete(request, response));
        
        // Rota raiz - redireciona para lista
        get("/", (request, response) -> {
            response.redirect("/livro/list/2");
            return null;
        });
        
        System.out.println("=== SISTEMA DE BIBLIOTECA INICIADO ===");
        System.out.println("Acesse: http://localhost:8080/livro/list/2");
        System.out.println("=======================================");
    }
}