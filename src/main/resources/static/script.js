function inData(){

    if (checkBarcode()) {
        var barcode = document.getElementById("barcode");
        var pName = document.getElementById("productName");
        var quantity = document.getElementById("quantity");
        var size = document.getElementById("size");
        console.log("run");
        var count = document.getElementById("productTable").getElementsByTagName("tr").length;
        document.getElementById("productTable").innerHTML += "<tr>" +
            '<td> ' + ++count + '</td>' +
            '<td> ' + barcode.value + '</td>' +
            '<td> ' + pName.value + '</td>' +
            '<td> ' + quantity.value + '</td>' +
            '<td> ' + size.value + '</td>' +
            '<td><i class="fas fa-times-circle" onclick="deleteRow(this)"></i></td>'+
            '</tr>';
             barcode.value = "";
             pName.value = "";
             quantity.value = "";
             size.value = "";
             document.getElementById("barcode").focus();
    }
    document.getElementById("barcode").value ="";
    document.getElementById("productName").value ="";
    document.getElementById("quantity").value ="";
    document.getElementById("size").value ="";
    document.getElementById("barcode").focus();
}


function openSub(element) {
    console.log(element.nextElementSibling)
    if (element.nextElementSibling.style.height == "0px") { element.nextElementSibling.style.height = "90px"; }
    else { element.nextElementSibling.style.height = "0px"; }
}

// if(document.getElementById("barcode").value == document.getElementsByClassName(""))

function checkBarcode() {
    for (var i = 0; i < document.getElementById("productTable").getElementsByTagName("tr").length; i++) {

        if (document.getElementById("productTable").getElementsByTagName("tr")[i].getElementsByTagName("td")[1].innerHTML == parseInt(document.getElementById("barcode").value)) {
             document.getElementById("productTable").getElementsByTagName("tr")[i].getElementsByTagName("td")[3].innerHTML =
               parseInt(document.getElementById("productTable").getElementsByTagName("tr")[i].getElementsByTagName("td")[3].innerHTML) + parseInt(document.getElementById("quantity").value) ;
//                document.getElementById("productName").value =document.getElementById("productTable").getElementsByTagName("tr")[i].getElementsByTagName("td")[2].innerHTML;
//                document.getElementById("size").value =document.getElementById("productTable").getElementsByTagName("tr")[i].getElementsByTagName("td")[4].innerHTML;
            return false;
        }
    }
    return true;
}


function deleteRow(btn) {
    var row = btn.parentNode.parentNode;
    row.parentNode.removeChild(row);
    for(var i=0;i<document.getElementById("productTable").getElementsByTagName("tr").length;i++){
        document.getElementById("productTable").getElementsByTagName("tr")[i].getElementsByTagName("td")[0].innerHTML = i+1;
    }
}