var main = {
    init : function () {
	var _this = this;

	$('#addCalendar').on('click', function () {
	    _this.save();
	});
	
	
	
	},
    save : function () {
        var data = {
            content : $("#calendar_content").val(),
            start_date: $("#calendar_start_date").val(),
            end_date: $("#calendar_end_date").val()
        };
        
        
        //내용 입력 여부 확인
        if(content == null || content == ""){
            alert("내용을 입력하세요.");
        }else if(start_date == "" || end_date ==""){
            alert("날짜를 입력하세요.");
        }else if(new Date(end_date)- new Date(start_date) < 0){ // date 타입으로 변경 후 확인
            alert("종료일이 시작일보다 먼저입니다.");
		}else{
			var obj = {
                        "title" : content,
                        "start" : start_date,
                        "end" : end_date
						}
		}
		
        $.ajax({
            type: 'POST',
            url: '/api/calendars',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
        	beforeSend : function(xhr){
					xhr.setRequestHeader(header, token);
			},
            data: JSON.stringify(data)
        }).done(function() {
            alert('일정이 등록되었습니다.');
            location.reload();
        }).fail(function (error) {
            //alert("request: "+ request + " status: "+ status + " error: " + error);
            alert("일정이 정상적으로 등록되지 않았습니다.");
        });
    }

};

main.init();