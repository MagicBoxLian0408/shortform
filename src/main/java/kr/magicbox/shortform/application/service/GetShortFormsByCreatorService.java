package kr.magicbox.shortform.application.service;

import kr.magicbox.shortform.application.dto.query.GetShortFormsByCreatorQuery;
import kr.magicbox.shortform.application.dto.result.ShortFormResult;
import kr.magicbox.shortform.application.port.in.GetShortFormsByCreatorUseCase;
import kr.magicbox.shortform.application.port.out.CreatorProfileQueryPort;
import kr.magicbox.shortform.application.port.out.ShortFormRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetShortFormsByCreatorService implements GetShortFormsByCreatorUseCase {

    private final ShortFormRepositoryPort shortFormRepositoryPort;
    private final CreatorProfileQueryPort creatorProfileQueryPort;

    @Override
    @Transactional(readOnly = true)
    public List<ShortFormResult> getShortFormsByCreator(GetShortFormsByCreatorQuery query) {
        CreatorProfileQueryPort.CreatorProfile creatorProfile = creatorProfileQueryPort.getCreatorProfile(query.creatorId());
        return shortFormRepositoryPort.findByCreatorIdByCursor(query.creatorId(), query.cursorId(), query.size() + 1)
                .stream()
                .map(sf -> GetShortFormService.toResult(sf, creatorProfile))
                .toList();
    }
}
