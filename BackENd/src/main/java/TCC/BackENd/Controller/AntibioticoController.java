package TCC.BackENd.Controller;

import TCC.BackENd.Model.Antibiotico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import TCC.BackENd.repository.AntibioticoRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value="/apiAntibiotico")
@CrossOrigin(origins = "*")
public class AntibioticoController {

    @Autowired
    AntibioticoRepository ant;

    @GetMapping(value="/Antibioticos")
    public List<Antibiotico> listarAntibioticos()
    {
        return ant.findAll();
    }

    @GetMapping(value = "/Antibioticos/{id}")
    public Optional<Antibiotico> listaPorID(@PathVariable(value="id")int id)
    {
        return ant.findById(id);
    }

    @GetMapping(value = "/AntibioticosNome/{nome}")
    public List<Antibiotico> listaPorNome(@PathVariable(value="nome")String nome)
    {
        return ant.findByNome(nome);
    }

    @GetMapping(value = "/AntibioticosGram_neg/{g}")
    public List<Antibiotico> listaPorGram_neg(@PathVariable(value="g")boolean g)
    {
        return ant.findByGram_neg(g);
    }
    @GetMapping(value = "/AntibioticosGram_pos/{g}")
    public List<Antibiotico> listaPorGram_Pos(@PathVariable(value="g")boolean g)
    {
        return ant.findByGram_pos(g);
    }

    @GetMapping(value = "/AntibioticosMorfologia/{g}")
    public List<Antibiotico> listaPorMorfoloogia(@PathVariable(value="g")int g)
    {
        return ant.findByMorfologia(g);
    }

    @GetMapping(value = "/AntibioticosTipo/{tipo}")
    public List<Antibiotico> listaPorTipo(@PathVariable(value="tipo")int tipo)
    {
        return ant.findByTipo(tipo);
    }
}
