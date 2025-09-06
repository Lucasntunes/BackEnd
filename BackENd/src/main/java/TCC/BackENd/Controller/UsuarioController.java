package TCC.BackENd.Controller;


import TCC.BackENd.Model.Usuario;
import TCC.BackENd.repository.TabelasPersonalizadasRepository;
import TCC.BackENd.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/apiUsuarios")
public class UsuarioController {
    @Autowired
    UsuarioRepository usuario;

    @GetMapping(value = "/Usuarios")
    public List<Usuario> listarUsuarios()
    {
        return usuario.findAll();
    }

    @GetMapping(value = "/Usuarios/{id}")
    public Optional<Usuario> ListarPorId(@PathVariable(value = "id") int id)
    {
        return usuario.findById(id);
    }

    @GetMapping(value = "/UsuariosNome/{nome}")
    public List<Usuario> listarPorUsername(@PathVariable(value = "nome") String nome)
    {
        return usuario.findByUsername(nome);
    }

    @GetMapping(value = "/UsuariosSenha/{senha}")
    public List<Usuario> listarPorSenha(@PathVariable(value = "senha") String senha)
    {
        return usuario.findBySenha(senha);
    }

    @GetMapping(value = "/UsuariosVersao/{versao}")
    public List<Usuario> listarPorVersao(@PathVariable(value = "versao") boolean versao)
    {
        return usuario.findByPagoVersaoPro(versao);
    }

    @GetMapping(value = "/UsuariosTelefone/{telefone}")
    public List<Usuario> listarPorTelefone(@PathVariable(value = "telefone") String telefone)
    {
        return usuario.findByTelefone(telefone);
    }

    @GetMapping(value = "/UsuarioEmail/{email}")
    public List<Usuario> listarPorEmail(@PathVariable(value = "email") String email)
    {
        return usuario.findByEmail(email);
    }

    @PostMapping("/cadastrarUsuario")
    public ResponseEntity<?> criarUsuario(@RequestBody Usuario novoUsuario) {
        // sempre inicia como false
        novoUsuario.setPagoVersaoPro(false);

        if (usuario.existsByEmail(novoUsuario.getEmail())) {
            return ResponseEntity.badRequest().body("Email já cadastrado!");
        }

        Usuario usuarioSalvo = usuario.save(novoUsuario);
        return ResponseEntity.ok(usuarioSalvo);
    }


    @PutMapping("/atualizarNome/{id}")
    public ResponseEntity<?> atualizarNome(
            @PathVariable("id") int id,
            @RequestBody String novoNome) {

        Optional<Usuario> usuarioExistente = usuario.findById(id);
        if (!usuarioExistente.isPresent()) {
            return ResponseEntity.status(404).body("Usuário não encontrado!");
        }

        Usuario usuarioAtual = usuarioExistente.get();
        if (novoNome == null || novoNome.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Nome inválido!");
        }

        usuarioAtual.setNome(novoNome.trim());
        Usuario usuarioSalvo = usuario.save(usuarioAtual);
        return ResponseEntity.ok(usuarioSalvo);
    }

    // Atualizar TELEFONE
    @PutMapping("/atualizarTelefone/{id}")
    public ResponseEntity<?> atualizarTelefone(
            @PathVariable("id") int id,
            @RequestBody String novoTelefone) {

        Optional<Usuario> usuarioExistente = usuario.findById(id);
        if (!usuarioExistente.isPresent()) {
            return ResponseEntity.status(404).body("Usuário não encontrado!");
        }

        Usuario usuarioAtual = usuarioExistente.get();
        if (novoTelefone == null || novoTelefone.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Telefone inválido!");
        }

        usuarioAtual.setTelefone(novoTelefone.trim());
        Usuario usuarioSalvo = usuario.save(usuarioAtual);
        return ResponseEntity.ok(usuarioSalvo);
    }

    // Atualizar EMAIL
    @PutMapping("/atualizarEmail/{id}")
    public ResponseEntity<?> atualizarEmail(
            @PathVariable("id") int id,
            @RequestBody String novoEmail) {

        Optional<Usuario> usuarioExistente = usuario.findById(id);
        if (!usuarioExistente.isPresent()) {
            return ResponseEntity.status(404).body("Usuário não encontrado!");
        }

        Usuario usuarioAtual = usuarioExistente.get();

        if (novoEmail == null || novoEmail.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Email inválido!");
        }

        // Verifica se email já está em uso por outro usuário
        if (usuario.existsByEmail(novoEmail) && !novoEmail.equals(usuarioAtual.getEmail())) {
            return ResponseEntity.badRequest().body("Email já cadastrado para outro usuário!");
        }

        usuarioAtual.setEmail(novoEmail.trim());
        Usuario usuarioSalvo = usuario.save(usuarioAtual);
        return ResponseEntity.ok(usuarioSalvo);
    }

    // Atualizar SENHA (hash já enviado pelo cliente)
    @PutMapping("/atualizarSenha/{id}")
    public ResponseEntity<?> atualizarSenha(
            @PathVariable("id") int id,
            @RequestBody String novaSenhaHash) {

        Optional<Usuario> usuarioExistente = usuario.findById(id);
        if (!usuarioExistente.isPresent()) {
            return ResponseEntity.status(404).body("Usuário não encontrado!");
        }

        if (novaSenhaHash == null || novaSenhaHash.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Senha inválida!");
        }

        Usuario usuarioAtual = usuarioExistente.get();
        usuarioAtual.setSenha(novaSenhaHash.trim());

        usuario.save(usuarioAtual);

        return ResponseEntity.ok("Senha atualizada com sucesso!");
    }

    @Autowired
    private TabelasPersonalizadasRepository tab; // importa esse repo

    // Deleta usuário + tabelas personalizadas
    @Transactional
    @DeleteMapping("/deletarUsuario/{id}")
    public ResponseEntity<?> deletarUsuario(@PathVariable("id") int id) {
        Optional<Usuario> usuarioExistente = usuario.findById(id);
        if (!usuarioExistente.isPresent()) {
            return ResponseEntity.status(404).body("Usuário não encontrado!");
        }

        // deleta tabelas do usuário
        tab.deleteByIdUsuario(id);

        // deleta o usuário
        usuario.deleteById(id);

        return ResponseEntity.ok("Usuário e tabelas associadas deletados com sucesso.");
    }


}
