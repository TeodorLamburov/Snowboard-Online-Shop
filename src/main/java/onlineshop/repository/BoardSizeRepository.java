package onlineshop.repository;

import onlineshop.model.entities.BoardSize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardSizeRepository extends JpaRepository<BoardSize,String> {
    Optional<BoardSize> findBySize(String size);
}
