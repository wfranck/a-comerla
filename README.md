# A Comerla!
En los últimos Hackatons en Zauber Software estuvimos probando Play! Framework. Para probar este framework decidimos hacer una aplicación que vayasemos a usar todos los dias.

## ¿Cúal es la idea de A Comerla!?
Nosotros todos los dias realizamos pedidos para almorzar. Es bastante complicado saber cuanto salen los platos, cúal es el total, quién se va a encargar de pedir, quién se va a encargar de juntar la plata y cuándo se realizará el pedido. 
Para solucionar esta problemas, decidimos crear A Comerla.

## ¿Cómo funciona el Login?
No queríamos que los empleados de Zauber tengan que crearse usuarios nuevamente. Aquí en Zauber usamos Google Apps para nuestros mails. Entonces, decidimos hacer el Login con Google.

## ¿Cómo la puedo usar yo?
Para poder utilizar A comerla en sus empresas, deberán actualizar el archivo application.conf dentro de la carpeta conf con los datos correspondientes a su empresa. Deberán: 

1.  Cargar los datos del SMTP del Mail para poder enviar los mails que la aplicacion necesita. Para esto buscar la sección __Mail configuration__
1.  Cargar el mail de broadcast de la empresa, a la cual se enviaran los mails con pedidos nuevos. Dicha property es __prod.broadcast.mail__
1.  Cargar la URL base de la aplicación donde esta deployeada en la sección __Url-resolving in Jobs__
1.  Cargar los datos de la base de datos donde se guardará la información
1.  Cargar los datos de consumer key y secret de la aplicación que hayan creada de Google para poder realizar el login. Esto se encuentra en la sección __Secure Social__