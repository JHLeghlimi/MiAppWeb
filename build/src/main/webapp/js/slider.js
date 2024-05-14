
let imagenes = new Array();
imagenes.push('img/slider/animales-mitologicos-1-655x368.jpg');
imagenes.push('img/slider/animales-mitologicos_-el-basilisco-655x368.jpg');
imagenes.push('img/slider/fotos-de-seres-mitologicos.jpg');
imagenes.push('img/slider/fonoNuevaDeSofia.jpg');



let cont = 0;
let fin = imagenes.length;


window.onload = () => {


       document.querySelector('.fa-angle-right').addEventListener('click', () => {
        cont++;
        if(cont == fin){
            cont = 0;
        }
        document.querySelector(".slider img").src = imagenes[cont];

    })
    document.querySelector('.fa-angle-left').addEventListener('click', () => {
        cont--;
        if(cont <= 0){
            cont = ik-1 ;
        }
        document.querySelector(".slider img").src = imagenes[cont];

    })


}