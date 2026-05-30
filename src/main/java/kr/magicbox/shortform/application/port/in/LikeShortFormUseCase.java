package kr.magicbox.shortform.application.port.in;

import kr.magicbox.shortform.application.dto.command.LikeShortFormCommand;

public interface LikeShortFormUseCase {
    void likeShortForm(LikeShortFormCommand command);
}
