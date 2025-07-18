package com.alurachallenge.forumhub.dto;

import com.alurachallenge.forumhub.entity.Usuario;

public record UsuarioResponseDTO(

        Long id,
        String username,
        String curso

) {

    public UsuarioResponseDTO(Usuario usuario) {
        this(usuario.getId(), usuario.getUsername(), usuario.getCurso());
    }


}
