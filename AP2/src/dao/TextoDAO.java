package dao;

import model.Idioma;
import model.Texto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TextoDAO implements BaseDAO<Texto> {
    private final Connection connection;

    public TextoDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Texto> listarTextosComTraducoes() throws SQLException {
        List<Texto> textos = new ArrayList<>();

        String sql = "SELECT DISTINCT t.id, t.conteudo, " +
                "io.id as id_origem, io.nome as nome_origem, io.codigo as codigo_origem, " +
                "id.id as id_destino, id.nome as nome_destino, id.codigo as codigo_destino " +
                "FROM Texto t " +
                "INNER JOIN Traducoes tr ON t.id = tr.texto_id " +
                "INNER JOIN Idioma io ON t.idioma_origem_id = io.id " +
                "INNER JOIN Idioma id ON t.idioma_destino_id = id.id";

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.execute();
            try (ResultSet rst = pstm.getResultSet()) {
                while (rst.next()) {
                    Idioma idiomaOrigem = new Idioma(rst.getInt("id_origem"), rst.getString("nome_origem"), rst.getString("codigo_origem"));
                    Idioma idiomaDestino = new Idioma(rst.getInt("id_destino"), rst.getString("nome_destino"), rst.getString("codigo_destino"));
                    Texto texto = new Texto(rst.getInt("id"), rst.getString("conteudo"), idiomaOrigem, idiomaDestino);
                    textos.add(texto);
                }
            }
        }
        return textos;
    }


    @Override
    public void salvar(Texto texto) throws SQLException {
        String sql = "INSERT INTO Texto (conteudo, idioma_origem_id, idioma_destino_id) VALUES (?, ?, ?)";
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, texto.getConteudo());
            pstm.setInt(2, texto.getIdiomaOrigem().getId());
            pstm.setInt(3, texto.getIdiomaDestino().getId());
            pstm.execute();
            System.out.println("Novo texto adicionado com sucesso!");
        }
    }

    @Override
    public List<Texto> listarTodos() throws SQLException {
        List<Texto> textos = new ArrayList<>();
        String sql = "SELECT t.id, t.conteudo, io.id as id_origem, io.nome as nome_origem, io.codigo as codigo_origem, id.id as id_destino, id.nome as nome_destino, id.codigo as codigo_destino FROM Texto t INNER JOIN Idioma io ON t.idioma_origem_id = io.id INNER JOIN Idioma id ON t.idioma_destino_id = id.id";
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.execute();
            try (ResultSet rst = pstm.getResultSet()) {
                while (rst.next()) {
                    Idioma idiomaOrigem = new Idioma(rst.getInt("id_origem"), rst.getString("nome_origem"), rst.getString("codigo_origem"));
                    Idioma idiomaDestino = new Idioma(rst.getInt("id_destino"), rst.getString("nome_destino"), rst.getString("codigo_destino"));
                    Texto texto = new Texto(rst.getInt("id"), rst.getString("conteudo"), idiomaOrigem, idiomaDestino);
                    textos.add(texto);
                }
            }
        }
        return textos;
    }

    @Override
    public Texto buscarPorId(int id) throws SQLException {
        String sql = "SELECT t.id, t.conteudo, io.id as id_origem, io.nome as nome_origem, io.codigo as codigo_origem, id.id as id_destino, id.nome as nome_destino, id.codigo as codigo_destino FROM Texto t INNER JOIN Idioma io ON t.idioma_origem_id = io.id INNER JOIN Idioma id ON t.idioma_destino_id = id.id WHERE t.id = ?";
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, id);
            try (ResultSet rst = pstm.executeQuery()) {
                if (rst.next()) {
                    Idioma idiomaOrigem = new Idioma(rst.getInt("id_origem"), rst.getString("nome_origem"), rst.getString("codigo_origem"));
                    Idioma idiomaDestino = new Idioma(rst.getInt("id_destino"), rst.getString("nome_destino"), rst.getString("codigo_destino"));
                    return new Texto(rst.getInt("id"), rst.getString("conteudo"), idiomaOrigem, idiomaDestino);
                }
            }
        }
        return null;
    }
}
