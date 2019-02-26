package com.djh.admin.config.db.mysql;

import com.alibaba.druid.pool.DruidDataSource;
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
        //DruidDataSource属性配置  https://www.cnblogs.com/SummerinShire/p/5828888.html
//        DruidDataSource dataSource = new DruidDataSource();
//        dataSource.setDriverClassName(dbProperties.getDriverClassName());
//        dataSource.setUrl(dbProperties.getUrl());
//        dataSource.setUsername(dbProperties.getUsername());
//        dataSource.setPassword(dbProperties.getPassword());
        //return dataSource;

        MysqlXADataSource mysqlXADataSource=new MysqlXADataSource();
        mysqlXADataSource.setUrl(dbProperties.getUrl());
        mysqlXADataSource.setPinGlobalTxToPhysicalConnection(true);
        mysqlXADataSource.setPassword(dbProperties.getPassword());
        mysqlXADataSource.setUser(dbProperties.getUsername());
        mysqlXADataSource.setPinGlobalTxToPhysicalConnection(true);

        AtomikosDataSourceBean atomikosDataSourceBean=new AtomikosDataSourceBean();
        atomikosDataSourceBean.setXaDataSource(mysqlXADataSource);
        atomikosDataSourceBean.setUniqueResourceName("mySqlDataSource");
        return atomikosDataSourceBean;
    }

    @Bean(name = "mySqlSqlSessionFactory")
    public SqlSessionFactory mySqlSqlSessionFactory(@Qualifier("mySqlDataSource") DataSource mySqlDataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(mySqlDataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactory.setMapperLocations(resolver.getResources("/mapper/mysql/*.xml"));
        sessionFactory.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
        return sessionFactory.getObject();
    }

    // 定义事务
//    @Bean(name = "mysqlTransactionManager")
//    public DataSourceTransactionManager mysqlTransactionManager(@Qualifier("mySqlDataSource") DataSource mySqlDataSource) {
//        return new DataSourceTransactionManager(mySqlDataSource);
//    }

    @Primary
    @Bean(name="test1SqlSessionTemplate")
    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("mySqlSqlSessionFactory")
                                                             SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
