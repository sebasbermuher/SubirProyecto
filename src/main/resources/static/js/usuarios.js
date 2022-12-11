// cogemos el elemento del documento html
let fechaDOM = document.getElementsByName("fechaNac")

// desde 0 hasta el tamaño del elemento
for (var i = 0; i < fechaDOM.length; i++) {
	// recorremos todos los elementos de fechaDOM
	const fecha = fechaDOM[i].innerHTML
	// separamos la fecha por dia, mes y año
	const año = fecha.split('-')[0]
	const mes = fecha.split('-')[1]
	const dia = fecha.split('-')[2]
// lo volvemos a insertar en el html con formato dd/mm/yyyy
	fechaDOM[i].innerHTML = dia + "/" + mes + "/" + año
}