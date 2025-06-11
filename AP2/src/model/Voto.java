package model;

public class Voto {

    private final int id;

    private final Usuario usuario;

    private final Traducao traducao;

    public Voto(Usuario usuario, Traducao traducao) {
        this.id = 0;
        this.usuario = usuario;
        this.traducao = traducao;
    }

    public int getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Traducao getTraducao() {
        return traducao;
    }
}
