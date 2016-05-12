function loadTable(idTable,json_data_url){
    var table=$(idTable).DataTable();
    table.destroy();
    table=$(idTable).DataTable({
       processing:true
    }).processing(true);
    $.getJSON(json_data_url,function(json){
        table.destroy();//reload data
        table=$(idTable).DataTable({
            data:json.data,
            paging:true,
            responsive:true,
            retrieve: true,
            dom: 'Bfrtip',
            buttons: [
            
        ]
            });
    });
}

