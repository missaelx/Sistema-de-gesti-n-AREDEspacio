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
import modelo.Mensualidades;

/**
 *
 * @author macbookpro
 */
public class MensualidadesJpaController implements Serializable {

    public MensualidadesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Mensualidades mensualidades) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Alumnos idalumno = mensualidades.getIdalumno();
            if (idalumno != null) {
                idalumno = em.getReference(idalumno.getClass(), idalumno.getIdalumno());
                mensualidades.setIdalumno(idalumno);
            }
            Ingresos idingreso = mensualidades.getIdingreso();
            if (idingreso != null) {
                idingreso = em.getReference(idingreso.getClass(), idingreso.getIdingreso());
                mensualidades.setIdingreso(idingreso);
            }
            em.persist(mensualidades);
            if (idalumno != null) {
                idalumno.getMensualidadesList().add(mensualidades);
                idalumno = em.merge(idalumno);
            }
            if (idingreso != null) {
                idingreso.getMensualidadesList().add(mensualidades);
                idingreso = em.merge(idingreso);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Mensualidades mensualidades) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Mensualidades persistentMensualidades = em.find(Mensualidades.class, mensualidades.getIdmensualidad());
            Alumnos idalumnoOld = persistentMensualidades.getIdalumno();
            Alumnos idalumnoNew = mensualidades.getIdalumno();
            Ingresos idingresoOld = persistentMensualidades.getIdingreso();
            Ingresos idingresoNew = mensualidades.getIdingreso();
            if (idalumnoNew != null) {
                idalumnoNew = em.getReference(idalumnoNew.getClass(), idalumnoNew.getIdalumno());
                mensualidades.setIdalumno(idalumnoNew);
            }
            if (idingresoNew != null) {
                idingresoNew = em.getReference(idingresoNew.getClass(), idingresoNew.getIdingreso());
                mensualidades.setIdingreso(idingresoNew);
            }
            mensualidades = em.merge(mensualidades);
            if (idalumnoOld != null && !idalumnoOld.equals(idalumnoNew)) {
                idalumnoOld.getMensualidadesList().remove(mensualidades);
                idalumnoOld = em.merge(idalumnoOld);
            }
            if (idalumnoNew != null && !idalumnoNew.equals(idalumnoOld)) {
                idalumnoNew.getMensualidadesList().add(mensualidades);
                idalumnoNew = em.merge(idalumnoNew);
            }
            if (idingresoOld != null && !idingresoOld.equals(idingresoNew)) {
                idingresoOld.getMensualidadesList().remove(mensualidades);
                idingresoOld = em.merge(idingresoOld);
            }
            if (idingresoNew != null && !idingresoNew.equals(idingresoOld)) {
                idingresoNew.getMensualidadesList().add(mensualidades);
                idingresoNew = em.merge(idingresoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = mensualidades.getIdmensualidad();
                if (findMensualidades(id) == null) {
                    throw new NonexistentEntityException("The mensualidades with id " + id + " no longer exists.");
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
            Mensualidades mensualidades;
            try {
                mensualidades = em.getReference(Mensualidades.class, id);
                mensualidades.getIdmensualidad();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The mensualidades with id " + id + " no longer exists.", enfe);
            }
            Alumnos idalumno = mensualidades.getIdalumno();
            if (idalumno != null) {
                idalumno.getMensualidadesList().remove(mensualidades);
                idalumno = em.merge(idalumno);
            }
            Ingresos idingreso = mensualidades.getIdingreso();
            if (idingreso != null) {
                idingreso.getMensualidadesList().remove(mensualidades);
                idingreso = em.merge(idingreso);
            }
            em.remove(mensualidades);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Mensualidades> findMensualidadesEntities() {
        return findMensualidadesEntities(true, -1, -1);
    }

    public List<Mensualidades> findMensualidadesEntities(int maxResults, int firstResult) {
        return findMensualidadesEntities(false, maxResults, firstResult);
    }

    private List<Mensualidades> findMensualidadesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Mensualidades.class));
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

    public Mensualidades findMensualidades(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Mensualidades.class, id);
        } finally {
            em.close();
        }
    }

    public int getMensualidadesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Mensualidades> rt = cq.from(Mensualidades.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
