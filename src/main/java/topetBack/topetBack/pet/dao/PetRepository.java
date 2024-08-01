package topetBack.topetBack.pet.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import topetBack.topetBack.pet.domain.PetEntity;

@Repository
public interface PetRepository extends JpaRepository<PetEntity, Object> , JpaSpecificationExecutor<PetEntity>{

    PetEntity getById(Long id);
    PetEntity getByUid(String uid);
    @Query("SELECT p FROM PetEntity p WHERE p.uid = :uid")
    Optional<PetEntity> findByUid(@Param("uid") String uid);
//    Optional<PetEntity> getByUid(String uid);
    
}
