package dao;

import model.Texto;
import model.Traducao;
import model.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TraducaoDAO implements BaseDAO<Traducao> {
    private final Connection connection;

    public TraducaoDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void salvar(Traducao traducao) throws SQLException {
        String sql = "INSERT INTO Traducoes (texto_id, usuario_id, conteudo) VALUES (?, ?, ?)";
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, traducao.getTexto().getId());
            pstm.setInt(2, traducao.getAutor().getId());
            pstm.setString(3, traducao.getConteudo());
            pstm.execute();
            System.out.println("Nova tradução salva com sucesso!");
        }
    }

    public void adicionarVoto(int usuarioId, int traducaoId) throws SQLException {
        String checkSql = "SELECT COUNT(*) FROM Voto WHERE usuario_id = ? AND traducao_id = ?";
        try (PreparedStatement checkPstm = connection.prepareStatement(checkSql)) {
            checkPstm.setInt(1, usuarioId);
            checkPstm.setInt(2, traducaoId);
            try (ResultSet rs = checkPstm.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    System.out.println("AVISO: Você já votou nesta tradução.");
                    return;
                }
            }
        }

        String sql = "INSERT INTO Voto (usuario_id, traducao_id) VALUES (?, ?)";
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, usuarioId);
            pstm.setInt(2, traducaoId);
            pstm.execute();
            System.out.println("Voto registrado com sucesso!");
        }
    }

    public List<Traducao> buscarPorTexto(Texto texto) throws SQLException {
        List<Traducao> traducoes = new ArrayList<>();
        UsuarioDAO usuarioDAO = new UsuarioDAO(this.connection);
        String sql = "SELECT t.id, t.conteudo, t.usuario_id, (SELECT COUNT(*) FROM Voto v WHERE v.traducao_id = t.id) as total_votos FROM Traducoes t WHERE t.texto_id = ?";
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, texto.getId());
            pstm.execute();
            try (ResultSet rst = pstm.getResultSet()) {
                while (rst.next()) {
                    Usuario autor = usuarioDAO.buscarPorId(rst.getInt("usuario_id"));
                    Traducao traducao = new Traducao(rst.getInt("id"), rst.getString("conteudo"), autor, texto);
                    traducao.setVotos(rst.getInt("total_votos"));
                    traducoes.add(traducao);
                }
            }
        }
        return traducoes;
    }

    @Override
    public Traducao buscarPorId(int id) throws SQLException { throw new UnsupportedOperationException("Não implementado."); }
    @Override
    public List<Traducao> listarTodos() throws SQLException { throw new UnsupportedOperationException("Não implementado."); }
}