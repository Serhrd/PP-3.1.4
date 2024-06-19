package ru.kata.spring.boot_security.demo.repository;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.entity.Role;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class RoleRepositoriesImpl implements RoleRepository {
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Role> findAll() {
        return em.createQuery("select role from Role role", Role.class).getResultList();
    }

    @Override
    public Set<Role> findAllId(List<Long> ids) {
        String jpql = "SELECT r FROM Role r WHERE r.id IN :ids";
        return new HashSet<>(em.createQuery(jpql, Role.class)
                .setParameter("ids", ids)
                .getResultList());
    }

    @Override
    public void save(Role role) {
        em.persist(role);
    }

    @Override
    public Role getById(Long id) {
        return em.find(Role.class, id);
    }
}
