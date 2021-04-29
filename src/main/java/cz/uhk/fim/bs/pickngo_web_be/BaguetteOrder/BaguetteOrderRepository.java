package cz.uhk.fim.bs.pickngo_web_be.BaguetteOrder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BaguetteOrderRepository extends JpaRepository<BaguetteOrder, Long> {

    Optional<List<BaguetteOrder>> findAllByCustomer_Id(Long customerId);
    Optional<List<BaguetteOrder>> findAllByState(int state);
}
