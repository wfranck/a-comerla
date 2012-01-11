# A Comerla!
In the latest Hackatons at Zauber Software we decided to start testing Play! Framework. We thought that the best way to test it would be creating an application that we'd use here at Zauber every day.

## What is A comerla!?
Everyday we call a lot of restaurants in order to have our meal delivered to the office. Right now, It's very complicated for us to know how much each meal costs, which is the total of the order, who's going to call the restaurant, who's going to collect the money to pay and when the order will be placed. 
So as to solve all of this problems, we created A Comerla!.

## How does the login work?
We didn't want Zauber employees to be forced to create users in order to use this app. As we use Google Apps for our emails, we decided to make the login work with Google.

## How can I use this?
In order to use A comerla in your businesses, you'll need to update the application.conf file in the conf folder with the following data:

1.  Put your SMTP mail information, so that the app can send emails. This can be changed in the __Mail configuration__ section.
1.  Put the broadcast email address of your company in the  __prod.broadcast.mail__ property. All new orders will be sent to this email.
1.  Put the base URL in which the app will be deployed in the __Url-resolving in   Jobs__ section.
1.  Put the database information in the __Database__ section.
1.  Put the consumer key and secred of the google application that you have created and verified in the __Secure social__ section. This must be done in order to be able to have Google Login.

---------------------------------------------------------------------------------------------------------------------------------------------


# A Comerla!
En los últimos Hackatons en Zauber Software estuvimos probando Play! Framework. Para probar este framework decidimos hacer una aplicación que vayasemos a usar todos los dias.

## ¿Cúal es la idea de A Comerla!?
Nosotros todos los dias realizamos pedidos para almorzar. Es bastante complicado saber cuanto salen los platos, cúal es el total, quién se va a encargar de pedir, quién se va a encargar de juntar la plata y cuándo se realizará el pedido. 
Para solucionar estos problemas, decidimos crear A Comerla.

## ¿Cómo funciona el Login?
No queríamos que los empleados de Zauber tengan que crearse usuarios nuevamente. Aquí en Zauber usamos Google Apps para nuestros mails. Entonces, decidimos hacer el Login con Google.

## ¿Cómo la puedo usar yo?
Para poder utilizar A comerla en sus empresas, deberán actualizar el archivo application.conf dentro de la carpeta conf con los datos correspondientes a su empresa. Deberán: 

1.  Cargar los datos del SMTP del Mail para poder enviar los mails que la aplicacion necesita. Para esto buscar la sección __Mail configuration__
1.  Cargar el mail de broadcast de la empresa, a la cual se enviaran los mails con pedidos nuevos. Dicha property es __prod.broadcast.mail__
1.  Cargar la URL base de la aplicación donde esta deployeada en la sección __Url-resolving in Jobs__
1.  Cargar los datos de la base de datos donde se guardará la información
1.  Cargar los datos de consumer key y secret de la aplicación que hayan creada de Google para poder realizar el login. Esto se encuentra en la sección __Secure Social__