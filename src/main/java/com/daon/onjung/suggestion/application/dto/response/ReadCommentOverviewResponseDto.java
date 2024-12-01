package com.daon.onjung.suggestion.application.dto.response;

import com.daon.onjung.account.domain.User;
import com.daon.onjung.core.dto.SelfValidating;
import com.daon.onjung.core.utility.DateTimeUtil;
import com.daon.onjung.suggestion.domain.mysql.Comment;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ReadCommentOverviewResponseDto extends SelfValidating<ReadCommentOverviewResponseDto> {

    @JsonProperty("comment_list")
    private final List<CommentDto> commentList;

    @JsonProperty("has_next")
    private final Boolean hasNext;

    @Builder
    public ReadCommentOverviewResponseDto(List<CommentDto> commentList, Boolean hasNext) {
        this.commentList = commentList;
        this.hasNext = hasNext;

        this.validateSelf();
    }

    @Getter
    public static class CommentDto extends SelfValidating<CommentDto> {

        @JsonProperty("writer_info")
        private final WriterInfoDto writerInfo;

        @JsonProperty("comment_info")
        private final CommentInfoDto commentInfo;

        @Builder
        public CommentDto(WriterInfoDto writerInfo, CommentInfoDto commentInfo) {
            this.writerInfo = writerInfo;
            this.commentInfo = commentInfo;

            this.validateSelf();
        }

        @Getter
        public static class WriterInfoDto extends SelfValidating<WriterInfoDto> {

            @JsonProperty("profile_img_url")
            private final String profileImgUrl;

            @JsonProperty("masked_nickname")
            private final String maskedNickname;

            @JsonProperty("is_me")
            private final Boolean isMe;

            @Builder
            public WriterInfoDto(String profileImgUrl, String maskedNickname, Boolean isMe) {
                this.profileImgUrl = profileImgUrl;
                this.maskedNickname = maskedNickname;
                this.isMe = isMe;

                this.validateSelf();
            }

            public static ReadCommentOverviewResponseDto.CommentDto.WriterInfoDto of(User user, Boolean isMe) {

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

                return ReadCommentOverviewResponseDto.CommentDto.WriterInfoDto.builder()
                        .maskedNickname(maskedNickname)
                        .profileImgUrl(user.getProfileImgUrl())
                        .isMe(isMe)
                        .build();
            }
        }

        @Getter
        public static class CommentInfoDto extends SelfValidating<CommentInfoDto> {

            @JsonProperty("id")
            private final Long id;

            @JsonProperty("posted_ago")
            private final String postedAgo;

            @JsonProperty("content")
            private final String content;

            @Builder
            public CommentInfoDto(Long id, String postedAgo, String content) {
                this.id = id;
                this.postedAgo = postedAgo;
                this.content = content;

                this.validateSelf();
            }

            public static CommentInfoDto fromEntity(Comment comment) {
                return CommentInfoDto.builder()
                        .id(comment.getId())
                        .postedAgo(DateTimeUtil.calculatePostedAgo(comment.getCreatedAt()))
                        .content(comment.getContent())
                        .build();
            }
        }

        public static ReadCommentOverviewResponseDto.CommentDto of(Comment comment, User user, Boolean isMe) {
            return ReadCommentOverviewResponseDto.CommentDto.builder()
                    .writerInfo(ReadCommentOverviewResponseDto.CommentDto.WriterInfoDto.of(user, isMe))
                    .commentInfo(ReadCommentOverviewResponseDto.CommentDto.CommentInfoDto.fromEntity(comment))
                    .build();
        }
    }

    public static ReadCommentOverviewResponseDto of(List<CommentDto> commentList, Boolean hasNext) {
        return ReadCommentOverviewResponseDto.builder()
                .commentList(commentList)
                .hasNext(hasNext)
                .build();
    }
}
