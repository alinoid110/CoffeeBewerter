package org.example.CoffeeBewerter.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CafeRepository extends CrudRepository<CafeReview, Long> {

}
