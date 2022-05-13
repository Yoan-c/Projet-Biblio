package com.studi.relancepret.batch;

import com.studi.relancepret.model.InfoPret;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InfoPretItemWriter implements ItemWriter<InfoPret> {



    @Override
    public void write(List<? extends InfoPret> list) throws Exception {
        return;
    }
}
