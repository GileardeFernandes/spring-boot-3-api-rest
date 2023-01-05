package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.paciente.DadosListagemPaciente;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.domain.paciente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository repository;


    @PostMapping
    @Transactional
    public ResponseEntity cradastrar(@RequestBody @Valid DadosCadastroPaciente dados, UriComponentsBuilder uriBuilder){

        var paciente  = new Paciente(dados);
        repository.save(paciente);

        var uri = uriBuilder.path("/{id}").buildAndExpand(paciente.getId()).toUri();

        return  ResponseEntity.created(uri).body(paciente);
    }

    @GetMapping
    public  ResponseEntity<Page<DadosListagemPaciente>> listar(Pageable paginacao){

        var pacientesPage = repository.findAll(paginacao).map(DadosListagemPaciente::new);

        return ResponseEntity.ok(pacientesPage);
    }

    @GetMapping("/{id}")
    public  ResponseEntity buscarPacientePorId(@PathVariable Long id){

        var paciente = repository.getReferenceById(id);

        return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
    }

    @PutMapping
    @Transactional
    public  ResponseEntity alterar(@RequestBody @Valid  DadosAtualizacaoPaciente dadosAtualizacaoPaciente){

        var paciente = repository.getReferenceById(dadosAtualizacaoPaciente.id());
        paciente.atualizarDados(dadosAtualizacaoPaciente);

        return  ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public  ResponseEntity deletar(@PathVariable Long id){

        var paciente = repository.getReferenceById(id);
        repository.delete(paciente);

        return  ResponseEntity.noContent().build();
    }
}
