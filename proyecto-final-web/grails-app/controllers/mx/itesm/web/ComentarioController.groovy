package mx.itesm.web

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.converters.*
import grails.rest.*
import grails.plugin.springsecurity.annotation.Secured

@Transactional(readOnly = true)
@Secured(['ROLE_ADMIN'])
class ComentarioController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    /*def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Comentario.list(params), model:[comentarioCount: Comentario.count()]
    }*/
    
    def consultar() {
        def valor = Comentario.list()
        render valor as JSON
    }
    
    def index() {
        respond Comentario.list()
    }

    def show(Comentario comentario) {
        respond comentario
    }

    def create() {
        respond new Comentario(params)
    }

    @Transactional
    def save(Comentario comentario) {
        if (comentario == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (comentario.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond comentario.errors, view:'create'
            return
        }

        comentario.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'comentario.label', default: 'Comentario'), comentario.id])
                redirect comentario
            }
            '*' { respond comentario, [status: CREATED] }
        }
    }

    def edit(Comentario comentario) {
        respond comentario
    }

    @Transactional
    def update(Comentario comentario) {
        if (comentario == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (comentario.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond comentario.errors, view:'edit'
            return
        }

        comentario.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'comentario.label', default: 'Comentario'), comentario.id])
                redirect comentario
            }
            '*'{ respond comentario, [status: OK] }
        }
    }

    @Transactional
    def delete(Comentario comentario) {

        if (comentario == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        comentario.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'comentario.label', default: 'Comentario'), comentario.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'comentario.label', default: 'Comentario'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
