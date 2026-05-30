package kr.magicbox.shortform.application.port.in;

import kr.magicbox.shortform.application.dto.query.GetCommentsByShortFormQuery;
import kr.magicbox.shortform.application.dto.result.CommentResult;

import java.util.List;

public interface GetCommentsByShortFormUseCase {
    List<CommentResult> getCommentsByShortForm(GetCommentsByShortFormQuery query);
}
