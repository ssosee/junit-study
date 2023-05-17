package howisitgoing.junitstudy.mock;

import howisitgoing.junitstudy.domain.Member;
import howisitgoing.junitstudy.example.Study;
import howisitgoing.junitstudy.repository.StudyRepository;
import howisitgoing.junitstudy.service.MemberService;
import howisitgoing.junitstudy.service.StudyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

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

}
