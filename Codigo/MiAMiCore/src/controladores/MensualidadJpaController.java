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
import modelo.Alumno;
import modelo.Ingreso;
import modelo.Mensualidad;

/**
 *
 * @author macbookpro
 */
public class MensualidadJpaController implements Serializable {

    public MensualidadJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Mensualidad mensualidad) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Alumno idalumno = mensualidad.getIdalumno();
            if (idalumno != null) {
                idalumno = em.getReference(idalumno.getClass(), idalumno.getId());
                mensualidad.setIdalumno(idalumno);
            }
            Ingreso idingreso = mensualidad.getIdingreso();
            if (idingreso != null) {
                idingreso = em.getReference(idingreso.getClass(), idingreso.getId());
                mensualidad.setIdingreso(idingreso);
            }
            em.persist(mensualidad);
            if (idalumno != null) {
                idalumno.getMensualidadList().add(mensualidad);
                idalumno = em.merge(idalumno);
            }
            if (idingreso != null) {
                idingreso.getMensualidadList().add(mensualidad);
                idingreso = em.merge(idingreso);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Mensualidad mensualidad) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Mensualidad persistentMensualidad = em.find(Mensualidad.class, mensualidad.getId());
            Alumno idalumnoOld = persistentMensualidad.getIdalumno();
            Alumno idalumnoNew = mensualidad.getIdalumno();
            Ingreso idingresoOld = persistentMensualidad.getIdingreso();
            Ingreso idingresoNew = mensualidad.getIdingreso();
            if (idalumnoNew != null) {
                idalumnoNew = em.getReference(idalumnoNew.getClass(), idalumnoNew.getId());
                mensualidad.setIdalumno(idalumnoNew);
            }
            if (idingresoNew != null) {
                idingresoNew = em.getReference(idingresoNew.getClass(), idingresoNew.getId());
                mensualidad.setIdingreso(idingresoNew);
            }
            mensualidad = em.merge(mensualidad);
            if (idalumnoOld != null && !idalumnoOld.equals(idalumnoNew)) {
                idalumnoOld.getMensualidadList().remove(mensualidad);
                idalumnoOld = em.merge(idalumnoOld);
            }
            if (idalumnoNew != null && !idalumnoNew.equals(idalumnoOld)) {
                idalumnoNew.getMensualidadList().add(mensualidad);
                idalumnoNew = em.merge(idalumnoNew);
            }
            if (idingresoOld != null && !idingresoOld.equals(idingresoNew)) {
                idingresoOld.getMensualidadList().remove(mensualidad);
                idingresoOld = em.merge(idingresoOld);
            }
            if (idingresoNew != null && !idingresoNew.equals(idingresoOld)) {
                idingresoNew.getMensualidadList().add(mensualidad);
                idingresoNew = em.merge(idingresoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = mensualidad.getId();
                if (findMensualidad(id) == null) {
                    throw new NonexistentEntityException("The mensualidad with id " + id + " no longer exists.");
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
            Mensualidad mensualidad;
            try {
                mensualidad = em.getReference(Mensualidad.class, id);
                mensualidad.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The mensualidad with id " + id + " no longer exists.", enfe);
            }
            Alumno idalumno = mensualidad.getIdalumno();
            if (idalumno != null) {
                idalumno.getMensualidadList().remove(mensualidad);
                idalumno = em.merge(idalumno);
            }
            Ingreso idingreso = mensualidad.getIdingreso();
            if (idingreso != null) {
                idingreso.getMensualidadList().remove(mensualidad);
                idingreso = em.merge(idingreso);
            }
            em.remove(mensualidad);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Mensualidad> findMensualidadEntities() {
        return findMensualidadEntities(true, -1, -1);
    }

    public List<Mensualidad> findMensualidadEntities(int maxResults, int firstResult) {
        return findMensualidadEntities(false, maxResults, firstResult);
    }

    private List<Mensualidad> findMensualidadEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Mensualidad.class));
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

    public Mensualidad findMensualidad(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Mensualidad.class, id);
        } finally {
            em.close();
        }
    }

    public int getMensualidadCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Mensualidad> rt = cq.from(Mensualidad.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
