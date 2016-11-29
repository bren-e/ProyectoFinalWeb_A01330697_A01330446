document.addEventListener("DOMContentLoaded", function () {
    var example = Popcorn.vimeo(
        '#video', 'http://player.vimeo.com/191430068');
    
    // add a footnote at 2 seconds, and remove it at 6 seconds
    example.footnote({
        start: 10,
        end: 16,
        text: "Sólo los DÍGITOS de tu placa",
        target: "footnotediv"
    });
    
    example.footnote({
        start: 30,
        end: 35,
        text: "NO DA CAMBIO",
        target: "footnotediv"
    });
    
    example.footnote({
        start: 39,
        end: 38,
        text: "El boleto en el tablero de tu auto para evitar multas!",
        target: "footnotediv"
    });
            
}, false);