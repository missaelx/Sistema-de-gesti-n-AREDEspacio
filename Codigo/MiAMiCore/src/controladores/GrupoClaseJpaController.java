/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import controladores.exceptions.IllegalOrphanException;
import controladores.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Maestro;
import modelo.TipoDanza;
import modelo.Alumno;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Horario;
import modelo.Asistencia;
import modelo.GrupoClase;

/**
 *
 * @author macbookpro
 */
public class GrupoClaseJpaController implements Serializable {

    public GrupoClaseJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(GrupoClase grupoClase) {
        if (grupoClase.getAlumnoList() == null) {
            grupoClase.setAlumnoList(new ArrayList<Alumno>());
        }
        if (grupoClase.getHorarioList() == null) {
            grupoClase.setHorarioList(new ArrayList<Horario>());
        }
        if (grupoClase.getAsistenciaList() == null) {
            grupoClase.setAsistenciaList(new ArrayList<Asistencia>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Maestro idMaestro = grupoClase.getIdMaestro();
            if (idMaestro != null) {
                idMaestro = em.getReference(idMaestro.getClass(), idMaestro.getId());
                grupoClase.setIdMaestro(idMaestro);
            }
            TipoDanza idTipoDanza = grupoClase.getIdTipoDanza();
            if (idTipoDanza != null) {
                idTipoDanza = em.getReference(idTipoDanza.getClass(), idTipoDanza.getId());
                grupoClase.setIdTipoDanza(idTipoDanza);
            }
            List<Alumno> attachedAlumnoList = new ArrayList<Alumno>();
            for (Alumno alumnoListAlumnoToAttach : grupoClase.getAlumnoList()) {
                alumnoListAlumnoToAttach = em.getReference(alumnoListAlumnoToAttach.getClass(), alumnoListAlumnoToAttach.getId());
                attachedAlumnoList.add(alumnoListAlumnoToAttach);
            }
            grupoClase.setAlumnoList(attachedAlumnoList);
            List<Horario> attachedHorarioList = new ArrayList<Horario>();
            for (Horario horarioListHorarioToAttach : grupoClase.getHorarioList()) {
                horarioListHorarioToAttach = em.getReference(horarioListHorarioToAttach.getClass(), horarioListHorarioToAttach.getId());
                attachedHorarioList.add(horarioListHorarioToAttach);
            }
            grupoClase.setHorarioList(attachedHorarioList);
            List<Asistencia> attachedAsistenciaList = new ArrayList<Asistencia>();
            for (Asistencia asistenciaListAsistenciaToAttach : grupoClase.getAsistenciaList()) {
                asistenciaListAsistenciaToAttach = em.getReference(asistenciaListAsistenciaToAttach.getClass(), asistenciaListAsistenciaToAttach.getId());
                attachedAsistenciaList.add(asistenciaListAsistenciaToAttach);
            }
            grupoClase.setAsistenciaList(attachedAsistenciaList);
            em.persist(grupoClase);
            if (idMaestro != null) {
                idMaestro.getGrupoClaseList().add(grupoClase);
                idMaestro = em.merge(idMaestro);
            }
            if (idTipoDanza != null) {
                idTipoDanza.getGrupoClaseList().add(grupoClase);
                idTipoDanza = em.merge(idTipoDanza);
            }
            for (Alumno alumnoListAlumno : grupoClase.getAlumnoList()) {
                alumnoListAlumno.getGrupoClaseList().add(grupoClase);
                alumnoListAlumno = em.merge(alumnoListAlumno);
            }
            for (Horario horarioListHorario : grupoClase.getHorarioList()) {
                GrupoClase oldIdGrupoClaseOfHorarioListHorario = horarioListHorario.getIdGrupoClase();
                horarioListHorario.setIdGrupoClase(grupoClase);
                horarioListHorario = em.merge(horarioListHorario);
                if (oldIdGrupoClaseOfHorarioListHorario != null) {
                    oldIdGrupoClaseOfHorarioListHorario.getHorarioList().remove(horarioListHorario);
                    oldIdGrupoClaseOfHorarioListHorario = em.merge(oldIdGrupoClaseOfHorarioListHorario);
                }
            }
            for (Asistencia asistenciaListAsistencia : grupoClase.getAsistenciaList()) {
                GrupoClase oldIdGrupoClaseOfAsistenciaListAsistencia = asistenciaListAsistencia.getIdGrupoClase();
                asistenciaListAsistencia.setIdGrupoClase(grupoClase);
                asistenciaListAsistencia = em.merge(asistenciaListAsistencia);
                if (oldIdGrupoClaseOfAsistenciaListAsistencia != null) {
                    oldIdGrupoClaseOfAsistenciaListAsistencia.getAsistenciaList().remove(asistenciaListAsistencia);
                    oldIdGrupoClaseOfAsistenciaListAsistencia = em.merge(oldIdGrupoClaseOfAsistenciaListAsistencia);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(GrupoClase grupoClase) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GrupoClase persistentGrupoClase = em.find(GrupoClase.class, grupoClase.getId());
            Maestro idMaestroOld = persistentGrupoClase.getIdMaestro();
            Maestro idMaestroNew = grupoClase.getIdMaestro();
            TipoDanza idTipoDanzaOld = persistentGrupoClase.getIdTipoDanza();
            TipoDanza idTipoDanzaNew = grupoClase.getIdTipoDanza();
            List<Alumno> alumnoListOld = persistentGrupoClase.getAlumnoList();
            List<Alumno> alumnoListNew = grupoClase.getAlumnoList();
            List<Horario> horarioListOld = persistentGrupoClase.getHorarioList();
            List<Horario> horarioListNew = grupoClase.getHorarioList();
            List<Asistencia> asistenciaListOld = persistentGrupoClase.getAsistenciaList();
            List<Asistencia> asistenciaListNew = grupoClase.getAsistenciaList();
            List<String> illegalOrphanMessages = null;
            for (Horario horarioListOldHorario : horarioListOld) {
                if (!horarioListNew.contains(horarioListOldHorario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Horario " + horarioListOldHorario + " since its idGrupoClase field is not nullable.");
                }
            }
            for (Asistencia asistenciaListOldAsistencia : asistenciaListOld) {
                if (!asistenciaListNew.contains(asistenciaListOldAsistencia)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Asistencia " + asistenciaListOldAsistencia + " since its idGrupoClase field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idMaestroNew != null) {
                idMaestroNew = em.getReference(idMaestroNew.getClass(), idMaestroNew.getId());
                grupoClase.setIdMaestro(idMaestroNew);
            }
            if (idTipoDanzaNew != null) {
                idTipoDanzaNew = em.getReference(idTipoDanzaNew.getClass(), idTipoDanzaNew.getId());
                grupoClase.setIdTipoDanza(idTipoDanzaNew);
            }
            List<Alumno> attachedAlumnoListNew = new ArrayList<Alumno>();
            for (Alumno alumnoListNewAlumnoToAttach : alumnoListNew) {
                alumnoListNewAlumnoToAttach = em.getReference(alumnoListNewAlumnoToAttach.getClass(), alumnoListNewAlumnoToAttach.getId());
                attachedAlumnoListNew.add(alumnoListNewAlumnoToAttach);
            }
            alumnoListNew = attachedAlumnoListNew;
            grupoClase.setAlumnoList(alumnoListNew);
            List<Horario> attachedHorarioListNew = new ArrayList<Horario>();
            for (Horario horarioListNewHorarioToAttach : horarioListNew) {
                horarioListNewHorarioToAttach = em.getReference(horarioListNewHorarioToAttach.getClass(), horarioListNewHorarioToAttach.getId());
                attachedHorarioListNew.add(horarioListNewHorarioToAttach);
            }
            horarioListNew = attachedHorarioListNew;
            grupoClase.setHorarioList(horarioListNew);
            List<Asistencia> attachedAsistenciaListNew = new ArrayList<Asistencia>();
            for (Asistencia asistenciaListNewAsistenciaToAttach : asistenciaListNew) {
                asistenciaListNewAsistenciaToAttach = em.getReference(asistenciaListNewAsistenciaToAttach.getClass(), asistenciaListNewAsistenciaToAttach.getId());
                attachedAsistenciaListNew.add(asistenciaListNewAsistenciaToAttach);
            }
            asistenciaListNew = attachedAsistenciaListNew;
            grupoClase.setAsistenciaList(asistenciaListNew);
            grupoClase = em.merge(grupoClase);
            if (idMaestroOld != null && !idMaestroOld.equals(idMaestroNew)) {
                idMaestroOld.getGrupoClaseList().remove(grupoClase);
                idMaestroOld = em.merge(idMaestroOld);
            }
            if (idMaestroNew != null && !idMaestroNew.equals(idMaestroOld)) {
                idMaestroNew.getGrupoClaseList().add(grupoClase);
                idMaestroNew = em.merge(idMaestroNew);
            }
            if (idTipoDanzaOld != null && !idTipoDanzaOld.equals(idTipoDanzaNew)) {
                idTipoDanzaOld.getGrupoClaseList().remove(grupoClase);
                idTipoDanzaOld = em.merge(idTipoDanzaOld);
            }
            if (idTipoDanzaNew != null && !idTipoDanzaNew.equals(idTipoDanzaOld)) {
                idTipoDanzaNew.getGrupoClaseList().add(grupoClase);
                idTipoDanzaNew = em.merge(idTipoDanzaNew);
            }
            for (Alumno alumnoListOldAlumno : alumnoListOld) {
                if (!alumnoListNew.contains(alumnoListOldAlumno)) {
                    alumnoListOldAlumno.getGrupoClaseList().remove(grupoClase);
                    alumnoListOldAlumno = em.merge(alumnoListOldAlumno);
                }
            }
            for (Alumno alumnoListNewAlumno : alumnoListNew) {
                if (!alumnoListOld.contains(alumnoListNewAlumno)) {
                    alumnoListNewAlumno.getGrupoClaseList().add(grupoClase);
                    alumnoListNewAlumno = em.merge(alumnoListNewAlumno);
                }
            }
            for (Horario horarioListNewHorario : horarioListNew) {
                if (!horarioListOld.contains(horarioListNewHorario)) {
                    GrupoClase oldIdGrupoClaseOfHorarioListNewHorario = horarioListNewHorario.getIdGrupoClase();
                    horarioListNewHorario.setIdGrupoClase(grupoClase);
                    horarioListNewHorario = em.merge(horarioListNewHorario);
                    if (oldIdGrupoClaseOfHorarioListNewHorario != null && !oldIdGrupoClaseOfHorarioListNewHorario.equals(grupoClase)) {
                        oldIdGrupoClaseOfHorarioListNewHorario.getHorarioList().remove(horarioListNewHorario);
                        oldIdGrupoClaseOfHorarioListNewHorario = em.merge(oldIdGrupoClaseOfHorarioListNewHorario);
                    }
                }
            }
            for (Asistencia asistenciaListNewAsistencia : asistenciaListNew) {
                if (!asistenciaListOld.contains(asistenciaListNewAsistencia)) {
                    GrupoClase oldIdGrupoClaseOfAsistenciaListNewAsistencia = asistenciaListNewAsistencia.getIdGrupoClase();
                    asistenciaListNewAsistencia.setIdGrupoClase(grupoClase);
                    asistenciaListNewAsistencia = em.merge(asistenciaListNewAsistencia);
                    if (oldIdGrupoClaseOfAsistenciaListNewAsistencia != null && !oldIdGrupoClaseOfAsistenciaListNewAsistencia.equals(grupoClase)) {
                        oldIdGrupoClaseOfAsistenciaListNewAsistencia.getAsistenciaList().remove(asistenciaListNewAsistencia);
                        oldIdGrupoClaseOfAsistenciaListNewAsistencia = em.merge(oldIdGrupoClaseOfAsistenciaListNewAsistencia);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = grupoClase.getId();
                if (findGrupoClase(id) == null) {
                    throw new NonexistentEntityException("The grupoClase with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GrupoClase grupoClase;
            try {
                grupoClase = em.getReference(GrupoClase.class, id);
                grupoClase.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The grupoClase with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Horario> horarioListOrphanCheck = grupoClase.getHorarioList();
            for (Horario horarioListOrphanCheckHorario : horarioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This GrupoClase (" + grupoClase + ") cannot be destroyed since the Horario " + horarioListOrphanCheckHorario + " in its horarioList field has a non-nullable idGrupoClase field.");
            }
            List<Asistencia> asistenciaListOrphanCheck = grupoClase.getAsistenciaList();
            for (Asistencia asistenciaListOrphanCheckAsistencia : asistenciaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This GrupoClase (" + grupoClase + ") cannot be destroyed since the Asistencia " + asistenciaListOrphanCheckAsistencia + " in its asistenciaList field has a non-nullable idGrupoClase field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Maestro idMaestro = grupoClase.getIdMaestro();
            if (idMaestro != null) {
                idMaestro.getGrupoClaseList().remove(grupoClase);
                idMaestro = em.merge(idMaestro);
            }
            TipoDanza idTipoDanza = grupoClase.getIdTipoDanza();
            if (idTipoDanza != null) {
                idTipoDanza.getGrupoClaseList().remove(grupoClase);
                idTipoDanza = em.merge(idTipoDanza);
            }
            List<Alumno> alumnoList = grupoClase.getAlumnoList();
            for (Alumno alumnoListAlumno : alumnoList) {
                alumnoListAlumno.getGrupoClaseList().remove(grupoClase);
                alumnoListAlumno = em.merge(alumnoListAlumno);
            }
            em.remove(grupoClase);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<GrupoClase> findGrupoClaseEntities() {
        return findGrupoClaseEntities(true, -1, -1);
    }

    public List<GrupoClase> findGrupoClaseEntities(int maxResults, int firstResult) {
        return findGrupoClaseEntities(false, maxResults, firstResult);
    }

    private List<GrupoClase> findGrupoClaseEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(GrupoClase.class));
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

    public GrupoClase findGrupoClase(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(GrupoClase.class, id);
        } finally {
            em.close();
        }
    }

    public int getGrupoClaseCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<GrupoClase> rt = cq.from(GrupoClase.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
