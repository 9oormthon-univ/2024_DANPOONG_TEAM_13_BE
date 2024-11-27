package com.daon.onjung.suggestion.domain;

import com.daon.onjung.account.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "boards")
public class Board {
    /* -------------------------------------------- */
    /* Default Column ----------------------------- */
    /* -------------------------------------------- */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* -------------------------------------------- */
    /* Information Column ------------------------- */
    /* -------------------------------------------- */
    @Column(name = "title", length = 20, nullable = false)
    private String title;

    @Column(name = "content", length = 500, nullable = false)
    private String content;

    @Column(name = "img_url", length = 2080, nullable = true)
    private String imgUrl;

    @Column(name = "like_count", nullable = false)
    private Integer likeCount;

    @Column(name = "comment_count", nullable = false)
    private Integer commentCount;

    /* -------------------------------------------- */
    /* Timestamp Column --------------------------- */
    /* -------------------------------------------- */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /* -------------------------------------------- */
    /* Many To One Mapping ------------------------ */
    /* -------------------------------------------- */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", nullable = false)
    private User user;

    /* -------------------------------------------- */
    /* Methods ------------------------------------ */
    /* -------------------------------------------- */
    @Builder
    public Board(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.likeCount = 0;
        this.commentCount = 0;
        this.createdAt = LocalDateTime.now();
    }

    public void updateImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void updateLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public void
    updateCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }
}
