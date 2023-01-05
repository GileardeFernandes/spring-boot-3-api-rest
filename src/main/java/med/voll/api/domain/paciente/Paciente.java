package med.voll.api.domain.paciente;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.endereco.Endereco;

@Table(name = "pacientes")
@Entity(name = "Paciente")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String email;

    private String cpf;

    private String telefone;

    @Embedded
    private Endereco endereco;


    public Paciente(DadosCadastroPaciente dados) {
        this.nome = dados.nome();
        this.email = dados.email();
        this.cpf = dados.cpf();
        this.telefone = dados.telefone();
        this.endereco = new Endereco(dados.endereco());
    }

    public void atualizarDados(DadosAtualizacaoPaciente dadosAtualizacaoPaciente) {

        if(dadosAtualizacaoPaciente.nome() != null)
            this.nome = dadosAtualizacaoPaciente.nome();

        if(dadosAtualizacaoPaciente.telefone() != null)
            this.telefone = dadosAtualizacaoPaciente.telefone();

        if(dadosAtualizacaoPaciente.email() != null)
            this.email = dadosAtualizacaoPaciente.email();

        if(dadosAtualizacaoPaciente.endereco() != null)
            this.endereco.atualizarInformacoes(dadosAtualizacaoPaciente.endereco());
    }
}
