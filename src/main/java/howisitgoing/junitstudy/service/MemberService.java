package howisitgoing.junitstudy.service;

import howisitgoing.junitstudy.domain.Member;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public interface MemberService {
    Optional<Member> findById(Long memberId);
    void validate(Long memberId);
}
