package topetBack.topetBack.file.dao;


import org.springframework.data.jpa.repository.JpaRepository;

import topetBack.topetBack.file.domain.FileGroupEntity;

public interface FileRepository extends JpaRepository<FileGroupEntity, Long>{

}
