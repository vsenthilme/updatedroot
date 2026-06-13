package com.courier.overc360.api.batch.config;

import com.courier.overc360.api.batch.mapper.ErrorLogFieldSetMapper;
import com.courier.overc360.api.config.PropertiesConfig;
import com.courier.overc360.api.model.dto.ErrorLogdto;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
@EnableScheduling
public class JobConfiguration extends DefaultBatchConfigurer {
    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public DataSource dataSource;

    @Autowired
    PropertiesConfig propertiesConfig;


    //---------------------------ErrorLog--------------------------------------------------------------------//
    @Bean
    public FlatFileItemReader<ErrorLogdto> errorLogItemReader() {
        FlatFileItemReader<ErrorLogdto> reader = new FlatFileItemReader<>();
        reader.setLinesToSkip(1);
        reader.setResource(new FileSystemResource(propertiesConfig.getErrorLogFolderName() + propertiesConfig.getErrorLogFileName()));

        DefaultLineMapper<ErrorLogdto> customerLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames(new String[]{"logId", "logDate", "errorMessage", "languageId", "companyId", "refDocNumber", "method",
                "referenceField1", "referenceField2", "referenceField3", "referenceField4", "referenceField5", "referenceField6",
                "referenceField7", "referenceField8", "referenceField9", "referenceField10", "createdBy"});
        customerLineMapper.setLineTokenizer(tokenizer);
        customerLineMapper.setFieldSetMapper(new ErrorLogFieldSetMapper());
        customerLineMapper.afterPropertiesSet();
        reader.setLineMapper(customerLineMapper);
        return reader;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Bean
    public JdbcBatchItemWriter<ErrorLogdto> errorLogItemWriter() {
        JdbcBatchItemWriter<ErrorLogdto> itemWriter = new JdbcBatchItemWriter<>();
        itemWriter.setDataSource(this.dataSource);
        itemWriter.setSql("INSERT INTO tblerrorlog"
                + " (LOG_DATE,ERR_MSG,LANG_ID,C_ID,REF_DOC_NO,METHOD,REF_FIELD_1,REF_FIELD_2,REF_FIELD_3,REF_FIELD_4,"
                + " REF_FIELD_5,REF_FIELD_6,REF_FIELD_7,REF_FIELD_8,REF_FIELD_9,REF_FIELD_10,CTD_BY)"
                + " VALUES (:logDate, :errorMessage, :languageId, :companyId, :refDocNumber, :method, :referenceField1,"
                + " :referenceField2, :referenceField3, :referenceField4, :referenceField5, :referenceField6, "
                + " :referenceField7, :referenceField8, :referenceField9, referenceField10, :createdBy)");
        itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider());
        itemWriter.afterPropertiesSet();
        return itemWriter;
    }

    @Bean
    public Step step16() {
        return stepBuilderFactory.get("step16").<ErrorLogdto, ErrorLogdto>chunk(10).reader(errorLogItemReader())
                .writer(errorLogItemWriter()).build();
    }


    /*-----------------------------------------------------------------------------------------*/
    @Bean
    public JobListener wmsListener() throws Exception {
        JobListener listener = new JobListener();
        return listener;
    }

    @Bean
    public Job errorlogJob() throws Exception {
        return jobBuilderFactory.get("jobErrorLog")
                .listener(wmsListener())
                .start(step16())
                .build();
    }

}