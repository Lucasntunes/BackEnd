package TCC.BackENd.DTO;

import java.util.List;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TabelaDTO {
    private String nomeTabela;
    private int idUsuario;
    private List<Integer> antibioticos;

    // Construtor vazio (necessário para desserialização JSON)
    public TabelaDTO() {
        this.antibioticos = new ArrayList<>();
    }

    public TabelaDTO(String nomeTabela, int idUsuario, List<Integer> antibioticos) {
        this.nomeTabela = nomeTabela;
        this.idUsuario = idUsuario;
        this.antibioticos = (antibioticos == null) ? new ArrayList<>() : new ArrayList<>(antibioticos);
    }

    public String getNomeTabela() {
        return nomeTabela;
    }

    public void setNomeTabela(String nomeTabela) {
        this.nomeTabela = nomeTabela;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public List<Integer> getAntibioticos() {
        if (antibioticos == null) antibioticos = new ArrayList<>();
        return antibioticos;
    }

    public void setAntibioticos(List<Integer> antibioticos) {
        this.antibioticos = (antibioticos == null) ? new ArrayList<>() : new ArrayList<>(antibioticos);
    }

    /* --- Métodos auxiliares / aliases --- */

    // alias para compatibilidade com código que usa "ids"
    public List<Integer> getIds() {
        return getAntibioticos();
    }

    public void setIds(List<Integer> ids) {
        setAntibioticos(ids);
    }

    // adicionar um id
    public void addAntibiotico(Integer id) {
        if (id == null) return;
        getAntibioticos().add(id);
    }

    // remover um id (retorna true se removido)
    public boolean removeAntibiotico(Integer id) {
        return getAntibioticos().remove(id);
    }

    public boolean isEmpty() {
        return getAntibioticos().isEmpty();
    }

    public int size() {
        return getAntibioticos().size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TabelaDTO)) return false;
        TabelaDTO tabelaDTO = (TabelaDTO) o;
        return idUsuario == tabelaDTO.idUsuario &&
                Objects.equals(nomeTabela, tabelaDTO.nomeTabela) &&
                Objects.equals(getAntibioticos(), tabelaDTO.getAntibioticos());
    }

    @Override
    public int hashCode() {
        return Objects.hash(nomeTabela, idUsuario, getAntibioticos());
    }

    @Override
    public String toString() {
        return "TabelaDTO{" +
                "nomeTabela='" + nomeTabela + '\'' +
                ", idUsuario=" + idUsuario +
                ", antibioticos=" + getAntibioticos() +
                '}';
    }
}

