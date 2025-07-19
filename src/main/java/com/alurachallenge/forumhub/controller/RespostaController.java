package com.alurachallenge.forumhub.controller;


import com.alurachallenge.forumhub.dto.DadosDetalhadoResposta;
import com.alurachallenge.forumhub.dto.DadosRespostaEnvio;
import com.alurachallenge.forumhub.entity.Usuario;
import com.alurachallenge.forumhub.infra.error.ValidacaoException;
import com.alurachallenge.forumhub.repository.RespostaRepository;
import com.alurachallenge.forumhub.repository.UsuarioRepository;
import com.alurachallenge.forumhub.service.RespostaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;



@RestController
@RequestMapping("/topicos/{idTopico}/resposta")
@SecurityRequirement(name = "bearer-key")
public class RespostaController {

    @Autowired
    private RespostaRepository respostaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RespostaService respostaService;

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhadoResposta> responderTopico(@PathVariable Long idTopico, @RequestBody @Valid DadosRespostaEnvio dadosRespostaEnvio, Authentication authentication){

       String username = authentication.getName();

      var autor = usuarioRepository.findByUsername(username)
              .orElseThrow(() -> new ValidacaoException("Erro ao carregar usuário"));
      var dto = respostaService.criar(idTopico,autor , dadosRespostaEnvio.mensagem());
      return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deletarResposta(@PathVariable Long id) {

        var resposta = respostaRepository.getReferenceById(id);
        respostaRepository.delete(resposta);

        return ResponseEntity.noContent().build();
    }


}
