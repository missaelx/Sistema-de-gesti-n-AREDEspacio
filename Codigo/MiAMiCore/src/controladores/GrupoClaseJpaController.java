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
import modelo.TipoDanza;
import modelo.Alumnos;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Horario;
import modelo.Asistencias;
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
        if (grupoClase.getAlumnosList() == null) {
            grupoClase.setAlumnosList(new ArrayList<Alumnos>());
        }
        if (grupoClase.getHorarioList() == null) {
            grupoClase.setHorarioList(new ArrayList<Horario>());
        }
        if (grupoClase.getAsistenciasList() == null) {
            grupoClase.setAsistenciasList(new ArrayList<Asistencias>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoDanza idTipoDanza = grupoClase.getIdTipoDanza();
            if (idTipoDanza != null) {
                idTipoDanza = em.getReference(idTipoDanza.getClass(), idTipoDanza.getIdTipoDanza());
                grupoClase.setIdTipoDanza(idTipoDanza);
            }
            List<Alumnos> attachedAlumnosList = new ArrayList<Alumnos>();
            for (Alumnos alumnosListAlumnosToAttach : grupoClase.getAlumnosList()) {
                alumnosListAlumnosToAttach = em.getReference(alumnosListAlumnosToAttach.getClass(), alumnosListAlumnosToAttach.getIdalumno());
                attachedAlumnosList.add(alumnosListAlumnosToAttach);
            }
            grupoClase.setAlumnosList(attachedAlumnosList);
            List<Horario> attachedHorarioList = new ArrayList<Horario>();
            for (Horario horarioListHorarioToAttach : grupoClase.getHorarioList()) {
                horarioListHorarioToAttach = em.getReference(horarioListHorarioToAttach.getClass(), horarioListHorarioToAttach.getIdhorario());
                attachedHorarioList.add(horarioListHorarioToAttach);
            }
            grupoClase.setHorarioList(attachedHorarioList);
            List<Asistencias> attachedAsistenciasList = new ArrayList<Asistencias>();
            for (Asistencias asistenciasListAsistenciasToAttach : grupoClase.getAsistenciasList()) {
                asistenciasListAsistenciasToAttach = em.getReference(asistenciasListAsistenciasToAttach.getClass(), asistenciasListAsistenciasToAttach.getIdasistencia());
                attachedAsistenciasList.add(asistenciasListAsistenciasToAttach);
            }
            grupoClase.setAsistenciasList(attachedAsistenciasList);
            em.persist(grupoClase);
            if (idTipoDanza != null) {
                idTipoDanza.getGrupoClaseList().add(grupoClase);
                idTipoDanza = em.merge(idTipoDanza);
            }
            for (Alumnos alumnosListAlumnos : grupoClase.getAlumnosList()) {
                alumnosListAlumnos.getGrupoClaseList().add(grupoClase);
                alumnosListAlumnos = em.merge(alumnosListAlumnos);
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
            for (Asistencias asistenciasListAsistencias : grupoClase.getAsistenciasList()) {
                GrupoClase oldIdGrupoClaseOfAsistenciasListAsistencias = asistenciasListAsistencias.getIdGrupoClase();
                asistenciasListAsistencias.setIdGrupoClase(grupoClase);
                asistenciasListAsistencias = em.merge(asistenciasListAsistencias);
                if (oldIdGrupoClaseOfAsistenciasListAsistencias != null) {
                    oldIdGrupoClaseOfAsistenciasListAsistencias.getAsistenciasList().remove(asistenciasListAsistencias);
                    oldIdGrupoClaseOfAsistenciasListAsistencias = em.merge(oldIdGrupoClaseOfAsistenciasListAsistencias);
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
            GrupoClase persistentGrupoClase = em.find(GrupoClase.class, grupoClase.getIdGrupoClase());
            TipoDanza idTipoDanzaOld = persistentGrupoClase.getIdTipoDanza();
            TipoDanza idTipoDanzaNew = grupoClase.getIdTipoDanza();
            List<Alumnos> alumnosListOld = persistentGrupoClase.getAlumnosList();
            List<Alumnos> alumnosListNew = grupoClase.getAlumnosList();
            List<Horario> horarioListOld = persistentGrupoClase.getHorarioList();
            List<Horario> horarioListNew = grupoClase.getHorarioList();
            List<Asistencias> asistenciasListOld = persistentGrupoClase.getAsistenciasList();
            List<Asistencias> asistenciasListNew = grupoClase.getAsistenciasList();
            List<String> illegalOrphanMessages = null;
            for (Horario horarioListOldHorario : horarioListOld) {
                if (!horarioListNew.contains(horarioListOldHorario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Horario " + horarioListOldHorario + " since its idGrupoClase field is not nullable.");
                }
            }
            for (Asistencias asistenciasListOldAsistencias : asistenciasListOld) {
                if (!asistenciasListNew.contains(asistenciasListOldAsistencias)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Asistencias " + asistenciasListOldAsistencias + " since its idGrupoClase field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idTipoDanzaNew != null) {
                idTipoDanzaNew = em.getReference(idTipoDanzaNew.getClass(), idTipoDanzaNew.getIdTipoDanza());
                grupoClase.setIdTipoDanza(idTipoDanzaNew);
            }
            List<Alumnos> attachedAlumnosListNew = new ArrayList<Alumnos>();
            for (Alumnos alumnosListNewAlumnosToAttach : alumnosListNew) {
                alumnosListNewAlumnosToAttach = em.getReference(alumnosListNewAlumnosToAttach.getClass(), alumnosListNewAlumnosToAttach.getIdalumno());
                attachedAlumnosListNew.add(alumnosListNewAlumnosToAttach);
            }
            alumnosListNew = attachedAlumnosListNew;
            grupoClase.setAlumnosList(alumnosListNew);
            List<Horario> attachedHorarioListNew = new ArrayList<Horario>();
            for (Horario horarioListNewHorarioToAttach : horarioListNew) {
                horarioListNewHorarioToAttach = em.getReference(horarioListNewHorarioToAttach.getClass(), horarioListNewHorarioToAttach.getIdhorario());
                attachedHorarioListNew.add(horarioListNewHorarioToAttach);
            }
            horarioListNew = attachedHorarioListNew;
            grupoClase.setHorarioList(horarioListNew);
            List<Asistencias> attachedAsistenciasListNew = new ArrayList<Asistencias>();
            for (Asistencias asistenciasListNewAsistenciasToAttach : asistenciasListNew) {
                asistenciasListNewAsistenciasToAttach = em.getReference(asistenciasListNewAsistenciasToAttach.getClass(), asistenciasListNewAsistenciasToAttach.getIdasistencia());
                attachedAsistenciasListNew.add(asistenciasListNewAsistenciasToAttach);
            }
            asistenciasListNew = attachedAsistenciasListNew;
            grupoClase.setAsistenciasList(asistenciasListNew);
            grupoClase = em.merge(grupoClase);
            if (idTipoDanzaOld != null && !idTipoDanzaOld.equals(idTipoDanzaNew)) {
                idTipoDanzaOld.getGrupoClaseList().remove(grupoClase);
                idTipoDanzaOld = em.merge(idTipoDanzaOld);
            }
            if (idTipoDanzaNew != null && !idTipoDanzaNew.equals(idTipoDanzaOld)) {
                idTipoDanzaNew.getGrupoClaseList().add(grupoClase);
                idTipoDanzaNew = em.merge(idTipoDanzaNew);
            }
            for (Alumnos alumnosListOldAlumnos : alumnosListOld) {
                if (!alumnosListNew.contains(alumnosListOldAlumnos)) {
                    alumnosListOldAlumnos.getGrupoClaseList().remove(grupoClase);
                    alumnosListOldAlumnos = em.merge(alumnosListOldAlumnos);
                }
            }
            for (Alumnos alumnosListNewAlumnos : alumnosListNew) {
                if (!alumnosListOld.contains(alumnosListNewAlumnos)) {
                    alumnosListNewAlumnos.getGrupoClaseList().add(grupoClase);
                    alumnosListNewAlumnos = em.merge(alumnosListNewAlumnos);
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
            for (Asistencias asistenciasListNewAsistencias : asistenciasListNew) {
                if (!asistenciasListOld.contains(asistenciasListNewAsistencias)) {
                    GrupoClase oldIdGrupoClaseOfAsistenciasListNewAsistencias = asistenciasListNewAsistencias.getIdGrupoClase();
                    asistenciasListNewAsistencias.setIdGrupoClase(grupoClase);
                    asistenciasListNewAsistencias = em.merge(asistenciasListNewAsistencias);
                    if (oldIdGrupoClaseOfAsistenciasListNewAsistencias != null && !oldIdGrupoClaseOfAsistenciasListNewAsistencias.equals(grupoClase)) {
                        oldIdGrupoClaseOfAsistenciasListNewAsistencias.getAsistenciasList().remove(asistenciasListNewAsistencias);
                        oldIdGrupoClaseOfAsistenciasListNewAsistencias = em.merge(oldIdGrupoClaseOfAsistenciasListNewAsistencias);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = grupoClase.getIdGrupoClase();
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
                grupoClase.getIdGrupoClase();
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
            List<Asistencias> asistenciasListOrphanCheck = grupoClase.getAsistenciasList();
            for (Asistencias asistenciasListOrphanCheckAsistencias : asistenciasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This GrupoClase (" + grupoClase + ") cannot be destroyed since the Asistencias " + asistenciasListOrphanCheckAsistencias + " in its asistenciasList field has a non-nullable idGrupoClase field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            TipoDanza idTipoDanza = grupoClase.getIdTipoDanza();
            if (idTipoDanza != null) {
                idTipoDanza.getGrupoClaseList().remove(grupoClase);
                idTipoDanza = em.merge(idTipoDanza);
            }
            List<Alumnos> alumnosList = grupoClase.getAlumnosList();
            for (Alumnos alumnosListAlumnos : alumnosList) {
                alumnosListAlumnos.getGrupoClaseList().remove(grupoClase);
                alumnosListAlumnos = em.merge(alumnosListAlumnos);
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
