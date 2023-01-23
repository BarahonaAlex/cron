/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
        function CheckForm(upload,text1, text2){    
        if(text1.value.length ==0 && text2.value.length ==0){
               alert("please reset it !"); 
        }
        alert("hola " + text1.value + text2.value);
        upload.disable();
        //var a = document.getElementById("#upload").getAttribute("disabled").toString();
        //upload.disabled=false;
        }
        function validarCampos(){
            var numero =  jQuery("input[id$='nodocumento']").val();
            var descripcion =  jQuery("input[id$='descripcion']").val();
            $("#upload").removeAttr("disabled");
            $("#descripcion").attr("disabled", "disabled");
            alert('hola ' + descripcion + ' ' + numero );
        }
        
	$(".rf-fu-btn-add").css('visibility','hidden');

       

