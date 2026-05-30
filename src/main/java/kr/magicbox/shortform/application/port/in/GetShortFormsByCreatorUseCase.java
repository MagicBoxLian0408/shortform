package kr.magicbox.shortform.application.port.in;

import kr.magicbox.shortform.application.dto.query.GetShortFormsByCreatorQuery;
import kr.magicbox.shortform.application.dto.result.ShortFormResult;

import java.util.List;

public interface GetShortFormsByCreatorUseCase {
    List<ShortFormResult> getShortFormsByCreator(GetShortFormsByCreatorQuery query);
}
