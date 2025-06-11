package dao;

import model.Idioma;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IdiomaDAO implements BaseDAO<Idioma> {
    private final Connection connection;

    public IdiomaDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Idioma> listarTodos() throws SQLException {
        List<Idioma> idiomas = new ArrayList<>();
        String sql = "SELECT id, nome, codigo FROM Idioma";
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.execute();
            try (ResultSet rst = pstm.getResultSet()) {
                while (rst.next()) {
                    Idioma idioma = new Idioma(rst.getInt("id"), rst.getString("nome"), rst.getString("codigo"));
                    idiomas.add(idioma);
                }
            }
        }
        return idiomas;
    }

    @Override
    public void salvar(Idioma objeto) throws SQLException { throw new UnsupportedOperationException("Não implementado."); }
    @Override
    public Idioma buscarPorId(int id) throws SQLException { throw new UnsupportedOperationException("Não implementado."); }
}