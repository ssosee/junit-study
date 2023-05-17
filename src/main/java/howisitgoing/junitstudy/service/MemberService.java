package howisitgoing.junitstudy.service;

import howisitgoing.junitstudy.domain.Member;
import howisitgoing.junitstudy.example.Study;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public interface MemberService {
    Optional<Member> findById(Long memberId);
    void validate(Long memberId);

    void notify(Study newStudy);

    void notify(Member owner);
}
