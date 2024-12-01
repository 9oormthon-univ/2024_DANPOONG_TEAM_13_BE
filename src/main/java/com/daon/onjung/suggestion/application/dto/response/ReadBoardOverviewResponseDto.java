package com.daon.onjung.suggestion.application.dto.response;

import com.daon.onjung.core.dto.SelfValidating;
import com.daon.onjung.core.utility.DateTimeUtil;
import com.daon.onjung.suggestion.domain.mysql.Board;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ReadBoardOverviewResponseDto extends SelfValidating<ReadBoardOverviewResponseDto> {

    @JsonProperty("board_list")
    private final List<BoardListDto> boardList;

    @JsonProperty("has_next")
    private final boolean hasNext;

    @Builder
    public ReadBoardOverviewResponseDto(List<BoardListDto> boardList, boolean hasNext) {
        this.boardList = boardList;
        this.hasNext = hasNext;

        this.validateSelf();
    }

    @Getter
    public static class BoardListDto extends SelfValidating<BoardListDto> {

        @JsonProperty("id")
        @NotNull(message = "id는 null일 수 없습니다.")
        private final Long id;

        @JsonProperty("img_url")
        private final String imgUrl;

        @JsonProperty("status")
        @NotNull(message = "status는 null일 수 없습니다.")
        private final String status;

        @JsonProperty("title_summary")
        @Size(min = 1, max = 30, message = "제목은 1자 이상 30자 이하여야 합니다.")
        @NotNull(message = "제목은 null일 수 없습니다.")
        private final String titleSummary;

        @JsonProperty("content_summary")
        @Size(min = 1, max = 60, message = "내용은 1자 이상 60자 이하여야 합니다.")
        @NotNull(message = "내용은 null일 수 없습니다.")
        private final String contentSummary;

        @JsonProperty("posted_ago")
        @NotNull(message = "posted_ago는 null일 수 없습니다.")
        private final String postedAgo;

        @JsonProperty("like_count")
        @NotNull(message = "like_count는 null일 수 없습니다.")
        private final Integer likeCount;

        @JsonProperty("comment_count")
        @NotNull(message = "comment_count는 null일 수 없습니다.")
        private final Integer commentCount;

        @Builder
        public BoardListDto(Long id, String imgUrl, String status, String titleSummary, String contentSummary, String postedAgo, Integer likeCount, Integer commentCount) {
            this.id = id;
            this.imgUrl = imgUrl;
            this.status = status;
            this.titleSummary = titleSummary;
            this.contentSummary = contentSummary;
            this.postedAgo = postedAgo;
            this.likeCount = likeCount;
            this.commentCount = commentCount;

            this.validateSelf();
        }

        public static BoardListDto fromEntity(Board board) {
            return BoardListDto.builder()
                    .id(board.getId())
                    .imgUrl(board.getImgUrl())
                    .status(board.getStatus().toString())
                    .titleSummary(board.getTitle().length() > 30 ? board.getTitle().substring(0, 15) : board.getTitle())
                    .contentSummary(board.getContent().length() > 30 ? board.getContent().substring(0, 60) : board.getContent())
                    .postedAgo(DateTimeUtil.calculatePostedAgo(board.getCreatedAt()))
                    .likeCount(board.getLikeCount())
                    .commentCount(board.getCommentCount())
                    .build();
        }
    }
    public static ReadBoardOverviewResponseDto of(List<BoardListDto> boardList, boolean hasNext) {
        return ReadBoardOverviewResponseDto.builder()
                .boardList(boardList)
                .hasNext(hasNext)
                .build();
    }
}
