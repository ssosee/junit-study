package howisitgoing.junitstudy.repository;

import howisitgoing.junitstudy.example.Study;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRepository extends JpaRepository<Study, Long> {
}
