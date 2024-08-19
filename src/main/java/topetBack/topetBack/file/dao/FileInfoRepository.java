package topetBack.topetBack.file.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import topetBack.topetBack.file.domain.FileInfoEntity;

public interface FileInfoRepository extends JpaRepository<FileInfoEntity, Long> , CrudRepository<FileInfoEntity, Long>{
	
	
//	@Query("DELETE FROM FileInfo f WHERE f.file_path= :filePath")
	void deleteById(Long id);
}
