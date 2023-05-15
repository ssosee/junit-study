package howisitgoing.junitstudy.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

// 인스턴스 생성 전략 클래스 단위로 변경
// @TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class InstanceTest {
    int value = 1;

    @Test
    @DisplayName("value를 1증가 했기 때문에 2가 된다.")
    void test1() {
        System.out.println(this); // 메소드 해시
        value++; // 1증가
        Assertions.assertEquals(2, value);
    }

    @Test
    @DisplayName("value를 1증가 했기 때문에 3 된다.")
    void test2() {
        System.out.println(this); // 메소드 해시
        value++; // 1증가
        Assertions.assertEquals(3, value);
    }
}
