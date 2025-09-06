package TCC.BackENd.Controller;

import TCC.BackENd.DTO.TabelaDTO;
import TCC.BackENd.Model.TabelasPersonalizadas;
import TCC.BackENd.Model.Usuario;
import TCC.BackENd.repository.TabelasPersonalizadasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/TabelasPersonalizadas")
public class TabelasPersonalizadasController {
    @Autowired
    TabelasPersonalizadasRepository tab;

    @GetMapping(value = "/{id}")
    public List<TabelaDTO> listarTabelaPorId(@PathVariable int id) {
        List<TabelasPersonalizadas> linhas = tab.findByIdUsuario(id);

        // Agrupar por nomeTabela
        Map<String, List<TabelasPersonalizadas>> agrupado =
                linhas.stream().collect(Collectors.groupingBy(TabelasPersonalizadas::getNomeTabela));

        // Transformar no DTO
        return agrupado.entrySet().stream()
                .map(entry -> new TabelaDTO(
                        entry.getKey(),
                        entry.getValue().get(0).getIdUsuario(),
                        entry.getValue().stream().map(TabelasPersonalizadas::getIdAntibiotico).toList()
                ))
                .toList();
    }


    @GetMapping(value = "/{id}/{nomeTabela}")
    public TabelaDTO TabelaPersonalizada(
            @PathVariable int id,
            @PathVariable String nomeTabela)
    {
        List<TabelasPersonalizadas> linhas = tab.findByIdUsuarioAndNomeTabela(id, nomeTabela);

        if (linhas.isEmpty()) {
            return null; // ou lançar exceção 404 se preferir
        }

        return new TabelaDTO(
                nomeTabela,
                linhas.get(0).getIdUsuario(),
                linhas.stream()
                        .map(TabelasPersonalizadas::getIdAntibiotico)
                        .toList()
        );
    }

    // Criar nova tabela a partir de um DTO contendo nomeTabela, idUsuario e lista de ids (idAntibiotico)
    @PostMapping("/criar")
    public ResponseEntity<String> criarTabela(@RequestBody TabelaDTO req) {
        if (req == null) return ResponseEntity.badRequest().body("Requisição inválida.");

        int idUsuario = req.getIdUsuario();
        String nomeTabela = (req.getNomeTabela() == null ? "" : req.getNomeTabela().trim());
        List<Integer> ids = req.getIds() == null ? List.of() : req.getIds();

        if (idUsuario <= 0) {
            return ResponseEntity.badRequest().body("ID do usuário inválido.");
        }
        if (nomeTabela.length() < 3) {
            return ResponseEntity.badRequest().body("O nome da tabela deve ter pelo menos 3 caracteres.");
        }
        if (ids.isEmpty()) {
            return ResponseEntity.badRequest().body("A lista de itens não pode estar vazia.");
        }

        // verifica se já existe tabela com esse nome para o usuário
        List<TabelasPersonalizadas> existentes = tab.findByIdUsuarioAndNomeTabela(idUsuario, nomeTabela);
        if (!existentes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Já existe uma tabela com esse nome.");
        }

        // cria as linhas e salva
        List<TabelasPersonalizadas> novasLinhas = new ArrayList<>();
        for (Integer idAntibiotico : ids) {
            TabelasPersonalizadas linha = new TabelasPersonalizadas();
            linha.setIdUsuario(idUsuario);
            linha.setNomeTabela(nomeTabela);
            linha.setIdAntibiotico(idAntibiotico);
            novasLinhas.add(linha);
        }

        tab.saveAll(novasLinhas);
        return ResponseEntity.status(HttpStatus.CREATED).body("Tabela criada com sucesso.");
    }


    // Renomear com verificação se já existe tabela com o novo nome para o mesmo usuário
    @PostMapping("/renomear")
    public ResponseEntity<String> renomearTabela(
            @RequestParam int idUsuario,
            @RequestParam String nomeAntigo,
            @RequestParam String nomeNovo) {

        nomeAntigo = (nomeAntigo == null ? "" : nomeAntigo.trim());
        nomeNovo = (nomeNovo == null ? "" : nomeNovo.trim());

        if (idUsuario <= 0) {
            return ResponseEntity.badRequest().body("ID do usuário inválido.");
        }
        if (nomeAntigo.isEmpty()) {
            return ResponseEntity.badRequest().body("Nome antigo não informado.");
        }
        if (nomeNovo.length() < 3) {
            return ResponseEntity.badRequest().body("O novo nome deve ter pelo menos 3 caracteres.");
        }
        if (nomeNovo.equals(nomeAntigo)) {
            return ResponseEntity.badRequest().body("O novo nome é igual ao nome atual.");
        }

        List<TabelasPersonalizadas> linhasAntigas = tab.findByIdUsuarioAndNomeTabela(idUsuario, nomeAntigo);
        if (linhasAntigas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tabela não encontrada.");
        }

        // verifica se já existe tabela com o nomeNovo para o mesmo usuário
        List<TabelasPersonalizadas> conflito = tab.findByIdUsuarioAndNomeTabela(idUsuario, nomeNovo);
        if (!conflito.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Já existe uma tabela com esse nome.");
        }

        // atualiza o nome em todas as linhas
        String finalNomeNovo = nomeNovo;
        linhasAntigas.forEach(linha -> linha.setNomeTabela(finalNomeNovo));
        tab.saveAll(linhasAntigas);

        return ResponseEntity.ok("Tabela renomeada com sucesso.");
    }


    @DeleteMapping("/{idUsuario}/{nomeTabela}")
    public ResponseEntity<String> deletarTabela(
            @PathVariable int idUsuario,
            @PathVariable String nomeTabela) {

        List<TabelasPersonalizadas> linhas = tab.findByIdUsuarioAndNomeTabela(idUsuario, nomeTabela);
        if (linhas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tabela não encontrada");
        }

        tab.deleteAll(linhas);
        return ResponseEntity.ok("Tabela deletada com sucesso");
    }

    @GetMapping("/contar/{idUsuario}")
    public ResponseEntity<Map<String, Integer>> contarTabelas(@PathVariable int idUsuario) {
        if (idUsuario <= 0) {
            return ResponseEntity.badRequest().body(Map.of("erro", 0)); // ID inválido
        }

        List<TabelasPersonalizadas> linhas = tab.findByIdUsuario(idUsuario);

        // Conta nomes distintos de tabela
        long totalTabelas = linhas.stream()
                .map(TabelasPersonalizadas::getNomeTabela)
                .distinct()
                .count();

        return ResponseEntity.ok(Map.of("totalTabelas", (int) totalTabelas));
    }
}
