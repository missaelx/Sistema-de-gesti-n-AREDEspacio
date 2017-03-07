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
import modelo.Alumnos;
import modelo.Ingresos;
import modelo.Inscripciones;

/**
 *
 * @author macbookpro
 */
public class InscripcionesJpaController implements Serializable {

    public InscripcionesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Inscripciones inscripciones) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Alumnos idalumno = inscripciones.getIdalumno();
            if (idalumno != null) {
                idalumno = em.getReference(idalumno.getClass(), idalumno.getIdalumno());
                inscripciones.setIdalumno(idalumno);
            }
            Ingresos idingreso = inscripciones.getIdingreso();
            if (idingreso != null) {
                idingreso = em.getReference(idingreso.getClass(), idingreso.getIdingreso());
                inscripciones.setIdingreso(idingreso);
            }
            em.persist(inscripciones);
            if (idalumno != null) {
                idalumno.getInscripcionesList().add(inscripciones);
                idalumno = em.merge(idalumno);
            }
            if (idingreso != null) {
                idingreso.getInscripcionesList().add(inscripciones);
                idingreso = em.merge(idingreso);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Inscripciones inscripciones) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Inscripciones persistentInscripciones = em.find(Inscripciones.class, inscripciones.getIdinscripcion());
            Alumnos idalumnoOld = persistentInscripciones.getIdalumno();
            Alumnos idalumnoNew = inscripciones.getIdalumno();
            Ingresos idingresoOld = persistentInscripciones.getIdingreso();
            Ingresos idingresoNew = inscripciones.getIdingreso();
            if (idalumnoNew != null) {
                idalumnoNew = em.getReference(idalumnoNew.getClass(), idalumnoNew.getIdalumno());
                inscripciones.setIdalumno(idalumnoNew);
            }
            if (idingresoNew != null) {
                idingresoNew = em.getReference(idingresoNew.getClass(), idingresoNew.getIdingreso());
                inscripciones.setIdingreso(idingresoNew);
            }
            inscripciones = em.merge(inscripciones);
            if (idalumnoOld != null && !idalumnoOld.equals(idalumnoNew)) {
                idalumnoOld.getInscripcionesList().remove(inscripciones);
                idalumnoOld = em.merge(idalumnoOld);
            }
            if (idalumnoNew != null && !idalumnoNew.equals(idalumnoOld)) {
                idalumnoNew.getInscripcionesList().add(inscripciones);
                idalumnoNew = em.merge(idalumnoNew);
            }
            if (idingresoOld != null && !idingresoOld.equals(idingresoNew)) {
                idingresoOld.getInscripcionesList().remove(inscripciones);
                idingresoOld = em.merge(idingresoOld);
            }
            if (idingresoNew != null && !idingresoNew.equals(idingresoOld)) {
                idingresoNew.getInscripcionesList().add(inscripciones);
                idingresoNew = em.merge(idingresoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = inscripciones.getIdinscripcion();
                if (findInscripciones(id) == null) {
                    throw new NonexistentEntityException("The inscripciones with id " + id + " no longer exists.");
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
            Inscripciones inscripciones;
            try {
                inscripciones = em.getReference(Inscripciones.class, id);
                inscripciones.getIdinscripcion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The inscripciones with id " + id + " no longer exists.", enfe);
            }
            Alumnos idalumno = inscripciones.getIdalumno();
            if (idalumno != null) {
                idalumno.getInscripcionesList().remove(inscripciones);
                idalumno = em.merge(idalumno);
            }
            Ingresos idingreso = inscripciones.getIdingreso();
            if (idingreso != null) {
                idingreso.getInscripcionesList().remove(inscripciones);
                idingreso = em.merge(idingreso);
            }
            em.remove(inscripciones);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Inscripciones> findInscripcionesEntities() {
        return findInscripcionesEntities(true, -1, -1);
    }

    public List<Inscripciones> findInscripcionesEntities(int maxResults, int firstResult) {
        return findInscripcionesEntities(false, maxResults, firstResult);
    }

    private List<Inscripciones> findInscripcionesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Inscripciones.class));
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

    public Inscripciones findInscripciones(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Inscripciones.class, id);
        } finally {
            em.close();
        }
    }

    public int getInscripcionesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Inscripciones> rt = cq.from(Inscripciones.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
