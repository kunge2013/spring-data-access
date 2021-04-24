package com.convertools.config;

import com.convertools.entity.CusIntIOTEntity;
import com.convertools.entity.DocNoGen;
import com.convertools.swing.SubmitDataFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.CountDownLatch;


/**
 * @author fangkun
 * @date 2021/4/14 21:34
 * @description:
 */
@Component
@Slf4j
public class Validator {

    // 委托单号校验
    public void validateInputEncoder(List<CusIntIOTEntity> cusIntIOTEntities) throws InterruptedException {
        boolean needInput = false;
        if (cusIntIOTEntities.isEmpty()) {
            throw new RuntimeException("data is null");
        }

        if (!cusIntIOTEntities.isEmpty()) {
            for (CusIntIOTEntity cusIntIOTEntity : cusIntIOTEntities) {
                if (StringUtils.isEmpty(cusIntIOTEntity.getDocNo() )) {
                    needInput = true;
                    break;
                }
            }
            if (needInput) {
                DocNoGen docNoGen = DocNoGen.builder().build();
                CountDownLatch countDownLatch = new CountDownLatch(1);
                new SubmitDataFrame(docNoGen, countDownLatch);
                countDownLatch.await();
                for (CusIntIOTEntity cusIntIOTEntity : cusIntIOTEntities) {
                    cusIntIOTEntity.setDocNo(docNoGen.getDocNo());
                    cusIntIOTEntity.setSampleNo(String.format("%s%s", docNoGen.getDocNo(), cusIntIOTEntity.getSampleNo()));
                }
            }
        }
    }

}
