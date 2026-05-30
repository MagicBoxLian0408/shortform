package kr.magicbox.shortform.application.port.in;

import kr.magicbox.shortform.application.dto.query.GetShortFormsByCreatorIdQuery;
import kr.magicbox.shortform.domain.aggregate.ShortForm;

import java.util.List;

public interface GetShortFormsByCreatorIdUseCase {
    List<ShortForm> getShortFormsByCreatorId(GetShortFormsByCreatorIdQuery query);
}
