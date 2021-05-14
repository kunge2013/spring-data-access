package com.convertools.repository;

import com.convertools.entity.CusIntIOTEntity;
import com.convertools.entity.MtsUploadRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author fangkun
 * @date 2021/5/13 12:52
 * @description:
 */
@Repository
public interface MtsUploadRecordRepository extends JpaRepository<MtsUploadRecord, Long> {

    MtsUploadRecord findByFileNameAndSampleNo(String fileName, String sampleNo);

    List<MtsUploadRecord> findByUploadStatus(int uploadStatus);

}
