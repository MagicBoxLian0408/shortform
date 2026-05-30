package kr.magicbox.shortform.application.service;

import kr.magicbox.shortform.application.dto.query.GetShortFormsByCreatorIdQuery;
import kr.magicbox.shortform.application.port.in.GetShortFormsByCreatorIdUseCase;
import kr.magicbox.shortform.application.port.out.ShortFormRepositoryPort;
import kr.magicbox.shortform.domain.aggregate.ShortForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetShortFormsByCreatorIdService implements GetShortFormsByCreatorIdUseCase {

    private final ShortFormRepositoryPort shortFormRepositoryPort;

    @Transactional(readOnly = true)
    @Override
    public List<ShortForm> getShortFormsByCreatorId(GetShortFormsByCreatorIdQuery query) {
        return shortFormRepositoryPort.findAllByCreatorId(query.creatorId());
    }
}
