package com.alurachallenge.forumhub.entity;


import com.alurachallenge.forumhub.dto.AtualizarUsuarioRequestDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.List;
import java.util.UUID;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuarios")
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String senha;

    private String curso;

    private String idAlternativo;

    @ManyToOne
    private Topico topicos;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void atualizarUsuario(AtualizarUsuarioRequestDTO dto) {
        if(dto.username() != null) {
            this.username = dto.username();
        }

        if(dto.senha() != null){
            this.senha = dto.senha();
        }
    }

    public void setIdAlternativo(){
        UUID uuid = UUID.randomUUID();
        String uuidComoString = uuid.toString();
        this.idAlternativo = uuidComoString;

    }


}
