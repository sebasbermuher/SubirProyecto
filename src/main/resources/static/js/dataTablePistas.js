$(document).ready(function() {
	$('#example').DataTable({
		dom: 'Bfrtip',
		buttons: [
			{
				extend: 'pdfHtml5',
				text: 'PDF',
				className: 'btn-danger',
				title: 'Pistas -  ReservaLaPista',
				exportOptions: {
					columns: [0, 1, 2, 3, 4]
				},

				customize: function(doc) {
					doc.content.splice(1, 0, {
						margin: [0, 0, 0, 12],
						alignment: 'center',
						image: 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADIAAAAyCAYAAAAeP4ixAAAAAXNSR0IArs4c6QAAAutJREFUaEPdmUvrT2EQxz9/dymKpSwo2SgWUkopZGHjDSh2ViSsvQFhwcqOvAAbCylsFElsbISVrWtyvzXlp9NpnmdmnstxOcvfmZnn+5mZ53Z+c/y558do6DPA8VI5c6WOFX4Lgc8Z/yJNRU4VEOI6roQWLqwr7DABhAwR1hV2mAhkG3AnMtbfCnIYOP8/gIQTHHaIZEmxXQR8csQI6wo7OERYJtaqVaSpyMlS6nj/AVii2BXrKXZ0iJ3UpDfIaeBYhugVsLIFcU8Qay402dFnQXqAnABOVWY5rCvsYAgsqUIupFuf29AA2AHcqqxCFVALkNZVSAFltdaC5CAkdhTS8knqrQFJibwNbC+AGB7fU5cvGXNey+UuBTFMTLQaF4BDI5FaDDX50YosBj4mmngY6z2wNDj5U1pcMBGQ1cBzB4SYRKuRuxXKzv9iNO59YMvwtwiIJu4LIEfz4bMbuB6sxhHgXMbHrEoNyGNggzJ4y2oMw2dhSkEOAJcSGewFsg+4Mhrzt/4IiMRYAHwNtoDVZTuBm5bRr/fjJBWDWOP1qobWYquAl7MX0YrkQN4ByyxS5X0TDU2CJMruYZoPfPcYWjatQFYAr63BelUjtwlFNeXmxn7gciJgq0TGv7EqgtYCzzJCU5DrgSfRjKXsW2QkJVT+OpCzmeeAWc1TC7IJeGi0zT8BYolcBzztOclb7SMaiHzLki8p8lig1S3VCmQ58Gakxrpcyb4h+0fTp3aOzMTIqnVSWWbN43crmlYgmh45XGqZ7zJml6CZ+SE3zDWtqjCMkwN5AGxWBpW9Iff38sxlsrbKHVGsjwdytnqbyexkq5W1annuFblqav4bgUc92ipVEQ+E+Mpt8ZsibCtwd4pN0JojXpBoInouLOrp1wtyD5Dsjx/NXz5zeuMWdZ+WpWvAHkc0zfcqsHfqtsqtWp7saSCTr1bWqiXvczCpfj8KnB1VpOvc8IBoMDeAXUbbDRNwETjoaNNqk5/LQ3MzhpnT2AAAAABJRU5ErkJggg=='
					});
				}

			}, {
				extend: 'excel',
				text: 'Excel',
				className: 'btn-danger',
				title: 'Pistas - ReservaLaPista',
				exportOptions: {
					columns: [0, 1, 2, 3, 4]
				},


			}, , 'copy'
		],
	});
});

$.extend(true, $.fn.dataTable.defaults, {
	"language": {
		"decimal": ",",
		"thousands": ".",
		"info": "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
		"infoEmpty": "Mostrando registros del 0 al 0 de un total de 0 registros",
		"infoPostFix": "",
		"infoFiltered": "(filtrado de un total de _MAX_ registros)",
		"loadingRecords": "Cargando...",
		"lengthMenu": "Mostrar _MENU_ registros",
		"paginate": {
			"first": "Primero",
			"last": "Último",
			"next": "Siguiente",
			"previous": "Anterior"
		},
		"processing": "Procesando...",
		"search": "Buscar:",
		"searchPlaceholder": "Buscar...",
		"zeroRecords": "No se encontraron resultados",
		"emptyTable": "Ningún dato disponible en esta tabla",
		"aria": {
			"sortAscending": ": Activar para ordenar la columna de manera ascendente",
			"sortDescending": ": Activar para ordenar la columna de manera descendente"
		},
		//only works for built-in buttons, not for custom buttons
		"buttons": {
			"create": "Nuevo",
			"edit": "Cambiar",
			"remove": "Borrar",
			"copy": "Copiar",
			"csv": "fichero CSV",
			"excel": "Excel",
			"pdf": "PDF",
			"print": "Imprimir",
			"colvis": "Visibilidad columnas",
			"collection": "Colección",
			"upload": "Seleccione fichero...."
		},
		"select": {
			"rows": {
				_: '%d filas seleccionadas',
				0: 'clic fila para seleccionar',
				1: 'una fila seleccionada'
			}
		}
	}
});