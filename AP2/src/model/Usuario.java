package model;

public abstract class Usuario {
    protected final int id;
    protected final String nome;
    protected final String email;

    public Usuario(int id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public abstract String getTipoUsuario();

    @Override
    public String toString() {
        return "ID: " + id + ", Nome: " + nome + ", Tipo: " + getTipoUsuario();
    }
}