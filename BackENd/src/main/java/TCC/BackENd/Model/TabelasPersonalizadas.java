package TCC.BackENd.Model;

import jakarta.persistence.*;

@Entity
@Table(name="TabelaPersonalizada")
public class TabelasPersonalizadas {

    public int getId() {
        return id;
    }

    public int getIdAntibiotico() {
        return idAntibiotico;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    public String getNomeTabela() {
        return nomeTabela;
    }

    @Column(nullable = false,name = "idAntibiotico")
    private int idAntibiotico;

    @Column(nullable = false,name="idUsuario")
    private int idUsuario;

    @Column(nullable = false,name="nomeTabela")
    private String nomeTabela;

    public void setId(int id) {
        this.id = id;
    }

    public void setIdAntibiotico(int idAntibiotico) {
        this.idAntibiotico = idAntibiotico;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setNomeTabela(String nomeTabela) {
        this.nomeTabela = nomeTabela;
    }

}
