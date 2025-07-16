package com.alurachallenge.forumhub.service;

import com.alurachallenge.forumhub.dto.AtualizarUsuarioDTO;
import com.alurachallenge.forumhub.dto.DadosDetalhadosUsuario;
import com.alurachallenge.forumhub.dto.UsuarioLoginDTO;
import com.alurachallenge.forumhub.entity.Usuario;
import com.alurachallenge.forumhub.infra.error.ValidacaoException;
import com.alurachallenge.forumhub.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public Usuario cadastrar(UsuarioLoginDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setUsername(dto.username());
        usuario.setSenha(passwordEncoder.encode(dto.senha()));
        usuario.setIdAlternativo();
       return usuarioRepository.save(usuario);
    }

    public void deletar(Long id) {
        var usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ValidacaoException("Usuário não encontrado"));
        if (usuario.isAdmin()) {
            throw new ValidacaoException("Não é permitido excluir um administrador");
        }

        usuarioRepository.delete(usuario);
    }

    public DadosDetalhadosUsuario atualizar(Long id, AtualizarUsuarioDTO dto){
        var usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ValidacaoException("Usuário não encontrado"));

       usuario.atualizarUsuario(dto);
        return new DadosDetalhadosUsuario(usuario);
    }

    public Usuario obterUsuarioAutenticado(Usuario usuarioAutenticado) {
        return usuarioRepository.findById(usuarioAutenticado.getId())
                .orElseThrow(() -> new ValidacaoException("Usuário não encontrado"));
    }

}
