package howisitgoing.junitstudy.example;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;
import org.springframework.test.context.junit.jupiter.EnabledIf;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

// Method와 Class 레퍼런스를 사용해서 테스트 이름을 표기하는 방법을 설정
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StudyTest {

    @Test
    @DisplayName("조건에 따라 테스트 실행하기")
    void assumptionsTest() {
        // 특정 조건을 만족하는 경우에만 테스트를 실행하는 방법
        String testEnv = System.getenv("TEST_ENV"); // 환경변수를 읽어옴
        System.out.println(testEnv);
        Study actual = new Study(10);
        assumingThat("LOCAL".equalsIgnoreCase(testEnv), () -> {
            assertAll(
                    () -> assertTrue(actual.getLimit() > 10),
                    () -> assertTrue(actual.getStatus().equals(StudyStatus.DRAFT))
            );
        });
    }

    @Test
    @DisplayName("조건에 따라 테스트 실행하기")
    @EnabledIfEnvironmentVariable(named = "TEST_ENV", matches = "LOCAL")
    void assumptionsTest2() {
        // 특정 조건을 만족하는 경우에만 테스트를 실행하는 방법
        String testEnv = System.getenv("TEST_ENV"); // 환경변수를 읽어옴
        System.out.println(testEnv);
        Study actual = new Study(10);
        assertAll(
                () -> assertTrue(actual.getLimit() > 10),
                () -> assertTrue(actual.getStatus().equals(StudyStatus.DRAFT))
        );
    }

    @Test
    @DisplayName("조건에 따라 테스트 실행하기")
    @EnabledOnOs({OS.MAC, OS.LINUX})
    void testByOS() {
        // .. 테스트 코드 .. //
    }


    @Test
    @DisplayName("조건에 따라 테스트 실행하기")
    @EnabledOnJre({JRE.JAVA_11})
    void testByJRE() {
        // .. 테스트 코드 .. //
    }

//    @Test
//    @DisplayName("스터디 만들기")
//    void my_new_test() {
//        Study study = new Study(10);
//        assertNotNull(study);
//
//        // expected, actual
//        assertEquals(StudyStatus.DRAFT, study.getStatus(), "스터디를 처음 만들면 상태값이 DRAFT여야 한다.");
//
//        // 람다로 작성하면 지연연산을 하기 때문에
//        // 테스트가 실패 했을때만 문구를 표시한다.
//        // 성능에 도움이 된다.
//        assertEquals(StudyStatus.DRAFT, study.getStatus(), () -> "스터디를 처음 만들면 상태값이 " +StudyStatus.DRAFT+ "여야 한다.");
//
//        assertTrue(study.getLimit() > 0, () -> "스터디 최대 참석 인원은 0보다 커야 한다.");
//
//        // 중간에 테스트가 깨져도 다음 테스트를 수행한다.
//        assertAll(
//                () -> assertNotNull(study),
//                () -> assertEquals(StudyStatus.DRAFT, study.getStatus(), "스터디를 처음 만들면 상태값이 DRAFT여야 한다."),
//                () -> assertEquals(StudyStatus.DRAFT, study.getStatus(), () -> "스터디를 처음 만들면 상태값이 " +StudyStatus.DRAFT+ "여야 한다."),
//                () -> assertTrue(study.getLimit() > 0, () -> "스터디 최대 참석 인원은 0보다 커야 한다."));
//    }
//
//    @Test
//    void assertNotNullTest() {
//        Study study = new Study(10);
//    }
//
//    @Test
//    void create() {
//        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Study(-10));
//        String message = e.getMessage();
//        assertEquals("limit은 0보다 작습니다.", message);
//    }
//
//    @Test
//    void assertTimeoutTest() {
//        // 특정 시간안에 실행이 완료되는지 확인
//        // 단점으로 로직완료까지 기다림
//        assertTimeout(Duration.ofMillis(1000), () -> {
//            new Study(10);
//            Thread.sleep(1100);
//        });
//    }
//
//    @Test
//    void assertTimeoutPreemptivelyTest() {
//        /**
//         * 특정 시간안에 실행이 안되면 바로 테스트 종료
//         * 별도의 스레드에서 코드블록을 실행함
//         * ThreadLocal을 사용하는 로직은 주의해야 합니다.
//         *
//         * 예를 들어 스프링 트랜잭션 처리는 기본적으로 ThreadLocal 전략을 사용합니다.
//         * (ThreadLocal은 다른 스레드간 메모리 공유가 불가합니다.)
//         * assertTimeoutPreemptively()는 별도의 스레드에서 코드블록을 실행하기 때문에
//         * 스프링의 트랜잭션 설정을 가지고 있는 스레드(ThreadLocal을 사용)를 실행할 수 없습니다.
//         *
//         * 따라서 롤백이 안되고 DB에 그대로 반영될 수도 있습니다.^^
//         */
//
//        assertTimeoutPreemptively(Duration.ofMillis(100), () -> {
//            new Study(10);
//            Thread.sleep(1100);
//        });
//
//        // TODO ThreadLocal
//    }
//
//    @Test
//    void assertJTest() {
//        Study study = new Study(10);
//        org.assertj.core.api.Assertions.assertThat(study.getLimit()).isEqualTo(10);
//    }
//
//    @BeforeAll
//    static void beforeAll() {
//        System.out.println("BeforeAll는 클래스 내에 모든 테스트 실행전 1회만 호출 된다. 반드시 반환 타입을 static void로 작성해야 한다.");
//    }
//
//    @AfterAll
//    static void afterAll() {
//        System.out.println("AfterAll은 클래스 내에 모든 테스트 실행 후 1회만 호출된다. 반드시 반환 타입을 static void로 작성");
//    }
//
//    @BeforeEach
//    void beforeEach() {
//        System.out.println("BeforeEach는 각각의 테스트 실행 전에 1회씩 호출된다.");
//    }
//
//    @AfterEach
//    void afterEach() {
//        System.out.println("AfterEach는 각각의 테스트 실행 후 1회씩 호출된다.");
//    }
//
//    @Disabled
//    void disabled() {
//        System.out.println("Disabled는 테스트를 실행하지 않는다.");
//    }
}