package kr.magicbox.shortform.application.service;

import kr.magicbox.shortform.application.dto.query.GetShortFormsByCreatorQuery;
import kr.magicbox.shortform.application.dto.result.ShortFormResult;
import kr.magicbox.shortform.application.port.in.GetShortFormsByCreatorUseCase;
import kr.magicbox.shortform.application.port.out.CreatorProfileQueryPort;
import kr.magicbox.shortform.application.port.out.ShortFormLikeRepositoryPort;
import kr.magicbox.shortform.application.port.out.ShortFormRepositoryPort;
import kr.magicbox.shortform.domain.aggregate.ShortForm;
import kr.magicbox.shortform.domain.vo.ShortFormId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class GetShortFormsByCreatorService implements GetShortFormsByCreatorUseCase {

    private final ShortFormRepositoryPort shortFormRepositoryPort;
    private final CreatorProfileQueryPort creatorProfileQueryPort;
    private final ShortFormLikeRepositoryPort shortFormLikeRepositoryPort;

    @Override
    @Transactional(readOnly = true)
    public List<ShortFormResult> getShortFormsByCreator(GetShortFormsByCreatorQuery query) {
        List<ShortForm> shortForms = shortFormRepositoryPort.findByCreatorIdByCursor(
                query.creatorId(), query.cursorId(), query.size() + 1);

        CreatorProfileQueryPort.CreatorProfile profile = creatorProfileQueryPort.getCreatorProfile(query.creatorId()).join();

        List<ShortFormId> shortFormIds = shortForms.stream().map(ShortForm::getId).toList();
        Set<Long> likedIds = shortFormLikeRepositoryPort.findLikedShortFormIds(shortFormIds, query.userId());

        return shortForms.stream()
                .map(sf -> ShortFormResult.from(
                        sf,
                        profile,
                        likedIds.contains(sf.getId().value())
                ))
                .toList();
    }
}
