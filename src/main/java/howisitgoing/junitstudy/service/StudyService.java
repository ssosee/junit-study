package howisitgoing.junitstudy.service;

import howisitgoing.junitstudy.domain.Member;
import howisitgoing.junitstudy.example.Study;
import howisitgoing.junitstudy.repository.StudyRepository;

import java.util.Optional;

public class StudyService {
    private final MemberService memberService;
    private final StudyRepository studyRepository;

    public StudyService(MemberService memberService, StudyRepository studyRepository) {
        assert memberService != null;
        assert studyRepository != null;
        this.memberService = memberService;
        this.studyRepository = studyRepository;
    }

    public Study createNewStudy(Long memberId, Study study) {

        Optional<Member> member = memberService.findById(memberId);
        Member owner = member.orElseThrow(() -> new IllegalArgumentException("회원이 없습니다."));
        study.setOwner(owner);
        Study newStudy = studyRepository.save(study);

        memberService.notify(newStudy);
        memberService.notify(owner);

        return newStudy;
    }
}
