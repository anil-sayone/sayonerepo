package com.hibernate.HibernateExample;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import com.hibernate.model.Employee;

/**
 * Hello world!
 *
 */
public class App {

	public static void main(String[] args) {

		Configuration configuration = new Configuration();
		configuration.addAnnotatedClass(Employee.class);
		configuration.configure();
		StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).build();
		SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		Session session = sessionFactory.openSession();
		session.getTransaction().begin();
		Employee employee = new Employee("Krishna", 726, 25000.0);
		session.save(employee);
		System.out.println("Employee Object is stored in DB");
		session.getTransaction().commit();
		System.out.println("----------------");
		// List <Employee> list = session.createQuery("from employee", Employee.class).list();
		List<Object[]> list = session.createNativeQuery("select * from employee").list();
		session.close();
		
		employee.setName("Krish");
		Session session2 = sessionFactory.openSession();
		Employee employee3 = session2.get(Employee.class, 22);
		System.out.println("session2.update");
		session2.beginTransaction();
		//session2.update(employee);// will through NonUniqueObjectException
		session2.merge(employee);
		session2.getTransaction().commit();
		
	
		List<Employee> arrayList = new ArrayList<Employee>();

		for (Object[] objects : list) {
			int empId = (Integer) objects[1];
			String name = (String) objects[2];
			Double salary = (Double) objects[3];
			Employee employee2 = new Employee(name, empId, salary);
			arrayList.add(employee2);
		}
		System.out.println(arrayList);
	}
}
