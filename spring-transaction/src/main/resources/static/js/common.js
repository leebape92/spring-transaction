function ajaxPost(url, dto, callback, pAsync) {
  $.ajax({
    url: url,
    type: "POST",
    data: JSON.stringify(dto),
    contentType: "application/json; charset=UTF-8",
    async: true, // 기본값 비동기 생략가능
    success: function(data) {
      callback(data); // ✅ 호출부에서 넘긴 콜백 실행
    },
    error: function(xhr) {
      errMsg = xhr.responseJSON.message;
      alert(errMsg);
    }
  });
}

function ajaxGet(url, params, callback) {
	debugger;
	$.ajax({
	    url: url,
	    type: "GET",
	    data: params,  // jQuery가 자동으로 쿼리스트링으로 변환해서 요청
	    success: function(data) {
	      callback(data);
    },
    error: function(xhr) {
		alert(xhr.responseJSON.message);
    }
  });
}