package com.freemind.freemind.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.freemind.freemind.model.TipoUsuario;

public interface TipoUsuarioRepository extends JpaRepository <TipoUsuario, Long> {
    
}
