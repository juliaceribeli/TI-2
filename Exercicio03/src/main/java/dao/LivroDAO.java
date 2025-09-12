package dao;

import model.Livro;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class LivroDAO extends DAO {
    
    public LivroDAO() {
        super();
        conectar();
    }
    
    public void finalize() {
        close();
    }
    
    public boolean insert(Livro livro) {
        boolean status = false;
        try {
            String sql = "INSERT INTO livro (titulo, autor, isbn, preco, paginas, data_publicacao, data_cadastro) "
                       + "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setString(1, livro.getTitulo());
            st.setString(2, livro.getAutor());
            st.setString(3, livro.getIsbn());
            st.setFloat(4, livro.getPreco());
            st.setInt(5, livro.getPaginas());
            st.setDate(6, Date.valueOf(livro.getDataPublicacao()));
            st.setTimestamp(7, Timestamp.valueOf(livro.getDataCadastro()));
            st.executeUpdate();
            st.close();
            status = true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return status;
    }
    
    public Livro get(int id) {
        Livro livro = null;
        
        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sql = "SELECT * FROM livro WHERE id=" + id;
            ResultSet rs = st.executeQuery(sql);
            if(rs.next()) {
                livro = new Livro(rs.getInt("id"), 
                                rs.getString("titulo"), 
                                rs.getString("autor"),
                                rs.getString("isbn"),
                                (float)rs.getDouble("preco"),
                                rs.getInt("paginas"),
                                rs.getDate("data_publicacao").toLocalDate(),
                                rs.getTimestamp("data_cadastro").toLocalDateTime());
            }
            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return livro;
    }
    
    public List<Livro> get() {
        return get("");
    }
    
    public List<Livro> getOrderByID() {
        return get("id");
    }
    
    public List<Livro> getOrderByTitulo() {
        return get("titulo");
    }
    
    public List<Livro> getOrderByAutor() {
        return get("autor");
    }
    
    public List<Livro> getOrderByPreco() {
        return get("preco");
    }
    
    private List<Livro> get(String orderBy) {
        List<Livro> livros = new ArrayList<Livro>();
        
        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sql = "SELECT * FROM livro" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()) {
                Livro l = new Livro(rs.getInt("id"), 
                                   rs.getString("titulo"), 
                                   rs.getString("autor"),
                                   rs.getString("isbn"),
                                   (float)rs.getDouble("preco"),
                                   rs.getInt("paginas"),
                                   rs.getDate("data_publicacao").toLocalDate(),
                                   rs.getTimestamp("data_cadastro").toLocalDateTime());
                livros.add(l);
            }
            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return livros;
    }
    
    public boolean update(Livro livro) {
        boolean status = false;
        try {
            String sql = "UPDATE livro SET titulo = ?, autor = ?, isbn = ?, " +
                        "preco = ?, paginas = ?, data_publicacao = ? WHERE id = ?";
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setString(1, livro.getTitulo());
            st.setString(2, livro.getAutor());
            st.setString(3, livro.getIsbn());
            st.setFloat(4, livro.getPreco());
            st.setInt(5, livro.getPaginas());
            st.setDate(6, Date.valueOf(livro.getDataPublicacao()));
            st.setInt(7, livro.getId());
            st.executeUpdate();
            st.close();
            status = true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return status;
    }
    
    public boolean delete(int id) {
        boolean status = false;
        try {
            Statement st = conexao.createStatement();
            st.executeUpdate("DELETE FROM livro WHERE id = " + id);
            st.close();
            status = true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return status;
    }
}