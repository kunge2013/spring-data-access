package com.convertools.handler;

import com.convertools.boot.Application;
import com.convertools.entity.CusIntIOTEntity;
import com.convertools.repository.CusIntIOTRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class DemoApplicationTests {

    @Qualifier(value = "cusIntIOTRepository")
    @Autowired
    private CusIntIOTRepository cusIntIOTRepository;

    @Test
    public void testQuery() {
       List<CusIntIOTEntity> list = cusIntIOTRepository.findAll();
        System.out.println(list);
    }

    @Test
    public void testSave() {
        CusIntIOTEntity cusIntIOTEntity = new CusIntIOTEntity();
        cusIntIOTEntity.setEcoder("XXX");
        cusIntIOTEntity.setDocNo("doc001");
        cusIntIOTEntity.setCreatedOn("2021-03-10 00:01:00");
        cusIntIOTEntity.setTestOn("2021-03-10 00:01:00");
        cusIntIOTEntity.setTestBy("管理员");
        CusIntIOTEntity save = cusIntIOTRepository.save(cusIntIOTEntity);
        System.out.println(save);
    }


    @Test
    public void testfindByeitemAndsamppleNoAndecoder() {
        CusIntIOTEntity cusIntIOTEntity = new CusIntIOTEntity();
        cusIntIOTEntity.setSampleNo("159753-1");
        cusIntIOTEntity.setEcoder("美特斯摆锤机");
        cusIntIOTEntity.setEitem("平均冲击功(J)");
        CusIntIOTEntity byeitemAndsamppleNoAndecoder = cusIntIOTRepository.findByEitemAndSampleNoAndEcoder(cusIntIOTEntity.getEitem(), cusIntIOTEntity.getSampleNo(), cusIntIOTEntity.getEcoder());
        System.out.println(byeitemAndsamppleNoAndecoder);
    }
}
