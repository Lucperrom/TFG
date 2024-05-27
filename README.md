# TFG - “Sistema de gestión de publicaciones”
Implementar un sistema de gestión de las publicaciones para papers de investigación.

Directrices de ejecución:


1º Instalar java 17.0.9
2º Instalar Apache Maven 3.9.6
3º Instalar Node.js v21.6.2
4ºConfigurar Variables de Entorno:

    Asegúrate de que las variables de entorno JAVA_HOME y M2_HOME están configuradas correctamente. Puedes configurarlas temporalmente en tu sesión de PowerShell:

    powershell:
    $env:JAVA_HOME = "C:\Path\To\Java"
    $env:M2_HOME = "C:\Path\To\Maven"
    $env:Path += ";$env:JAVA_HOME\bin;$env:M2_HOME\bin"

    Reemplaza "C:\Path\To\Java" y "C:\Path\To\Maven" con las rutas correctas a tus instalaciones de Java y Maven.

En la terminal dentro de la carpeta descomprimida en la raiz:

    5º Para ejecutar el BackEnd:
    npm install 
    mvn clean
    mvn install
    mvn spring-boot:run

    6º Para ejecutar el FronteEnd:
    cd frontend
    npm install
    npm start (este paso suele tardar en cargar, cuando termine abrirá el buscador solo)