var main = {
    init : function () {
	var _this = this;

	$('#btn-save').on('click', function () {
	    _this.save();
	});
	
	$('#btn-update').on('click', function () {
		_this.update();
	});
	
	$('#btn-delete').on('click', function () {
		 _this.delete();
	});
	
	
	
	},
    save : function () {
        var data = {
            empNo: $('#empNo').val(),
            name: $('#name').val()
        };
        
        
        if(data.empNo == null || data.empNo ==""){
			alert("사번을 입력해주세요");
			empNo.focus();
			return false;
		}
		
		if(data.name == null || data.name ==""){
			alert("사원명을 입력해주세요");
			empNo.focus();
			return false;
		}
		
        $.ajax({
            type: 'POST',
            url: '/api/employees',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
        	beforeSend : function(xhr){
					xhr.setRequestHeader(header, token);
			},
            data: JSON.stringify(data)
        }).done(function() {
            alert('사원이 등록되었습니다.');
            location.reload();
        }).fail(function (error) {
            //alert("request: "+ request + " status: "+ status + " error: " + error);
            alert("사원이 정상적으로 등록되지 않았습니다.");
        });
    },
    
	update : function () {
		var data = {
			empNo: $('#empNo').val(),
			name: $('#name').val()
	};

	var empNo = $('#empNo').val();

	$.ajax({
		type: 'PUT',
		url: '/api/employees/'+ empNo,
		dataType: 'json',
		contentType:'application/json; charset=utf-8',
		data: JSON.stringify(data)
	}).done(function() {
		alert('수정되었습니다.');
		window.location.href = '/employees/view?empNo=' + empNo;
	}).fail(function (error) {
		alert(JSON.stringify(error));
		});
	},
    
	delete : function () {
		var empNo = $('#empNo').val();
	
	$.ajax({
		type: 'DELETE',
		url: '/api/employees/'+ empNo,
		contentType:'application/json; charset=utf-8'
	}).done(function() {
		alert('삭제되었습니다.');
		window.location.href = '/employees';
	}).fail(function (error) {
		alert(JSON.stringify(error));
	});
}	
    

};

main.init();