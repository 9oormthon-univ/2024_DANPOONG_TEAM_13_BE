package com.daon.onjung.suggestion.application.dto.response;

import com.daon.onjung.account.domain.User;
import com.daon.onjung.core.dto.SelfValidating;
import com.daon.onjung.core.utility.DateTimeUtil;
import com.daon.onjung.suggestion.domain.Board;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadBoardDetailResponseDto extends SelfValidating<ReadBoardOverviewResponseDto> {

    @JsonProperty("writer_info")
    @NotNull(message = "작성자 정보는 null일 수 없습니다.")
    private final WriterInfoDto writerInfo;

    @JsonProperty("board_info")
    @NotNull(message = "게시글 정보는 null일 수 없습니다.")
    private final BoardInfoDto boardInfo;

    @Builder
    public ReadBoardDetailResponseDto(WriterInfoDto writerInfo, BoardInfoDto boardInfo) {
        this.writerInfo = writerInfo;
        this.boardInfo = boardInfo;
    }

    @Getter
    public static class WriterInfoDto extends SelfValidating<WriterInfoDto> {

        @JsonProperty("profile_img_url")
        private final String profileImgUrl;

        @JsonProperty("masked_nickname")
        private final String maskedNickname;

        @Builder
        public WriterInfoDto(String profileImgUrl, String maskedNickname) {
            this.profileImgUrl = profileImgUrl;
            this.maskedNickname = maskedNickname;

            this.validateSelf();
        }

        public static WriterInfoDto fromEntity(User user) {

            String nickname = user.getNickName();
            String maskedNickname;

            if (nickname.length() == 1) {
                maskedNickname = nickname;
            } else if (nickname.length() == 2) {
                maskedNickname = nickname.charAt(0) + "*";
            } else {
                String firstChar = nickname.substring(0, 1);
                String lastChar = nickname.substring(nickname.length() - 1);
                String middleMask = "*".repeat(nickname.length() - 2);
                maskedNickname = firstChar + middleMask + lastChar;
            }

            return WriterInfoDto.builder()
                    .maskedNickname(maskedNickname)
                    .profileImgUrl(user.getProfileImgUrl())
                    .build();
        }
    }

    @Getter
    public static class BoardInfoDto extends SelfValidating<BoardInfoDto> {

        @JsonProperty("id")
        @NotNull(message = "id는 null일 수 없습니다.")
        private final Long id;

        @JsonProperty("img_url")
        private final String imgUrl;

        @JsonProperty("title")
        @NotNull(message = "제목은 null일 수 없습니다.")
        @Size(min = 1, max = 20, message = "제목은 1자 이상 20자 이하여야 합니다")
        private final String title;

        @JsonProperty("content")
        @NotNull(message = "내용은 null일 수 없습니다.")
        @Size(min = 1, max = 500, message = "내용은 1자 이상 500자 이하여야 합니다")
        private final String content;

        @JsonProperty("posted_ago")
        @NotNull(message = "posted_ago는 null일 수 없습니다.")
        private final String postedAgo;

        @JsonProperty("like_count")
        @NotNull(message = "like_count는 null일 수 없습니다.")
        @Min(value = 0, message = "like_count는 0 이상이어야 합니다.")
        private final Integer likeCount;

        @JsonProperty("comment_count")
        @NotNull(message = "comment_count는 null일 수 없습니다.")
        @Min(value = 0, message = "comment_count는 0 이상이어야 합니다.")
        private final Integer commentCount;

        @JsonProperty("is_liked")
        private final Boolean isLiked;

        @Builder
        public BoardInfoDto(Long id, String imgUrl, String title, String content, String postedAgo, Integer likeCount, Integer commentCount, Boolean isLiked) {
            this.id = id;
            this.imgUrl = imgUrl;
            this.title = title;
            this.content = content;
            this.postedAgo = postedAgo;
            this.likeCount = likeCount;
            this.commentCount = commentCount;
            this.isLiked = isLiked;

            this.validateSelf();
        }

        public static BoardInfoDto of(
                Board board,
                Integer likeCount,
                Integer commentCount,
                Boolean isLiked
        ) {
            return BoardInfoDto.builder()
                    .id(board.getId())
                    .imgUrl(board.getImgUrl())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .postedAgo(DateTimeUtil.calculatePostedAgo(board.getCreatedAt()))
                    .likeCount(likeCount)
                    .commentCount(commentCount)
                    .isLiked(isLiked)
                    .build();
        }
    }

    public static ReadBoardDetailResponseDto of(Board board, User user, Integer likeCount, Integer commentCount, Boolean isLiked) {
        return ReadBoardDetailResponseDto.builder()
                .writerInfo(WriterInfoDto.fromEntity(user))
                .boardInfo(BoardInfoDto.of(board, likeCount, commentCount, isLiked))
                .build();
    }

}
