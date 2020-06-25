package cn.pbj2019.demo.spring_annotion.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.StringValueResolver;

import javax.sql.DataSource;

/**
 * @pClassName: ProfileConfig
 * @author: pengbingjiang
 * @create: 2020/6/25 16:45
 * @description: TODO
 */
@PropertySource("classpath:/dbconfig.properties")
@Configuration
public class ProfileConfig implements EmbeddedValueResolverAware {

    @Value("${db.user}")
    private String User;

    @Profile("test")
    @Bean
    public DataSource dataSourceTest() throws Exception {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser("root");
        dataSource.setPassword("mysqladmin");
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/db_pbj_test");
        dataSource.setDriverClass("com.mysql.jdbc.Driver");
        return dataSource;
    }

    @Profile("dev")
    @Bean
    public DataSource dataSourceDev(@Value("${db.password}") String pwd) throws Exception {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser(User);
        dataSource.setPassword(pwd);
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/db_pbj_dev");
        String driver = valueResolver.resolveStringValue("${db.driver}");
        dataSource.setDriverClass(driver);
        return dataSource;
    }

    @Profile("prod")
    @Bean
    public DataSource dataSourceProd() throws Exception {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser("root");
        dataSource.setPassword("mysqladmin");
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/db_pbj_prod");
        dataSource.setDriverClass("com.mysql.jdbc.Driver");
        return dataSource;
    }

    private StringValueResolver valueResolver;
    @Override
    public void setEmbeddedValueResolver(StringValueResolver valueResolver) {
        this.valueResolver = valueResolver;
    }
}
