package com.daon.onjung.suggestion.application.dto.response;

import com.daon.onjung.account.domain.User;
import com.daon.onjung.core.dto.SelfValidating;
import com.daon.onjung.core.utility.DateTimeUtil;
import com.daon.onjung.suggestion.domain.mysql.Comment;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateCommentResponseDto extends SelfValidating<CreateCommentResponseDto> {

    @JsonProperty("writer_info")
    private final WriterInfoDto writerInfo;

    @JsonProperty("comment_info")
    private final CommentInfoDto commentInfo;

    @Builder
    public CreateCommentResponseDto(WriterInfoDto writerInfo, CommentInfoDto commentInfo) {
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

        public static WriterInfoDto of(User user, Boolean isMe) {
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
                    .isMe(isMe)
                    .build();
        }
    }

    @Getter
    public static class CommentInfoDto extends SelfValidating<CommentInfoDto> {

        @JsonProperty("id")
        private final Long id;

        @JsonProperty("content")
        private final String content;

        @JsonProperty("posted_ago")
        private final String postedAgo;

        @Builder
        public CommentInfoDto(Long id, String content, String postedAgo) {
            this.id = id;
            this.content = content;
            this.postedAgo = postedAgo;

            this.validateSelf();
        }

        public static CommentInfoDto fromEntity(Comment comment) {
            return CommentInfoDto.builder()
                    .id(comment.getId())
                    .content(comment.getContent())
                    .postedAgo(DateTimeUtil.calculatePostedAgo(comment.getCreatedAt()))
                    .build();
        }
    }

    public static CreateCommentResponseDto of(User user, Boolean isMe, Comment comment) {
        return CreateCommentResponseDto.builder()
                .writerInfo(WriterInfoDto.of(user, isMe))
                .commentInfo(CommentInfoDto.fromEntity(comment))
                .build();
    }
}
