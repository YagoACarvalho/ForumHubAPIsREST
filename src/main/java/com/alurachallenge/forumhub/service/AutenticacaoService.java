package com.alurachallenge.forumhub.service;

import com.alurachallenge.forumhub.infra.error.ValidacaoException;
import com.alurachallenge.forumhub.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;


    @Override
    public UserDetails loadUserByUsername(String username)  {
        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new ValidacaoException("Usuário não encontrado"));
    }
}
