package howisitgoing.junitstudy.mock;

import howisitgoing.junitstudy.domain.Member;
import howisitgoing.junitstudy.example.Study;
import howisitgoing.junitstudy.repository.StudyRepository;
import howisitgoing.junitstudy.service.MemberService;
import howisitgoing.junitstudy.service.StudyService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

// @ExtendWith(MockitoExtension.class) 있어야 @Mock 사용 가능
@ExtendWith(MockitoExtension.class)
public class StudyServiceTest {

    // Mock 객체 생성
    @Mock
    MemberService memberService;
    @Mock
    StudyRepository studyRepository;

    @Test
    void createStudyService() {

        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        Member member = new Member();
        member.setId(1L);
        member.setName("장세웅");
        // memberId가 1일 때 member를 리턴하세요.
        when(memberService.findById(any())).thenReturn(Optional.of(member));

        Study study = new Study(10, "java");

        assertEquals("장세웅", memberService.findById(1L).get().getName());
        assertEquals("장세웅", memberService.findById(2L).get().getName());

        // memberService가 예외를 던지도록
        doThrow(new IllegalArgumentException()).when(memberService).validate(1L);

        assertThrows(IllegalArgumentException.class, () -> memberService.validate(1L));

        memberService.validate(2L);
    }

    @Test
    void createStudyService2() {
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        Member member = new Member();
        member.setId(1L);
        member.setName("장세웅");

        // stub
        when(memberService.findById(any()))
                .thenReturn(Optional.of(member))
                .thenThrow(new RuntimeException())
                .thenReturn(Optional.empty());

        Optional<Member> optionalMember = memberService.findById(1L);

        assertAll(
                () -> assertEquals("장세웅", optionalMember.get().getName()),
                () -> assertThrows(RuntimeException.class, () -> {
                    memberService.findById(1L);
                }),
                () -> assertEquals(Optional.empty(), memberService.findById(2L))
                );
    }


    /**
     * 연습문제
     * TODO memberService 객체에 findById 메소드를 1L 값으로 호출하면 Optional.of(member) 객체를 리턴하도록 Stubbing
     * TODO studyRepository 객체에 save 메소드를 study 객체로 호출하면 study 객체 그대로 리턴하도록 Stubbing
     */

    @Test
    void practiceStubbing() {

        StudyService studyService = new StudyService(memberService, studyRepository);

        Member member = new Member();
        member.setName("장세웅");
        member.setId(1L);
        // memberService 객체에 findById 메소드를 1L 값으로 호출하면 Optional.of(member) 객체를 리턴하도록 Stubbing
        when(memberService.findById(1L)).thenReturn(Optional.of(member));

        Study study = new Study(10, "테스팅");
        // studyRepository 객체에 save 메소드를 study 객체로 호출하면 study 객체 그대로 리턴하도록 Stubbing
        when(studyRepository.save(study)).thenReturn(study);

        studyService.createNewStudy(1L, study);
        assertNotNull(study.getName());
        assertEquals(member, study.getMember());

        assertAll(
                () -> assertNotNull(study.getMember()),
                () -> assertEquals(member, study.getMember())
        );
    }

    // Mock 객체 확인
    @Test
    @DisplayName("Mock 객체 확인")
    void createNewStudy() {
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        Member member = new Member();
        member.setId(1L);
        member.setName("장세웅");

        Study study = new Study(10, "Mock 객체 확인");

        when(memberService.findById(1L)).thenReturn(Optional.of(member));
        when(studyRepository.save(study)).thenReturn(study);

        studyService.createNewStudy(1L, study);

        assertEquals(member, study.getMember());

        // 특정 메소드가 특정 매개변수로 몇번 호출 되었는지
        verify(memberService, times(1)).notify(study);
        verify(memberService, times(1)).notify(member);
        verify(memberService, never()).validate(any());

        // 어떤 순서대로 호출했는지
        InOrder inOrder = inOrder(memberService);
        inOrder.verify(memberService).notify(study);
        inOrder.verify(memberService).notify(member);
    }
}
