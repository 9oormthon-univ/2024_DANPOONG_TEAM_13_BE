package com.daon.onjung.suggestion.repository.mysql;

import com.daon.onjung.suggestion.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
}
