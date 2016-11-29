package proyectoservidor
import mx.itesm.web.*

class BootStrap {
 
    def init = { servl0etContext ->
        Role admin = new Role("ROLE_ADMIN").save()
        User user = new User("user", "pass").save()
        UserRole.create(user, admin, true)
        
        Parquimetro p = new Parquimetro(nombre:'Lomas', latitud:'19.424587', longitud:'-99.210502');
        p.save();
        Parquimetro p1 = new Parquimetro(nombre:'Polanco', latitud:'19.43406', longitud:'-99.196599');
        p1.save();
        Parquimetro p2 = new Parquimetro(nombre:'Anzures', latitud:'19.432658', longitud:'-99.177446');
        p2.save();
        Parquimetro p3 = new Parquimetro(nombre:'Condesa', latitud:'19.412901', longitud:'-99.169967');
        p3.save();
        Parquimetro p4 = new Parquimetro(nombre:'Roma', latitud:'19.419869', longitud:'-99.164064');
        p4.save();
        Parquimetro p5 = new Parquimetro(nombre:'Florida', latitud:'19.35855', longitud:'-99.181579');
        p5.save();
        Parquimetro p6 = new Parquimetro(nombre:'Crédito C.', latitud:'19.364596', longitud:'-99.179769');
        p6.save();
        Parquimetro p7 = new Parquimetro(nombre:'San Jose In.', latitud:'19.366114', longitud:'-99.184753');
        p7.save();
        Parquimetro p8 = new Parquimetro(nombre:'Ins. Mixcoac', latitud:'19.373118', longitud:'-99.182278');
        p8.save();
        Parquimetro p9 = new Parquimetro(nombre:'Extremadura Ins.', latitud:'19.376982', longitud:'-99.180727');
        p9.save();
        Parquimetro p10 = new Parquimetro(nombre:'Noche Buena', latitud:'19.380452', longitud:'-99.179442');
        p10.save();
        Parquimetro p11 = new Parquimetro(nombre:'Cd. de Los Dep.', latitud:'19.38413', longitud:'-99.179504');
        p11.save();
        Parquimetro p12 = new Parquimetro(nombre:'Nápoles', latitud:'19.393395', longitud:'-99.175825');
        p12.save();
        Parquimetro p13 = new Parquimetro(nombre:'Ampl. Nápoles', latitud:'19.387559', longitud:'-99.178031');
        p13.save();
        
        Comentario c = new Comentario(nombre:'Andrés', fecha:'2 de Julio de 2016', texto:'hola');
        c.save();
    }
    def destroy = {
    }
}
