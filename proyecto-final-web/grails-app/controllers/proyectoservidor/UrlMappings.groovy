package proyectoservidor

class UrlMappings {

    static mappings = {
        "/comentario"(controller:"Comentario", action:"consultar")
        "/parquimetro"(controller:"Parquimetro", action:"consultar")
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(view:"/index")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
