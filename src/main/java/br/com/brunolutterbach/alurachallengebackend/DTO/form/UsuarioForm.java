package br.com.brunolutterbach.alurachallengebackend.DTO.form;

import br.com.brunolutterbach.alurachallengebackend.model.Usuario;
import br.com.brunolutterbach.alurachallengebackend.repository.UsuarioRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UsuarioForm {

    @NotNull
    private String nome;
    @NotNull
    @Email
    private String email;
    @NotNull
    private String senha;


    public Usuario converter() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        this.senha = encoder.encode(this.senha);
        return new Usuario(this.nome, this.email, this.senha);
    }

    public Boolean existeCadastro(UsuarioRepository usuarioRepository) {
        return usuarioRepository.findByEmail(this.email).isPresent();
    }


}
