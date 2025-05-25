<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ar" dir="rtl">
<head>
    <meta charset="UTF-8">
    <title>رفع ملف بوت بايثون</title>
    <style>
        @font-face {
            font-family: 'HaineL';
            src: url('fonts/HaineL.ttf') format('truetype');
        }
        body {
            margin: 0;
            background: linear-gradient(135deg, #667eea, #764ba2);
            font-family: 'HaineL', Arial, sans-serif;
            color: #fff;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            direction: rtl;
        }
        .container {
            background: rgba(255, 255, 255, 0.1);
            padding: 40px 60px;
            border-radius: 20px;
            box-shadow: 0 8px 32px rgba(0, 0, 0, 0.4);
            text-align: center;
            width: 400px;
        }
        h1 {
            margin-bottom: 25px;
            font-size: 2.5rem;
            letter-spacing: 2px;
            text-shadow: 1px 1px 2px #000;
        }
        input[type="file"] {
            margin-top: 20px;
            color: #fff;
        }
        button {
            margin-top: 30px;
            background: #764ba2;
            border: none;
            padding: 15px 30px;
            border-radius: 30px;
            color: white;
            font-size: 1.2rem;
            cursor: pointer;
            transition: background 0.3s ease;
            box-shadow: 0 4px 15px #5a348c;
        }
        button:hover {
            background: #8e5fc3;
            box-shadow: 0 6px 20px #7c4db9;
        }
        .message {
            margin-top: 25px;
            font-weight: bold;
            font-size: 1.1rem;
            color: #cdf27f;
            text-shadow: 0 0 3px #9cc85f;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>رفع ملف بوت بايثون</h1>
    <form action="upload" method="post" enctype="multipart/form-data">
        <input type="file" name="file" accept=".py" required />
        <br />
        <button type="submit">رفع وتشغيل الملف</button>
    </form>
    <div class="message">
        <% String msg = (String) request.getAttribute("message");
           if(msg != null) { %>
           <%= msg %>
        <% } %>
    </div>
</div>
</body>
</html>
