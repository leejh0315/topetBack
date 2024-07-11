package topetBack.topetBack.file.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import topetBack.topetBack.file.domain.Image;
import topetBack.topetBack.user.domain.Member;

public interface ImageRepository extends JpaRepository<Image, Long>{
}
