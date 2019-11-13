package com.djh.admin.config.db.mysql;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.xa.DruidXADataSource;
import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;
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
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import javax.sql.XADataSource;
import java.io.IOException;

/**
 * 从数据源配置
 */
@Component
@Configuration
@MapperScan(basePackages = "com.djh.admin.dao.mysql", sqlSessionFactoryRef = "mySqlSqlSessionFactory")

public class MySqlDataSourceConfig {
    @Autowired
    private MySqlDBProperties dbProperties;

    @Bean(name = "mySqlDataSource")
    public DataSource mySqlDataSource() {

        DruidXADataSource dataSource = new DruidXADataSource();
        dataSource.setDriverClassName(dbProperties.getDriverClassName());
        dataSource.setUrl(dbProperties.getUrl());
        dataSource.setUsername(dbProperties.getUsername());
        dataSource.setPassword(dbProperties.getPassword());

        AtomikosDataSourceBean atomikosDataSource = new AtomikosDataSourceBean();
        atomikosDataSource.setXaDataSource(dataSource);
        atomikosDataSource.setUniqueResourceName("mySqlDataSource");
        atomikosDataSource.setTestQuery("SELECT 1");
        return atomikosDataSource;
    }

    /**
     * 自定义sqlSessionFactory配置（因为没有用到MybatisAutoConfiguration自动配置类，需要手动配置）
     * @param dataSource
     * @return
     * @throws Exception
     */
    @Bean(name = "mySqlSqlSessionFactory")
    public SqlSessionFactory mySqlSqlSessionFactory(@Qualifier("mySqlDataSource") DataSource dataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactory.setMapperLocations(resolver.getResources("/mapper/mysql/*.xml"));
        sessionFactory.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
        return sessionFactory.getObject();
    }

    /**
     * SqlSessionTemplate 是 SqlSession接口的实现类，是spring-mybatis中的，实现了SqlSession线程安全
     *
     * @param sqlSessionFactory
     * @return
     */
    @Bean(name="testSqlSessionTemplate")
    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("mySqlSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
