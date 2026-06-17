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
    private final ShortFormResultMapper shortFormResultMapper;

    @Override
    @Transactional(readOnly = true)
    public ShortFormResult getShortForm(GetShortFormQuery query) {
        ShortForm shortForm = shortFormRepositoryPort.findById(query.shortFormId())
                .orElseThrow(ShortFormNotFoundException::new);
        return shortFormResultMapper.toResult(shortForm, query.userId());
    }
}
