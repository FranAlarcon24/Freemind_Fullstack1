package com.freemind.freemind.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.freemind.freemind.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    
}
