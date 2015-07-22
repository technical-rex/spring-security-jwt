<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Logged In</title>

    <!-- Bootstrap core CSS -->
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="/css/home.css" rel="stylesheet">

    <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
    <!--[if lt IE 9]><script src="http://getbootstrap.com/assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
    <script src="http://getbootstrap.com/assets/js/ie-emulation-modes-warning.js"></script>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<div class="jumbotron">
    <div class="container">
        <h1>Logged In as <%=request.getAttribute("email")%></h1>
        <p>Click the button below to put that stateless authentication to use!</p>
        <p><a id="sayHello" class="btn btn-info" href="javascript:void(0)" role="button">Say Hello</a></p>
    </div>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<script src="http://getbootstrap.com/assets/js/ie10-viewport-bug-workaround.js"></script>
<script type="text/javascript">
    var authToken = '<%=request.getAttribute("authToken")%>';
    $('#sayHello').click(function() {
        $.ajax({
            url: '/api/greetings',
            method: 'GET',
            dataType: 'json',
            headers: {
                'X-AUTH-TOKEN': authToken
            }
        }).done(function(json) {
            var i = Math.floor(Math.random() * json.greetings.length);
            alert(json.greetings[i].text);
        });
    });
</script>
</body>
</html>
