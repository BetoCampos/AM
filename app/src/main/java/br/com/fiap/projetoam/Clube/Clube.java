package br.com.fiap.projetoam.Clube;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.media.Image;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Objects;

@Entity
public class Clube implements Serializable {

    //talvez falte ano de fundacao

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "email")
    private String email;

    @NonNull
    @ColumnInfo(name = "senha")
    private String senha;


    @ColumnInfo(name = "nome")
    private String nome;

    @ColumnInfo(name = "divisão")
    private String divisao;

    @ColumnInfo(name = "estadio")
    //@TypeConverters({br.com.fiap.projetoam.DateConverter.class})
    private String estadio;

    @ColumnInfo(name = "escudo")
    private String escudo;

    public Clube(@NonNull String email, @NonNull String senha, String nome, String divisao, String estadio, String escudo) {
        this.email = email;
        this.senha = senha;
        this.nome = nome;
        this.divisao = divisao;
        this.estadio = estadio;
        this.escudo = escudo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    @NonNull
    public String getSenha() {
        return senha;
    }

    public void setSenha(@NonNull String senha) {
        this.senha = senha;
    }


    public String getDivisao() {
        return divisao;
    }

    public void setDivisao(String divisao) {
        this.divisao = divisao;
    }

    public String getEstadio() {
        return estadio;
    }

    public void setEstadio(String estadio) {
        this.estadio = estadio;
    }

    public String getEscudo() {
        return escudo;
    }

    public void setEscudo(String escudo) {
        this.escudo = escudo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clube clube = (Clube) o;
        return getEmail() == clube.email;
    }

    @Override
    public int hashCode() {

        return Objects.hash(getEmail());
    }

    @Override
    public String toString() {
        return "Clube{" +
                "nome='" + getNome() + '\'' +
                ", estadio='" + getEstadio() + '\'' +
                '}';
    }
}
