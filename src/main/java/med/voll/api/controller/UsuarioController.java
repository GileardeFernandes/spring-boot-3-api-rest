package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.medico.DadosDetalhamentoMedico;
import med.voll.api.domain.usuario.DadosCastroUsuario;
import med.voll.api.domain.usuario.DadosDetalhamentoUsuario;
import med.voll.api.domain.usuario.Usuario;
import med.voll.api.domain.usuario.UsuarioRepository;
import med.voll.api.infra.security.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;
    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCastroUsuario dados, UriComponentsBuilder uriBuilder){

          var hash = Util.gerarHash(dados.senha());
          var usuario = new Usuario(dados.login(), hash);
          repository.save(usuario);

          var uri = uriBuilder.path("/{id}").buildAndExpand(usuario.getId()).toUri();
          return  ResponseEntity.created(uri).body(new DadosDetalhamentoUsuario(usuario));
    }
}
