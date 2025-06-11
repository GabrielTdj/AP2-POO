package dao;

import model.Colaborador;
import model.Moderador;
import model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UsuarioDAO implements BaseDAO<Usuario> {
    private final Connection connection;

    public UsuarioDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Usuario buscarPorId(int id) throws SQLException {
        String sql = "SELECT id, nome, email, tipo FROM Usuario WHERE id = ?";
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, id);
            try (ResultSet rst = pstm.executeQuery()) {
                if (rst.next()) {
                    String nome = rst.getString("nome");
                    String email = rst.getString("email");
                    String tipo = rst.getString("tipo");

                    if ("Colaborador".equals(tipo)) {
                        return new Colaborador(id, nome, email);
                    } else if ("Moderador".equals(tipo)) {
                        return new Moderador(id, nome, email);
                    }
                }
            }
        }
        return null;
    }

    @Override
    public void salvar(Usuario objeto) throws SQLException { throw new UnsupportedOperationException("Não implementado."); }
    @Override
    public List<Usuario> listarTodos() throws SQLException { throw new UnsupportedOperationException("Não implementado."); }
}