package com.tfg.springmarket.model.repositories;

import com.tfg.springmarket.model.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    @Query("""
            select t from Token t inner join Usuario u on t.usuario.id = u.id
            where t.usuario.id = :userId and t.loggedOut = false
            """)
    List<Token> findAllTokensByUsuario(Long userId);

    Optional<Token> findByToken(String token);
}