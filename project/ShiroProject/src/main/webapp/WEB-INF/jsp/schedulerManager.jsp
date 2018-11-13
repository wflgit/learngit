<%@ page contentType="text/html;charset=UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>   
<head>
    <title>定时器管理</title>
    <meta name="decorator" content="bootstrap3" />
    <script type="text/javascript" src="${ctxStatic}/scheduler/dashboard.js"></script>
    <link href="${ctxStatic}/bootstrap/panel.css" type="text/css" rel="stylesheet" />
    <style type="text/css">
.running {
    color: green;
    padding-right: 5px;
    font-size: 1.3em;
    font-weight: bold;
}

.stoped {
    color: red;
    padding-right: 5px;
    font-size: 1.3em;
    font-weight: bold;
}
</style>
<script type="text/javascript">
function schedulerOperate(operate){
	location.href = operate; 
}

function triggerOperate(operate){
	location.href = operate;
}
</script>
</head>
<body>
    <div style="padding: 0 20px;">
        <div style="margin-top: 30px;">
            <div class="span12">
                定时器状态：
                <c:if test="${!scheduler.inStandbyMode && scheduler.started}">
                    <span class="running">运行中……</span>
                    <button type="button" class="btn btn-danger" onclick="schedulerOperate('/ShiroProject/scheduler/stop')">停止</button>
                </c:if>
                <c:if test="${scheduler.inStandbyMode || !scheduler.started}">
                    <span class="stoped">停止中……</span>
                    <button type="button" class="btn btn-primary" onclick="schedulerOperate('/ShiroProject/scheduler/run')">启动</button>
                </c:if>
                &nbsp;&nbsp;
                <button type="button" class="btn btn-success" onclick="location.href='/ShiroProject/scheduler/list'">刷新</button>
                &nbsp;&nbsp;
                <span class="glyphicon glyphicon-refresh" />
                当前时间：
                <span id="time"></span>
            </div>
        </div>
        <div style="margin-top: 30px;">
            <c:forEach items="${tiggerGroups}" var="group">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <span class="glyphicon glyphicon-cog"></span>组名:${group.groupName}
                    </div>
                    <table class="table table-bordered table-hover-custom">
                        <thead>
                            <tr>
                                <th style="width: 20%;">任务名</th>
                                <th style="width: 25%;">描述</th>
                                <th style="width: 10%;">周期表达式</th>
                                <th style="width: 12%;">上一次运行时间</th>
                                <th style="width: 12%;">下一次运行时间</th>
                                <th style="width: 5%;">状态</th>
                                <th style="width: 15%;">操作</th>
                            </tr>
                        </thead>
                        <c:forEach items="${group.triggerModels}" var="triggerModel">
                            <tr>
                                <td>${triggerModel.trigger.name}</td>
                                <td>${triggerModel.trigger.description}</td>
                                <td>${triggerModel.trigger.cronExpression}</td>
                                <td>
                                    <fmt:formatDate value="${triggerModel.trigger.previousFireTime}" pattern="yyyy-MM-dd HH:mm:ss" />
                                </td>
                                <td>
                                    <fmt:formatDate value="${triggerModel.trigger.nextFireTime}" pattern="yyyy-MM-dd HH:mm:ss" />
                                </td>
                                <td>
                                    <c:if test="${triggerModel.status == 0}"> <font color="green">正常</font>
                                    </c:if>
                                    <c:if test="${triggerModel.status == 1}">暂停</c:if>
                                    <c:if test="${triggerModel.status == 2}">完成</c:if>
                                    <c:if test="${triggerModel.status == 3}"> <font color="red">错误</font>
                                    </c:if>
                                    <c:if test="${triggerModel.status == 4}">
                                        <font color="red">阻塞</font>
                                    </c:if>
                                    <c:if test="${triggerModel.status == -1}">无</c:if>
                                </td>
                                <td>
                                    <c:if test="${triggerModel.status == 1}">
                                        <button type="button" class="btn btn-success btn-xs" onclick="triggerOperate('/ShiroProject/scheduler/resumeTrigger?name=${triggerModel.trigger.name}&group=${group.groupName}')">启动</button>
                                    </c:if>
                                    <c:if test="${triggerModel.status != 1}">
                                        <button type="button" class="btn btn-danger btn-xs" onclick="triggerOperate('/ShiroProject/scheduler/pauseTrigger?name=${triggerModel.trigger.name}&group=${group.groupName}')">暂停</button>
                                    </c:if>
                                    <button type="button" class="btn btn-warning btn-xs" onclick="triggerOperate('/ShiroProject/scheduler/triggerTrigger?name=${triggerModel.trigger.name}&group=${group.groupName}')">立即启动</button>
                                    &nbsp;
                                    <a role="button" class="btn btn-primary btn-xs" data-toggle="modal" data-target="#editModal" onclick="remoteUrl('${ctx}/sys/schedulerManager/editTrigger?name=${triggerModel.trigger.name}&group=${group.groupName}')">编辑</a>
                                </td>
                            </tr>
                            <tr></tr>
                        </c:forEach>
                    </table>
                    
                </div>
            </c:forEach>
        </div>
        <div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="myModalLabel">
                            <span class="glyphicon glyphicon-floppy-disk" />
                            任务编辑：
                        </h4>
                    </div>
                    <div class="modal-body"></div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                        <button type="button" class="btn btn-primary" id="saveTriggerBtn" name="saveTriggerBtn" onclick="saveTrigger('${ctx}/sys/schedulerManager/saveTrigger')">保存</button>
                    </div>
                    <div class="panel panel-success">
                        <div class="panel-heading">
                            <h3 class="panel-title">
                                常用表达式，
                                <small>
                                    <a href="http://www.jeasyuicn.com/cron/" target="_blank">在线cron表达式生成器</a>
                                </small>
                            </h3>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>