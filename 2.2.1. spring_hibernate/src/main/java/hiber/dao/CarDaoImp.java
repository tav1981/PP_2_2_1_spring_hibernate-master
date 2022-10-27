package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.lang.reflect.Parameter;
import java.util.List;

@Repository
public class CarDaoImp implements CarDao{

    //@Autowired
    private SessionFactory sessionFactory;

    @Autowired
    public CarDaoImp(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(Car car) {
        sessionFactory.getCurrentSession().save(car);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Car> listCar() {
        TypedQuery<Car> query=sessionFactory.getCurrentSession().createQuery("from Car");
        return query.getResultList();
    }

    @Override
    public User getUser(String model, int series) {
        String hql = "FROM Car WHERE model = :paramModel AND series = :paramSeries" ;
        TypedQuery<Car> query=sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("paramModel", model);
        query.setParameter("paramSeries", series);
        Car car = query.getSingleResult();


        String hql2 = "FROM User WHERE id = :paramId" ;
        TypedQuery<User> query2=sessionFactory.getCurrentSession().createQuery(hql2);
        query2.setParameter("paramId", car.getUserId());
        User user = query2.getSingleResult();


        return user;
    }
}
