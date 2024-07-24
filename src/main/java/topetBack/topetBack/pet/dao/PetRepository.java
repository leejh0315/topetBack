package topetBack.topetBack.pet.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import topetBack.topetBack.pet.domain.PetEntity;

public interface PetRepository extends JpaRepository<PetEntity, Long> , JpaSpecificationExecutor<PetEntity>{

    PetEntity getById(Long id);
}
