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
import modelo.GrupoClase;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Alumno;
import modelo.Mensualidad;
import modelo.Inscripcion;
import modelo.Asistencia;

/**
 *
 * @author macbookpro
 */
public class AlumnoJpaController implements Serializable {

    public AlumnoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Alumno alumno) {
        if (alumno.getGrupoClaseList() == null) {
            alumno.setGrupoClaseList(new ArrayList<GrupoClase>());
        }
        if (alumno.getMensualidadList() == null) {
            alumno.setMensualidadList(new ArrayList<Mensualidad>());
        }
        if (alumno.getInscripcionList() == null) {
            alumno.setInscripcionList(new ArrayList<Inscripcion>());
        }
        if (alumno.getAsistenciaList() == null) {
            alumno.setAsistenciaList(new ArrayList<Asistencia>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<GrupoClase> attachedGrupoClaseList = new ArrayList<GrupoClase>();
            for (GrupoClase grupoClaseListGrupoClaseToAttach : alumno.getGrupoClaseList()) {
                grupoClaseListGrupoClaseToAttach = em.getReference(grupoClaseListGrupoClaseToAttach.getClass(), grupoClaseListGrupoClaseToAttach.getId());
                attachedGrupoClaseList.add(grupoClaseListGrupoClaseToAttach);
            }
            alumno.setGrupoClaseList(attachedGrupoClaseList);
            List<Mensualidad> attachedMensualidadList = new ArrayList<Mensualidad>();
            for (Mensualidad mensualidadListMensualidadToAttach : alumno.getMensualidadList()) {
                mensualidadListMensualidadToAttach = em.getReference(mensualidadListMensualidadToAttach.getClass(), mensualidadListMensualidadToAttach.getId());
                attachedMensualidadList.add(mensualidadListMensualidadToAttach);
            }
            alumno.setMensualidadList(attachedMensualidadList);
            List<Inscripcion> attachedInscripcionList = new ArrayList<Inscripcion>();
            for (Inscripcion inscripcionListInscripcionToAttach : alumno.getInscripcionList()) {
                inscripcionListInscripcionToAttach = em.getReference(inscripcionListInscripcionToAttach.getClass(), inscripcionListInscripcionToAttach.getId());
                attachedInscripcionList.add(inscripcionListInscripcionToAttach);
            }
            alumno.setInscripcionList(attachedInscripcionList);
            List<Asistencia> attachedAsistenciaList = new ArrayList<Asistencia>();
            for (Asistencia asistenciaListAsistenciaToAttach : alumno.getAsistenciaList()) {
                asistenciaListAsistenciaToAttach = em.getReference(asistenciaListAsistenciaToAttach.getClass(), asistenciaListAsistenciaToAttach.getId());
                attachedAsistenciaList.add(asistenciaListAsistenciaToAttach);
            }
            alumno.setAsistenciaList(attachedAsistenciaList);
            em.persist(alumno);
            for (GrupoClase grupoClaseListGrupoClase : alumno.getGrupoClaseList()) {
                grupoClaseListGrupoClase.getAlumnoList().add(alumno);
                grupoClaseListGrupoClase = em.merge(grupoClaseListGrupoClase);
            }
            for (Mensualidad mensualidadListMensualidad : alumno.getMensualidadList()) {
                Alumno oldIdalumnoOfMensualidadListMensualidad = mensualidadListMensualidad.getIdalumno();
                mensualidadListMensualidad.setIdalumno(alumno);
                mensualidadListMensualidad = em.merge(mensualidadListMensualidad);
                if (oldIdalumnoOfMensualidadListMensualidad != null) {
                    oldIdalumnoOfMensualidadListMensualidad.getMensualidadList().remove(mensualidadListMensualidad);
                    oldIdalumnoOfMensualidadListMensualidad = em.merge(oldIdalumnoOfMensualidadListMensualidad);
                }
            }
            for (Inscripcion inscripcionListInscripcion : alumno.getInscripcionList()) {
                Alumno oldIdalumnoOfInscripcionListInscripcion = inscripcionListInscripcion.getIdalumno();
                inscripcionListInscripcion.setIdalumno(alumno);
                inscripcionListInscripcion = em.merge(inscripcionListInscripcion);
                if (oldIdalumnoOfInscripcionListInscripcion != null) {
                    oldIdalumnoOfInscripcionListInscripcion.getInscripcionList().remove(inscripcionListInscripcion);
                    oldIdalumnoOfInscripcionListInscripcion = em.merge(oldIdalumnoOfInscripcionListInscripcion);
                }
            }
            for (Asistencia asistenciaListAsistencia : alumno.getAsistenciaList()) {
                Alumno oldIdAlumnoOfAsistenciaListAsistencia = asistenciaListAsistencia.getIdAlumno();
                asistenciaListAsistencia.setIdAlumno(alumno);
                asistenciaListAsistencia = em.merge(asistenciaListAsistencia);
                if (oldIdAlumnoOfAsistenciaListAsistencia != null) {
                    oldIdAlumnoOfAsistenciaListAsistencia.getAsistenciaList().remove(asistenciaListAsistencia);
                    oldIdAlumnoOfAsistenciaListAsistencia = em.merge(oldIdAlumnoOfAsistenciaListAsistencia);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Alumno alumno) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Alumno persistentAlumno = em.find(Alumno.class, alumno.getId());
            List<GrupoClase> grupoClaseListOld = persistentAlumno.getGrupoClaseList();
            List<GrupoClase> grupoClaseListNew = alumno.getGrupoClaseList();
            List<Mensualidad> mensualidadListOld = persistentAlumno.getMensualidadList();
            List<Mensualidad> mensualidadListNew = alumno.getMensualidadList();
            List<Inscripcion> inscripcionListOld = persistentAlumno.getInscripcionList();
            List<Inscripcion> inscripcionListNew = alumno.getInscripcionList();
            List<Asistencia> asistenciaListOld = persistentAlumno.getAsistenciaList();
            List<Asistencia> asistenciaListNew = alumno.getAsistenciaList();
            List<String> illegalOrphanMessages = null;
            for (Mensualidad mensualidadListOldMensualidad : mensualidadListOld) {
                if (!mensualidadListNew.contains(mensualidadListOldMensualidad)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Mensualidad " + mensualidadListOldMensualidad + " since its idalumno field is not nullable.");
                }
            }
            for (Inscripcion inscripcionListOldInscripcion : inscripcionListOld) {
                if (!inscripcionListNew.contains(inscripcionListOldInscripcion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Inscripcion " + inscripcionListOldInscripcion + " since its idalumno field is not nullable.");
                }
            }
            for (Asistencia asistenciaListOldAsistencia : asistenciaListOld) {
                if (!asistenciaListNew.contains(asistenciaListOldAsistencia)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Asistencia " + asistenciaListOldAsistencia + " since its idAlumno field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<GrupoClase> attachedGrupoClaseListNew = new ArrayList<GrupoClase>();
            for (GrupoClase grupoClaseListNewGrupoClaseToAttach : grupoClaseListNew) {
                grupoClaseListNewGrupoClaseToAttach = em.getReference(grupoClaseListNewGrupoClaseToAttach.getClass(), grupoClaseListNewGrupoClaseToAttach.getId());
                attachedGrupoClaseListNew.add(grupoClaseListNewGrupoClaseToAttach);
            }
            grupoClaseListNew = attachedGrupoClaseListNew;
            alumno.setGrupoClaseList(grupoClaseListNew);
            List<Mensualidad> attachedMensualidadListNew = new ArrayList<Mensualidad>();
            for (Mensualidad mensualidadListNewMensualidadToAttach : mensualidadListNew) {
                mensualidadListNewMensualidadToAttach = em.getReference(mensualidadListNewMensualidadToAttach.getClass(), mensualidadListNewMensualidadToAttach.getId());
                attachedMensualidadListNew.add(mensualidadListNewMensualidadToAttach);
            }
            mensualidadListNew = attachedMensualidadListNew;
            alumno.setMensualidadList(mensualidadListNew);
            List<Inscripcion> attachedInscripcionListNew = new ArrayList<Inscripcion>();
            for (Inscripcion inscripcionListNewInscripcionToAttach : inscripcionListNew) {
                inscripcionListNewInscripcionToAttach = em.getReference(inscripcionListNewInscripcionToAttach.getClass(), inscripcionListNewInscripcionToAttach.getId());
                attachedInscripcionListNew.add(inscripcionListNewInscripcionToAttach);
            }
            inscripcionListNew = attachedInscripcionListNew;
            alumno.setInscripcionList(inscripcionListNew);
            List<Asistencia> attachedAsistenciaListNew = new ArrayList<Asistencia>();
            for (Asistencia asistenciaListNewAsistenciaToAttach : asistenciaListNew) {
                asistenciaListNewAsistenciaToAttach = em.getReference(asistenciaListNewAsistenciaToAttach.getClass(), asistenciaListNewAsistenciaToAttach.getId());
                attachedAsistenciaListNew.add(asistenciaListNewAsistenciaToAttach);
            }
            asistenciaListNew = attachedAsistenciaListNew;
            alumno.setAsistenciaList(asistenciaListNew);
            alumno = em.merge(alumno);
            for (GrupoClase grupoClaseListOldGrupoClase : grupoClaseListOld) {
                if (!grupoClaseListNew.contains(grupoClaseListOldGrupoClase)) {
                    grupoClaseListOldGrupoClase.getAlumnoList().remove(alumno);
                    grupoClaseListOldGrupoClase = em.merge(grupoClaseListOldGrupoClase);
                }
            }
            for (GrupoClase grupoClaseListNewGrupoClase : grupoClaseListNew) {
                if (!grupoClaseListOld.contains(grupoClaseListNewGrupoClase)) {
                    grupoClaseListNewGrupoClase.getAlumnoList().add(alumno);
                    grupoClaseListNewGrupoClase = em.merge(grupoClaseListNewGrupoClase);
                }
            }
            for (Mensualidad mensualidadListNewMensualidad : mensualidadListNew) {
                if (!mensualidadListOld.contains(mensualidadListNewMensualidad)) {
                    Alumno oldIdalumnoOfMensualidadListNewMensualidad = mensualidadListNewMensualidad.getIdalumno();
                    mensualidadListNewMensualidad.setIdalumno(alumno);
                    mensualidadListNewMensualidad = em.merge(mensualidadListNewMensualidad);
                    if (oldIdalumnoOfMensualidadListNewMensualidad != null && !oldIdalumnoOfMensualidadListNewMensualidad.equals(alumno)) {
                        oldIdalumnoOfMensualidadListNewMensualidad.getMensualidadList().remove(mensualidadListNewMensualidad);
                        oldIdalumnoOfMensualidadListNewMensualidad = em.merge(oldIdalumnoOfMensualidadListNewMensualidad);
                    }
                }
            }
            for (Inscripcion inscripcionListNewInscripcion : inscripcionListNew) {
                if (!inscripcionListOld.contains(inscripcionListNewInscripcion)) {
                    Alumno oldIdalumnoOfInscripcionListNewInscripcion = inscripcionListNewInscripcion.getIdalumno();
                    inscripcionListNewInscripcion.setIdalumno(alumno);
                    inscripcionListNewInscripcion = em.merge(inscripcionListNewInscripcion);
                    if (oldIdalumnoOfInscripcionListNewInscripcion != null && !oldIdalumnoOfInscripcionListNewInscripcion.equals(alumno)) {
                        oldIdalumnoOfInscripcionListNewInscripcion.getInscripcionList().remove(inscripcionListNewInscripcion);
                        oldIdalumnoOfInscripcionListNewInscripcion = em.merge(oldIdalumnoOfInscripcionListNewInscripcion);
                    }
                }
            }
            for (Asistencia asistenciaListNewAsistencia : asistenciaListNew) {
                if (!asistenciaListOld.contains(asistenciaListNewAsistencia)) {
                    Alumno oldIdAlumnoOfAsistenciaListNewAsistencia = asistenciaListNewAsistencia.getIdAlumno();
                    asistenciaListNewAsistencia.setIdAlumno(alumno);
                    asistenciaListNewAsistencia = em.merge(asistenciaListNewAsistencia);
                    if (oldIdAlumnoOfAsistenciaListNewAsistencia != null && !oldIdAlumnoOfAsistenciaListNewAsistencia.equals(alumno)) {
                        oldIdAlumnoOfAsistenciaListNewAsistencia.getAsistenciaList().remove(asistenciaListNewAsistencia);
                        oldIdAlumnoOfAsistenciaListNewAsistencia = em.merge(oldIdAlumnoOfAsistenciaListNewAsistencia);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = alumno.getId();
                if (findAlumno(id) == null) {
                    throw new NonexistentEntityException("The alumno with id " + id + " no longer exists.");
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
            Alumno alumno;
            try {
                alumno = em.getReference(Alumno.class, id);
                alumno.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The alumno with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Mensualidad> mensualidadListOrphanCheck = alumno.getMensualidadList();
            for (Mensualidad mensualidadListOrphanCheckMensualidad : mensualidadListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Alumno (" + alumno + ") cannot be destroyed since the Mensualidad " + mensualidadListOrphanCheckMensualidad + " in its mensualidadList field has a non-nullable idalumno field.");
            }
            List<Inscripcion> inscripcionListOrphanCheck = alumno.getInscripcionList();
            for (Inscripcion inscripcionListOrphanCheckInscripcion : inscripcionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Alumno (" + alumno + ") cannot be destroyed since the Inscripcion " + inscripcionListOrphanCheckInscripcion + " in its inscripcionList field has a non-nullable idalumno field.");
            }
            List<Asistencia> asistenciaListOrphanCheck = alumno.getAsistenciaList();
            for (Asistencia asistenciaListOrphanCheckAsistencia : asistenciaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Alumno (" + alumno + ") cannot be destroyed since the Asistencia " + asistenciaListOrphanCheckAsistencia + " in its asistenciaList field has a non-nullable idAlumno field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<GrupoClase> grupoClaseList = alumno.getGrupoClaseList();
            for (GrupoClase grupoClaseListGrupoClase : grupoClaseList) {
                grupoClaseListGrupoClase.getAlumnoList().remove(alumno);
                grupoClaseListGrupoClase = em.merge(grupoClaseListGrupoClase);
            }
            em.remove(alumno);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Alumno> findAlumnoEntities() {
        return findAlumnoEntities(true, -1, -1);
    }

    public List<Alumno> findAlumnoEntities(int maxResults, int firstResult) {
        return findAlumnoEntities(false, maxResults, firstResult);
    }

    private List<Alumno> findAlumnoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Alumno.class));
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

    public Alumno findAlumno(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Alumno.class, id);
        } finally {
            em.close();
        }
    }

    public int getAlumnoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Alumno> rt = cq.from(Alumno.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
