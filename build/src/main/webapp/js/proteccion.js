
    window.onload = function() {
    	
    	 let usuario; 
	      let prt = new XMLHttpRequest();
               prt.onreadystatechange = function () {
                   if (prt.readyState === 4){
                       if (prt.status === 200) {
                         try{
                    	 usuario = JSON.parse(prt.responseText);            
              
                    	 document.getElementById('usuario').innerHTML = usuario.nombre + " <a href='CerrarSesion'>CerrarSesión</a>";
                         }catch (e) {
							// TODO: handle exception
							location.href = "login.html";
						}
                       }                    
                   }
               };

               prt.open("GET", "Logueo", false);
               prt.setRequestHeader("Content-Type", "application/json");
               prt.send();
    
    
    }