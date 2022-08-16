package br.com.brunolutterbach.alurachallengebackend.enums;

public enum Categoria {

    ALIMENTACAO,
    SAUDE,
    MORADIA,
    TRANSPORTE,
    EDUCACAO,
    LAZER,
    IMPREVISTOS,
    OUTROS;


    public String getNome() {
        return this.name();
    }
}
