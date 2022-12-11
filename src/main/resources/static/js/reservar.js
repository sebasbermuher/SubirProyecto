//--- BLOQUEAR FECHAS VENCINAS CALENDARIO DE FECHAS ---
// Obtener fecha actual
let fecha = new Date();
// Obtener cadena en formato yyyy-mm-dd, eliminando zona y hora
let fechaMin = fecha.toISOString().split('T')[0];

// Agregar 3 días
fecha.setDate(fecha.getDate() + 3);
// Obtener cadena en formato yyyy-mm-dd, eliminando zona y hora
let fechaMax = fecha.toISOString().split('T')[0];

// Asignar valor mínimo
document.querySelector('#fecha').min = fechaMin;
// Asignar valor máximo
document.querySelector('#fecha').max = fechaMax;


//----------------------------
//Recoge la hora del input que esta seleccionado
$(document).on('change', 'input[type="radio"]', function(e) {
	if (this.id == "hora_inicio") {
		console.log((this.value))
	}
});


//------------------------------------
function getHora_inicio() {
	var x = 0;
	var y = document.getElementsByName("hora_inicio");
	// desde 0 hasa el tamaño del elemento y (hora_inicio)
	for (var i = 0; i < y.length; i++) {
		// si hora_inicio está checked (clickado)
		if (y[i].checked) {
			//x igual al valor que esta checked
			x = y[i].value;
		}
	}
	console.log(x);
}

//---------------------

// creamos un objeto dia con la fecha actual
let dia = new Date();
// separamos el elemento dia y nos quedamos solo con las 2 cifras de la hora
let horaActual = dia.toString().split(' ')[4].split(':')[0]

// cogemos estos dos elementos del html
let grupo = document.getElementsByName("hora_inicio");
let labelBoton = document.getElementsByName("labelBoton");

function getFechaInput() {
	// cogemos el elemento del html
	var fechaElement = document.getElementsByName("fecha");
	// Obtenemos la fecha actual
	let dia = new Date()
	// extraemos cadena de dia, para quedarnos solo con la fecha (yyyy-mm-dd)
	let diaActual = dia.toISOString().substring(0, 10)

	//desde 0 hasta el tamaño del elemento de fechaElement
	for (var i = 0; i < fechaElement.length; i++) {
		// va recorriendo cada valor de fecha y saca valor a valor
		const fecha = fechaElement[i].value
		//si el dia actual es igual a la fecha
		if (diaActual === fecha) {
			//CAMBIAR COLOR A LOS LABEL QUE YA HA PASADO LA HORA
			for (var i = 0; i < labelBoton.length; i++) {
				// extraemos las 2 primeras cifras del elemento, por lo tanto nos muestras las 2 cifras de la hora del elemento html
				const acortado = labelBoton[i].innerHTML.substring(0, 2)
				// si acortado es menor o igual a hora actual (reserva no disponible)
				if (acortado <= horaActual) {
					labelBoton[i].style.backgroundColor = "#FD6161";
					labelBoton[i].style.color = "white";
				}
			}
			//CAMBIAR DISABLED A LOS INPUT QUE YA HA PASADO LA HORA
			grupo.forEach((element) => {
				// extraemos las 2 primeras letras/cifras del elemento
				const values = element.defaultValue.substring(0, 2)

				// si values es menos o igual a la horaActual(reserva no disponible), por lo tanto se pone el elemento disabled
				if (values <= horaActual) {
					element.disabled = true;
				}
			})
			// si la fecha actual es difente a la fecha del elemento del html
		} else if ((diaActual != fecha)) {
			for (var i = 0; i < labelBoton.length; i++) {
				// extraemos las 2 primeras letras/cifras del elemento
				const acortado = labelBoton[i].innerHTML.substring(0, 2)
				// si el elemento de html, es menor o igual a la hora actual (reserva disponible)
				if (acortado <= horaActual) {
					labelBoton[i].style.backgroundColor = "";
					labelBoton[i].style.color = "";
				}
			}
			//	QUITAR DISABLE A LOS INPUTS
			grupo.forEach((element) => {
				const values = element.defaultValue.substring(0, 2)

				if (values <= horaActual) {
					//console.log(values)
					element.disabled = false;
				}
			})
		}

	}
}