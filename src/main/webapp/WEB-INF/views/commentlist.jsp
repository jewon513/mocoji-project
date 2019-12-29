<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:choose>
	<c:when test="${commentList == null}">
		<div class="text-center">댓글 목록이 없습니다.</div>
	</c:when>
	<c:otherwise>
		<c:forEach items="${commentList}" var="vo">
			<div class="row bg-light" id="comment-box" data-id="${vo.c_contentid}" data-seq="${vo.c_seq}" data-writer="${vo.c_writer}">

				<div class="col-md-3 text-center">
					${vo.c_writer}<br /> ${vo.c_date}
				</div>
				<div class="col-md-9 text-start">${vo.c_text}</div>

			</div>
		</c:forEach>
	</c:otherwise>
</c:choose>
