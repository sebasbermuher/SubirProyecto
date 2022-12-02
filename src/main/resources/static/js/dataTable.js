$(document).ready(function() {
	var table = $('#mydatatable').DataTable({
		"dom": 'B<"float-left"i><"float-right"f>t<"float-left"l><"float-right"p><"clearfix">',
		"responsive": false,
		"language": {
			"url": "https://cdn.datatables.net/plug-ins/1.10.19/i18n/Spanish.json"
		},
		"order": [
			[0, "asc"]
		],
		"buttons": [
			{
				"extend": 'pdf',
				"text": 'Exportar a PDF',
				"title": 'PDF-ReservaLaPista ',
			},
			{
				"extend": 'excel',
				"text": 'Exportar a Excel',
				"title": 'Excel-ReservaLaPista',
			},
			{
				"extend": 'copy',
				"text": ' Copiar',
			}

		],
	});
});