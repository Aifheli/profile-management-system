package ayo.profile.management.repository;

import ayo.profile.management.entities.CustomerRegistrationEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Aifheli Maganya on 2022/05/19.
 */
@Repository
public interface CustomerRegistrationRepository extends PagingAndSortingRepository<CustomerRegistrationEntity, Long> {

    @Override
    <S extends CustomerRegistrationEntity> S save(S s);

    CustomerRegistrationEntity findById(long id);

    List<CustomerRegistrationEntity> findAll();

    @Transactional
    @Modifying
    @Query("update CustomerRegistrationEntity t set t.regStatus = :status where t.id = :id")
    void updateEntity(@Param(value = "id") Long id, @Param(value = "status") String status);

}
