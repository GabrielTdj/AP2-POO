package model;

public class Idioma {
    private final int id;
    private final String nome;
    private final String codigo;

    public Idioma(int id, String nome, String codigo) {
        this.id = id;
        this.nome = nome;
        this.codigo = codigo;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getCodigo() { return codigo; }

    @Override
    public String toString() {
        return getNome() + " (" + getCodigo().toUpperCase() + ")";
    }
}