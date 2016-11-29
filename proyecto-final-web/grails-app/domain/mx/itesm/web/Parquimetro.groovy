package mx.itesm.web
import grails.rest.*

class Parquimetro {
    String nombre
    String latitud
    String longitud

    static constraints = {
        latitud nullable:false , blank:false
        longitud nullable:false, blank:false
    }
    
    String toString(){
        latitud + ", " + longitud
    }
}
