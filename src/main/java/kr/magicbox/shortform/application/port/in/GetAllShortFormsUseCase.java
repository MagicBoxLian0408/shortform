package kr.magicbox.shortform.application.port.in;

import kr.magicbox.shortform.application.dto.query.GetAllShortFormsQuery;
import kr.magicbox.shortform.application.dto.result.ShortFormResult;

import java.util.List;

public interface GetAllShortFormsUseCase {
    List<ShortFormResult> getAllShortForms(GetAllShortFormsQuery query);
}
