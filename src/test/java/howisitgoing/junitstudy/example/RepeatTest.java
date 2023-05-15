package howisitgoing.junitstudy.example;

import org.assertj.core.util.Strings;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.springframework.test.util.AssertionErrors.assertEquals;

public class RepeatTest {
    @RepeatedTest(10)
    void repeatTest(RepetitionInfo repetitionInfo) {
        System.out.println("test "+ repetitionInfo.getCurrentRepetition());
    }

    @DisplayName("스터디 만들기")
    @RepeatedTest(value = 10, name = "{displayName}")
    void repeatTest2(RepetitionInfo repetitionInfo) {
        System.out.println("test "+ repetitionInfo.getCurrentRepetition());
    }

    @DisplayName("반복테스트")
    @ParameterizedTest(name = "{index} {displayName} message={0}")
    @ValueSource(strings = {"날씨가", "많이", "추워지고", "있습니다."})
    void parameterizedTest(String message) {
        System.out.println(message);
    }

    @DisplayName("반복테스트")
    @ParameterizedTest(name = "{index} {displayName} message={0}")
    @ValueSource(strings = {"안녕하세요", "여러분", "지금부터", "춤을 추며 놀아요"})
    @NullAndEmptySource
    void parameterizedTestWithNullAndEmpty(String message) {
        System.out.println(message);
    }

    @DisplayName("반복테스트")
    @ParameterizedTest(name = "{index} {displayName} message={0}")
    @ValueSource(ints = {10, 20, 30})
    void parameterizedTest1(@ConvertWith(StudyConverter.class) Study study) {
        System.out.println(study.getLimit());
    }

    // SimpleArgumentConverter을 상속받아 컨버터 구현
    static class StudyConverter extends SimpleArgumentConverter {
        @Override
        protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
            // targetType이 Study.class
            Assertions.assertEquals(Study.class, targetType, "Can Only convert to Study");
            return new Study(Integer.parseInt(source.toString()));
        }
    }

    @DisplayName("반복테스트")
    @ParameterizedTest(name = "{index} {displayName}, message={0}")
    @CsvSource({"10, 자바 테스트", "20, 스프링"})
    void parameterTest2(Integer limit, String name) {
        System.out.println(new Study(limit, name));
    }

    @DisplayName("반복테스트")
    @ParameterizedTest(name = "{index} {displayName}, message={0}")
    @CsvSource({"10, ArgumentsAccessor 테스트", "20, 스프링"})
    void parameterTestArgumentAccessor(ArgumentsAccessor argumentsAccessor) {
        Study study = new Study(argumentsAccessor.getInteger(0), argumentsAccessor.getString(1));
        System.out.println(study);
    }

    @DisplayName("반복테스트")
    @ParameterizedTest(name = "{index} {displayName}, message={0}")
    @CsvSource({"100, StudyAggregator 테스트", "20, 스프링"})
    void parameterTestArgumentAggregator(@AggregateWith(StudyAggregator.class) Study study) {
        System.out.println(study);
    }

    static class StudyAggregator implements ArgumentsAggregator {
        @Override
        public Object aggregateArguments(ArgumentsAccessor accessor, ParameterContext context) throws ArgumentsAggregationException {
            Study study = new Study(accessor.getInteger(0), accessor.getString(1));
            return study;
        }
    }

}
