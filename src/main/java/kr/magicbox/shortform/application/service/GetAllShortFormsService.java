package kr.magicbox.shortform.application.service;

import kr.magicbox.shortform.application.dto.query.GetAllShortFormsQuery;
import kr.magicbox.shortform.application.dto.result.ShortFormResult;
import kr.magicbox.shortform.application.port.in.GetAllShortFormsUseCase;
import kr.magicbox.shortform.application.port.out.CreatorProfileQueryPort;
import kr.magicbox.shortform.application.port.out.ShortFormLikeRepositoryPort;
import kr.magicbox.shortform.application.port.out.ShortFormRepositoryPort;
import kr.magicbox.shortform.domain.aggregate.ShortForm;
import kr.magicbox.shortform.domain.vo.CreatorId;
import kr.magicbox.shortform.domain.vo.ShortFormId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class GetAllShortFormsService implements GetAllShortFormsUseCase {

    private final ShortFormRepositoryPort shortFormRepositoryPort;
    private final CreatorProfileQueryPort creatorProfileQueryPort;
    private final ShortFormLikeRepositoryPort shortFormLikeRepositoryPort;

    @Override
    @Transactional(readOnly = true)
    public List<ShortFormResult> getAllShortForms(GetAllShortFormsQuery query) {
        List<ShortForm> shortForms = shortFormRepositoryPort.findAllByCursor(query.cursorId(), query.size() + 1);

        List<CreatorId> creatorIds = shortForms.stream()
                .map(ShortForm::getCreatorId)
                .distinct()
                .toList();
        Map<Long, CreatorProfileQueryPort.CreatorProfile> profiles = creatorProfileQueryPort.getCreatorProfilesBatch(creatorIds).join();

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
