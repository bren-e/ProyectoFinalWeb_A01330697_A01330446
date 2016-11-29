package mx.itesm.web
import grails.rest.*

class Comentario {
    String nombre
    String fecha
    String texto
    
    static constraints = {
        nombre nullable:false, blank:false
        fecha nullable:false, blank:false
        texto nullable:false, blank:false
    }
}
