package com.efs.core;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IdeaSessionRepository extends JpaRepository<IdeaSession, Integer> {
    List<IdeaSession> findByUsernameOrderByIdDesc(String username);
}