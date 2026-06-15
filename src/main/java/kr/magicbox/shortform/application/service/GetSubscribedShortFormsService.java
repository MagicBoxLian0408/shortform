package kr.magicbox.shortform.application.service;

import kr.magicbox.shortform.application.dto.query.GetSubscribedShortFormsQuery;
import kr.magicbox.shortform.application.dto.result.ShortFormResult;
import kr.magicbox.shortform.application.port.in.GetSubscribedShortFormsUseCase;
import kr.magicbox.shortform.application.port.out.CreatorNicknameQueryPort;
import kr.magicbox.shortform.application.port.out.ShortFormRepositoryPort;
import kr.magicbox.shortform.application.port.out.SubscribedCreatorIdsQueryPort;
import kr.magicbox.shortform.domain.vo.CreatorId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetSubscribedShortFormsService implements GetSubscribedShortFormsUseCase {

    private final SubscribedCreatorIdsQueryPort subscribedCreatorIdsQueryPort;
    private final ShortFormRepositoryPort shortFormRepositoryPort;
    private final CreatorNicknameQueryPort creatorNicknameQueryPort;

    @Override
    @Transactional(readOnly = true)
    public List<ShortFormResult> getSubscribedShortForms(GetSubscribedShortFormsQuery query) {
        List<Long> creatorIds = subscribedCreatorIdsQueryPort.getSubscribedCreatorIds(query.userId());
        if (creatorIds.isEmpty()) {
            return Collections.emptyList();
        }
        return shortFormRepositoryPort.findByCreatorIdInByCursor(creatorIds, query.cursorId(), query.size() + 1)
                .stream()
                .map(sf -> GetShortFormService.toResult(sf, creatorNicknameQueryPort.getCreatorNickname(sf.getCreatorId())))
                .toList();
    }
}
