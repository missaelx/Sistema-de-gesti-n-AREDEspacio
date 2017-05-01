/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author macbookpro
 */
@Entity
@Table(name = "promociones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Promociones.findAll", query = "SELECT p FROM Promociones p")
        ,@NamedQuery(name = "promociones.findByActivo", query = "SELECT p FROM Promociones p WHERE p.activo = 1 ")
    , @NamedQuery(name = "Promociones.findById", query = "SELECT p FROM Promociones p WHERE p.id = :id")
    , @NamedQuery(name = "Promociones.findByTitulo", query = "SELECT p FROM Promociones p WHERE p.titulo = :titulo")
    , @NamedQuery(name = "Promociones.findByDescripcion", query = "SELECT p FROM Promociones p WHERE p.descripcion = :descripcion")
    , @NamedQuery(name = "Promociones.findByPorcentajeDescuento", query = "SELECT p FROM Promociones p WHERE p.porcentajeDescuento = :porcentajeDescuento")
    , @NamedQuery(name = "Promociones.findByAplicaPara", query = "SELECT p FROM Promociones p WHERE p.aplicaPara = :aplicaPara")})
public class Promociones implements Serializable {

    @Basic(optional = false)
    @Column(name = "activo")
    private boolean activo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPromocion")
    private List<Ingreso> ingresoList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "titulo")
    private String titulo;
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "porcentaje_descuento")
    private int porcentajeDescuento;
    @Basic(optional = false)
    @Column(name = "aplica_para")
    private String aplicaPara;

    public Promociones() {
    }

    public Promociones(Integer id) {
        this.id = id;
    }

    public Promociones(Integer id, String titulo, int porcentajeDescuento, String aplicaPara) {
        this.id = id;
        this.titulo = titulo;
        this.porcentajeDescuento = porcentajeDescuento;
        this.aplicaPara = aplicaPara;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(int porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }

    public String getAplicaPara() {
        return aplicaPara;
    }

    public void setAplicaPara(String aplicaPara) {
        this.aplicaPara = aplicaPara;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Promociones)) {
            return false;
        }
        Promociones other = (Promociones) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.titulo;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @XmlTransient
    public List<Ingreso> getIngresoList() {
        return ingresoList;
    }

    public void setIngresoList(List<Ingreso> ingresoList) {
        this.ingresoList = ingresoList;
    }
    
}
