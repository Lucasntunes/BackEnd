package TCC.BackENd.repository;

import TCC.BackENd.Model.Antibiotico;
import TCC.BackENd.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario,Integer> {

    @Query(value = "select a from Usuario a where a.username like %?1%")
    List<Usuario> findByUsername (String username);

    @Query(value = "select a from Usuario a where a.senha like %?1%")
    List<Usuario> findBySenha (String senha);

    @Query(value = "select a from Usuario a where a.pagoVersaoPro = ?1")
    List<Usuario> findByVersao (boolean pagoVersaoPro);

    @Query(value = "select a from Usuario a where a.telefone like %?1%")
    List<Usuario> findBytelefone (String telefone);

    @Query(value = "select a from Usuario a where a.email like %?1%")
    List<Usuario> findByemail (String email);

    boolean existsByEmail(String email);


}
