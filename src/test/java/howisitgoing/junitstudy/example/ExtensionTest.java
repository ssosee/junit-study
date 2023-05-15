package howisitgoing.junitstudy.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;

// @ExtendWith(FindSlowTestExtension.class)
public class ExtensionTest {

    @RegisterExtension
    static FindSlowTestExtension findSlowTestExtension = new FindSlowTestExtension(1000L);

    @Test
    @Order(1)
    @DisplayName("느린 테스트")
    @SlowTest
    void slowTest() throws InterruptedException {
        Thread.sleep(1100);
    }

    @Test
    @Order(2)
    @DisplayName("일반 테스트")
    void normalTest() {

    }
}
