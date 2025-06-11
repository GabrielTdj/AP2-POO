package dao;
import java.sql.SQLException;
import java.util.List;

public interface BaseDAO<T> {
    void salvar(T objeto) throws SQLException;
    T buscarPorId(int id) throws SQLException;
    List<T> listarTodos() throws SQLException;
}