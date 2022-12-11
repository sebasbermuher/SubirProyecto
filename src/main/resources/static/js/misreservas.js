// creamos un objeto dia con la fecha actual
let dia = new Date()
// extraemos solo el dia de la fecha (dia)
let diaActual = dia.toISOString().substring(0, 10)
// extraemos la hora y los minutos de la fecha actual (ej-> 12:15)
let horaActual = dia.toLocaleString().split(',')[1].substring(1, 6)

// recogemos los siguientes elementos del documento html
let fechaDOM = document.getElementsByName("fecha")
let horas = document.getElementsByName("horas")
let estado = document.getElementsByName("estado")
let eliminar = document.getElementsByName("eliminar")

// de 0 hasta el tamaño de fechaDOM (recorre todo 'fechaDOM')
for (var i = 0; i < fechaDOM.length; i++) {
	// nos devueve todos los valores del elemento, en este caso las fechas (con name="fecha"))
	const fecha = fechaDOM[i].innerHTML
	// nos devuelve la parte de las horas de las horas de inicio
	const horasInicio = horas[i].innerHTML.substring(0, 5)
	// nos devuelve la parte de las horas de las horas fin
	const horasFin = horas[i].innerHTML.substring(8, 13)
	// separamos la fecha
	const año = fecha.split('-')[0]
	const mes = fecha.split('-')[1]
	const dia = fecha.split('-')[2]

	// si el elemento fecha recogido del elemento es menor al dia actual (por lo tanto el partido ya se ha jugado)
	if (fecha < diaActual) {
		estado[i].innerHTML = "JUGADO"
		estado[i].style.backgroundColor = "#FB6C63"
		estado[i].style.textAlign = "center"
		eliminar[i].style.display = "none"
	}
	// si el elemento fecha recogido del elemento es mayor al dia actual (aun no se ha jugado el partido)
	if (fecha > diaActual) {
		estado[i].innerHTML = "POR JUGAR"
		estado[i].style.backgroundColor = "#85EA8E"
		estado[i].style.textAlign = "center"

	}
	// si el elemento fecha recogido del elemento es igual al dia actual
	if (fecha === diaActual) {
		// si la hora actual es mayor o igual, el partido esta en juego
		if (horaActual >= horasInicio) {
			estado[i].innerHTML = "EN JUEGO"
			estado[i].style.backgroundColor = "#FBF663"
			estado[i].style.textAlign = "center"
			eliminar[i].style.display = "none"
			// si la hora actual es mayor a la hora fin, el partido ya se ha jugado
			if (horaActual > horasFin) {
				estado[i].innerHTML = "JUGADO"
				estado[i].style.backgroundColor = "#FB6C63"
				estado[i].style.textAlign = "center"
				eliminar[i].style.display = "none"
			}

		} else {
			// si la hora actual es menor a la ahora inicio, el partido aun no se ha jugado
			estado[i].innerHTML = "POR JUGAR"
			estado[i].style.backgroundColor = "#85EA8E"
			estado[i].style.textAlign = "center"
		}

	}
	// formateo de fecha (dd/mm/yyyy)
	fechaDOM[i].innerHTML = dia + "/" + mes + "/" + año

}



