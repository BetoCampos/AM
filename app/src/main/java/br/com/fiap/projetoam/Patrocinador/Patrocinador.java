package br.com.fiap.projetoam.Patrocinador;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import br.com.fiap.projetoam.DateConverter;

@Entity
public class Patrocinador implements Serializable {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "nome")
    private String nome;

    @ColumnInfo(name = "data")
    //@TypeConverters({DateConverter.class})
    //private Date data;
    private String data;

    @ColumnInfo(name = "ramo")
    private String ramo;

    @ColumnInfo(name = "valor")
    private Double valor;

    @ColumnInfo(name = "porte")
    private String porte;

    @NonNull
    @ColumnInfo(name = "senha")
    private String senha;

    public Patrocinador(@NonNull String email,@NonNull String senha, String nome, String data, String ramo, Double valor, String porte) {
        this.email = email;
        this.senha = senha;
        this.nome = nome;
        this.data = data;
        this.ramo = ramo;
        this.valor = valor;
        this.porte = porte;
    }

    @NonNull
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getRamo() {
        return ramo;
    }

    public void setRamo(String ramo) {
        this.ramo = ramo;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getPorte() {
        return porte;
    }

    public void setPorte(String porte) {
        this.porte = porte;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patrocinador patrocinador = (Patrocinador) o;
        return getEmail() == patrocinador.email;
    }

    @Override
    public int hashCode() {

        return Objects.hash(getEmail());
    }

    @Override
    public String toString() {
        return "Patrocinador{" +
                "nome='" + getNome() + '\'' +
                ", data='" + getData() + '\'' +
                '}';
    }
}
