package TCC.BackENd.repository;

import TCC.BackENd.Model.Antibiotico;
import TCC.BackENd.Model.TabelasPersonalizadas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TabelasPersonalizadasRepository extends JpaRepository<TabelasPersonalizadas,Integer> {
    // Busca todas as tabelas de um usuário
    List<TabelasPersonalizadas> findByIdUsuario(int idUsuario);

    // Busca tabelas de um usuário com nome exato
    List<TabelasPersonalizadas> findByIdUsuarioAndNomeTabela(int idUsuario, String nomeTabela);

    // Verifica se já existe tabela com nome exato para o usuário
    boolean existsByIdUsuarioAndNomeTabela(int idUsuario, String nomeTabela);

    // Deleta todas as tabelas de um usuário
    @Transactional
    void deleteByIdUsuario(int idUsuario);
}
