package com.alurachallenge.forumhub.dto;

import com.alurachallenge.forumhub.entity.Usuario;

public record UsuarioDTO(

        Usuario username
) {
    public UsuarioDTO(Usuario username) {
        this.username = username;
    }

}
