package br.com.rcedesenvolvimento.dev_lean_store.modules.courses;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rcedesenvolvimento.dev_lean_store.modules.courses.entities.CourseEntity;

public interface CourseRepository extends JpaRepository<CourseEntity, UUID> {
    
} 
