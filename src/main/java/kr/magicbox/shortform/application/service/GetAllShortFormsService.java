package kr.magicbox.shortform.application.service;

import kr.magicbox.shortform.application.dto.query.GetAllShortFormsQuery;
import kr.magicbox.shortform.application.dto.result.ShortFormResult;
import kr.magicbox.shortform.application.port.in.GetAllShortFormsUseCase;
import kr.magicbox.shortform.application.port.out.ShortFormRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllShortFormsService implements GetAllShortFormsUseCase {

    private final ShortFormRepositoryPort shortFormRepositoryPort;

    @Transactional(readOnly = true)
    @Override
    public List<ShortFormResult> getAllShortForms(GetAllShortFormsQuery query) {
        return shortFormRepositoryPort.findAllByCursor(query.cursorId(), query.size() + 1)
                .stream()
                .map(GetShortFormService::toResult)
                .toList();
    }
}
