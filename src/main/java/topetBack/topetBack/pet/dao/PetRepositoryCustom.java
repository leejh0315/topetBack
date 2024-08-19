package topetBack.topetBack.pet.dao;

import org.springframework.stereotype.Repository;

@Repository
public interface PetRepositoryCustom {
	
	Long deleteByPetId(Long id);
}
