package kr.magicbox.shortform.application.service;

import kr.magicbox.shortform.application.dto.result.ShortFormResult;
import kr.magicbox.shortform.application.port.out.CreatorProfileQueryPort;
import kr.magicbox.shortform.application.port.out.ShortFormLikeRepositoryPort;
import kr.magicbox.shortform.domain.aggregate.ShortForm;
import kr.magicbox.shortform.domain.vo.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ShortFormResultMapper {

    private final CreatorProfileQueryPort creatorProfileQueryPort;
    private final ShortFormLikeRepositoryPort shortFormLikeRepositoryPort;

    public ShortFormResult toResult(ShortForm shortForm, UserId userId) {
        CreatorProfileQueryPort.CreatorProfile profile = creatorProfileQueryPort.getCreatorProfile(shortForm.getCreatorId());
        boolean isLiked = shortFormLikeRepositoryPort.existsByShortFormIdAndUserId(shortForm.getId(), userId);
        return ShortFormResult.builder()
                .id(shortForm.getId())
                .creator(ShortFormResult.CreatorInfo.builder()
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
