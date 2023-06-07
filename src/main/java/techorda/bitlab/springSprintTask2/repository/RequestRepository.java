package techorda.bitlab.springSprintTask2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import techorda.bitlab.springSprintTask2.model.ApplicationRequest;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<ApplicationRequest, Long> {
    List<ApplicationRequest> findAllByHandledEquals(Boolean handled);
}
