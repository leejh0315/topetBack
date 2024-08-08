package topetBack.topetBack.file.dao;


import org.springframework.data.jpa.repository.JpaRepository;

import topetBack.topetBack.file.domain.FileGroupEntity;
import topetBack.topetBack.file.domain.FileInfoEntity;

public interface FileRepository extends JpaRepository<FileGroupEntity, Long>{
	public FileInfoEntity save(FileInfoEntity fileInfoEntity);

}
