<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">
      <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>いいねした人　一覧</h2>
        <div id="report.title">
       ( 日報のタイトル：${report.title} )
       </div>
       <table id="iine_list_list">
        <tbody>
            <tr>
                <th class="iine_list_name">いいねした人</th>
                <th class="iine_list_tame">いいねした日時</th>
            </tr>
            <c:forEach var="iine_list" items="${iine_lists}" varStatus="status">
                <tr class="row${status.count % 2}">
                    <td class="iine_list_name"><c:out value="${iine_list.employee.name}" /></td>
                    <td class="iine_list_time"><fmt:formatDate value='${iine_list.created_at}' pattern='yyyy-MM-dd HH:mm:ss' /> </td>
            </tr>
            </c:forEach>
        </tbody>
       </table>
        <div id="pagination">
            （全 ${iine_lists_count} 件）<br />
            <c:forEach var="i" begin="1" end="${((iine_lists_count - 1) / 15) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value="/iine_lists/index?id=${report.id}&page=${i}" />"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <p><a href="<c:url value='/reports/index' />">一覧に戻る</a></p>
    </c:param>
</c:import>