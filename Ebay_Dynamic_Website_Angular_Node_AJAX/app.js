var express = require('express');
var app = express();
var http=require('http');
var https= require('https');
var url= require('url');
var request = require('request');
var cors = require('cors');
app.use(cors());
app.use(express.static('public'));

//app.use(cors());
console.log("Hi, this is Vani");

app.get('/form', function (req, res,next) {
    res.setHeader("Content-Type","text/plain");

    
   res.setHeader("Access-Control-Allow-Origin","*");

    var q = url.parse(req.url,true);

   // console.log(q);

    var qdata = q.query;



  console.log(qdata);

      

   var keyword = encodeURIComponent(qdata.Keyword);
   var CatId = qdata.CatId ; 
   var zip = qdata.ZipCode;

   var ebay_api_key = 'VaniSing-CSCI571H-PRD-155bfe8c7-9e50ab1c';

   var url_link = "https://svcs.ebay.com/services/search/FindingService/v1?OPERATION-NAME=findItemsAdvanced&SERVICE-VERSION=1.0.0&SECURITY-APPNAME="+ebay_api_key+"&RESPONSE-DATA-FORMAT=JSON&REST-PAYLOAD&paginationInput.entriesPerPage=35&keywords="+keyword+"&buyerPostalCode="+zip;

  

  if(qdata.CatId != '0')
   {

      url_link+= "&categoryId="+qdata.CatId;

   }

   url_link+= "&itemFilter(0).name=MaxDistance&itemFilter(0).value="+qdata.miles;




   if(qdata.local == 'true' && qdata.free != 'true')
   {
   var   local="true";
   var   free = "false";
   }
   else if (qdata.local != 'true' && qdata.free == 'true')
   {
   var   local="false";
   var   free = "true";
   }

   else
   {
      var local = "false";
      var free = "false";
   }

   url_link+= "&itemFilter(1).name=FreePickupOnly&itemFilter(1).value="+free+"&itemFilter(2).name=LocalPickupOnly&itemFilter(2).value="+local;





   var cond = [];

   if(qdata.New == 'true')
   {
      cond.push('New');

   }
   if(qdata.Used == 'true')
   {
      cond.push('Used');
   }
   if(qdata.Unspecified == 'true')
   {
      cond.push('Unspecified');
   }


   var length = cond.length;

   for(var i =0 ;i < length; i++ )

   {
         url_link+= "&itemFilter(3).name=Condition&itemFilter(3).value("+i+")="+cond[i];

   }


   url_link+= "&outputSelector(0)=SellerInfo&outputSelector(1)=StoreInfo";




   console.log(url_link);



   

   request(
      {
         json:true,
         uri:url_link,

      },function(error,response,body){

      res.setHeader('Content-Type','application/json');
      res.setHeader('Access-Control-Allow-Origin','*');
      res.json(body);


      
});

   
 

})



app.get('/product_details', function (req, res,next) {



   res.setHeader("Content-Type","text/plain");

    
   res.setHeader("Access-Control-Allow-Origin","*");

    var q = url.parse(req.url,true);
    var ebay_api_key = 'VaniSing-CSCI571H-PRD-155bfe8c7-9e50ab1c';

   

    var qdata = q.query;

    var ItemId = qdata.ItemId;

    var url_link2 = "http://open.api.ebay.com/shopping?callname=GetSingleItem&responseencoding=JSON&appid="+ebay_api_key+"&siteid=0&version=967&IncludeSelector=Description,ItemSpecifics,Details&ItemID="+ItemId;


     request(
      {
         json:true,
         uri:url_link2,

      },function(error,response,body){
         //console.log(url_link2);

      res.setHeader('Content-Type','application/json');
      res.setHeader('Access-Control-Allow-Origin','*');
      res.json(body);


   });

   });



app.get('/autocomplete', function (req, res,next) {



   res.setHeader("Content-Type","text/plain");

    
   res.setHeader("Access-Control-Allow-Origin","*");

    var q = url.parse(req.url,true);
    var ebay_api_key = 'VaniSing-CSCI571H-PRD-155bfe8c7-9e50ab1c';

   

    var qdata = q.query;

    var searchText = qdata.searchText;

    var url_auto = "http://api.geonames.org/postalCodeSearchJSON?postalcode_startsWith="+searchText+"&username=vanisingala&country=US&maxRows=5"

    request(
      {

         uri:url_auto,

      },function(error,response,body){
         console.log(url_auto);

      res.setHeader('Content-Type','application/json');
      res.setHeader('Access-Control-Allow-Origin','*');
      console.log(body);
      res.send(body);


   });


  });


app.get('/similar_products', function (req, res,next) {



   res.setHeader("Content-Type","text/plain");

    
   res.setHeader("Access-Control-Allow-Origin","*");

    var q = url.parse(req.url,true);
    var ebay_api_key = 'VaniSing-CSCI571H-PRD-155bfe8c7-9e50ab1c';

   

    var qdata = q.query;

    var ItemId = qdata.ItemId;

    var url_link3 = "http://svcs.ebay.com/MerchandisingService?OPERATION-NAME=getSimilarItems&SERVICE-NAME=MerchandisingService&SERVICE-VERSION=1.1.0&CONSUMER-ID="+ebay_api_key+"&RESPONSE-DATA-FORMAT=JSON&REST-PAYLOAD&itemId="+ItemId+"&maxResults=20";


     request(
      {
    
         uri:url_link3,

      },function(error,response,body){
         console.log(url_link3);

      res.setHeader('Content-Type','application/json');
      res.setHeader('Access-Control-Allow-Origin','*');
      console.log(body);
      res.send(body);


   });

   });




app.get('/product_photos', function (req, res,next) {



   res.setHeader("Content-Type","text/plain");

    
   res.setHeader("Access-Control-Allow-Origin","*");

    var q = url.parse(req.url,true);
   

    var qdata = q.query;

    var product_title = encodeURIComponent(qdata.product_title);

    

    var photo_url = "https://www.googleapis.com/customsearch/v1?q="+product_title+"&cx=011572014524656636668:fuajcjgw75g&imgSize=medium&imgType=news&num=8&searchType=image&key=AIzaSyDV8_05oaa-iVMJ-_p2gOsr2Ymsw90EVBY";

    //var  photo_url = 'https://www.googleapis.com/customsearch/v1' +'?key=' + key + '&cx=' + cx + '&searchType=image&q=' + product_title;
console.log(photo_url);
     request(
      {
         json:true,
         uri:photo_url,

      },function(error,response,body){
         //console.log(photo_url);

      res.setHeader('Content-Type','application/json');
      res.setHeader('Access-Control-Allow-Origin','*');
      res.json(body);


   });
   });




app.listen(8080);

