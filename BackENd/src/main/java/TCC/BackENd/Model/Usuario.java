package TCC.BackENd.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "Usuario")
public class Usuario {
    public int getId() {
        return id;
    }

    public String getNome() {
        return username;
    }

    public String getSenha() {
        return senha;
    }

    public boolean isPagoVersaoPro() {
        return pagoVersaoPro;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private int id;

    @Column(nullable = false,name= "username")
    private String username;

    @Column(nullable = false,name="senha")
    private String senha;

    @Column(nullable = false,name="pagoVersaoPro")
    private boolean pagoVersaoPro;

    @Column(nullable = true,name="telefone")
    private String telefone;

    @Column(nullable = false,name="email",unique = true)
    private String email;

    @Column(nullable = false,name="userId",unique = true)
    private int userId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.username = nome;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setPagoVersaoPro(boolean pagoVersaoPro) {
        this.pagoVersaoPro = pagoVersaoPro;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @PrePersist
    @PreUpdate
    public void syncIds() {
        this.userId = this.id;
    }
}
