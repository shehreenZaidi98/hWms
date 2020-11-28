//var ip = "http://localhost:8102";
function putData(){
    for(var i=0;i<document.getElementById("productTable").getElementsByTagName("tr").length;i++){
         var barcode = parseInt(document.getElementById("productTable").getElementsByTagName("tr")[i].getElementsByTagName("td")[1].innerHTML);
         var pName = document.getElementById("productTable").getElementsByTagName("tr")[i].getElementsByTagName("td")[2].innerHTML;
         var quantity = parseInt(document.getElementById("productTable").getElementsByTagName("tr")[i].getElementsByTagName("td")[3]);
         var size = parseInt(document.getElementById("productTable").getElementsByTagName("tr")[i].getElementsByTagName("td")[4].innerHTML);

         var temp = {
             "barcode":barcode,
             "product_name":pName,
             "quantity":quantity,
             "size":size,
         }

         console.log(temp)
        var xhttp = new XMLHttpRequest();
          xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
             console.log(this.responseText);

            }
          };
          xhttp.open("POST", "/inStock", true);
          xhttp.setRequestHeader("Content-type", "application/json");
          xhttp.send(JSON.stringify(temp));
    }
}

function putData(){
    for(var i=0;i<document.getElementById("productTable").getElementsByTagName("tr").length;i++){
         var barcode = parseInt(document.getElementById("productTable").getElementsByTagName("tr")[i].getElementsByTagName("td")[1].innerHTML);
         var pName = document.getElementById("productTable").getElementsByTagName("tr")[i].getElementsByTagName("td")[2].innerHTML;
         var quantity = parseInt(document.getElementById("productTable").getElementsByTagName("tr")[i].getElementsByTagName("td")[3].innerHTML);
         var size = parseInt(document.getElementById("productTable").getElementsByTagName("tr")[i].getElementsByTagName("td")[4].innerHTML);

         var temp = {
             "barcode":barcode,
             "product_name":pName,
             "quantity":quantity,
             "size":size,
         }

         console.log(temp)
        var xhttp = new XMLHttpRequest();
          xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
             console.log(this.responseText);

            }
          };
          xhttp.open("POST", "/inStock", true);
          xhttp.setRequestHeader("Content-type", "application/json");
          xhttp.send(JSON.stringify(temp));
    }
}

function getTotalData(){
  var xhttp = new XMLHttpRequest();
          xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
             console.log(this.responseText);
            }
          };
          xhttp.open("GET", "/getAllList", true);
          xhttp.send();
}

var timer = null;
document.getElementById("barcode").addEventListener('input',()=>{
       clearTimeout(timer);
       timer = setTimeout(getBarcodeData, 1500)
});

function getBarcodeData(){
    var barcode = document.getElementById("barcode").value;
    var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
        console.log(this.responseText);
        var result = JSON.parse(this.responseText);
        document.getElementById("productName").value = result.product[0].product_name;
        document.getElementById("size").value = result.product[0].size;
        }
    };
    xhttp.open("GET", "/getBarcodeData?barcode="+barcode, true);
    xhttp.send();
}

function removeQuantity(){
     var barcode = document.getElementById("barcode");
     var pName = document.getElementById("productName");
     var quantity = document.getElementById("quantity");
     var size = document.getElementById("size");
     var temp = {
                  "barcode":barcode.value,
                  "product_name":pName.value,
                  "quantity":quantity.value,
                  "size":size.value,
              }
     var xhttp = new XMLHttpRequest();
     xhttp.onreadystatechange = function() {
     if (this.readyState == 4 && this.status == 200) {
           console.log(this.responseText);
        }
     };
     xhttp.open("POST", "/outStock", true);
     xhttp.setRequestHeader("Content-type", "application/json");
    xhttp.send(JSON.stringify(temp));
}

function outData(){

        var barcode = document.getElementById("barcode");
        var pName = document.getElementById("productName");
        var quantity = document.getElementById("quantity");
        var size = document.getElementById("size");
        console.log("run");
        var count = document.getElementById("todayOutProductTable").getElementsByTagName("tr").length;
        document.getElementById("todayOutProductTable").innerHTML += "<tr>" +
            '<td> ' + ++count + '</td>' +
            '<td> ' + barcode.value + '</td>' +
            '<td> ' + pName.value + '</td>' +
            '<td>' + quantity.value + '</td>' +
            '<td> ' + size.value + '</td>' +

            '</tr>';
            removeQuantity();
             barcode.value = "";
             pName.value = "";
             quantity.value = "";
             size.value = "";
             document.getElementById("barcode").focus();
}

