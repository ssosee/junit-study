package howisitgoing.junitstudy.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class CalmDownManTest {
    @Test
    @DisplayName("assertEquals 테스트")
    void assertEqualsTest() {
        CalmDownMan calmDownMan = new CalmDownMan(40);
        // 람다로 작성하면 지연연산을 하기 때문에
        // 테스트가 실패 했을때만 문구를 표시한다.
        // 성능에 도움이 된다.
        assertEquals(calmDownMan.getAge(), 40, () -> "침착맨의 나이는 "+calmDownMan.getAge()+"살 입니다.");
        assertEquals(calmDownMan.getStatus(), CalmDownManStatus.OFF_AIR, () -> "침착맨을 생성하면 기본 상태는 "+CalmDownManStatus.OFF_AIR+" 입니다.");
    }

    @Test
    @DisplayName("assertNotNull 테스트")
    void assertNotNullTest() {
        CalmDownMan calmDownMan = new CalmDownMan(40);
        assertNotNull(calmDownMan, () -> "침착맨을 생성하면 null이 아닙니다.");
    }

    @Test
    @DisplayName("assertTrue 테스트")
    void assertTrueTest() {
        CalmDownMan calmDownMan = new CalmDownMan(40);
        assertTrue(calmDownMan.getAge() == 40, () -> "침착맨의 나이는 "+calmDownMan.getAge() +" 입니다.");
    }

    @Test
    @DisplayName("assertAll 테스트")
    void assertAllTest() {
        CalmDownMan calmDownMan = new CalmDownMan(40);
        assertAll(
                () -> assertEquals(calmDownMan.getAge(), 20, () -> "침착맨의 나이는 "+calmDownMan.getAge()+"살 입니다."),
                () -> assertEquals(calmDownMan.getStatus(), CalmDownManStatus.OFF_AIR, () -> "침착맨을 생성하면 기본 상태는 "+CalmDownManStatus.OFF_AIR+" 입니다."),
                () -> assertNotNull(calmDownMan, () -> "침착맨을 생성하면 null이 아닙니다."),
                () -> assertTrue(calmDownMan.getAge() > 40, () -> "침착맨의 나이는 "+calmDownMan.getAge() +" 입니다.")
        );
    }

    @Test
    @DisplayName("assertThrows 테스트")
    void assertThrowsTest() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new CalmDownMan(-10));
        String message = e.getMessage();
        assertEquals("나이는 0보다 커야합니다.", message);
    }

    @Test
    @DisplayName("assertTimeout 테스트")
    void assertTimeoutTest() {
        assertTimeout(Duration.ofMillis(100), () -> {
            CalmDownMan calmDownMan = new CalmDownMan(10);
            Thread.sleep(1000);
        });
    }

    @Test
    @DisplayName("assertTimeout 테스트")
    void assertTimeoutPreemptivelyTest() {
        /**
         * 특정 시간안에 실행이 안되면 바로 테스트 종료
         * 별도의 스레드에서 코드블록을 실행함
         * ThreadLocal을 사용하는 로직은 주의해야 합니다.
         *
         * 예를 들어 스프링 트랜잭션 처리는 기본적으로 ThreadLocal 전략을 사용합니다.
         * (ThreadLocal은 다른 스레드간 메모리 공유가 불가합니다.)
         * assertTimeoutPreemptively()는 별도의 스레드에서 코드블록을 실행하기 때문에
         * 스프링의 트랜잭션 설정을 가지고 있는 스레드(ThreadLocal을 사용)를 실행할 수 없습니다.
         *
         * 따라서 롤백이 안되고 DB에 그대로 반영될 수도 있습니다.^^
         */
        assertTimeoutPreemptively(Duration.ofMillis(100), () -> {
            CalmDownMan calmDownMan = new CalmDownMan(40);
            Thread.sleep(1000);
        });
    }
}