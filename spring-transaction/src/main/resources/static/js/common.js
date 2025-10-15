function ajaxPost(url, dto, func, pAsync) {
  $.ajax({
    url: url,
    type: "POST",
    data: JSON.stringify(dto),
    contentType: "application/json; charset=UTF-8",
    async: true, // 기본값 비동기
    success: function(data) {
      func(data); // ✅ 호출부에서 넘긴 콜백 실행
    },
    error: function(xhr) {
      errMsg = xhr.responseJSON.message;
      alert(errMsg);
    }
  });
}