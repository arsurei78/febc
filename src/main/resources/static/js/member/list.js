/**
 * 페이지 처리
 */
function page(page) {
    $("#listForm").empty();
    const param = $("<input type='hidden' value=" + page + " name='page' readonly>");
    const selectCondHtml = $("<input type='hidden' value=" + searchAdmin + " name='selectAdmin' id='selectAdmin' readonly>");
    const selectDataHtml = $("<input type='hidden' value=" + searchData + " name='searchData' id='searchData' readonly>");

    $("#listForm").append(selectCondHtml);
    $("#listForm").append(selectDataHtml);
    $("#listForm").append(param)
    $("#listForm").submit();
}

/**
 * 검색 조건 (관리자 / 전체 검색)
 */
function changeAdmin(id) {
    $('#selectAdmin').remove();
    const selectCond = $("<input type='hidden' value=" + id + " name='selectAdmin' id='selectAdmin' readonly>");
    $("#listForm").append(selectCond);
}
