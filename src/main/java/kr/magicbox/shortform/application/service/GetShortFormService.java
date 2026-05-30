package kr.magicbox.shortform.application.service;

import kr.magicbox.shortform.application.dto.query.GetShortFormQuery;
import kr.magicbox.shortform.application.dto.result.ShortFormResult;
import kr.magicbox.shortform.application.port.in.GetShortFormUseCase;
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

    @Transactional(readOnly = true)
    @Override
    public ShortFormResult getShortForm(GetShortFormQuery query) {
        ShortForm shortForm = shortFormRepositoryPort.findById(query.shortFormId())
                .orElseThrow(ShortFormNotFoundException::new);
        return toResult(shortForm);
    }

    static ShortFormResult toResult(ShortForm shortForm) {
        return ShortFormResult.builder()
                .id(shortForm.getId())
                .creatorId(shortForm.getCreatorId())
                .title(shortForm.getTitle())
                .description(shortForm.getDescription())
                .videoUuid(shortForm.getVideoUuid())
                .thumbnailUuid(shortForm.getThumbnailUuid())
                .genre(shortForm.getGenre())
                .visibility(shortForm.getVisibility())
                .likeCount(shortForm.getLikeCount())
                .commentCount(shortForm.getCommentCount())
                .viewCount(shortForm.getViewCount())
                .build();
    }
}
