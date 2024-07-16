package topetBack.topetBack.comment.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import topetBack.topetBack.comment.domain.CommentEntity;


@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long>{

}
