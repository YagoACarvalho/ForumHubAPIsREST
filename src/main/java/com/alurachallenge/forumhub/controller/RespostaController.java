package com.alurachallenge.forumhub.controller;


import com.alurachallenge.forumhub.dto.RespostaRequestDTO;
import com.alurachallenge.forumhub.dto.RespostaResponseDTO;
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
    public ResponseEntity<RespostaResponseDTO> responderTopico(@PathVariable Long idTopico, @RequestBody @Valid RespostaRequestDTO respostaRequestDTO, Authentication authentication){

       String username = authentication.getName();

      var autor = usuarioRepository.findByUsername(username)
              .orElseThrow(() -> new ValidacaoException("Erro ao carregar usuário"));
      var dto = respostaService.criar(idTopico,autor , respostaRequestDTO.mensagem() );
      return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deletarResposta(@PathVariable Long id) {
        respostaService.deletar(id);
        return ResponseEntity.noContent().build();
    }


}
