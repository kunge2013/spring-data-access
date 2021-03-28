package com.convertools.repository;

import com.convertools.entity.CusIntIOTEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * @author fangkun
 * @date 2021/3/27 23:23
 * @description:
 */
@Repository
public interface CusIntIOTRepository extends JpaRepository<CusIntIOTEntity, Long> {

    CusIntIOTEntity findByEitemAndSampleNoAndEcoder(String eitem, String sampleNo, String ecoder);

}
