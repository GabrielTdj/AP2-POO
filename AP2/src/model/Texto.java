package model;

import java.util.List;

public class Texto {
    private final int id;
    private final String conteudo;
    private final Idioma idiomaOrigem;
    private final Idioma idiomaDestino;
    private List<Traducao> traducoes;

    public Texto(int id, String conteudo, Idioma idiomaOrigem, Idioma idiomaDestino) {
        this.id = id;
        this.conteudo = conteudo;
        this.idiomaOrigem = idiomaOrigem;
        this.idiomaDestino = idiomaDestino;
    }

    public Texto(String conteudo, Idioma idiomaOrigem, Idioma idiomaDestino) {
        this.id = 0; // ID será gerado pelo banco
        this.conteudo = conteudo;
        this.idiomaOrigem = idiomaOrigem;
        this.idiomaDestino = idiomaDestino;
    }

    public int getId() { return id; }
    public String getConteudo() { return conteudo; }
    public Idioma getIdiomaOrigem() { return idiomaOrigem; }
    public Idioma getIdiomaDestino() { return idiomaDestino; }
    public List<Traducao> getTraducoes() { return traducoes; }
    public void setTraducoes(List<Traducao> traducoes) { this.traducoes = traducoes; }

    @Override
    public String toString() {
        return "ID: " + id + " | De: " + idiomaOrigem.getNome() + " | Para: " + idiomaDestino.getNome() + "\nTexto Original: '" + conteudo + "'";
    }
}