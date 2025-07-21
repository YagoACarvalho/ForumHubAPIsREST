package com.alurachallenge.forumhub.service;

import com.alurachallenge.forumhub.dto.UsuarioRequestDTO;
import com.alurachallenge.forumhub.entity.Usuario;
import com.alurachallenge.forumhub.infra.error.ValidacaoException;
import com.alurachallenge.forumhub.repository.UsuarioRepository;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // <-- Habilita o uso do Mockito com JUnit 5
class UsuarioServiceTest {

    @InjectMocks // <-- Diz que vamos testar uma classe real, mas injetando falsos objetos nos atributos dela
    private UsuarioService usuarioService; // <--  classe que queremos testar

    @Mock
    private UsuarioRepository usuarioRepository; // <-- repositorio falso, simula o original

    @Mock
    private PasswordEncoder passwordEncoder; // simula a criptografia


    @Test
    void deveCadastrarUsuarioQuandoUsernameNaoExiste() {

        var dto = new UsuarioRequestDTO("novouser", "123456", "Java"); // <-- cria um dto como se fosse o usuario preenchendo o front


        when(usuarioRepository.existsByUsername("novouser")).thenReturn(false); // <-- Simulamos o comportamento do repositório
        // isso diz --> quando alguém chamar usuarioRepository.existsByUsername("novouser"), retorne false, ou seja, o username não existe

        when(passwordEncoder.encode("123456")).thenReturn("senhaCriptografada"); // <-- simulando senha criptografada

        // simulando o usuário que será salvo
        Usuario usuarioSalvo = new Usuario();
        usuarioSalvo.setUsername("novouser");
        usuarioSalvo.setSenha("senhaCriptografada");
        usuarioSalvo.setCurso("Java");

        when(usuarioRepository.save(any())).thenReturn(usuarioSalvo); // <-- Dizemos: quando alguém salvar o usuário, retorne esse usuário fictício.


        // execução ou act
        var response = usuarioService.cadastrarUsuario(dto); // <-- chama o mét@do real da nossa classe

        // verificação ou Assert
        assertEquals("novouser", response.username()); // <-- verifica se o nome de usuário retornado está certo

        verify(usuarioRepository).save(any()); // <-- checa se o meteodo save() foi realmente chamado
    }

    @Test
    void deveLancarExcecaoQuandoUserNameJaExiste() {
        //Arrange
        var dto = new UsuarioRequestDTO("userExiste", "123456", "Java");

        when(usuarioRepository.existsByUsername("userExiste")).thenReturn(true);

        //Act + Assert
        ValidacaoException exception = assertThrows(ValidacaoException.class, () -> {
            usuarioService.cadastrarUsuario(dto);
        });

        assertEquals("Já existe um cadastro com esse username.", exception.getMessage());
    }


    @Test
    void deveDeletarUsuarioQuandoIdExistir() {
        //Arrange
        Long id = 1L;
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setUsername("User");
        usuario.setSenha("123456");
        usuario.setCurso("Java");

        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));

        //Act
        usuarioService.deletar(id);

        //Assert
        verify(usuarioRepository).delete(usuario);

    }

    @Test
    void deveLancarQuandoIdNaoExistir() {

        Long idInexistente = 99L;

        //Simula que o usuário não existe no banco
        when(usuarioRepository.findById(idInexistente)).thenReturn(Optional.empty());

        //act e assert
        ValidacaoException exception = assertThrows(ValidacaoException.class, () -> {
            usuarioService.deletar(idInexistente);
        });

        assertEquals("Usuário não encontrado!", exception.getMessage());

    }

    @Test
    void atualizar() {
    }

    @Test
    void listarUsuario() {
    }

    @Test
    void obterUsuarioAutenticado() {
    }
}