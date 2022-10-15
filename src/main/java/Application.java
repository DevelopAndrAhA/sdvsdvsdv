import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.ServletContext;
//import javax.sql.DataSource;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
import java.net.URI;
import java.util.Properties;


@SpringBootApplication
@ComponentScan({"controllers","models","service"})
@PropertySource(value = "classpath:application.properties")
public class Application {

    @Autowired
    ServletContext context;

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
     public ComboPooledDataSource dataSource()  {
        try{
            String dbUrl = "jdbc:postgresql://localhost:5432/lesadb";

            ComboPooledDataSource basicDataSource = new ComboPooledDataSource();
            basicDataSource.setJdbcUrl(dbUrl);
            basicDataSource.setUser("postgres");
            basicDataSource.setPassword("123");
            basicDataSource.setMinPoolSize(0);
            basicDataSource.setMaxPoolSize(5);
            basicDataSource.setMaxIdleTime(30000);
            return basicDataSource;
        }catch (Exception e){e.printStackTrace();}
        return null;
    }*/
    /*@Bean //red hat || aws
     public ComboPooledDataSource dataSource()  {
        try{
            //String dbUrl = "jdbc:postgresql://172.30.193.226:5432/alohadb";
            String dbUrl = "jdbc:postgresql://localhost:5432/postgres";
            ComboPooledDataSource basicDataSource = new ComboPooledDataSource();
            basicDataSource.setJdbcUrl(dbUrl);
            basicDataSource.setUser("postgres");
            basicDataSource.setPassword("ebanyibaran123");
            basicDataSource.setMinPoolSize(0);
            basicDataSource.setMaxPoolSize(5);
            basicDataSource.setMaxIdleTime(30000);
            return basicDataSource;
        }catch (Exception e){e.printStackTrace();}
        return null;
    }*/
    /*@Bean //heroku
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
    private final Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty(
                "hibernate.hbm2ddl.auto", "update");
        hibernateProperties.setProperty(
                "hibernate.dialect", "org.hibernate.dialect.PostgreSQL95Dialect");

        return hibernateProperties;
    }
    @Bean
     public ComboPooledDataSource dataSource()  {
        try{
            String dbUrl = "jdbc:postgresql://10.2.6.155:5432/lesadb";

            ComboPooledDataSource basicDataSource = new ComboPooledDataSource();
            basicDataSource.setJdbcUrl(dbUrl);
            basicDataSource.setUser("webadmin");
            basicDataSource.setPassword("199Jekalt,.");
            basicDataSource.setMinPoolSize(0);
            basicDataSource.setMaxPoolSize(5);
            basicDataSource.setMaxIdleTime(30000);
            return basicDataSource;
        }catch (Exception e){e.printStackTrace();}
        return null;
    }
}

    /*@Bean
     public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://172.30.193.226:5432/aloha");
        dataSource.setUsername("aloha");
        dataSource.setPassword("123");
        return dataSource;
    }*/