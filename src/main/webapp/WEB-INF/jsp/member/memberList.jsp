<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
	<head>
		<title>불량 회원 관리</title>
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<meta http-equiv="cache-control" content="max-age=0" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="expires" content="Tue, 01 Jan 1980 1:00:00 GMT" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta name="robots" content="none" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="stylesheet" href="/css/bootstrap.min.css" />
	</head>
	<body>
	<div class="container">
		<div class="panel panel-info">
			<!-- Default panel contents -->
			<div class="panel-heading"><strong>회원 관리</strong></div>
			<div class="panel-body">
				<form class="form-inline">
					<div class="form-group">
						<label class="sr-only" for="member_id">회원 아이디</label>
						<input type="text" class="form-control" id="member_id" placeholder="아이디를 입력해주세요.">
					</div>
					<button type="button" id="member_add_btn" class="btn btn-primary">등록</button>
				</form>
			</div>
			<table class="table table-striped">
				<thead>
					<tr>
						<th>아이디</th>
						<th>등록일</th>
						<th>삭제</th>
					</tr>
				</thead>
				<tbody id="member_list">
					<c:forEach var="member" items="${memberList}" varStatus="status">
					<tr>
						<td>${member.memberId}</td>
						<td>${member.creationTime}</td>
						<td><button type="button" data-id="${member.memberId}" class="btn btn-default btn-xs btn-delete">삭제</button></td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	<script type="text/javascript" charset="utf-8" src="/js/jquery-2.0.3.min.js"></script>
	<script type="text/javascript" charset="utf-8" src="/js/bootstrap.min.js"></script>
	<script type="text/javascript">
    (function($) {
        String.prototype.zf = function(len) {
            return "0".getCut(len - this.length) + this;
        };
        String.prototype.getCut = function(len, last) {
            var str = this;
            var l = 0;
            for ( var i = 0; i < str.length; i++) {
              l += (str.charCodeAt(i) > 128) ? 2 : 1;
              if (l > len) {
                if (typeof last != 'undefined' && last != null)
                  return str.substring(0, i) + last;
                else
                  return str.substring(0, i).toString();
              }
            }
            return String(str);
        };
        Number.prototype.zf = function(len) {
            return this.toString().zf(len);
        };
        Date.prototype.format = function(f) {
            if (!this.valueOf())
                return " ";
            var d = this;

            return f.replace(/(yyyy|yy|MM|dd|ee|hh|mm|ss)/gi, function($1) {
                switch ($1) {
                case "yyyy":
                  return d.getFullYear();
                case "yy":
                  return (d.getFullYear() % 1000).zf(2);
                case "MM":
                  return (d.getMonth() + 1).zf(2);
                case "dd":
                  return d.getDate().zf(2);
                case "ee":
                  return d.getDay();
                case "HH":
                  return d.getHours().zf(2);
                case "hh":
                  return ((h = d.getHours() % 12) ? h : 12).zf(2);
                case "mm":
                  return d.getMinutes().zf(2);
                case "ss":
                  return d.getSeconds().zf(2);
                default:
                  return $1;
                }
            });
        };
        // 등록버튼 클릭
        $('#member_add_btn').on('click', function(e) {
            e.preventDefault();
            if (!$('#member_id').val().length) {
                alert('등록할 회원 아이디를 입력해주세요.');
                return;
            }
            $.post('/dp/member', {'id': $.trim($('#member_id').val())}, function(rs, status) {
                if (status === 'success' && rs.status === 200) {
                    var rowTmpl = new Array();
                    rowTmpl.push('<tr>');
                    rowTmpl.push('<td>');
                    rowTmpl.push($.trim($('#member_id').val()));
                    rowTmpl.push('</td>');
                    rowTmpl.push('<td>');
                    rowTmpl.push(new Date().format('yyyy/MM/dd HH:mm:ss'));
                    rowTmpl.push('</td>');
                    rowTmpl.push('<td><button type="button" data-id="');
                    rowTmpl.push($.trim($('#member_id').val()));
                    rowTmpl.push('" class="btn btn-default btn-xs btn-delete">삭제</button></td>');
                    rowTmpl.push('</tr>');
                    $('#member_list').prepend(rowTmpl.join(''));
                }
            });
        });
        // 회원 삭제 버튼 클릭
        $(document).on('click', '.btn-delete', function(e) {
            e.preventDefault();
            var _that = $(this);
            $.ajax({
                type: 'DELETE',
                url: '/dp/member?id=' + _that.data('id'),
                success: function(rs) {
                  if (rs.status === 200) {
                    _that.parents('tr').remove();
                  } else {
                    alert('삭제를 실패했습니다.');
                  }
                }
            });
        })
    })(jQuery);
	</script>
	</body>
</html>