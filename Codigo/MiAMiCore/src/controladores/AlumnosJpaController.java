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
import modelo.Alumnos;
import modelo.Inscripciones;
import modelo.Mensualidades;
import modelo.Asistencias;

/**
 *
 * @author macbookpro
 */
public class AlumnosJpaController implements Serializable {

    public AlumnosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Alumnos alumnos) {
        if (alumnos.getGrupoClaseList() == null) {
            alumnos.setGrupoClaseList(new ArrayList<GrupoClase>());
        }
        if (alumnos.getInscripcionesList() == null) {
            alumnos.setInscripcionesList(new ArrayList<Inscripciones>());
        }
        if (alumnos.getMensualidadesList() == null) {
            alumnos.setMensualidadesList(new ArrayList<Mensualidades>());
        }
        if (alumnos.getAsistenciasList() == null) {
            alumnos.setAsistenciasList(new ArrayList<Asistencias>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<GrupoClase> attachedGrupoClaseList = new ArrayList<GrupoClase>();
            for (GrupoClase grupoClaseListGrupoClaseToAttach : alumnos.getGrupoClaseList()) {
                grupoClaseListGrupoClaseToAttach = em.getReference(grupoClaseListGrupoClaseToAttach.getClass(), grupoClaseListGrupoClaseToAttach.getIdGrupoClase());
                attachedGrupoClaseList.add(grupoClaseListGrupoClaseToAttach);
            }
            alumnos.setGrupoClaseList(attachedGrupoClaseList);
            List<Inscripciones> attachedInscripcionesList = new ArrayList<Inscripciones>();
            for (Inscripciones inscripcionesListInscripcionesToAttach : alumnos.getInscripcionesList()) {
                inscripcionesListInscripcionesToAttach = em.getReference(inscripcionesListInscripcionesToAttach.getClass(), inscripcionesListInscripcionesToAttach.getIdinscripcion());
                attachedInscripcionesList.add(inscripcionesListInscripcionesToAttach);
            }
            alumnos.setInscripcionesList(attachedInscripcionesList);
            List<Mensualidades> attachedMensualidadesList = new ArrayList<Mensualidades>();
            for (Mensualidades mensualidadesListMensualidadesToAttach : alumnos.getMensualidadesList()) {
                mensualidadesListMensualidadesToAttach = em.getReference(mensualidadesListMensualidadesToAttach.getClass(), mensualidadesListMensualidadesToAttach.getIdmensualidad());
                attachedMensualidadesList.add(mensualidadesListMensualidadesToAttach);
            }
            alumnos.setMensualidadesList(attachedMensualidadesList);
            List<Asistencias> attachedAsistenciasList = new ArrayList<Asistencias>();
            for (Asistencias asistenciasListAsistenciasToAttach : alumnos.getAsistenciasList()) {
                asistenciasListAsistenciasToAttach = em.getReference(asistenciasListAsistenciasToAttach.getClass(), asistenciasListAsistenciasToAttach.getIdasistencia());
                attachedAsistenciasList.add(asistenciasListAsistenciasToAttach);
            }
            alumnos.setAsistenciasList(attachedAsistenciasList);
            em.persist(alumnos);
            for (GrupoClase grupoClaseListGrupoClase : alumnos.getGrupoClaseList()) {
                grupoClaseListGrupoClase.getAlumnosList().add(alumnos);
                grupoClaseListGrupoClase = em.merge(grupoClaseListGrupoClase);
            }
            for (Inscripciones inscripcionesListInscripciones : alumnos.getInscripcionesList()) {
                Alumnos oldIdalumnoOfInscripcionesListInscripciones = inscripcionesListInscripciones.getIdalumno();
                inscripcionesListInscripciones.setIdalumno(alumnos);
                inscripcionesListInscripciones = em.merge(inscripcionesListInscripciones);
                if (oldIdalumnoOfInscripcionesListInscripciones != null) {
                    oldIdalumnoOfInscripcionesListInscripciones.getInscripcionesList().remove(inscripcionesListInscripciones);
                    oldIdalumnoOfInscripcionesListInscripciones = em.merge(oldIdalumnoOfInscripcionesListInscripciones);
                }
            }
            for (Mensualidades mensualidadesListMensualidades : alumnos.getMensualidadesList()) {
                Alumnos oldIdalumnoOfMensualidadesListMensualidades = mensualidadesListMensualidades.getIdalumno();
                mensualidadesListMensualidades.setIdalumno(alumnos);
                mensualidadesListMensualidades = em.merge(mensualidadesListMensualidades);
                if (oldIdalumnoOfMensualidadesListMensualidades != null) {
                    oldIdalumnoOfMensualidadesListMensualidades.getMensualidadesList().remove(mensualidadesListMensualidades);
                    oldIdalumnoOfMensualidadesListMensualidades = em.merge(oldIdalumnoOfMensualidadesListMensualidades);
                }
            }
            for (Asistencias asistenciasListAsistencias : alumnos.getAsistenciasList()) {
                Alumnos oldIdAlumnoOfAsistenciasListAsistencias = asistenciasListAsistencias.getIdAlumno();
                asistenciasListAsistencias.setIdAlumno(alumnos);
                asistenciasListAsistencias = em.merge(asistenciasListAsistencias);
                if (oldIdAlumnoOfAsistenciasListAsistencias != null) {
                    oldIdAlumnoOfAsistenciasListAsistencias.getAsistenciasList().remove(asistenciasListAsistencias);
                    oldIdAlumnoOfAsistenciasListAsistencias = em.merge(oldIdAlumnoOfAsistenciasListAsistencias);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Alumnos alumnos) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Alumnos persistentAlumnos = em.find(Alumnos.class, alumnos.getIdalumno());
            List<GrupoClase> grupoClaseListOld = persistentAlumnos.getGrupoClaseList();
            List<GrupoClase> grupoClaseListNew = alumnos.getGrupoClaseList();
            List<Inscripciones> inscripcionesListOld = persistentAlumnos.getInscripcionesList();
            List<Inscripciones> inscripcionesListNew = alumnos.getInscripcionesList();
            List<Mensualidades> mensualidadesListOld = persistentAlumnos.getMensualidadesList();
            List<Mensualidades> mensualidadesListNew = alumnos.getMensualidadesList();
            List<Asistencias> asistenciasListOld = persistentAlumnos.getAsistenciasList();
            List<Asistencias> asistenciasListNew = alumnos.getAsistenciasList();
            List<String> illegalOrphanMessages = null;
            for (Inscripciones inscripcionesListOldInscripciones : inscripcionesListOld) {
                if (!inscripcionesListNew.contains(inscripcionesListOldInscripciones)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Inscripciones " + inscripcionesListOldInscripciones + " since its idalumno field is not nullable.");
                }
            }
            for (Mensualidades mensualidadesListOldMensualidades : mensualidadesListOld) {
                if (!mensualidadesListNew.contains(mensualidadesListOldMensualidades)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Mensualidades " + mensualidadesListOldMensualidades + " since its idalumno field is not nullable.");
                }
            }
            for (Asistencias asistenciasListOldAsistencias : asistenciasListOld) {
                if (!asistenciasListNew.contains(asistenciasListOldAsistencias)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Asistencias " + asistenciasListOldAsistencias + " since its idAlumno field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<GrupoClase> attachedGrupoClaseListNew = new ArrayList<GrupoClase>();
            for (GrupoClase grupoClaseListNewGrupoClaseToAttach : grupoClaseListNew) {
                grupoClaseListNewGrupoClaseToAttach = em.getReference(grupoClaseListNewGrupoClaseToAttach.getClass(), grupoClaseListNewGrupoClaseToAttach.getIdGrupoClase());
                attachedGrupoClaseListNew.add(grupoClaseListNewGrupoClaseToAttach);
            }
            grupoClaseListNew = attachedGrupoClaseListNew;
            alumnos.setGrupoClaseList(grupoClaseListNew);
            List<Inscripciones> attachedInscripcionesListNew = new ArrayList<Inscripciones>();
            for (Inscripciones inscripcionesListNewInscripcionesToAttach : inscripcionesListNew) {
                inscripcionesListNewInscripcionesToAttach = em.getReference(inscripcionesListNewInscripcionesToAttach.getClass(), inscripcionesListNewInscripcionesToAttach.getIdinscripcion());
                attachedInscripcionesListNew.add(inscripcionesListNewInscripcionesToAttach);
            }
            inscripcionesListNew = attachedInscripcionesListNew;
            alumnos.setInscripcionesList(inscripcionesListNew);
            List<Mensualidades> attachedMensualidadesListNew = new ArrayList<Mensualidades>();
            for (Mensualidades mensualidadesListNewMensualidadesToAttach : mensualidadesListNew) {
                mensualidadesListNewMensualidadesToAttach = em.getReference(mensualidadesListNewMensualidadesToAttach.getClass(), mensualidadesListNewMensualidadesToAttach.getIdmensualidad());
                attachedMensualidadesListNew.add(mensualidadesListNewMensualidadesToAttach);
            }
            mensualidadesListNew = attachedMensualidadesListNew;
            alumnos.setMensualidadesList(mensualidadesListNew);
            List<Asistencias> attachedAsistenciasListNew = new ArrayList<Asistencias>();
            for (Asistencias asistenciasListNewAsistenciasToAttach : asistenciasListNew) {
                asistenciasListNewAsistenciasToAttach = em.getReference(asistenciasListNewAsistenciasToAttach.getClass(), asistenciasListNewAsistenciasToAttach.getIdasistencia());
                attachedAsistenciasListNew.add(asistenciasListNewAsistenciasToAttach);
            }
            asistenciasListNew = attachedAsistenciasListNew;
            alumnos.setAsistenciasList(asistenciasListNew);
            alumnos = em.merge(alumnos);
            for (GrupoClase grupoClaseListOldGrupoClase : grupoClaseListOld) {
                if (!grupoClaseListNew.contains(grupoClaseListOldGrupoClase)) {
                    grupoClaseListOldGrupoClase.getAlumnosList().remove(alumnos);
                    grupoClaseListOldGrupoClase = em.merge(grupoClaseListOldGrupoClase);
                }
            }
            for (GrupoClase grupoClaseListNewGrupoClase : grupoClaseListNew) {
                if (!grupoClaseListOld.contains(grupoClaseListNewGrupoClase)) {
                    grupoClaseListNewGrupoClase.getAlumnosList().add(alumnos);
                    grupoClaseListNewGrupoClase = em.merge(grupoClaseListNewGrupoClase);
                }
            }
            for (Inscripciones inscripcionesListNewInscripciones : inscripcionesListNew) {
                if (!inscripcionesListOld.contains(inscripcionesListNewInscripciones)) {
                    Alumnos oldIdalumnoOfInscripcionesListNewInscripciones = inscripcionesListNewInscripciones.getIdalumno();
                    inscripcionesListNewInscripciones.setIdalumno(alumnos);
                    inscripcionesListNewInscripciones = em.merge(inscripcionesListNewInscripciones);
                    if (oldIdalumnoOfInscripcionesListNewInscripciones != null && !oldIdalumnoOfInscripcionesListNewInscripciones.equals(alumnos)) {
                        oldIdalumnoOfInscripcionesListNewInscripciones.getInscripcionesList().remove(inscripcionesListNewInscripciones);
                        oldIdalumnoOfInscripcionesListNewInscripciones = em.merge(oldIdalumnoOfInscripcionesListNewInscripciones);
                    }
                }
            }
            for (Mensualidades mensualidadesListNewMensualidades : mensualidadesListNew) {
                if (!mensualidadesListOld.contains(mensualidadesListNewMensualidades)) {
                    Alumnos oldIdalumnoOfMensualidadesListNewMensualidades = mensualidadesListNewMensualidades.getIdalumno();
                    mensualidadesListNewMensualidades.setIdalumno(alumnos);
                    mensualidadesListNewMensualidades = em.merge(mensualidadesListNewMensualidades);
                    if (oldIdalumnoOfMensualidadesListNewMensualidades != null && !oldIdalumnoOfMensualidadesListNewMensualidades.equals(alumnos)) {
                        oldIdalumnoOfMensualidadesListNewMensualidades.getMensualidadesList().remove(mensualidadesListNewMensualidades);
                        oldIdalumnoOfMensualidadesListNewMensualidades = em.merge(oldIdalumnoOfMensualidadesListNewMensualidades);
                    }
                }
            }
            for (Asistencias asistenciasListNewAsistencias : asistenciasListNew) {
                if (!asistenciasListOld.contains(asistenciasListNewAsistencias)) {
                    Alumnos oldIdAlumnoOfAsistenciasListNewAsistencias = asistenciasListNewAsistencias.getIdAlumno();
                    asistenciasListNewAsistencias.setIdAlumno(alumnos);
                    asistenciasListNewAsistencias = em.merge(asistenciasListNewAsistencias);
                    if (oldIdAlumnoOfAsistenciasListNewAsistencias != null && !oldIdAlumnoOfAsistenciasListNewAsistencias.equals(alumnos)) {
                        oldIdAlumnoOfAsistenciasListNewAsistencias.getAsistenciasList().remove(asistenciasListNewAsistencias);
                        oldIdAlumnoOfAsistenciasListNewAsistencias = em.merge(oldIdAlumnoOfAsistenciasListNewAsistencias);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = alumnos.getIdalumno();
                if (findAlumnos(id) == null) {
                    throw new NonexistentEntityException("The alumnos with id " + id + " no longer exists.");
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
            Alumnos alumnos;
            try {
                alumnos = em.getReference(Alumnos.class, id);
                alumnos.getIdalumno();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The alumnos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Inscripciones> inscripcionesListOrphanCheck = alumnos.getInscripcionesList();
            for (Inscripciones inscripcionesListOrphanCheckInscripciones : inscripcionesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Alumnos (" + alumnos + ") cannot be destroyed since the Inscripciones " + inscripcionesListOrphanCheckInscripciones + " in its inscripcionesList field has a non-nullable idalumno field.");
            }
            List<Mensualidades> mensualidadesListOrphanCheck = alumnos.getMensualidadesList();
            for (Mensualidades mensualidadesListOrphanCheckMensualidades : mensualidadesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Alumnos (" + alumnos + ") cannot be destroyed since the Mensualidades " + mensualidadesListOrphanCheckMensualidades + " in its mensualidadesList field has a non-nullable idalumno field.");
            }
            List<Asistencias> asistenciasListOrphanCheck = alumnos.getAsistenciasList();
            for (Asistencias asistenciasListOrphanCheckAsistencias : asistenciasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Alumnos (" + alumnos + ") cannot be destroyed since the Asistencias " + asistenciasListOrphanCheckAsistencias + " in its asistenciasList field has a non-nullable idAlumno field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<GrupoClase> grupoClaseList = alumnos.getGrupoClaseList();
            for (GrupoClase grupoClaseListGrupoClase : grupoClaseList) {
                grupoClaseListGrupoClase.getAlumnosList().remove(alumnos);
                grupoClaseListGrupoClase = em.merge(grupoClaseListGrupoClase);
            }
            em.remove(alumnos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Alumnos> findAlumnosEntities() {
        return findAlumnosEntities(true, -1, -1);
    }

    public List<Alumnos> findAlumnosEntities(int maxResults, int firstResult) {
        return findAlumnosEntities(false, maxResults, firstResult);
    }

    private List<Alumnos> findAlumnosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Alumnos.class));
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

    public Alumnos findAlumnos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Alumnos.class, id);
        } finally {
            em.close();
        }
    }

    public int getAlumnosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Alumnos> rt = cq.from(Alumnos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
