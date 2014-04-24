<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String path = request.getContextPath();
	String uiRoot = request.getScheme() + "://" + request.getServerName() + 
			":" + request.getServerPort() + path + "/ui/";
%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8">
    <title>Login Page</title>
    <link rel="stylesheet" href="<%=uiRoot %>assets/lib/bootstrap/css/bootstrap.css">
    <link rel="stylesheet" href="<%=uiRoot %>assets/css/login.css">
    <link rel="stylesheet" href="<%=uiRoot %>assets/lib/magic/magic.css">
    <script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),

m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-1669764-16', 'onokumus.com');
  ga('send', 'pageview');

</script>
  </head>
  <body>


<div class="container">
    <div class="text-center">
        <img src="<%=uiRoot %>assets/img/logo.png" alt="Metis Logo">
    </div>
    <div class="tab-content">
        <div id="login" class="tab-pane active">
            <form action="<%=uiRoot %>login" class="form-signin" method="post">
                <p class="text-muted text-center">
                    Enter your username and password
                </p>
                <input type="text" placeholder="Username" class="form-control" name="username">
                <input type="password" placeholder="Password" class="form-control" name="password">
                <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
            </form>
        </div>
        <div id="signup" class="tab-pane">
            <form action="<%=uiRoot %>register" class="form-signin" method="post">
                <input type="text" placeholder="username" class="form-control" name="username">
                <input type="email" placeholder="mail@domain.com" class="form-control" name="email">
                <input type="password" placeholder="password" class="form-control" name="password">
                <button class="btn btn-lg btn-success btn-block" type="submit">Register</button>

            </form>
        </div>
    </div>
    <div class="text-center">
        <ul class="list-inline">
            <li><a class="text-muted" href="#login" data-toggle="tab">Login</a></li>
            <li><a class="text-muted" href="#signup" data-toggle="tab">Signup</a></li>
        </ul>
    </div>


</div> <!-- /container -->



      <script src="<%=uiRoot %>assets/lib/jquery-2.0.3.min.js"></script>
      <script src="<%=uiRoot %>assets/lib/bootstrap/js/bootstrap.js"></script>

      <script>
            $('.list-inline li > a').click(function() {
                var activeForm = $(this).attr('href') + ' > form';
                //console.log(activeForm);
                $(activeForm).addClass('magictime swap');
                //set timer to 1 seconds, after that, unload the magic animation
                setTimeout(function() {
                    $(activeForm).removeClass('magictime swap');
                }, 1000);
            });

            $("div#signup form").submit(function() {
            	var inputs = $("div#signup input");
            	for (var i = 0; i < inputs.size(); i++) {
            		if ($.trim(inputs.eq(i).val()) == "") {
            			alert(inputs.eq(i).attr("name") + " cannot be null");
            			return false;
            		}
            	}
            });
            
            $("div#login form").submit(function() {
            	var inputs = $("div#login input");
            	for (var i = 0; i < inputs.size(); i++) {
            		if ($.trim(inputs.eq(i).val()) == "") {
            			alert(inputs.eq(i).attr("name") + " cannot be null");
            			return false;
            		}
            	}
            });
        </script>
        <c:if test="${errorMsg != null }">
        <script type="text/javascript">
        	alert("${errorMsg}");
        </script>
        </c:if>
  </body>
</html>
