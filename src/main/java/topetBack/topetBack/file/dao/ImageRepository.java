package topetBack.topetBack.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import topetBack.topetBack.domain.Image;
import topetBack.topetBack.domain.Member;

public interface ImageRepository extends JpaRepository<Image, Long>{

}
