package org.fullstack4.hammerteen.repository;


import org.fullstack4.hammerteen.domain.CartEntity;
import org.fullstack4.hammerteen.domain.LectureEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<CartEntity, Integer> {


}
