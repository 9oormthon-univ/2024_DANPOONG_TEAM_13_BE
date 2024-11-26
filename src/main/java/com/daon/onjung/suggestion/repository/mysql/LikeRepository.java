package com.daon.onjung.suggestion.repository.mysql;

import com.daon.onjung.account.domain.User;
import com.daon.onjung.suggestion.domain.Board;
import com.daon.onjung.suggestion.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    Integer countByBoard(Board board);

    Boolean existsByBoardAndUser(Board board, User user);

    Optional<Like> findByBoardAndUser(Board board, User user);
}
