package TCC.BackENd.repository;

import TCC.BackENd.Model.Antibiotico;
import TCC.BackENd.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario,Integer> {

    // Buscar usuários cujo username contém uma string
    List<Usuario> findByUsername(String username);

    // Nunca use like em senha. Se quiser verificar senha, compare hash exato:
    List<Usuario> findBySenha(String senhaHash);

    // Versão Pro (boolean)
    List<Usuario> findByPagoVersaoPro(boolean pagoVersaoPro);

    // Telefone contendo
    List<Usuario> findByTelefone(String telefone);

    // Email exato (recomendado)
    List<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);


}
