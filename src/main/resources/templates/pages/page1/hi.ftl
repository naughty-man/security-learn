<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="/css/auth.css">
</head>
<body>
    <div class="lowin">
        <div class="lowin-brand">
            <img src="/img/kodinger.jpg" alt="logo">
        </div>
        <div class="lowin-wrapper">
            <div class="lowin-box lowin-login">
                <div class="lowin-box-inner">
                    <form action="/executeLogin" method="post">
                        <p>Sign in to continue</p>
                        <div class="lowin-group">
                            <label>用户名 <a href="#" class="login-back-link">Sign in?</a></label>
                            <input type="text" name="username" class="lowin-input">
                        </div>
                        <div class="lowin-group password-group">
                            <label>密码 <a href="#" class="forgot-link">Forgot Password?</a></label>
                            <input type="password" name="password" class="lowin-input">
                        </div>
                        <div class="lowin-group">
                            <label>验证码</label>
                            <input type="text" name="kaptcha" class="lowin-input">
                            <img src="/kaptcha.jpg" alt="kaptcha" height="50px" width="150px" style="margin-left: 20px">
                        </div>
                        <div class="lowin-group">
                            <label>记住我</label>
                            <input name="remember-me" type="checkbox" value="true" />
                        </div>
                        <input class="lowin-btn login-btn" type="submit">
                    </form>
                </div>
            </div>
        </div>
    </div>

</body>
</html>