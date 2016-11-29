var tau = 2 * Math.PI;
            var tiempoT = 0
            var tiempo = 0
            var seg = 0
            var start = false
            var per = 0
            var terminado = false
            var interval = 0
            var interval1 = 0
            
            var arc = d3.arc()
                .innerRadius(30)
                .outerRadius(52)
                .startAngle(0);

            var svg = d3.select("svg"),
            width = +svg.attr("width"),
            height = +svg.attr("height"),
            g = svg.append("g").attr("transform", "translate(" + width / 2 + "," + height / 2 + ")");

            var background = g.append("path")
                .datum({endAngle: tau})
                .style("fill", "#ddd")
                .attr("d", arc);

            var foreground = g.append("path")
                .datum({endAngle: 1.0 * tau})
                .style("fill", "green")
                .attr("d", arc);
            
            var center_group = svg.append("svg:g")
                .attr("class", "ctrGroup")
                .attr("transform", "translate(" + (width / 2) + "," + (height / 2) + ")");

            var pieLabel = center_group.append("svg:text")
                .attr("dy", ".35em").attr("class", "chartLabel")
                .attr("text-anchor", "middle")
                .attr("font-size", "70px")
                .text("");
            
            $(document).ready(function(){
                $("#empezar").click(function() {
                    if (($("#valor").val().length > 0) && isNumber($("#valor").val()) ) {
                        start = true
                        tiempoT = $("#valor").val()
                        seg = segundos(tiempoT)
                        tiempo = seg
                        startTimer(seg,pieLabel)
                        $("#empezar").prop('disabled', true)
                    }else{
                        alert("Introduzca un valor numerico")
                    }
                });
            
                $("#reset").click(function() {
                    foreground.datum({endAngle: 1.0 * tau})
                    foreground.style("fill", "green");
                    clearInterval(interval);
                    pieLabel.text("");
                    $("#empezar").prop('disabled', false)
                    $('#valor').val('');
                    clearInterval(interval1);
                    start = false
                });
                
                interval1 = d3.interval(function() {
                    if(start){
                        if(tiempo <= 0){
                            per = 0
                            clearInterval(interval);
                            $("#empezar").prop('disabled', false)
                        }else{
                            per = calcularPer(seg,tiempo)
                        }
                        foreground.transition()
                            .duration(750)
                            .attrTween("d", arcTween(per * tau));
                        tiempo = tiempo - 1

                        if(per < 0.5 && per > 0.25){
                            foreground.style("fill", "orange")
                        }
                        if(per < 0.25){
                            foreground.style("fill", "red")
                        }
                    }
                }, 1000);
            });

            function arcTween(newAngle) {
              return function(d) {
                var interpolate = d3.interpolate(d.endAngle, newAngle);
                return function(t) {
                  d.endAngle = interpolate(t);
                  return arc(d);
                };
              };
            }

            function calcularPer(tiempoT, tiempo){
                var res = tiempo / tiempoT
                return res
            }

            function segundos(tiempoT){
                var res = tiempoT * 60
                return res
            }

            function isNumber(n) {
              return !isNaN(parseFloat(n)) && isFinite(n);
            }

            function startTimer(duration, display) {
                var timer = duration, minutes, seconds;
                interval = setInterval(function () {
                    minutes = parseInt(timer / 60, 10);
                    seconds = parseInt(timer % 60, 10);

                    minutes = minutes < 10 ? "0" + minutes : minutes;
                    seconds = seconds < 10 ? "0" + seconds : seconds;

                    display.text(minutes + ":" + seconds)

                    if (--timer < 0) {
                        timer = duration;
                    }
                }, 1000);

            }