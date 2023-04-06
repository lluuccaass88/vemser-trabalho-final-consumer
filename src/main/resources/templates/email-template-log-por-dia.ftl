<head>
    <meta charset="UTF-8">
    <title>TruckLog</title>
</head>

<body>
    <div align=center style="background-color: #94caba;">
        <br><br>
        <img src="https://github.com/lluuccaass88/vemser-trabalho-final/blob/main/images/trucklog-logo.png?raw=true" alt="Logo" height="100px" width="150px">
        <br><br>
    </div>

    <div style="background-color: #f2f2f2; padding: 20px; font-family:courier;">
        <br>
        <br>
        <br>
        <br>
        <br>
        <#list logDTO as logPorDiaDTO>
          <p>${logPorDiaDTO}
        </#list>
        <br>
        <br>
        <br>
        <br>
        <br>Qualquer dúvida é só contatar o suporte pelo email ->  ${emailContato}
        <br>
        <br>
        <br>
        <br>Atenciosamente,
        <br><b>${nome}</b>
        <br>
        <br>
    </div>

    <div align=center style="background-color: #94caba;">
        <br>
        <h1 style="color: #204844; font-family:courier;">"Voamos por você!"</h1>
        <br>
    </div>

</body>
