package com.alurachallenge.forumhub.dto;

import com.alurachallenge.forumhub.entity.Usuario;

public record ListaDeUsuariosDTO(
        Long id,
        String username,
        String curso
) {
    public ListaDeUsuariosDTO(Usuario usuario){
        this(usuario.getId(), usuario.getUsername(), usuario.getCurso());
    }
}
