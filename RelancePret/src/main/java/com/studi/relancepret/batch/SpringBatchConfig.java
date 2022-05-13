package com.studi.relancepret.batch;

import com.studi.relancepret.model.InfoPret;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.core.io.UrlResource;
import java.util.Random;

@Slf4j
@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private ItemWriter<InfoPret> infoPretItemWriter;

    Random random = new Random();
    int randomInt = random.nextInt();

    @Bean
    public Job relanceJob() throws Exception {
        try {
            Step step = stepBuilderFactory.get("step-load_pret")
                    .<InfoPret, InfoPret>chunk(100)
                    .reader(jsonItemReader())
                    .processor(infoPretProcessor())
                    .writer(infoPretItemWriter)
                    .build();

            return jobBuilderFactory.get("pret-data-load" + randomInt)
                    .incrementer(new RunIdIncrementer())
                    .start(step)
                    .build();
        }catch(Exception e){
            log.error("relanceJob : Erreur URL "+e.getMessage());
        }
        return null;
    }
    @Bean
    public JsonItemReader<InfoPret> jsonItemReader() throws Exception {
        try {

            return new JsonItemReaderBuilder<InfoPret>()
                    .jsonObjectReader(new JacksonJsonObjectReader(InfoPret.class))
                    .resource(new UrlResource(
                            "http://localhost:8081/batch?username=admin&password=admin"))
                    .name("InfoPretJsonItemReader")
                    .build();
        }catch(Exception e){
            log.error("jsonItemReader : Erreur lors de la lecture URL "+e.getMessage());
        }
        return null;
    }
    @Bean
    public ItemProcessor<InfoPret,InfoPret> infoPretProcessor() {
        return new InfoPretItemProcessor();
    }
}
