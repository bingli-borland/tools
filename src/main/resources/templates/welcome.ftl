<!DOCTYPE html>

<html lang="en">

<body>
	Date: ${time?date}
	<br>
	Time: ${time?time}
	<br>
	Message: ${message}
	<br>
<form action="/tv" method="post">
  <p>
  type: <textarea rows="1" cols="100" name="type" ></textarea><br>
  name: <textarea rows="1" cols="100" name="name" ></textarea>
  </p>
  <input type="submit" value="TV" />
</form> &nbsp;&nbsp;&nbsp;
<br>
<a href="/ParseData" target="_blank">JAVA</a> &nbsp;&nbsp;&nbsp;
<a href="/ToUpdate" target="_blank">UPDATE</a> &nbsp;&nbsp;&nbsp;
</body>

</html>
