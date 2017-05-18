/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import controladores.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TemporalType;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Alumno;
import modelo.Asistencia;
import modelo.GrupoClase;

/**
 *
 * @author macbookpro
 */
public class AsistenciaJpaController implements Serializable {

    public AsistenciaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Asistencia asistencia) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Alumno idAlumno = asistencia.getIdAlumno();
            if (idAlumno != null) {
                idAlumno = em.getReference(idAlumno.getClass(), idAlumno.getId());
                asistencia.setIdAlumno(idAlumno);
            }
            GrupoClase idGrupoClase = asistencia.getIdGrupoClase();
            if (idGrupoClase != null) {
                idGrupoClase = em.getReference(idGrupoClase.getClass(), idGrupoClase.getId());
                asistencia.setIdGrupoClase(idGrupoClase);
            }
            em.persist(asistencia);
            if (idAlumno != null) {
                idAlumno.getAsistenciaList().add(asistencia);
                idAlumno = em.merge(idAlumno);
            }
            if (idGrupoClase != null) {
                idGrupoClase.getAsistenciaList().add(asistencia);
                idGrupoClase = em.merge(idGrupoClase);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Asistencia asistencia) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Asistencia persistentAsistencia = em.find(Asistencia.class, asistencia.getId());
            Alumno idAlumnoOld = persistentAsistencia.getIdAlumno();
            Alumno idAlumnoNew = asistencia.getIdAlumno();
            GrupoClase idGrupoClaseOld = persistentAsistencia.getIdGrupoClase();
            GrupoClase idGrupoClaseNew = asistencia.getIdGrupoClase();
            if (idAlumnoNew != null) {
                idAlumnoNew = em.getReference(idAlumnoNew.getClass(), idAlumnoNew.getId());
                asistencia.setIdAlumno(idAlumnoNew);
            }
            if (idGrupoClaseNew != null) {
                idGrupoClaseNew = em.getReference(idGrupoClaseNew.getClass(), idGrupoClaseNew.getId());
                asistencia.setIdGrupoClase(idGrupoClaseNew);
            }
            asistencia = em.merge(asistencia);
            if (idAlumnoOld != null && !idAlumnoOld.equals(idAlumnoNew)) {
                idAlumnoOld.getAsistenciaList().remove(asistencia);
                idAlumnoOld = em.merge(idAlumnoOld);
            }
            if (idAlumnoNew != null && !idAlumnoNew.equals(idAlumnoOld)) {
                idAlumnoNew.getAsistenciaList().add(asistencia);
                idAlumnoNew = em.merge(idAlumnoNew);
            }
            if (idGrupoClaseOld != null && !idGrupoClaseOld.equals(idGrupoClaseNew)) {
                idGrupoClaseOld.getAsistenciaList().remove(asistencia);
                idGrupoClaseOld = em.merge(idGrupoClaseOld);
            }
            if (idGrupoClaseNew != null && !idGrupoClaseNew.equals(idGrupoClaseOld)) {
                idGrupoClaseNew.getAsistenciaList().add(asistencia);
                idGrupoClaseNew = em.merge(idGrupoClaseNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = asistencia.getId();
                if (findAsistencia(id) == null) {
                    throw new NonexistentEntityException("The asistencia with id " + id + " no longer exists.");
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
            Asistencia asistencia;
            try {
                asistencia = em.getReference(Asistencia.class, id);
                asistencia.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The asistencia with id " + id + " no longer exists.", enfe);
            }
            Alumno idAlumno = asistencia.getIdAlumno();
            if (idAlumno != null) {
                idAlumno.getAsistenciaList().remove(asistencia);
                idAlumno = em.merge(idAlumno);
            }
            GrupoClase idGrupoClase = asistencia.getIdGrupoClase();
            if (idGrupoClase != null) {
                idGrupoClase.getAsistenciaList().remove(asistencia);
                idGrupoClase = em.merge(idGrupoClase);
            }
            em.remove(asistencia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Asistencia> findAsistenciaEntities() {
        return findAsistenciaEntities(true, -1, -1);
    }

    public List<Asistencia> findAsistenciaEntities(int maxResults, int firstResult) {
        return findAsistenciaEntities(false, maxResults, firstResult);
    }

    private List<Asistencia> findAsistenciaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Asistencia.class));
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

    public Asistencia findAsistencia(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Asistencia.class, id);
        } finally {
            em.close();
        }
    }
   public List<Asistencia> getAsistenciaAlumno(int alumno, Date fecha){//Regresa las asistencias registradas de un alumno en un tiempo determinado
        EntityManager em = getEntityManager();
        List<Asistencia> Asistencias = (List<Asistencia>) em.createNamedQuery("Asistencia.findByAlumno")
                .setParameter("idAlumno", alumno)
                .setParameter("fecha",fecha, TemporalType.DATE)
                .getResultList();
        
        return Asistencias;
    }

    public int getAsistenciaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Asistencia> rt = cq.from(Asistencia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
