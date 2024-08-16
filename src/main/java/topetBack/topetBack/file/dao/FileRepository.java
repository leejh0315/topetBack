package topetBack.topetBack.file.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import topetBack.topetBack.file.domain.FileGroupEntity;
import topetBack.topetBack.file.domain.FileInfoEntity;

@Repository
public interface FileRepository extends JpaRepository<FileGroupEntity, Long>{
	FileInfoEntity save(FileInfoEntity fileInfoEntity);

}
