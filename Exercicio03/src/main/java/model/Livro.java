package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Livro {
    private int id;
    private String titulo;
    private String autor;
    private String isbn;
    private float preco;
    private int paginas;
    private LocalDate dataPublicacao;
    private LocalDateTime dataCadastro;
    
    public Livro() {
        id = -1;
        titulo = "";
        autor = "";
        isbn = "";
        preco = 0.00F;
        paginas = 0;
        dataPublicacao = LocalDate.now().minusYears(1);
        dataCadastro = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    }

    public Livro(int id, String titulo, String autor, String isbn, 
                 float preco, int paginas, LocalDate dataPublicacao, 
                 LocalDateTime dataCadastro) {
        setId(id);
        setTitulo(titulo);
        setAutor(autor);
        setIsbn(isbn);
        setPreco(preco);
        setPaginas(paginas);
        setDataPublicacao(dataPublicacao);
        setDataCadastro(dataCadastro);
    }

    // Getters e Setters
    public int getId() { 
        return id; 
    }
    
    public void setId(int id) { 
        this.id = id; 
    }
    
    public String getTitulo() { 
        return titulo; 
    }
    
    public void setTitulo(String titulo) { 
        this.titulo = titulo != null ? titulo.trim() : ""; 
    }
    
    public String getAutor() { 
        return autor; 
    }
    
    public void setAutor(String autor) { 
        this.autor = autor != null ? autor.trim() : ""; 
    }
    
    public String getIsbn() { 
        return isbn; 
    }
    
    public void setIsbn(String isbn) { 
        this.isbn = isbn != null ? isbn.trim() : ""; 
    }
    
    public float getPreco() { 
        return preco; 
    }
    
    public void setPreco(float preco) { 
        this.preco = preco >= 0 ? preco : 0; 
    }
    
    public int getPaginas() { 
        return paginas; 
    }
    
    public void setPaginas(int paginas) { 
        this.paginas = paginas > 0 ? paginas : 0; 
    }
    
    public LocalDate getDataPublicacao() { 
        return dataPublicacao; 
    }
    
    public void setDataPublicacao(LocalDate dataPublicacao) {
        LocalDate hoje = LocalDate.now();
        if (dataPublicacao != null && !dataPublicacao.isAfter(hoje)) {
            this.dataPublicacao = dataPublicacao;
        } else {
            this.dataPublicacao = LocalDate.now().minusYears(1);
        }
    }
    
    public LocalDateTime getDataCadastro() { 
        return dataCadastro; 
    }
    
    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro != null ? dataCadastro : LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Livro: " + titulo + " - Autor: " + autor + " - ISBN: " + isbn + 
               " - Preço: R$" + preco + " - Páginas: " + paginas + 
               " - Publicação: " + dataPublicacao;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Livro)) {
            return false;
        }
        return (this.getId() == ((Livro) obj).getId());
    }
}