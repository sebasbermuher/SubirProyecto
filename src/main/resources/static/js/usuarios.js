let fechaDOM = document.getElementsByName("fechaNac")

for (var i = 0; i < fechaDOM.length; i++) {
	const fecha = fechaDOM[i].innerHTML
	const año = fecha.split('-')[0]
	const mes = fecha.split('-')[1]
	const dia = fecha.split('-')[2]

	fechaDOM[i].innerHTML = dia + "/" + mes + "/" + año
}



