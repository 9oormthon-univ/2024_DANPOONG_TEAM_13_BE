package com.daon.onjung.suggestion.repository.mysql;

import com.daon.onjung.suggestion.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
