<!DOCTYPE html>

<html lang="en">

<body>
<#list beans as item>
<a href="${item.url}" target="_blank">${item.name}</a> &nbsp;&nbsp;&nbsp;
</#list>
</body>
</html>
