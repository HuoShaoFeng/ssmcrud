<%--
  Created by IntelliJ IDEA.
  User: huosf
  Date: 2017/12/24
  Time: 16:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>员工列表</title>
<%
    pageContext.setAttribute("APP_PATH", request.getContextPath());
%>
<!-- web路径：
不以/开始的相对路径，找资源，以当前资源的路径为基准，经常容易出问题。
以/开始的相对路径，找资源，以服务器的路径(webapp)为标准(http://localhost:3306)；需要加上项目名
        http://localhost:3306/crud
 -->
<script type="text/javascript"
        src="${APP_PATH }/static/js/jquery-1.12.4.min.js"></script>
<link
        href="${APP_PATH }/static/bootstrap-3.3.7-dist/css/bootstrap.min.css"
        rel="stylesheet">
<script
        src="${APP_PATH }/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>

</head>
<body>

    <%--搭建显示页面BootStrap--%>
    <div class="container">
        <%--标题--%>
        <div class="row">
            <div class="col-lg-12">
                <h1>所有员工信息</h1>
            </div>

        </div>
        <%--两个按钮--%>
        <div class="row">
            <div class="col-md-4 col-md-offset-8">
                <button class="btn btn-success">  新增  </button>
                <button class="btn btn-danger">  删除  </button>
            </div>

        </div>
        <%--表格数据集--%>
        <div class="row">
            <div class="col-md-10">
                <table class="table table-hover">
                    <tr>
                        <th>编号</th>
                        <th>姓名</th>
                        <th>性别</th>
                        <th>邮箱</th>
                        <th>部门</th>
                        <th>操作</th>
                    </tr>

                    <c:forEach items="${pageInfo.list}" var="emp">
                        <tr>
                            <td>${emp.empId}</td>
                            <td>${emp.empName}</td>
                            <td>${emp.gender=="M"?"男":"女"}</td>
                            <td>${emp.email}</td>
                            <td>${emp.department.deptName}</td>
                            <td>
                                <button class="btn btn-primary btn-sm">
                                    <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                                    编辑
                                </button>
                                <button class="btn btn-danger btn-sm">
                                    <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                                    删除
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>

        </div>
        <%--分页信息--%>
        <div class="row">
            <%--分页文字信息--%>
            <div class="col-md-6">
                当前第<font class="bg-info"> ${pageInfo.pageNum}</font>页，总共<font class="bg-info">${pageInfo.pages}</font>页，总共<font class="bg-info">${pageInfo.total}</font>条记录
            </div>
            <%--分页条信息--%>
            <div class="col-md-6">

                <nav aria-label="Page navigation">
                    <ul class="pagination">
                        <li><a href="${APP_PATH}/emps?pn=1">首页</a></li>

                        <c:if test="${pageInfo.hasPreviousPage}">
                            <li>
                                <a href="${APP_PATH}/emps?pn=${pageInfo.pageNum-1}" aria-label="Previous">
                                    <span aria-hidden="true">&laquo;</span>
                                </a>
                            </li>
                        </c:if>

                            <c:forEach items="${pageInfo.navigatepageNums}" var="pageNum">
                                <c:if test="${pageNum==pageInfo.pageNum}">
                                    <li class="active"><a href="#">${pageNum}</a></li>
                                </c:if>
                                <c:if test="${pageNum!=pageInfo.pageNum}">
                                    <li><a href="${APP_PATH}/emps?pn=${pageNum}">${pageNum}</a></li>
                                </c:if>


                            </c:forEach>

                            <c:if test="${pageInfo.hasNextPage}">
                                <li>
                                    <a href="${APP_PATH}/emps?pn=${pageInfo.pageNum+1}" aria-label="Next">
                                        <span aria-hidden="true">&raquo;</span>
                                    </a>
                                </li>
                            </c:if>
                        <li><a href="${APP_PATH}/emps?pn=${pageInfo.pages}">尾页</a></li>
                    </ul>
                </nav>

            </div>

        </div>

    </div>

</body>
</html>
