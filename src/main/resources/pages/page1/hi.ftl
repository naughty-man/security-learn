<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>

    <div>
        <h2>Access Form</h2>
        <div>
            <#--此地址只需要和配置的executeLogin地址相同即可，实际映射security处理，我们无需实现-->
            <form action="/executeLogin" method="post">
                用户名<input type="text" name="username">
                密码<input type="password" name="password">
                验证码<input type="text" name="kaptcha">
                <img src="/kaptcha.jpg" alt="kaptcha" height="50px" width="150px" style="margin-left: 20px">
                <div>
                    <input type="submit" value="login">
                </div>
            </form>
        </div>
    </div>

</body>
</html>