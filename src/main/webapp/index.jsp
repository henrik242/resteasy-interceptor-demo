<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>RESTEasy Interceptor demo</title>
</head>
<body>

<h2>1. Post to resource, with username</h2>
<ul>
<li>FAIL: The MessageBodyReaderInterceptor doesn't trigger</li>
<li>OK: The PreProcessorInterceptor triggers</li>
</ul>
<form action="api/role" method="post">

<input type=text name="en" value="ein"/><br>
<input type=text name="to" value="schwei"/><br>
<input type=text name="username" value="john"/><br>

<input type="submit" value="Post it!" />
</form>

<h2>2. Post to resource, with username</h2>
<ul>
<li>OK: Triggers the MessageBodyReaderInterceptor</li>
<li>OK: The PreProcessorInterceptor triggers</li>
</ul>
The only difference from 1. is in the method signature:
<b>postTest(String formParams)</b> works, while <b>post(MultivaluedMap&lt;String, String&gt; formParams)</b> doesn't.

<form action="api/roletest" method="post">

    <input type=text name="en" value="ein"/><br>
    <input type=text name="to" value="schwei"/><br>
    <input type=text name="tre" value="drei"/><br>
    <input type=text name="username" value="john"/><br>

    <input type="submit" value="Post it!" />
</form>

<h2>3. Get a resource without a username</h2>
<ul>
<li>OK: The MessageBodyReaderInterceptor doesn't trigger, because the PreProcessorInterceptor has higher priority and throws an Exception</li>
<li>OK: The PreProcessInterceptor triggers and throws a SecurityException</li>
<li>OK: The exception is nicely formatted by MyExceptionMapper</li>
</ul>

<a href="api/name/testing">Throw an exception</a>

<h2>4. Get a resource (with username) that throws an inner exception</h2>
<ul>
<li>FAIL: The MessageBodyReaderInterceptor doesn't trigger</li>
<li>OK: The PreProcessInterceptor triggers and doesn't throw an Exception</li>
<li>OK: The service method throws an Exception, and it is nicely formatted by MyExceptionMapper</li>
</ul>
<a href="api/name/testing?username=john">Throw an exception</a>

<h2>5. Post to a resource that throws an exception</h2>
<ul>
<li>FAIL: The MessageBodyReaderInterceptor doesn't trigger</li>
<li>OK: The PreProcessInterceptor triggers</li>
<li>FAIL: Somehow there isn't any MessageBodyWriter for application/x-www-form-urlencoded</li>
<li>FAIL: No nice formatting by MyExceptionMapper</li>
</ul>

<form action="api/another" method="post">
<input type="hidden" name="yo" value="hey" />
<input type=text name="username" value="john"/><br>
<input type="submit" value="Fail" />
</form>

</body>
</html>
