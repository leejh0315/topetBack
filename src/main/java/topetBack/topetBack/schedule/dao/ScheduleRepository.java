package topetBack.topetBack.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import topetBack.topetBack.domain.Member;

public interface ScheduleRepository  extends JpaRepository<Member, Integer>{

}
