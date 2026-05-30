package kr.magicbox.shortform.application.port.in;

import kr.magicbox.shortform.application.dto.query.GetShortFormQuery;
import kr.magicbox.shortform.application.dto.result.ShortFormResult;

public interface GetShortFormUseCase {
    ShortFormResult getShortForm(GetShortFormQuery query);
}
