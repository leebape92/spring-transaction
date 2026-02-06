

// key값 data가 body 서버에서  @RequstBodt로 받음
// Content-Type: application/json 기준으로 {"name":"lee", "age","33"}
function ajaxPost(url, dto, callback, pAsync) {
	$.ajax({
		url: url,
		type: "POST",
		data: JSON.stringify(dto),
		contentType: "application/json; charset=UTF-8",
		async: true, // 기본값 비동기 생략가능
    success: function(data) {
		debugger;
		callback(data); // ✅ 호출부에서 넘긴 콜백 실행
	},
	error: function(xhr) {
		debugger;
		errMsg = xhr.responseJSON.message;
		alert(errMsg);
	}
	});
}

// body 존재하지않음 data는 제이쿼리가 쿼리스트링으로 변환해서 서버에서 @ModelAttribute 받음
// findMessageList?name=lee&age=33
// 어떤기준으로 변할까?
// type : "GET" 기준으로 쿼리스트링으로 URL에 붙임
function ajaxGet(url, params, callback) {
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

// asycn 사용해야하는 이유
// 내부 시스템 데이터, 기간계 데이터 등등 순서데로 작동해야하는 경우가 필요함
async function ajaxRequest(url, { method, data = null } = {}) {
	
	if (!method) throw new Error("HTTP method가 지정되지 않았습니다.");
	
	const httpMethod = method.toUpperCase();
	let callUrl = url;
	const options = { method: httpMethod, headers: {} };
	
	if (httpMethod === "GET" && data) {
	    const queryString = new URLSearchParams(data).toString();
	    callUrl += callUrl.includes("?") ? "&" + queryString : "?" + queryString;
	} else if (data) {
	    options.headers["Content-Type"] = "application/json; charset=UTF-8";
	    options.body = JSON.stringify(data);
	}
	
	try {
	    const response = await fetch(callUrl, options);

	    if (!response.ok) {
	        const errData = await response.json().catch(() => ({}));
	        const errMsg = errData.message || `HTTP ${response.status}`;
	        throw new Error(errMsg);
	    }

	    return await response.json();
	} catch (err) {
	    alert(err.message);
	    throw err;
	}
}
