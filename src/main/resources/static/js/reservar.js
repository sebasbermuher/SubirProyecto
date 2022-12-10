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

	for (var i = 0; i < y.length; i++) {
		if (y[i].checked) {
			x = y[i].value;
		}
	}
	console.log(x);
}

//---------------------


let dia = new Date();
let horaActual = dia.toString().split(' ')[4].split(':')[0]


let grupo = document.getElementsByName("hora_inicio");
let labelBoton = document.getElementsByName("labelBoton");

function getFechaInput() {
	var fechaElement = document.getElementsByName("fecha");
	let dia = new Date()
	let diaActual = dia.toISOString().substring(0, 10)
	console.log(diaActual)

	for (var i = 0; i < fechaElement.length; i++) {
		const fecha = fechaElement[i].value
		console.log("ESTA" + fecha)
		if (diaActual === fecha) {
			//CAMBIAR COLOR A LOS LABEL QUE YA HA PASADO LA HORA
			for (var i = 0; i < labelBoton.length; i++) {
				const acortado = labelBoton[i].innerHTML.substring(0, 2)
				//console.log(acortado)
				if (acortado <= horaActual) {
					labelBoton[i].style.backgroundColor = "#FD6161";
					labelBoton[i].style.color = "white";
				}
			}
			//CAMBIAR DISABLED A LOS INPUT QUE YA HA PASADO LA HORA
			grupo.forEach((element) => {
				const values = element.defaultValue.substring(0, 2)

				if (values <= horaActual) {
					//console.log(values)
					element.disabled = true;
				}
			})

		} else if ((diaActual != fecha)) {
			for (var i = 0; i < labelBoton.length; i++) {
				const acortado = labelBoton[i].innerHTML.substring(0, 2)
				//console.log(acortado)
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