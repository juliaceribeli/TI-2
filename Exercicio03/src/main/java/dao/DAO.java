package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAO {
    protected Connection conexao;
    
    public DAO() {
        conexao = null;
    }
    
    public boolean conectar() {
        String url = "jdbc:postgresql://localhost:5432/biblioteca";
        String username = "postgres";
        String password = "batata123";
        boolean status = false;
        
        try {
            conexao = DriverManager.getConnection(url, username, password);
            status = (conexao != null);
            System.out.println("Conexão efetuada com PostgreSQL!");
        } catch (SQLException e) {
            System.err.println("Erro na conexão: " + e.getMessage());
        }
        return status;
    }
    
    public boolean close() {
        boolean status = false;
        try {
            if (conexao != null) {
                conexao.close();
                status = true;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar conexão: " + e.getMessage());
        }
        return status;
    }
}