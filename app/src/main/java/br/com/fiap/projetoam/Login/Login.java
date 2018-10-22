package br.com.fiap.projetoam.Login;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.widget.Spinner;

import java.util.Objects;

import br.com.fiap.projetoam.Clube.Clube;

@Entity
public class Login {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "login")
    private String login;


    @ColumnInfo(name = "senha")
    private String senha;


    private String tipo;

    public Login(String login, String senha,String tipo) {
        this.login = login;
        this.senha = senha;
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Login login = (Login) o;
        return getLogin() == login.login;
    }

    @Override
    public int hashCode() {

        return Objects.hash(getLogin());
    }

    @Override
    public String toString() {
        return "Login{" +
                "login='" + getLogin() + '\'' +
                ", senha='" + getSenha() + '\'' +
                '}';
    }
}
