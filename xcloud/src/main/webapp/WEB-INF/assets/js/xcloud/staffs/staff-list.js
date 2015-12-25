$("#list-table").dataTable({
    "processing": true,
    "serverSide": true,
    "ordering":  false,
    "bPaginate": true, // Pagination True 
    "aLengthMenu" : false,
    "pageLength": 10,
    "searching": false,
    "scrollY": "300px",
    "language": {
        "lengthMenu": "",
        "paginate": {
            "previous": "前一页",
            "next": "下一页"
          },
        "zeroRecords": "",
        "sEmptyTable": "",
        "info": "显示_START_到_END_, 共有_TOTAL_条数据"
      },
    "ajax": {
        "url": "/xcloud/staff/get-by-dept.json",
        "type": "GET",
        "data": function ( d ) {
        	var info = $('#list-table').DataTable().page.info();
        	d.page = info.page + 1;
        	var dept_id = $("#dept_id").val();
            d.dept_id = dept_id;
        }
    },
    
    
    "columns": [
		{ "data": "id",
		    "render": function ( data, type, full, meta ) {
		  	    return '<input type="checkbox" id="staff_id" name="staff_id" value="' + data + '"\>';
		    }
		},
        { "data": "job_number" },
        { "data": "name" },
        { "data": "mobile" },
        { "data": "job_name" },
        { "data": "dept_name" },
        { "data": "staff_type_name" },
        { "data": "id",
          "render": function ( data, type, full, meta ) {

        	      return '<a href="/xcloud/staff/'+data+'"><i class="am-icon-edit am-icon-md"></i></a>';
          }
        },

    ]
} );


function TreeNodeClick() {
	$("#list-table").DataTable().ajax.reload();
}

//Handle click on "Select all" control

//表格全选
$("#select_all").click(function() {
	
	var table = $("#list-table").DataTable();
	 // Get all rows with search applied
    var rows = table.rows({ 'search': 'applied' }).nodes();
    // Check/uncheck checkboxes for all rows in the table
    $('input[type="checkbox"]', rows).prop('checked', this.checked);
});

//表格全选
$("#btn-change-dept").click(function() {
	console.log("btn-change-dept click");
	var table = $("#list-table").DataTable();
	
	var selectStaffNames = "";
	var selectStaffIds = "";
	table.$('input[type="checkbox"]').each(function(){
        // If checkbox doesn't exist in DOM
		   console.log($(this).val());
           if(this.checked){
        	   
        	   var $row = this.closest('tr');
        	   var data = table.row($row).data();
        	   console.log(data.name);
        	   console.log(data.id);
        	   console.log("this.name = " + this.name);
        	   selectStaffNames += data.name + ",";
        	   selectStaffIds += data.id + ","
           }
    });
	console.log("staffNames = " + selectStaffNames);
	console.log("selectStaffIds = " + selectStaffIds);
	if (selectStaffIds == "") {
		alert("请选择需要分配部门的员工.");
		return false;
	}
	$("")
	
	
	
});
