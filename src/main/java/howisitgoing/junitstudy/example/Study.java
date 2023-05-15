package howisitgoing.junitstudy.example;

import lombok.Data;
import lombok.Getter;

@Data
public class Study {

    private StudyStatus status;
    private int limit;
    private String name;

    public Study(int limit) {
        this.status = StudyStatus.DRAFT;
        if(limit < 0) {
            throw new IllegalArgumentException("limit은 0보다 작습니다.");
        }
        this.limit = limit;
    }

    public Study(int limit, String name) {
        this.limit = limit;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Study{" +
                "limit=" + limit +
                ", name='" + name + '\'' +
                '}';
    }
}
