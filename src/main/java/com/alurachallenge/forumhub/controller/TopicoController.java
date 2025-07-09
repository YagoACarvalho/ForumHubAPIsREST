package com.alurachallenge.forumhub.controller;

import com.alurachallenge.forumhub.dto.DadosAtualizacaoTopico;
import com.alurachallenge.forumhub.dto.DadosDetalhadoTopico;
import com.alurachallenge.forumhub.dto.DadosPostTopico;
import com.alurachallenge.forumhub.dto.DadosTopico;
import com.alurachallenge.forumhub.entity.Topico;
import com.alurachallenge.forumhub.entity.Usuario;
import com.alurachallenge.forumhub.repository.TopicoRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {


    @Autowired
    private  TopicoRepository topicoRepository;


    @PostMapping
    @Transactional
    public ResponseEntity postarTopico(@RequestBody @Valid DadosPostTopico dadosPostTopico, UriComponentsBuilder uriComponentsBuilder, @AuthenticationPrincipal Usuario usuarioAutenticado) {

        if(topicoRepository.existsByTituloAndMensagem(dadosPostTopico.titulo(), dadosPostTopico.mensagem())) {
            throw new ValidationException("Já existe um tópico com este título e mensagem");
        }

        Topico topico = new Topico(dadosPostTopico.titulo(), dadosPostTopico.mensagem(), usuarioAutenticado, dadosPostTopico.curso());
        topicoRepository.save(topico);
        var uri = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosTopico(topico));
    }


    @GetMapping
    public ResponseEntity<Page<DadosTopico>> listarTopico(Pageable paginacao) {
        var page = topicoRepository.findTop10ByOrderByCriacaoAsc(paginacao).map(DadosTopico::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/curso/{curso}")
    public ResponseEntity<Page<DadosTopico>> listarTopicosPorNomeCurso(@PageableDefault(size = 10, sort = "criacao", direction = Sort.Direction.DESC) Pageable paginacao, @PathVariable String curso) {
        Page<DadosTopico> cursosEncontrados = topicoRepository.findByCurso(curso, paginacao).map(DadosTopico::new);
        return ResponseEntity.ok(cursosEncontrados);
    }

    //Marca o tópico como resolvido fazendo uma espécie de exclusão lógica
    @DeleteMapping("/resolved/{id}")
    @Transactional
    public ResponseEntity marcarTopicoComoResolvido(@PathVariable Long id){
        var topicoResolvido = topicoRepository.getReferenceById(id);
        topicoResolvido.resolver();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detalharTopico(@PathVariable Long id){
        var topicoEscolhido = topicoRepository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhadoTopico(topicoEscolhido));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity atualizarTopico(@PathVariable Long id,@RequestBody @Valid DadosAtualizacaoTopico dadosAtualizacaoTopico){

        if(topicoRepository.existsByTituloAndMensagem(dadosAtualizacaoTopico.titulo(), dadosAtualizacaoTopico.mensagem())) {
            throw new ValidationException("Já existe um tópico com este título e mensagem");
        }

        var topicoEscolhido = topicoRepository.getReferenceById(dadosAtualizacaoTopico.id());
        topicoEscolhido.atualizarInformacoes(dadosAtualizacaoTopico);
        return ResponseEntity.ok(new DadosDetalhadoTopico(topicoEscolhido));
    }
    //Deleta o tópico
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deletarTopico(@PathVariable Long id){
        Optional<Topico> topicoEscolhido = Optional.of(topicoRepository.getReferenceById(id));
        if (topicoEscolhido.isPresent()){
            topicoRepository.deleteById(id);
        }
        return  ResponseEntity.noContent().build();
    }


}
