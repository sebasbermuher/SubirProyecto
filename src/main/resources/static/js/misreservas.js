let dia = new Date()
let diaActual = dia.toISOString().substring(0, 10)
let horaActual = dia.toLocaleString().split(',')[1].substring(1, 6)
let fechaDOM = document.getElementsByName("fecha")
let horas = document.getElementsByName("horas")
let estado = document.getElementsByName("estado")
let eliminar = document.getElementsByName("eliminar")

for (var i = 0; i < fechaDOM.length; i++) {
	const fecha = fechaDOM[i].innerHTML
	const horasInicio = horas[i].innerHTML.substring(0, 5)
	const horasFin = horas[i].innerHTML.substring(8, 13)
	const año = fecha.split('-')[0]
	const mes = fecha.split('-')[1]
	const dia = fecha.split('-')[2]
	
	
	console.log(dia)
	console.log(mes)
	console.log(año)
	
	if (fecha < diaActual) {
		estado[i].innerHTML = "JUGADO"
		estado[i].style.backgroundColor = "#FB6C63"
		estado[i].style.textAlign = "center"
		eliminar[i].style.display = "none"
	}

	if (fecha > diaActual) {
		estado[i].innerHTML = "POR JUGAR"
		estado[i].style.backgroundColor = "#85EA8E"
		estado[i].style.textAlign = "center"

	}

	if (fecha === diaActual) {
		if (horaActual >= horasInicio) {
			estado[i].innerHTML = "EN JUEGO"
			estado[i].style.backgroundColor = "#FBF663"
			estado[i].style.textAlign = "center"
			eliminar[i].style.display = "none"
			if (horaActual > horasFin) {
				estado[i].innerHTML = "JUGADO"
				estado[i].style.backgroundColor = "#FB6C63"
				estado[i].style.textAlign = "center"
				eliminar[i].style.display = "none"
			}

		} else{
			estado[i].innerHTML = "POR JUGAR"
			estado[i].style.backgroundColor = "#85EA8E"
			estado[i].style.textAlign = "center"
		}

	}
	
	fechaDOM[i].innerHTML=dia+"-"+mes+"-"+año
	

}



