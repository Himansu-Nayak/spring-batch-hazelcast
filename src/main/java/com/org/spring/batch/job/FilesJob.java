package com.org.spring.batch.job;

import com.org.spring.batch.listener.ProtocolListener;
import com.org.spring.batch.pojo.Name;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import javax.sql.DataSource;

@Configuration
public class FilesJob {

    private static final String OVERRIDDEN_BY_EXPRESSION = null;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    public DataSource dataSource;

    @Value("${watched.path.inprogress}")
    private String inprogress;

    @Value("${chunk.size}")
    private int chunkSize;

    @Bean
    public Job paneljob() {
        System.out.println("registering job bean for points job");

        Job job = jobBuilderFactory.get("filejob")
                                   .listener(protocolListener())
                                   .start(step1())
                                   .build();
        return job;
    }

    @Bean
    public Step step1() {

        return stepBuilderFactory.get("step1")
                .<Name, Name>chunk(chunkSize)
                .reader(reader(OVERRIDDEN_BY_EXPRESSION))
                .writer(writer())
                .build();
    }

    @Bean
    @StepScope
    public FlatFileItemReader<Name> reader(@Value("#{jobParameters[filename]}") String filename) {

        FlatFileItemReader<Name> reader = new FlatFileItemReader<Name>();
        reader.setResource(new FileSystemResource("/" + inprogress + "/" + filename) {});
        reader.setLineMapper(new DefaultLineMapper<Name>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[]{"name", "country"
                });
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Name>() {{
                setTargetType(Name.class);
            }});


        }});
        return reader;
    }


    @Bean
    public ProtocolListener protocolListener() {
        return new ProtocolListener();
    }

    @Bean
    public JdbcBatchItemWriter<Name> writer() {
        JdbcBatchItemWriter<Name> writer = new JdbcBatchItemWriter<Name>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Name>());
        writer.setSql("INSERT INTO people (first_name, last_name) VALUES (:firstName, :lastName)");
        writer.setDataSource(dataSource);
        return writer;
    }
}
