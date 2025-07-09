package com.alurachallenge.forumhub.dto;

import com.alurachallenge.forumhub.entity.Topico;
import com.alurachallenge.forumhub.entity.Usuario;

import java.util.List;


public record DadosDetalhadosUsuario(

        Long id,
        String username,
        String curso


) {

    public DadosDetalhadosUsuario(Usuario usuario){
        this(usuario.getId(), usuario.getUsername(), usuario.getCurso());
    }


}
