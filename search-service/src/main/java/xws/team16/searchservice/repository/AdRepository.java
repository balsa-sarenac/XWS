package xws.team16.searchservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import xws.team16.searchservice.model.Ad;

@Repository
@EnableJpaRepositories
public interface AdRepository extends JpaRepository<Ad, Long> {
}
