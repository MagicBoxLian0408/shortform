package kr.magicbox.shortform.application.port.out;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface UserNicknameQueryPort {
    CompletableFuture<Map<Long, String>> getNicknamesBatch(List<Long> userIds);
}
