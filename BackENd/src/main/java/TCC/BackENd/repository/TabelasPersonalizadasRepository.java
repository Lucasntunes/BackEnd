package TCC.BackENd.repository;

import TCC.BackENd.Model.Antibiotico;
import TCC.BackENd.Model.TabelasPersonalizadas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TabelasPersonalizadasRepository extends JpaRepository<TabelasPersonalizadas,Integer> {
    @Query(value = "select a from TabelasPersonalizadas a where a.idUsuario = ?1")
    List<TabelasPersonalizadas> findByIdUsuario (int idUsuario);

    @Query(value = "select a from TabelasPersonalizadas a where a.idUsuario = ?1 and a.nomeTabela like %?2%")
    List<TabelasPersonalizadas> findTabela(int id,String nomeTabela);

    //boolean existsBynomeTabela(String nomeTabela,int id);

}
