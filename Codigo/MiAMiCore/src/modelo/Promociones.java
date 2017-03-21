/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author macbookpro
 */
@Entity
@Table(name = "promociones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Promociones.findAll", query = "SELECT p FROM Promociones p")
    , @NamedQuery(name = "Promociones.findByIdpromocion", query = "SELECT p FROM Promociones p WHERE p.idpromocion = :idpromocion")
    , @NamedQuery(name = "Promociones.findByTitulo", query = "SELECT p FROM Promociones p WHERE p.titulo = :titulo")
    , @NamedQuery(name = "Promociones.findByDescripcion", query = "SELECT p FROM Promociones p WHERE p.descripcion = :descripcion")
    , @NamedQuery(name = "Promociones.findByPorcentajeDescuento", query = "SELECT p FROM Promociones p WHERE p.porcentajeDescuento = :porcentajeDescuento")
    , @NamedQuery(name = "Promociones.findByAplicaPara", query = "SELECT p FROM Promociones p WHERE p.aplicaPara = :aplicaPara")})
public class Promociones implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpromocion")
    private Integer idpromocion;
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

    public Promociones(Integer idpromocion) {
        this.idpromocion = idpromocion;
    }

    public Promociones(Integer idpromocion, String titulo, int porcentajeDescuento, String aplicaPara) {
        this.idpromocion = idpromocion;
        this.titulo = titulo;
        this.porcentajeDescuento = porcentajeDescuento;
        this.aplicaPara = aplicaPara;
    }

    public Integer getIdpromocion() {
        return idpromocion;
    }

    public void setIdpromocion(Integer idpromocion) {
        this.idpromocion = idpromocion;
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
        hash += (idpromocion != null ? idpromocion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Promociones)) {
            return false;
        }
        Promociones other = (Promociones) object;
        if ((this.idpromocion == null && other.idpromocion != null) || (this.idpromocion != null && !this.idpromocion.equals(other.idpromocion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "recursos.Promociones[ idpromocion=" + idpromocion + " ]";
    }
    
}