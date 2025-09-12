package app;

import model.Livro;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TesteLivro {
    public static void main(String[] args) {
        System.out.println("=== TESTE DA CLASSE LIVRO ===");
        
        try {
            // Teste 1: Construtor vazio
            Livro livro1 = new Livro();
            System.out.println("✅ Construtor vazio OK");
            System.out.println("Livro vazio: " + livro1.toString());
            
            // Teste 2: Construtor com parâmetros
            Livro livro2 = new Livro(1, "Dom Casmurro", "Machado de Assis", 
                                   "978-85-254-0001-0", 29.90f, 256, 
                                   LocalDate.of(1899, 12, 1), 
                                   LocalDateTime.now());
            System.out.println("✅ Construtor com parâmetros OK");
            System.out.println("Livro completo: " + livro2.toString());
            
            // Teste 3: Getters e Setters
            livro1.setTitulo("O Cortiço");
            livro1.setAutor("Aluísio Azevedo");
            System.out.println("✅ Setters OK");
            System.out.println("Título: " + livro1.getTitulo());
            System.out.println("Autor: " + livro1.getAutor());
            
            System.out.println("\n🎉 TODOS OS TESTES PASSARAM!");
            System.out.println("A classe Livro está funcionando corretamente.");
            
        } catch (Exception e) {
            System.err.println("❌ ERRO NO TESTE: " + e.getMessage());
            e.printStackTrace();
        }
    }
}