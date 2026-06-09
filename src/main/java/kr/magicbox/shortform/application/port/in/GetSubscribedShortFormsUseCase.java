package kr.magicbox.shortform.application.port.in;

import kr.magicbox.shortform.application.dto.query.GetSubscribedShortFormsQuery;
import kr.magicbox.shortform.application.dto.result.ShortFormResult;

import java.util.List;

public interface GetSubscribedShortFormsUseCase {
    List<ShortFormResult> getSubscribedShortForms(GetSubscribedShortFormsQuery query);
}
