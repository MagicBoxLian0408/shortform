package kr.magicbox.shortform.application.service;

import kr.magicbox.shortform.application.dto.query.GetShortFormQuery;
import kr.magicbox.shortform.application.dto.result.ShortFormResult;
import kr.magicbox.shortform.application.port.in.GetShortFormUseCase;
import kr.magicbox.shortform.application.port.out.CreatorProfileQueryPort;
import kr.magicbox.shortform.application.port.out.ShortFormLikeRepositoryPort;
import kr.magicbox.shortform.application.port.out.ShortFormRepositoryPort;
import kr.magicbox.shortform.domain.aggregate.ShortForm;
import kr.magicbox.shortform.domain.exception.ShortFormNotFoundException;
import kr.magicbox.shortform.domain.vo.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetShortFormService implements GetShortFormUseCase {

    private final ShortFormRepositoryPort shortFormRepositoryPort;
    private final CreatorProfileQueryPort creatorProfileQueryPort;
    private final ShortFormLikeRepositoryPort shortFormLikeRepositoryPort;

    @Override
    @Transactional(readOnly = true)
    public ShortFormResult getShortForm(GetShortFormQuery query) {
        ShortForm shortForm = shortFormRepositoryPort.findById(query.shortFormId())
                .orElseThrow(ShortFormNotFoundException::new);
        boolean isLiked = shortFormLikeRepositoryPort.existsByShortFormIdAndUserId(query.shortFormId(), query.userId());
        return toResult(shortForm, creatorProfileQueryPort.getCreatorProfile(shortForm.getCreatorId()), isLiked);
    }

    static ShortFormResult toResult(ShortForm shortForm, CreatorProfileQueryPort.CreatorProfile profile, boolean isLiked) {
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
