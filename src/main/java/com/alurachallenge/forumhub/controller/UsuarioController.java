package com.alurachallenge.forumhub.controller;


import com.alurachallenge.forumhub.dto.AtualizarUsuarioDTO;
import com.alurachallenge.forumhub.dto.DadosDetalhadosUsuario;
import com.alurachallenge.forumhub.dto.ListaDeUsuariosDTO;
import com.alurachallenge.forumhub.dto.UsuarioLoginDTO;
import com.alurachallenge.forumhub.entity.Usuario;
import com.alurachallenge.forumhub.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/cadastrar")
    @Transactional
    public ResponseEntity<?> cadastrarUsuario(@RequestBody UsuarioLoginDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setUsername(dto.username());
        usuario.setSenha(passwordEncoder.encode(dto.senha()));
        usuario.setIdAlternativo();
        System.out.println(usuario.getIdAlternativo());
        usuarioRepository.save(usuario);
        return ResponseEntity.ok("Usuario cadastrado com sucesso!");
    }



    @PutMapping("/{id}")
    @Transactional
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity atualizarUsuario(@RequestBody AtualizarUsuarioDTO dto){
        var usuario = usuarioRepository.getReferenceById(dto.id());
        usuario.atualizarUsuario(dto);
        return ResponseEntity.ok(new DadosDetalhadosUsuario(usuario));
    }

    @GetMapping
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Page<Usuario>> ListarUsuarios(Pageable paginacao, @RequestBody ListaDeUsuariosDTO listaDeUsuariosDTO){
      var page = usuarioRepository.findAll(paginacao);
      return ResponseEntity.ok(page);
    }


    @DeleteMapping("/{id}")
    @Transactional
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity deletarUsuario(@PathVariable Long id) {
        var usuario = usuarioRepository.getReferenceById(id);
        usuarioRepository.delete(usuario);

        return ResponseEntity.noContent().build();
    }



}
