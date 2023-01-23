package med.voll.api.domain.usuario;

import jakarta.validation.constraints.NotBlank;

public record DadosAutenticacao(
        @NotBlank
        String login,
        @NotBlank
        String senha) {

    public DadosAutenticacao(Usuario usuario){
        this(usuario.getLogin(), usuario.getSenha());
    }
}
