package com.daon.onjung.suggestion.repository.mysql;

import com.daon.onjung.suggestion.domain.mysql.Board;
import com.daon.onjung.suggestion.domain.mysql.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Integer countByBoard(Board board);

    Page<Comment> findAllByBoardIdOrderByCreatedAtAsc(Long boardId, Pageable pageable);
}
