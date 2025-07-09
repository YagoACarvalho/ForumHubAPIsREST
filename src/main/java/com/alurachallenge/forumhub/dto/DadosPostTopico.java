package com.alurachallenge.forumhub.dto;

import com.alurachallenge.forumhub.entity.Resposta;
import com.alurachallenge.forumhub.entity.Topico;
import com.alurachallenge.forumhub.entity.Usuario;
import jakarta.validation.constraints.NotNull;

public record DadosPostTopico(

        @NotNull
        String titulo,
        @NotNull
        String mensagem,
        @NotNull
        String curso

) {
    public DadosPostTopico (Topico topico){
        this(topico.getTitulo(), topico.getMensagem(), topico.getCurso());
    }

}
