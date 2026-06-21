package kr.magicbox.shortform.application.port.out;

import kr.magicbox.shortform.domain.vo.CreatorId;

import java.util.concurrent.CompletableFuture;

public interface CreatorNicknameQueryPort {
    CompletableFuture<String> getCreatorNickname(CreatorId creatorId) throws Exception;
}
