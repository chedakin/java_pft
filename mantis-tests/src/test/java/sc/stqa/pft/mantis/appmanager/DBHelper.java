package sc.stqa.pft.mantis.appmanager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import sc.stqa.pft.mantis.Models.UserData;
import sc.stqa.pft.mantis.Models.Users;

import java.util.List;


public class DBHelper {

    private final SessionFactory sessionFactory;
    private ApplicationManager app;

    public DBHelper(ApplicationManager app) {
        this.app = app;
    // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();

        sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
    }

    public Users users() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<UserData> result = session.createQuery( "from UserData" ).list();
        session.getTransaction().commit();
        session.close();
        return new Users(result);
    }


}
