package xws.team16.searchservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xws.team16.searchservice.model.MyImage;

@Repository
public interface MyImageRepository extends JpaRepository<MyImage, Long> {
}
