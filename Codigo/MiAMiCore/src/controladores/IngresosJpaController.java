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
import modelo.Inscripciones;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Ingresos;
import modelo.Mensualidades;

/**
 *
 * @author macbookpro
 */
public class IngresosJpaController implements Serializable {

    public IngresosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ingresos ingresos) {
        if (ingresos.getInscripcionesList() == null) {
            ingresos.setInscripcionesList(new ArrayList<Inscripciones>());
        }
        if (ingresos.getMensualidadesList() == null) {
            ingresos.setMensualidadesList(new ArrayList<Mensualidades>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Inscripciones> attachedInscripcionesList = new ArrayList<Inscripciones>();
            for (Inscripciones inscripcionesListInscripcionesToAttach : ingresos.getInscripcionesList()) {
                inscripcionesListInscripcionesToAttach = em.getReference(inscripcionesListInscripcionesToAttach.getClass(), inscripcionesListInscripcionesToAttach.getIdinscripcion());
                attachedInscripcionesList.add(inscripcionesListInscripcionesToAttach);
            }
            ingresos.setInscripcionesList(attachedInscripcionesList);
            List<Mensualidades> attachedMensualidadesList = new ArrayList<Mensualidades>();
            for (Mensualidades mensualidadesListMensualidadesToAttach : ingresos.getMensualidadesList()) {
                mensualidadesListMensualidadesToAttach = em.getReference(mensualidadesListMensualidadesToAttach.getClass(), mensualidadesListMensualidadesToAttach.getIdmensualidad());
                attachedMensualidadesList.add(mensualidadesListMensualidadesToAttach);
            }
            ingresos.setMensualidadesList(attachedMensualidadesList);
            em.persist(ingresos);
            for (Inscripciones inscripcionesListInscripciones : ingresos.getInscripcionesList()) {
                Ingresos oldIdingresoOfInscripcionesListInscripciones = inscripcionesListInscripciones.getIdingreso();
                inscripcionesListInscripciones.setIdingreso(ingresos);
                inscripcionesListInscripciones = em.merge(inscripcionesListInscripciones);
                if (oldIdingresoOfInscripcionesListInscripciones != null) {
                    oldIdingresoOfInscripcionesListInscripciones.getInscripcionesList().remove(inscripcionesListInscripciones);
                    oldIdingresoOfInscripcionesListInscripciones = em.merge(oldIdingresoOfInscripcionesListInscripciones);
                }
            }
            for (Mensualidades mensualidadesListMensualidades : ingresos.getMensualidadesList()) {
                Ingresos oldIdingresoOfMensualidadesListMensualidades = mensualidadesListMensualidades.getIdingreso();
                mensualidadesListMensualidades.setIdingreso(ingresos);
                mensualidadesListMensualidades = em.merge(mensualidadesListMensualidades);
                if (oldIdingresoOfMensualidadesListMensualidades != null) {
                    oldIdingresoOfMensualidadesListMensualidades.getMensualidadesList().remove(mensualidadesListMensualidades);
                    oldIdingresoOfMensualidadesListMensualidades = em.merge(oldIdingresoOfMensualidadesListMensualidades);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ingresos ingresos) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ingresos persistentIngresos = em.find(Ingresos.class, ingresos.getIdingreso());
            List<Inscripciones> inscripcionesListOld = persistentIngresos.getInscripcionesList();
            List<Inscripciones> inscripcionesListNew = ingresos.getInscripcionesList();
            List<Mensualidades> mensualidadesListOld = persistentIngresos.getMensualidadesList();
            List<Mensualidades> mensualidadesListNew = ingresos.getMensualidadesList();
            List<String> illegalOrphanMessages = null;
            for (Inscripciones inscripcionesListOldInscripciones : inscripcionesListOld) {
                if (!inscripcionesListNew.contains(inscripcionesListOldInscripciones)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Inscripciones " + inscripcionesListOldInscripciones + " since its idingreso field is not nullable.");
                }
            }
            for (Mensualidades mensualidadesListOldMensualidades : mensualidadesListOld) {
                if (!mensualidadesListNew.contains(mensualidadesListOldMensualidades)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Mensualidades " + mensualidadesListOldMensualidades + " since its idingreso field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Inscripciones> attachedInscripcionesListNew = new ArrayList<Inscripciones>();
            for (Inscripciones inscripcionesListNewInscripcionesToAttach : inscripcionesListNew) {
                inscripcionesListNewInscripcionesToAttach = em.getReference(inscripcionesListNewInscripcionesToAttach.getClass(), inscripcionesListNewInscripcionesToAttach.getIdinscripcion());
                attachedInscripcionesListNew.add(inscripcionesListNewInscripcionesToAttach);
            }
            inscripcionesListNew = attachedInscripcionesListNew;
            ingresos.setInscripcionesList(inscripcionesListNew);
            List<Mensualidades> attachedMensualidadesListNew = new ArrayList<Mensualidades>();
            for (Mensualidades mensualidadesListNewMensualidadesToAttach : mensualidadesListNew) {
                mensualidadesListNewMensualidadesToAttach = em.getReference(mensualidadesListNewMensualidadesToAttach.getClass(), mensualidadesListNewMensualidadesToAttach.getIdmensualidad());
                attachedMensualidadesListNew.add(mensualidadesListNewMensualidadesToAttach);
            }
            mensualidadesListNew = attachedMensualidadesListNew;
            ingresos.setMensualidadesList(mensualidadesListNew);
            ingresos = em.merge(ingresos);
            for (Inscripciones inscripcionesListNewInscripciones : inscripcionesListNew) {
                if (!inscripcionesListOld.contains(inscripcionesListNewInscripciones)) {
                    Ingresos oldIdingresoOfInscripcionesListNewInscripciones = inscripcionesListNewInscripciones.getIdingreso();
                    inscripcionesListNewInscripciones.setIdingreso(ingresos);
                    inscripcionesListNewInscripciones = em.merge(inscripcionesListNewInscripciones);
                    if (oldIdingresoOfInscripcionesListNewInscripciones != null && !oldIdingresoOfInscripcionesListNewInscripciones.equals(ingresos)) {
                        oldIdingresoOfInscripcionesListNewInscripciones.getInscripcionesList().remove(inscripcionesListNewInscripciones);
                        oldIdingresoOfInscripcionesListNewInscripciones = em.merge(oldIdingresoOfInscripcionesListNewInscripciones);
                    }
                }
            }
            for (Mensualidades mensualidadesListNewMensualidades : mensualidadesListNew) {
                if (!mensualidadesListOld.contains(mensualidadesListNewMensualidades)) {
                    Ingresos oldIdingresoOfMensualidadesListNewMensualidades = mensualidadesListNewMensualidades.getIdingreso();
                    mensualidadesListNewMensualidades.setIdingreso(ingresos);
                    mensualidadesListNewMensualidades = em.merge(mensualidadesListNewMensualidades);
                    if (oldIdingresoOfMensualidadesListNewMensualidades != null && !oldIdingresoOfMensualidadesListNewMensualidades.equals(ingresos)) {
                        oldIdingresoOfMensualidadesListNewMensualidades.getMensualidadesList().remove(mensualidadesListNewMensualidades);
                        oldIdingresoOfMensualidadesListNewMensualidades = em.merge(oldIdingresoOfMensualidadesListNewMensualidades);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ingresos.getIdingreso();
                if (findIngresos(id) == null) {
                    throw new NonexistentEntityException("The ingresos with id " + id + " no longer exists.");
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
            Ingresos ingresos;
            try {
                ingresos = em.getReference(Ingresos.class, id);
                ingresos.getIdingreso();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ingresos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Inscripciones> inscripcionesListOrphanCheck = ingresos.getInscripcionesList();
            for (Inscripciones inscripcionesListOrphanCheckInscripciones : inscripcionesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ingresos (" + ingresos + ") cannot be destroyed since the Inscripciones " + inscripcionesListOrphanCheckInscripciones + " in its inscripcionesList field has a non-nullable idingreso field.");
            }
            List<Mensualidades> mensualidadesListOrphanCheck = ingresos.getMensualidadesList();
            for (Mensualidades mensualidadesListOrphanCheckMensualidades : mensualidadesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ingresos (" + ingresos + ") cannot be destroyed since the Mensualidades " + mensualidadesListOrphanCheckMensualidades + " in its mensualidadesList field has a non-nullable idingreso field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(ingresos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ingresos> findIngresosEntities() {
        return findIngresosEntities(true, -1, -1);
    }

    public List<Ingresos> findIngresosEntities(int maxResults, int firstResult) {
        return findIngresosEntities(false, maxResults, firstResult);
    }

    private List<Ingresos> findIngresosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ingresos.class));
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

    public Ingresos findIngresos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ingresos.class, id);
        } finally {
            em.close();
        }
    }

    public int getIngresosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ingresos> rt = cq.from(Ingresos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
