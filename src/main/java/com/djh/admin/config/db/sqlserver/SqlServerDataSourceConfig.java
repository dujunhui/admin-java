package com.djh.admin.config.db.sqlserver;

import com.alibaba.druid.pool.xa.DruidXADataSource;
import com.atomikos.jdbc.AtomikosDataSourceBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;

@Component
@Configuration
@MapperScan(basePackages = "com.djh.admin.dao.sqlserver", sqlSessionFactoryRef = "sqlServerSqlSessionFactory")
@Slf4j
public class SqlServerDataSourceConfig {
    @Autowired
    private SqlServerDBProperties dbProperties;

    @Bean(name = "sqlServerDataSource")
    @Primary
    public DataSource sqlServerDataSource() {
        DruidXADataSource dataSource = new DruidXADataSource();
        dataSource.setDriverClassName(dbProperties.getDriverClassName());
        dataSource.setUrl(dbProperties.getUrl());
        dataSource.setValidationQuery(dbProperties.getValidationQuery());
        dataSource.setTestWhileIdle(true);
        dataSource.setUsername(dbProperties.getUsername());
        try {
            dataSource.setFilters("stat");
        } catch (SQLException e) {
            log.error("error",e);
        }
        dataSource.setPassword(dbProperties.getPassword());
        AtomikosDataSourceBean atomikosDataSource = new AtomikosDataSourceBean();
        atomikosDataSource.setXaDataSource(dataSource);
        atomikosDataSource.setUniqueResourceName("sqlServerDataSource");
        return atomikosDataSource;
    }

    @Bean(name = "sqlServerSqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlServerSqlSessionFactory(@Qualifier("sqlServerDataSource") DataSource sqlServerDataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(sqlServerDataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactory.setMapperLocations(resolver.getResources("/mapper/sqlserver/*.xml"));
        sessionFactory.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
        return sessionFactory.getObject();
    }

//    // 定义事务
//    @Bean(name = "sqlserverTransactionManager")
//    @Primary
//    public DataSourceTransactionManager sqlserverTransactionManager(@Qualifier("sqlServerDataSource") DataSource sqlServerDataSource) {
//        return new DataSourceTransactionManager(sqlServerDataSource);
//    }

    /**
     * SqlSessionTemplate 是 SqlSession接口的实现类，是spring-mybatis中的，实现了SqlSession线程安全
     *
     * @param sqlSessionFactory
     * @return
     */
    @Bean(name="test1SqlSessionTemplate")
    @Primary
    public SqlSessionTemplate test1SqlSessionTemplate(@Qualifier("sqlServerSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
