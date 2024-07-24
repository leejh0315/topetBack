package topetBack.topetBack.pet.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import topetBack.topetBack.pet.domain.PetEntity;

@Repository
public interface PetRepository extends JpaRepository<PetEntity, Long> , JpaSpecificationExecutor<PetEntity>{

    PetEntity getById(Long id);
    
    
}
