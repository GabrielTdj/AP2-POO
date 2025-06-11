package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDB {
    private static final String URL = "jdbc:mysql://localhost:3306/traducao_colaborativa?useTimezone=true&serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String SENHA = "123456789";

    public static Connection recuperaConexao() {
        try {

            return DriverManager.getConnection(URL, USUARIO, SENHA);

        } catch (SQLException e) {

            throw new RuntimeException("Erro ao conectar ao banco de dados.", e);
        }
    }
}