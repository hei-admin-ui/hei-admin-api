package school.hei.haapi.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import school.hei.haapi.model.DelayPenalty;

import java.time.Instant;
import java.util.List;

@Repository
public interface DelayPenaltyRepository extends JpaRepository<DelayPenalty, String> {

    @Query("select dp from DelayPenalty dp where dp.creationDatetime <= :creationDateTime")
    List<DelayPenalty> getCurrentDelayPenalty(@Param("creationDateTime") Instant creationDateTime, Pageable pageable);
}
