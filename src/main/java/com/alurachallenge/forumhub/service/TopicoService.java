package com.alurachallenge.forumhub.service;

import com.alurachallenge.forumhub.dto.DadosAtualizacaoTopico;
import com.alurachallenge.forumhub.dto.DadosPostTopico;
import com.alurachallenge.forumhub.dto.DadosTopico;
import com.alurachallenge.forumhub.entity.Topico;
import com.alurachallenge.forumhub.entity.Usuario;
import com.alurachallenge.forumhub.infra.error.ValidacaoException;
import com.alurachallenge.forumhub.repository.TopicoRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Cria um novo tópico no sistema.
     *
     * Etapas:
     *  Verifica se já existe um tópico com o mesmo título e mensagem para evitar duplicidade
     *  Recupera o usuário autenticado e persistido no banco de dados
     *  Cria a instância do tópico associando o autor e o curso
     *  Salva o tópico no banco de dados
     *  Retorna o tópico criado (com ID gerado)
     *
     *  dadosPostTopico DTO com os dados do novo tópico
     *  usuarioAutenticado Usuário autenticado, extraído do token JWT
     *  retorna o tópico criado e persistido
     *  retorna ValidacaoException se já existir um tópico igual
     */

    public Topico criar(DadosPostTopico dadosPostTopico, @AuthenticationPrincipal Usuario usuarioAutenticado) {
        if(topicoRepository.existsByTituloAndMensagem(dadosPostTopico.titulo(), dadosPostTopico.mensagem())) {
            throw new ValidacaoException("Já existe um tópico com este título e mensagem");
        }

        Usuario usuarioLogado = usuarioService.obterUsuarioAutenticado(usuarioAutenticado);

        Topico topico = new Topico(
                dadosPostTopico.titulo(),
                dadosPostTopico.mensagem(),
                usuarioLogado,
                dadosPostTopico.curso());
        topicoRepository.save(topico);

        return topico;
    }





    public DadosAtualizacaoTopico atualizar(Long id, DadosAtualizacaoTopico dadosAtualizacaoTopico){

        if(topicoRepository.existsByTituloAndMensagem(dadosAtualizacaoTopico.titulo(), dadosAtualizacaoTopico.mensagem())) {
            throw new ValidationException("Já existe um tópico com este título e mensagem");
        }

        var topicoEscolhido = topicoRepository.getReferenceById(dadosAtualizacaoTopico.id());

        topicoEscolhido.atualizarInformacoes(dadosAtualizacaoTopico);

        return new DadosAtualizacaoTopico(topicoEscolhido);
    }


    /**
     * Lista os tópicos de forma paginada.
     * Usa o Pageable para controlar página, tamanho e ordenação.
     * Atenção: o mét0do findTop10ByOrderByCriacaoAsc limita sempre a 10 resultados,
     * mesmo que o parâmetro 'size' seja maior.
     */
    public Page<DadosTopico> listar(Pageable paginacao) {
        return topicoRepository.findTop10ByOrderByCriacaoAsc(paginacao).map(DadosTopico::new);
    }



    public Page<DadosTopico> listarPorCurso(Pageable page, String curso) {
        Page<DadosTopico> cursosEncontrados = topicoRepository.findByCurso(curso, page).map(DadosTopico::new);
        return cursosEncontrados;
    }

}
