package kr.magicbox.shortform.application.service;

import kr.magicbox.shortform.application.dto.query.GetShortFormQuery;
import kr.magicbox.shortform.application.dto.result.ShortFormResult;
import kr.magicbox.shortform.application.port.in.GetShortFormUseCase;
import kr.magicbox.shortform.application.port.out.CreatorProfileQueryPort;
import kr.magicbox.shortform.application.port.out.ShortFormLikeRepositoryPort;
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
    private final ShortFormLikeRepositoryPort shortFormLikeRepositoryPort;

    @Override
    @Transactional(readOnly = true)
    public ShortFormResult getShortForm(GetShortFormQuery query) {
        ShortForm shortForm = shortFormRepositoryPort.findById(query.shortFormId())
                .orElseThrow(ShortFormNotFoundException::new);
        CreatorProfileQueryPort.CreatorProfile profile = creatorProfileQueryPort.getCreatorProfile(shortForm.getCreatorId());
        boolean isLiked = shortFormLikeRepositoryPort.existsByShortFormIdAndUserId(query.shortFormId(), query.userId());
        return ShortFormResult.from(shortForm, profile, isLiked);
    }
}
