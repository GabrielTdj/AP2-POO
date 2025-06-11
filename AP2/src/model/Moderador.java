package model;

public class Moderador extends Usuario {
    public Moderador(int id, String nome, String email) {
        super(id, nome, email);
    }

    @Override
    public String getTipoUsuario() {
        return "Moderador";
    }
}