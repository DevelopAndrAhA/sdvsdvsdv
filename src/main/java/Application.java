import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;


@SpringBootApplication
@ComponentScan({"controllers","models","service"})
public class Application {


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("123")
                        .roles("USER")
                        .build();

        return new InMemoryUserDetailsManager(user);
    }


    @Bean(name = "mySession")
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("neural_network.models");
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    /*@Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/aloha");
        dataSource.setUsername("postgres");
        dataSource.setPassword("123");

        return dataSource;
    }*/

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://ip-10-0-188-15.us-east-2.compute.internal:5432/aloha");
        dataSource.setUsername("aloha");
        dataSource.setPassword("123");
        return dataSource;
    }
    /*@Bean
    public ComboPooledDataSource dataSource()  {
        try{
            URI dbUri = new URI(System.getenv("DATABASE_URL"));
            String username = dbUri.getUserInfo().split(":")[0];
            String password = dbUri.getUserInfo().split(":")[1];
            String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + "?sslmode=require";

            ComboPooledDataSource basicDataSource = new ComboPooledDataSource();
            basicDataSource.setJdbcUrl(dbUrl);
            basicDataSource.setUser(username);
            basicDataSource.setPassword(password);
            basicDataSource.setMinPoolSize(0);
            basicDataSource.setMaxPoolSize(5);
            basicDataSource.setMaxIdleTime(30000);
            return basicDataSource;
        }catch (Exception e){e.printStackTrace();}


        return null;

    }*/

    @Bean
    public PlatformTransactionManager hibernateTransactionManager() {
        HibernateTransactionManager transactionManager
                = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }

    private final Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty(
                "hibernate.hbm2ddl.auto", "update");
        hibernateProperties.setProperty(
                "hibernate.dialect", "org.hibernate.dialect.PostgreSQL95Dialect");

        return hibernateProperties;
    }

}