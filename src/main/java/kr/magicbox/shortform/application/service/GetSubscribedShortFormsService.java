package kr.magicbox.shortform.application.service;

import kr.magicbox.shortform.application.dto.query.GetSubscribedShortFormsQuery;
import kr.magicbox.shortform.application.dto.result.ShortFormResult;
import kr.magicbox.shortform.application.port.in.GetSubscribedShortFormsUseCase;
import kr.magicbox.shortform.application.port.out.CreatorProfileQueryPort;
import kr.magicbox.shortform.application.port.out.ShortFormLikeRepositoryPort;
import kr.magicbox.shortform.application.port.out.ShortFormRepositoryPort;
import kr.magicbox.shortform.application.port.out.SubscribedCreatorIdsQueryPort;
import kr.magicbox.shortform.domain.aggregate.ShortForm;
import kr.magicbox.shortform.domain.vo.CreatorId;
import kr.magicbox.shortform.domain.vo.ShortFormId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class GetSubscribedShortFormsService implements GetSubscribedShortFormsUseCase {

    private final SubscribedCreatorIdsQueryPort subscribedCreatorIdsQueryPort;
    private final ShortFormRepositoryPort shortFormRepositoryPort;
    private final CreatorProfileQueryPort creatorProfileQueryPort;
    private final ShortFormLikeRepositoryPort shortFormLikeRepositoryPort;

    @Override
    @Transactional(readOnly = true)
    public List<ShortFormResult> getSubscribedShortForms(GetSubscribedShortFormsQuery query) {
        List<Long> creatorIds = subscribedCreatorIdsQueryPort.getSubscribedCreatorIds(query.userId()).join();
        if (creatorIds.isEmpty()) {
            return Collections.emptyList();
        }

        List<ShortForm> shortForms = shortFormRepositoryPort.findByCreatorIdInByCursor(
                creatorIds, query.cursorId(), query.size() + 1);

        List<CreatorId> distinctCreatorIds = shortForms.stream()
                .map(ShortForm::getCreatorId)
                .distinct()
                .toList();
        Map<Long, CreatorProfileQueryPort.CreatorProfile> profiles =
                creatorProfileQueryPort.getCreatorProfilesBatch(distinctCreatorIds);

        List<ShortFormId> shortFormIds = shortForms.stream().map(ShortForm::getId).toList();
        Set<Long> likedIds = shortFormLikeRepositoryPort.findLikedShortFormIds(shortFormIds, query.userId());

        return shortForms.stream()
                .map(sf -> ShortFormResult.from(
                        sf,
                        profiles.get(sf.getCreatorId().value()),
                        likedIds.contains(sf.getId().value())
                ))
                .toList();
    }
}
