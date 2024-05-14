/**
 * 
 */
 
 function proteccion(){
		  let usuario; 
	      let xhr = new XMLHttpRequest();
               xhr.onreadystatechange = function () {
                   if (xhr.readyState === 4){
                       if (xhr.status === 200) {
                         try{
                    	 usuario = JSON.parse(xhr.responseText);            
              
                    	 document.getElementById('usuario').innerHTML = usuario.nombre + " <a href='CerrarSesion'>CerrarSesión</a>";
                         }catch (e) {
							// TODO: handle exception
							location.href = "login.html";
						}
                       }                    
                   }
               };

               xhr.open("GET", "Logueo", false);
               xhr.setRequestHeader("Content-Type", "application/json");
               xhr.send();
    
    
	
	
}