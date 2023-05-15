package howisitgoing.junitstudy.example;

import org.junit.jupiter.api.*;

// 테스트 순서 정하기
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderTest {
    // Order의 숫자가 낮을 수록 먼저 실행
    @Test
    @DisplayName("4번으로 실행")
    @Order(4)
    void test4() {
        System.out.println(4);
    }

    @Test
    @DisplayName("1번으로 실행")
    @Order(1)
    void test1() {
        System.out.println(1);
    }

    @Test
    @DisplayName("2번으로 실행")
    @Order(2)
    void test2() {
        System.out.println(2);
    }

    @Test
    @DisplayName("3번으로 실행")
    @Order(3)
    void test3a() {
        System.out.println(3);
    }
}
