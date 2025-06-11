package model;

public class Colaborador extends Usuario {
    public Colaborador(int id, String nome, String email) {
        super(id, nome, email);
    }

    @Override
    public String getTipoUsuario() {
        return "Colaborador";
    }
}