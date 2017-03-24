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
import modelo.Mensualidad;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Ingreso;
import modelo.Inscripcion;

/**
 *
 * @author macbookpro
 */
public class IngresoJpaController implements Serializable {

    public IngresoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ingreso ingreso) {
        if (ingreso.getMensualidadList() == null) {
            ingreso.setMensualidadList(new ArrayList<Mensualidad>());
        }
        if (ingreso.getInscripcionList() == null) {
            ingreso.setInscripcionList(new ArrayList<Inscripcion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Mensualidad> attachedMensualidadList = new ArrayList<Mensualidad>();
            for (Mensualidad mensualidadListMensualidadToAttach : ingreso.getMensualidadList()) {
                mensualidadListMensualidadToAttach = em.getReference(mensualidadListMensualidadToAttach.getClass(), mensualidadListMensualidadToAttach.getId());
                attachedMensualidadList.add(mensualidadListMensualidadToAttach);
            }
            ingreso.setMensualidadList(attachedMensualidadList);
            List<Inscripcion> attachedInscripcionList = new ArrayList<Inscripcion>();
            for (Inscripcion inscripcionListInscripcionToAttach : ingreso.getInscripcionList()) {
                inscripcionListInscripcionToAttach = em.getReference(inscripcionListInscripcionToAttach.getClass(), inscripcionListInscripcionToAttach.getId());
                attachedInscripcionList.add(inscripcionListInscripcionToAttach);
            }
            ingreso.setInscripcionList(attachedInscripcionList);
            em.persist(ingreso);
            for (Mensualidad mensualidadListMensualidad : ingreso.getMensualidadList()) {
                Ingreso oldIdingresoOfMensualidadListMensualidad = mensualidadListMensualidad.getIdingreso();
                mensualidadListMensualidad.setIdingreso(ingreso);
                mensualidadListMensualidad = em.merge(mensualidadListMensualidad);
                if (oldIdingresoOfMensualidadListMensualidad != null) {
                    oldIdingresoOfMensualidadListMensualidad.getMensualidadList().remove(mensualidadListMensualidad);
                    oldIdingresoOfMensualidadListMensualidad = em.merge(oldIdingresoOfMensualidadListMensualidad);
                }
            }
            for (Inscripcion inscripcionListInscripcion : ingreso.getInscripcionList()) {
                Ingreso oldIdingresoOfInscripcionListInscripcion = inscripcionListInscripcion.getIdingreso();
                inscripcionListInscripcion.setIdingreso(ingreso);
                inscripcionListInscripcion = em.merge(inscripcionListInscripcion);
                if (oldIdingresoOfInscripcionListInscripcion != null) {
                    oldIdingresoOfInscripcionListInscripcion.getInscripcionList().remove(inscripcionListInscripcion);
                    oldIdingresoOfInscripcionListInscripcion = em.merge(oldIdingresoOfInscripcionListInscripcion);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ingreso ingreso) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ingreso persistentIngreso = em.find(Ingreso.class, ingreso.getId());
            List<Mensualidad> mensualidadListOld = persistentIngreso.getMensualidadList();
            List<Mensualidad> mensualidadListNew = ingreso.getMensualidadList();
            List<Inscripcion> inscripcionListOld = persistentIngreso.getInscripcionList();
            List<Inscripcion> inscripcionListNew = ingreso.getInscripcionList();
            List<String> illegalOrphanMessages = null;
            for (Mensualidad mensualidadListOldMensualidad : mensualidadListOld) {
                if (!mensualidadListNew.contains(mensualidadListOldMensualidad)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Mensualidad " + mensualidadListOldMensualidad + " since its idingreso field is not nullable.");
                }
            }
            for (Inscripcion inscripcionListOldInscripcion : inscripcionListOld) {
                if (!inscripcionListNew.contains(inscripcionListOldInscripcion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Inscripcion " + inscripcionListOldInscripcion + " since its idingreso field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Mensualidad> attachedMensualidadListNew = new ArrayList<Mensualidad>();
            for (Mensualidad mensualidadListNewMensualidadToAttach : mensualidadListNew) {
                mensualidadListNewMensualidadToAttach = em.getReference(mensualidadListNewMensualidadToAttach.getClass(), mensualidadListNewMensualidadToAttach.getId());
                attachedMensualidadListNew.add(mensualidadListNewMensualidadToAttach);
            }
            mensualidadListNew = attachedMensualidadListNew;
            ingreso.setMensualidadList(mensualidadListNew);
            List<Inscripcion> attachedInscripcionListNew = new ArrayList<Inscripcion>();
            for (Inscripcion inscripcionListNewInscripcionToAttach : inscripcionListNew) {
                inscripcionListNewInscripcionToAttach = em.getReference(inscripcionListNewInscripcionToAttach.getClass(), inscripcionListNewInscripcionToAttach.getId());
                attachedInscripcionListNew.add(inscripcionListNewInscripcionToAttach);
            }
            inscripcionListNew = attachedInscripcionListNew;
            ingreso.setInscripcionList(inscripcionListNew);
            ingreso = em.merge(ingreso);
            for (Mensualidad mensualidadListNewMensualidad : mensualidadListNew) {
                if (!mensualidadListOld.contains(mensualidadListNewMensualidad)) {
                    Ingreso oldIdingresoOfMensualidadListNewMensualidad = mensualidadListNewMensualidad.getIdingreso();
                    mensualidadListNewMensualidad.setIdingreso(ingreso);
                    mensualidadListNewMensualidad = em.merge(mensualidadListNewMensualidad);
                    if (oldIdingresoOfMensualidadListNewMensualidad != null && !oldIdingresoOfMensualidadListNewMensualidad.equals(ingreso)) {
                        oldIdingresoOfMensualidadListNewMensualidad.getMensualidadList().remove(mensualidadListNewMensualidad);
                        oldIdingresoOfMensualidadListNewMensualidad = em.merge(oldIdingresoOfMensualidadListNewMensualidad);
                    }
                }
            }
            for (Inscripcion inscripcionListNewInscripcion : inscripcionListNew) {
                if (!inscripcionListOld.contains(inscripcionListNewInscripcion)) {
                    Ingreso oldIdingresoOfInscripcionListNewInscripcion = inscripcionListNewInscripcion.getIdingreso();
                    inscripcionListNewInscripcion.setIdingreso(ingreso);
                    inscripcionListNewInscripcion = em.merge(inscripcionListNewInscripcion);
                    if (oldIdingresoOfInscripcionListNewInscripcion != null && !oldIdingresoOfInscripcionListNewInscripcion.equals(ingreso)) {
                        oldIdingresoOfInscripcionListNewInscripcion.getInscripcionList().remove(inscripcionListNewInscripcion);
                        oldIdingresoOfInscripcionListNewInscripcion = em.merge(oldIdingresoOfInscripcionListNewInscripcion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ingreso.getId();
                if (findIngreso(id) == null) {
                    throw new NonexistentEntityException("The ingreso with id " + id + " no longer exists.");
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
            Ingreso ingreso;
            try {
                ingreso = em.getReference(Ingreso.class, id);
                ingreso.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ingreso with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Mensualidad> mensualidadListOrphanCheck = ingreso.getMensualidadList();
            for (Mensualidad mensualidadListOrphanCheckMensualidad : mensualidadListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ingreso (" + ingreso + ") cannot be destroyed since the Mensualidad " + mensualidadListOrphanCheckMensualidad + " in its mensualidadList field has a non-nullable idingreso field.");
            }
            List<Inscripcion> inscripcionListOrphanCheck = ingreso.getInscripcionList();
            for (Inscripcion inscripcionListOrphanCheckInscripcion : inscripcionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ingreso (" + ingreso + ") cannot be destroyed since the Inscripcion " + inscripcionListOrphanCheckInscripcion + " in its inscripcionList field has a non-nullable idingreso field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(ingreso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ingreso> findIngresoEntities() {
        return findIngresoEntities(true, -1, -1);
    }

    public List<Ingreso> findIngresoEntities(int maxResults, int firstResult) {
        return findIngresoEntities(false, maxResults, firstResult);
    }

    private List<Ingreso> findIngresoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ingreso.class));
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

    public Ingreso findIngreso(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ingreso.class, id);
        } finally {
            em.close();
        }
    }

    public int getIngresoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ingreso> rt = cq.from(Ingreso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
