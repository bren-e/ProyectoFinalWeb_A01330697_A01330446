package mx.itesm.web

import static org.springframework.http.HttpStatus.*
import grails.converters.*
import grails.rest.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Transactional(readOnly = true)
@Secured(['ROLE_ADMIN'])
class ParquimetroController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    /*def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Parquimetro.list(params), model:[parquimetroCount: Parquimetro.count()]
    }*/
    
    def consultar() {
        def valor = Parquimetro.list()
        render valor as JSON
    }
    
    def index() {
        respond Parquimetro.list()
    }

    def show(Parquimetro parquimetro) {
        respond parquimetro
    }

    def create() {
        respond new Parquimetro(params)
    }

    @Transactional
    def save(Parquimetro parquimetro) {
        if (parquimetro == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (parquimetro.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond parquimetro.errors, view:'create'
            return
        }

        parquimetro.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'parquimetro.label', default: 'Parquimetro'), parquimetro.id])
                redirect parquimetro
            }
            '*' { respond parquimetro, [status: CREATED] }
        }
    }

    def edit(Parquimetro parquimetro) {
        respond parquimetro
    }

    @Transactional
    def update(Parquimetro parquimetro) {
        if (parquimetro == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (parquimetro.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond parquimetro.errors, view:'edit'
            return
        }

        parquimetro.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'parquimetro.label', default: 'Parquimetro'), parquimetro.id])
                redirect parquimetro
            }
            '*'{ respond parquimetro, [status: OK] }
        }
    }

    @Transactional
    def delete(Parquimetro parquimetro) {

        if (parquimetro == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        parquimetro.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'parquimetro.label', default: 'Parquimetro'), parquimetro.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'parquimetro.label', default: 'Parquimetro'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
