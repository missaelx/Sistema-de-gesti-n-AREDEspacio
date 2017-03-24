/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import controladores.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.GrupoClase;
import modelo.Horario;

/**
 *
 * @author macbookpro
 */
public class HorarioJpaController implements Serializable {

    public HorarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Horario horario) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GrupoClase idGrupoClase = horario.getIdGrupoClase();
            if (idGrupoClase != null) {
                idGrupoClase = em.getReference(idGrupoClase.getClass(), idGrupoClase.getId());
                horario.setIdGrupoClase(idGrupoClase);
            }
            em.persist(horario);
            if (idGrupoClase != null) {
                idGrupoClase.getHorarioList().add(horario);
                idGrupoClase = em.merge(idGrupoClase);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Horario horario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Horario persistentHorario = em.find(Horario.class, horario.getId());
            GrupoClase idGrupoClaseOld = persistentHorario.getIdGrupoClase();
            GrupoClase idGrupoClaseNew = horario.getIdGrupoClase();
            if (idGrupoClaseNew != null) {
                idGrupoClaseNew = em.getReference(idGrupoClaseNew.getClass(), idGrupoClaseNew.getId());
                horario.setIdGrupoClase(idGrupoClaseNew);
            }
            horario = em.merge(horario);
            if (idGrupoClaseOld != null && !idGrupoClaseOld.equals(idGrupoClaseNew)) {
                idGrupoClaseOld.getHorarioList().remove(horario);
                idGrupoClaseOld = em.merge(idGrupoClaseOld);
            }
            if (idGrupoClaseNew != null && !idGrupoClaseNew.equals(idGrupoClaseOld)) {
                idGrupoClaseNew.getHorarioList().add(horario);
                idGrupoClaseNew = em.merge(idGrupoClaseNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = horario.getId();
                if (findHorario(id) == null) {
                    throw new NonexistentEntityException("The horario with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Horario horario;
            try {
                horario = em.getReference(Horario.class, id);
                horario.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The horario with id " + id + " no longer exists.", enfe);
            }
            GrupoClase idGrupoClase = horario.getIdGrupoClase();
            if (idGrupoClase != null) {
                idGrupoClase.getHorarioList().remove(horario);
                idGrupoClase = em.merge(idGrupoClase);
            }
            em.remove(horario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Horario> findHorarioEntities() {
        return findHorarioEntities(true, -1, -1);
    }

    public List<Horario> findHorarioEntities(int maxResults, int firstResult) {
        return findHorarioEntities(false, maxResults, firstResult);
    }

    private List<Horario> findHorarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Horario.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Horario findHorario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Horario.class, id);
        } finally {
            em.close();
        }
    }

    public int getHorarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Horario> rt = cq.from(Horario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
