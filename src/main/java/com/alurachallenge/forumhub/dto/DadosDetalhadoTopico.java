package com.alurachallenge.forumhub.dto;


import com.alurachallenge.forumhub.entity.StatusTopico;
import com.alurachallenge.forumhub.entity.Topico;
import java.time.LocalDateTime;
import java.util.List;

public record DadosDetalhadoTopico(

        String titulo,
        String mensagem,
        String autor,
        String curso,
        LocalDateTime criacao,
        StatusTopico status,
        List<DadosDetalhadoResposta> respostas
) {

    public DadosDetalhadoTopico(Topico topico) {
        this(
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getAutor().getUsername(),
                topico.getCurso(),
                topico.getCriacao(),
                topico.getStatus(),
                topico.getResposta().stream()
                        .map(r -> new DadosDetalhadoResposta(
                                r.getId(),
                                r.getMensagem(),
                                r.getAutor().getUsername(),
                                r.getCriacao()
                        ))
                        .toList()
        );
    }

}
