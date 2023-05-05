package howisitgoing.junitstudy.example;

import lombok.Data;

@Data
public class CalmDownMan {
    private String name;
    private int age;
    private CalmDownManStatus status;

    public CalmDownMan(int age) {
        if(age < 0) {
            throw new IllegalArgumentException("나이는 0보다 커야합니다.");
        }
        this.age = age;
        this.status = CalmDownManStatus.OFF_AIR;
    }
}
