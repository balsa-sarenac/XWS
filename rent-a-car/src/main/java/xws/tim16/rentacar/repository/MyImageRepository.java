package xws.tim16.rentacar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xws.tim16.rentacar.model.MyImage;

@Repository
public interface MyImageRepository extends JpaRepository<MyImage, Long> {
}
