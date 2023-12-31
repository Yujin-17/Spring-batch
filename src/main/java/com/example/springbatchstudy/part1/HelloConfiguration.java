package com.example.springbatchstudy.part1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class HelloConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    public HelloConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public Job helloJob() { // spring batch 의 실행단위
        return jobBuilderFactory.get("helloJob")
                .incrementer(new RunIdIncrementer()) // 이 설정 추가시 항상 다른 JobInstance 실행
                .start(this.helloStep())
                .build();
    }

    @Bean
    public Step helloStep(){ // step -> job 의 실행단위. 1개의 Job 은 1개 이상의 step 을  가질 수 있다.
        return stepBuilderFactory.get("helloStep") //
                .tasklet((contribution, chunkContext) -> { // step의 실행단위. step의 실행단위는 tasklet / chunk 기반 두개가 존재.
                    log.info("hello spring batch");
                    return RepeatStatus.FINISHED;
                }).build();
    }

}
