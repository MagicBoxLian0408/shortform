package kr.magicbox.shortform.application.dto.result;

import kr.magicbox.shortform.application.port.out.CreatorProfileQueryPort;
import kr.magicbox.shortform.domain.aggregate.ShortForm;
import kr.magicbox.shortform.domain.enums.MagicGenre;
import kr.magicbox.shortform.domain.vo.ShortFormId;
import lombok.Builder;

import java.time.Instant;

@Builder
public record ShortFormResult(
        ShortFormId id,
        CreatorInfo creator,
        String title,
        String description,
        String videoUuid,
        String thumbnailUuid,
        MagicGenre genre,
        Long likeCount,
        Long commentCount,
        Long viewCount,
        boolean isLiked,
        Instant createdAt
) {
    @Builder
    public record CreatorInfo(Long id, String nickname, String profileImage) {}

    public static ShortFormResult from(ShortForm shortForm, CreatorProfileQueryPort.CreatorProfile profile, boolean isLiked) {
        return ShortFormResult.builder()
                .id(shortForm.getId())
                .creator(CreatorInfo.builder()
                        .id(profile.creatorId())
                        .nickname(profile.nickname())
                        .profileImage(profile.profileImageUrl())
                        .build())
                .title(shortForm.getTitle())
                .description(shortForm.getDescription())
                .videoUuid(shortForm.getVideoUuid())
                .thumbnailUuid(shortForm.getThumbnailUuid())
                .genre(shortForm.getGenre())
                .likeCount(shortForm.getLikeCount())
                .commentCount(shortForm.getCommentCount())
                .viewCount(shortForm.getViewCount())
                .isLiked(isLiked)
                .createdAt(shortForm.getCreatedAt())
                .build();
    }
}
