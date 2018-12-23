<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>SSM框架Excel文件操作</title>

    <script type="text/javascript" src="js/jquery-1.10.2.js"></script>
    <script type="text/javascript" src="js/jquery-2.1.1.min.js"></script>

</head>

<body>
<div id="head" align="center">
    <table>
        <tr>
            <td><input type="file" id="upload" name="upload" value=""/></td>
            <td>
                <button onclick="uploadFile()">上传</button>
            </td>
            <td>
                <button onclick="outputExcel()">导出</button>
            </td>
        </tr>
    </table>
</div>

<div id="body" align="center">
    <table id="bodyData">
        <thead>
        <tr>
            <th>id</th>
            <th>name</th>
            <th>sex</th>
            <th>email</th>
            <th>deptName</th>
        </tr>
        </thead>
        <tbody>

        </tbody>
    </table>
</div>

<script type="text/javascript">

    $(function () {
        getAll();
    })

    function getAll() {
        $.ajax({
            url: "excel/getAll.do",
            type: "GET",
            dataType: "json",
            success: function (e) {
                $("#bodyData").empty();
                var Data = '<tr><th>id</th><th>name</th><th>sex</th><th>email</th><th>deptName</th></tr>';
                $("#bodyData").append(Data);
                var list = eval(e);
                if (list.length != 0) {
                    console.log(list);
                    for (var j = 0; j < list.length; j++) {
                        var Data1 = '<tr style="cursor:pointer;">'
                            + '<td>' + list[j].id + '</td>'
                            + '<td>' + list[j].name + '</td>'
                            + '<td>' + list[j].sex + '</td>'
                            + '<td>' + list[j].email + '</td>'
                            + '<td>' + list[j].deptName.dName + '</td>'
                            + '</tr>';
                        $("#bodyData").append(Data1);
                    }
                }
                if (list == "") {
                    $("#bodyData").empty();
                }
            }
        });
    }

    function uploadFile() {
        var file = $("#upload").val();
        file = file.substring(file.lastIndexOf('.'), file.length);
        if (file == '') {
            alert("上传文件不能为空！");
        } else if (file != '.xlsx' && file != '.xls') {
            alert("请选择正确的excel类型文件！");
        } else {
            ajaxFileUpload();
        }
    }

    function ajaxFileUpload() {

        var formData = new FormData();
        var name = $("#upload").val();
        formData.append("file", $("#upload")[0].files[0]);
        formData.append("name", name);
        $.ajax({
            url: "excel/inputExcel.do",
            type: "POST",
            async: false,
            data: formData,
            processData: false,
            contentType: false,
            beforeSend: function () {
                console.log("正在进行，请稍候");
            },
            success: function (e) {
                if (e == "01") {
                    alert("导入成功");
                } else {
                    alert("导入失败");
                }
            }
        });
    }

    function OutputExce() {
        window.location.href = "/ExcelDemo/excel/OutputExcel.do";
    }
</script>

</body>
</html>