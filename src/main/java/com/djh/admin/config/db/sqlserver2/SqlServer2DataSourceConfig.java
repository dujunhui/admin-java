package com.djh.admin.config.db.sqlserver2;

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
@MapperScan(basePackages = "com.djh.admin.dao.sqlserver2", sqlSessionFactoryRef = "sqlServer2SqlSessionFactory")
@Slf4j
public class SqlServer2DataSourceConfig {
    @Autowired
    private SqlServer2DBProperties dbProperties;

    @Bean(name = "sqlServer2DataSource")
    public DataSource sqlServer2DataSource() {
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
        atomikosDataSource.setUniqueResourceName("sqlServer2DataSource");
        return atomikosDataSource;
    }

    @Bean(name = "sqlServer2SqlSessionFactory")
    public SqlSessionFactory sqlServerSqlSessionFactory(@Qualifier("sqlServer2DataSource") DataSource sqlServer2DataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(sqlServer2DataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactory.setMapperLocations(resolver.getResources("/mapper/sqlserver2/*.xml"));
        sessionFactory.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
        return sessionFactory.getObject();
    }

    /**
     * SqlSessionTemplate 是 SqlSession接口的实现类，是spring-mybatis中的，实现了SqlSession线程安全
     *
     * @param sqlSessionFactory
     * @return
     */
    @Bean(name="test2SqlSessionTemplate")
    public SqlSessionTemplate test2SqlSessionTemplate(@Qualifier("sqlServer2SqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
