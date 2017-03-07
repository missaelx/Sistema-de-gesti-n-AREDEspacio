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
import modelo.Asistencias;
import modelo.GrupoClase;

/**
 *
 * @author macbookpro
 */
public class AsistenciasJpaController implements Serializable {

    public AsistenciasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Asistencias asistencias) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Alumnos idAlumno = asistencias.getIdAlumno();
            if (idAlumno != null) {
                idAlumno = em.getReference(idAlumno.getClass(), idAlumno.getIdalumno());
                asistencias.setIdAlumno(idAlumno);
            }
            GrupoClase idGrupoClase = asistencias.getIdGrupoClase();
            if (idGrupoClase != null) {
                idGrupoClase = em.getReference(idGrupoClase.getClass(), idGrupoClase.getIdGrupoClase());
                asistencias.setIdGrupoClase(idGrupoClase);
            }
            em.persist(asistencias);
            if (idAlumno != null) {
                idAlumno.getAsistenciasList().add(asistencias);
                idAlumno = em.merge(idAlumno);
            }
            if (idGrupoClase != null) {
                idGrupoClase.getAsistenciasList().add(asistencias);
                idGrupoClase = em.merge(idGrupoClase);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Asistencias asistencias) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Asistencias persistentAsistencias = em.find(Asistencias.class, asistencias.getIdasistencia());
            Alumnos idAlumnoOld = persistentAsistencias.getIdAlumno();
            Alumnos idAlumnoNew = asistencias.getIdAlumno();
            GrupoClase idGrupoClaseOld = persistentAsistencias.getIdGrupoClase();
            GrupoClase idGrupoClaseNew = asistencias.getIdGrupoClase();
            if (idAlumnoNew != null) {
                idAlumnoNew = em.getReference(idAlumnoNew.getClass(), idAlumnoNew.getIdalumno());
                asistencias.setIdAlumno(idAlumnoNew);
            }
            if (idGrupoClaseNew != null) {
                idGrupoClaseNew = em.getReference(idGrupoClaseNew.getClass(), idGrupoClaseNew.getIdGrupoClase());
                asistencias.setIdGrupoClase(idGrupoClaseNew);
            }
            asistencias = em.merge(asistencias);
            if (idAlumnoOld != null && !idAlumnoOld.equals(idAlumnoNew)) {
                idAlumnoOld.getAsistenciasList().remove(asistencias);
                idAlumnoOld = em.merge(idAlumnoOld);
            }
            if (idAlumnoNew != null && !idAlumnoNew.equals(idAlumnoOld)) {
                idAlumnoNew.getAsistenciasList().add(asistencias);
                idAlumnoNew = em.merge(idAlumnoNew);
            }
            if (idGrupoClaseOld != null && !idGrupoClaseOld.equals(idGrupoClaseNew)) {
                idGrupoClaseOld.getAsistenciasList().remove(asistencias);
                idGrupoClaseOld = em.merge(idGrupoClaseOld);
            }
            if (idGrupoClaseNew != null && !idGrupoClaseNew.equals(idGrupoClaseOld)) {
                idGrupoClaseNew.getAsistenciasList().add(asistencias);
                idGrupoClaseNew = em.merge(idGrupoClaseNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = asistencias.getIdasistencia();
                if (findAsistencias(id) == null) {
                    throw new NonexistentEntityException("The asistencias with id " + id + " no longer exists.");
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
            Asistencias asistencias;
            try {
                asistencias = em.getReference(Asistencias.class, id);
                asistencias.getIdasistencia();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The asistencias with id " + id + " no longer exists.", enfe);
            }
            Alumnos idAlumno = asistencias.getIdAlumno();
            if (idAlumno != null) {
                idAlumno.getAsistenciasList().remove(asistencias);
                idAlumno = em.merge(idAlumno);
            }
            GrupoClase idGrupoClase = asistencias.getIdGrupoClase();
            if (idGrupoClase != null) {
                idGrupoClase.getAsistenciasList().remove(asistencias);
                idGrupoClase = em.merge(idGrupoClase);
            }
            em.remove(asistencias);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Asistencias> findAsistenciasEntities() {
        return findAsistenciasEntities(true, -1, -1);
    }

    public List<Asistencias> findAsistenciasEntities(int maxResults, int firstResult) {
        return findAsistenciasEntities(false, maxResults, firstResult);
    }

    private List<Asistencias> findAsistenciasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Asistencias.class));
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

    public Asistencias findAsistencias(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Asistencias.class, id);
        } finally {
            em.close();
        }
    }

    public int getAsistenciasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Asistencias> rt = cq.from(Asistencias.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
