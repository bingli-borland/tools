<!DOCTYPE html>

<html lang="en">

<body>
<#list beans as item>
<a href="${item.content_url}" target="_blank">${item.datetime} ${item.title}</a><br><br>
</#list>
</body>
</html>
