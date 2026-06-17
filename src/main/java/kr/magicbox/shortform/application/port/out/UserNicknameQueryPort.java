package kr.magicbox.shortform.application.port.out;

import java.util.List;
import java.util.Map;

public interface UserNicknameQueryPort {
    Map<Long, String> getNicknamesBatch(List<Long> userIds);
}
