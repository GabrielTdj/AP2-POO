package model;

public class Traducao {
    private final int id;
    private final String conteudo;
    private final Usuario autor;
    private final Texto texto;
    private int votos;

    public Traducao(int id, String conteudo, Usuario autor, Texto texto) {
        this.id = id;
        this.conteudo = conteudo;
        this.autor = autor;
        this.texto = texto;
    }

    public Traducao(String conteudo, Usuario autor, Texto texto) {
        this.id = 0; // ID será gerado pelo banco
        this.conteudo = conteudo;
        this.autor = autor;
        this.texto = texto;
    }

    public int getId() { return id; }
    public String getConteudo() { return conteudo; }
    public Usuario getAutor() { return autor; }
    public Texto getTexto() { return texto; }
    public int getVotos() { return votos; }
    public void setVotos(int votos) { this.votos = votos; }

    @Override
    public String toString() {
        return "   - ID Tradução: " + id + " | Votos: " + votos + " | Autor: " + autor.getNome() + "\n     Sugestão: '" + conteudo + "'";
    }
}