package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class TaskRepository {
    private final SessionFactory sessionFactory;

    public TaskRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(Task task) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(task);
            transaction.commit();
        }
    }

    public Task findById(long id) {
        try (Session session = sessionFactory.openSession()){
            return session.find(Task.class, id);
        }
    }

    public List<Task> findAllByStatus() {
        try (Session session = sessionFactory.openSession()){
            return session.createQuery("FROM Task WHERE status = 'TODO'", Task.class).list();
        }
    }

    public void update(Task task) {
        try (Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            session.merge(task);
            transaction.commit();
        }
    }

    public void delete(Task task) {
        try (Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            session.remove(task);
            transaction.commit();
        }
    }
}