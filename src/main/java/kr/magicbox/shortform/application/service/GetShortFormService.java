package kr.magicbox.shortform.application.service;

import kr.magicbox.shortform.application.dto.query.GetShortFormQuery;
import kr.magicbox.shortform.application.dto.result.ShortFormResult;
import kr.magicbox.shortform.application.port.in.GetShortFormUseCase;
import kr.magicbox.shortform.application.port.out.CreatorProfileQueryPort;
import kr.magicbox.shortform.application.port.out.ShortFormRepositoryPort;
import kr.magicbox.shortform.domain.aggregate.ShortForm;
import kr.magicbox.shortform.domain.exception.ShortFormNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetShortFormService implements GetShortFormUseCase {

    private final ShortFormRepositoryPort shortFormRepositoryPort;
    private final CreatorProfileQueryPort creatorProfileQueryPort;

    @Override
    @Transactional(readOnly = true)
    public ShortFormResult getShortForm(GetShortFormQuery query) {
        ShortForm shortForm = shortFormRepositoryPort.findById(query.shortFormId())
                .orElseThrow(ShortFormNotFoundException::new);
        return toResult(shortForm, creatorProfileQueryPort.getCreatorProfile(shortForm.getCreatorId()));
    }

    static ShortFormResult toResult(ShortForm shortForm, CreatorProfileQueryPort.CreatorProfile profile) {
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
                .build();
    }
}