function getTodayStockData(){
     var xhttp = new XMLHttpRequest();
         xhttp.onreadystatechange = function() {
         if (this.readyState == 4 && this.status == 200) {
               console.log(this.responseText);
               var result = JSON.parse(this.responseText);
               for(var i=0;i<result.TodayData.length;i++){
                   document.getElementById("todayOutProductTable").innerHTML +=
                   "<tr>"+
                   '<td>'+ (i+1) + '</td>'+
                   '<td>'+result.TodayData[i].barcode + '</td>'+
                   '<td>'+result.TodayData[i].product_name + '</td>'+
                   '<td>'+result.TodayData[i].quantity + '</td>'+
                   '<td>'+result.TodayData[i].size + '</td>'+
                   '</tr>';
               }
            }
         };
         xhttp.open("GET", "/getTodayData", true);
        xhttp.send();
}
function allOutProductTable(){
     var xhttp = new XMLHttpRequest();
         xhttp.onreadystatechange = function() {
         if (this.readyState == 4 && this.status == 200) {
               console.log(this.responseText);
               var result = JSON.parse(this.responseText);
               for(var i=0;i<result.TodayData.length;i++){
                   document.getElementById("allOutProductTable").innerHTML += "<tr>"+
                   '<td>'+ (i+1) + '</td>'+
                   '<td>'+result.TodayData[i].barcode + '</td>'+
                   '<td>'+result.TodayData[i].product_name + '</td>'+
                   '<td>'+result.TodayData[i].quantity + '</td>'+
                   '<td>'+result.TodayData[i].size + '</td>'+
                   '</tr>';
               }
            }
         };
         xhttp.open("GET", "/getAllPurData", true);
        xhttp.send();
}
function todayStockTable(){
     var xhttp = new XMLHttpRequest();
         xhttp.onreadystatechange = function() {
         if (this.readyState == 4 && this.status == 200) {
               console.log(this.responseText);
               var result = JSON.parse(this.responseText);
               for(var i=0;i<result.data.length;i++){
                   document.getElementById("todayStockTable").innerHTML += "<tr>"+
                   '<td>'+ (i+1) + '</td>'+
                   '<td>'+result.data[i].barcode + '</td>'+
                   '<td>'+result.data[i].product_name + '</td>'+
                   '<td>'+result.data[i].quantity + '</td>'+
                   '<td>'+result.data[i].size + '</td>'+
                   '</tr>';
               }
            }
         };
         xhttp.open("GET", "/getTodayStock", true);
        xhttp.send();
}
function totalStockTable(){
     var xhttp = new XMLHttpRequest();
         xhttp.onreadystatechange = function() {
         if (this.readyState == 4 && this.status == 200) {
               console.log(this.responseText);
               var result = JSON.parse(this.responseText);

               for(var i=0;i<result.data.length;i++){
                   document.getElementById("totalStockTable").innerHTML += "<tr>"+
                   '<td>'+ (i+1) + '</td>'+
                   '<td>'+result.data[i].barcode + '</td>'+
                   '<td>'+result.data[i].product_name + '</td>'+
                   '<td>'+result.data[i].quantity + '</td>'+
                   '<td>'+result.data[i].size + '</td>'+
                   '</tr>';
               }
            }
         };
         xhttp.open("GET", "/getAllList", true);
        xhttp.send();
}function getCount(){
     var xhttp = new XMLHttpRequest();
         xhttp.onreadystatechange = function() {
         if (this.readyState == 4 && this.status == 200) {
               console.log(this.responseText);
               var result = JSON.parse(this.responseText);
                document.getElementById("todaySTCount").innerHTML = result.data[0].todayStock;
                document.getElementById("totalSTCount").innerHTML = result.data[0].totalStock;
                document.getElementById("totalPTCount").innerHTML = result.data[0].totalPurchase;
                document.getElementById("todayPTCount").innerHTML = result.data[0].todayPurchase;
            }
         };
         xhttp.open("GET", "/getCountData", true);
        xhttp.send();
}
